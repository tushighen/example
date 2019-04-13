package com.golomt.example.service;

import com.golomt.example.constant.Constants;
import com.golomt.example.dto.ResponseDTO;
import com.golomt.example.entity.Role;
import com.golomt.example.entity.User;
import com.golomt.example.exception.CustomException;
import com.golomt.example.exception.ValidationException;
import com.golomt.example.repository.UserRepository;
import com.golomt.example.security.JwtTokenProvider;
import com.golomt.example.service.utilities.HelperDTOService;
import com.golomt.example.service.utilities.HelperService;
import com.golomt.example.service.utilities.ResponseService;
import com.golomt.example.utilities.LogUtilities;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.swing.table.TableRowSorter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements Constants {

    /**
     * Autowire
     **/

    @Autowired
    UserRepository repository;

    @Autowired
    HelperService helperService;

    @Autowired
    HelperDTOService<User> helperDTOService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * do.Save.User
     **/

    public void doSaveUser(User user) {
        LogUtilities.info(this.getClass().getName(), "[srvc][user.save][ini][" + user.getUsername() + "]");

        if (!repository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        LogUtilities.info(this.getClass().getName(), "[srvc][user.save][end][" + user.getUsername() + "]");
    }

    /**
     * do.Inquire.User
     **/

    public void doInquireUser(String userName, HttpServletRequest req) {

    }

    /**
     * do.Inquire.Users
     *
     * @param page      page number
     * @param size      page size
     * @param key       sort key /Table column/
     * @param direction sort direction /ex: ASC or DESC/
     * @param role      sort role /User Role/
     * @param value     sort value /Search value/
     * @return @{@link ResponseDTO}
     **/

    public ResponseDTO doInquireUsers(Integer page, Integer size, String key, String direction, String role, String value, HttpServletRequest req) throws ValidationException {
        LogUtilities.info(this.getClass().getName(), "[srvc][user.inq.page][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

        try {
            if (page != null && size != null) {
                Page<User> pagination;
                role = req.isUserInRole(Roles.ADMIN) ? role : req.isUserInRole(Roles.SUPERIOR) && Roles.SUPERIOR.equals(role) ? "" : role;
                List<Role> roles = StringUtils.isNotBlank(role) ? Arrays.asList(Role.valueOf(role)) : req.isUserInRole(Roles.ADMIN) ? Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_SUPERIOR, Role.ROLE_AGENT, Role.ROLE_COMPLAIN, Role.ROLE_CLIENT) : Arrays.asList(Role.ROLE_AGENT, Role.ROLE_CLIENT);

                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(direction)) {
                    pagination = repository.findAllByRolesInAndUsernameNotAndUsernameContains(roles, req.getRemoteUser(), value.toUpperCase(), PageRequest.of(page, size, direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, key));
                } else {
                    pagination = repository.findAllByRolesInAndUsernameNotAndUsernameContains(roles, req.getRemoteUser(), value.toUpperCase(), PageRequest.of(page, size));
                }

                LogUtilities.info(this.getClass().getName(), "[srvc][user.inq.page][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

                return new ResponseService(HttpStatus.OK.value(), null, helperDTOService.getPaginationDTO(pagination)).getResponse();
            } else {
                throw new ValidationException("mandatory parameters missing: [" + ((page != null ? "page, " : "") + (size != null ? "size" : "")) + "]");
            }
        } catch (ValidationException ex) {
            LogUtilities.warn(this.getClass().getName(), "[srvc][user.inq.page][validation][ " + ex.getMessage() + "][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
            throw ex;
        } catch (Exception ex) {
            LogUtilities.error(this.getClass().getName(), "[srvc][user.inq.page][unknown][ " + ex.getMessage() + "][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
            throw ex;
        }
    }

    /**
     * do.Modify.User.Role
     **/

    public void doModifyUserRole(List<User> users, HttpServletRequest req) throws ValidationException {
        LogUtilities.info(this.getClass().getName(), "[srvc][user.modify.role][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

        try {
            if (users.size() > 0) {
                for (User user : users) {
                    List<Role> roles = user.getRoles();
                    user = repository.findByUsername(user.getUsername());
                    user.setRoles(roles);
                    user.setUpdateUser(req.getRemoteUser());
                    user.setUpdateDate(new Date());
                    repository.save(user);
                }
            } else {
                throw new ValidationException("no.users");
            }

            LogUtilities.info(this.getClass().getName(), "[srvc][user.modify.role][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        } catch (ValidationException ex) {
            LogUtilities.warn(this.getClass().getName(), "[srvc][user.modify.role][validation][ " + ex.getMessage() + "][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
            throw ex;
        } catch (Exception ex) {
            LogUtilities.error(this.getClass().getName(), "[srvc][user.modify.role][unknown][ " + ex.getMessage() + "][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
            throw ex;
        }
    }

}
