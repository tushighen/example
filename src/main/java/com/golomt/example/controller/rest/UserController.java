package com.golomt.example.controller.rest;

import com.golomt.example.constant.Constants;
import com.golomt.example.dto.ErrorDTO;
import com.golomt.example.dto.ResponseDTO;
import com.golomt.example.entity.User;
import com.golomt.example.exception.ValidationException;
import com.golomt.example.security.JwtTokenProvider;
import com.golomt.example.service.UserService;
import com.golomt.example.service.utilities.ResponseService;
import com.golomt.example.utilities.LogUtilities;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "USER")
public class UserController implements Constants {

    /**
     * Autowire
     **/

    @Autowired
    UserService service;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    /**
     * do.Inquire.Users
     *
     * @param page      page number
     * @param size      page size
     * @param key       sort key /Table column/
     * @param direction sort direction /ex: ASC or DESC/
     * @param role      sort role /User Role/
     * @param value     sort value /Search value/
     * @return @{@link com.golomt.example.dto.ResponseDTO}
     **/

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERIOR')")
    public ResponseDTO doInquireUsers(@ApiParam("page") @RequestParam Integer page, @ApiParam("size") @RequestParam Integer size,
                                      @ApiParam("key") @RequestParam String key, @ApiParam("direction") @RequestParam String direction,
                                      @ApiParam("role") @RequestParam String role, @ApiParam("value") @RequestParam String value,
                                      HttpServletRequest req) {

        try {
            LogUtilities.info(this.getClass().getName(), "[ctrl][user.inq.page][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            ResponseDTO responseDTO = service.doInquireUsers(page, size, key, direction, role, value, req);

            LogUtilities.info(this.getClass().getName(), "[ctrl][user.inq.page][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            return responseDTO;
        } catch (ValidationException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][user.inq.page][validation][ " + ex.getMessage() + "][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
            return new ResponseService(HttpStatus.BAD_REQUEST.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.VALIDATION)).getError();
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[ctrl][user.inq.page][unknown][ " + ex.getMessage() + "][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]", ex);
            return new ResponseService(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ErrorDTO(null, ErrorDesc.CONTACT_ADMIN, ErrorType.UNKNOWN)).getError();
        }

    }

    /**
     * do.Modify.User.Role
     **/

    @RequestMapping(value = "roles/update", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERIOR')")
    public ResponseDTO doModifyUserRole(@RequestBody List<User> users, HttpServletRequest req) {
        try {
            LogUtilities.info(this.getClass().getName(), "[ctrl][user.modify.role][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            service.doModifyUserRole(users, req);

            LogUtilities.info(this.getClass().getName(), "[ctrl][user.modify.role][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            return new ResponseService(HttpStatus.OK.value(), null, null).getResponse();
        } catch (ValidationException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][user.modify.role][validation][ " + ex.getMessage() + "][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
            return new ResponseService(HttpStatus.BAD_REQUEST.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.VALIDATION)).getError();
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[ctrl][user.modify.role][unknown][ " + ex.getMessage() + "][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]", ex);
            return new ResponseService(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ErrorDTO(null, ErrorDesc.CONTACT_ADMIN, ErrorType.UNKNOWN)).getError();
        }
    }

}