package com.golomt.example.service.auth;

import com.golomt.example.constant.Constants;
import com.golomt.example.dto.ErrorDTO;
import com.golomt.example.dto.LoginDTO;
import com.golomt.example.dto.ResponseDTO;
import com.golomt.example.dto.UserResponseDTO;
import com.golomt.example.entity.User;
import com.golomt.example.exception.CustomException;
import com.golomt.example.exception.NotFoundException;
import com.golomt.example.exception.ValidationException;
import com.golomt.example.repository.UserRepository;
import com.golomt.example.security.JwtTokenProvider;
import com.golomt.example.service.utilities.ResponseService;
import com.golomt.example.service.utilities.ValidationService;
import com.golomt.example.utilities.LogUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Auth Service @author Tushig
 */

@Service
public class AuthService implements Constants {

    /**
     * Autowire
     **/

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ValidationService validationService;

    /**
     * Mapper
     **/

    private ModelMapper modelMapper;

    /**
     * DTO
     **/

    private LoginDTO loginDTO;

    private ErrorDTO errorDTO;

    /**
     * do.Login
     **/

    public ResponseDTO doLogin(String userName, String password) throws ValidationException, NotFoundException {
        LogUtilities.info(this.getClass().getName(), "[srvc][auth][login][ini][" + userName.toUpperCase() + "]");
        try {
            if (validationService.doValidatePassword(password)) {
                if (userRepository.existsByUsername(userName)) {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
                    return this.doGenerate(userName);
                } else {
                    throw new NotFoundException("user.not.found");
                }
            } else {
                throw new ValidationException("password.length.doesnt.match");
            }
        } catch (ValidationException ex) {
            LogUtilities.warn(this.getClass().getName(), "[srvc][auth][login][validation][" + ex.getMessage() + "]");
            throw ex;
        } catch (NotFoundException ex) {
            LogUtilities.warn(this.getClass().getName(), "[srvc][auth][login][not.found][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[srvc][auth][login][unknown][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * do.Refresh
     **/

    public ResponseDTO doRefresh(HttpServletRequest req) {
        LogUtilities.info(this.getClass().getName(), "[srvc][auth][refresh][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        String token = jwtTokenProvider.createToken(req.getRemoteUser(), userRepository.findByUsername(req.getRemoteUser()).getRoles());
        this.doValueClean();
        this.getLoginDTO().setToken(token);
        this.getLoginDTO().setUser(this.getModelMapper().map(userRepository.findByUsername(req.getRemoteUser()), UserResponseDTO.class));
        LogUtilities.info(this.getClass().getName(), "[srvc][auth][refresh][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        return new ResponseService(HttpStatus.OK.value(), null, this.getLoginDTO()).getResponse();
    }

    /**
     * do.Check.Who.Am.I
     **/

    public ResponseDTO doCheckWhoAmI(HttpServletRequest req) {
        LogUtilities.info(this.getClass().getName(), "[srvc][auth][who.am.i][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        User user = userRepository.findByUsername(req.getRemoteUser());
        if (user != null) {
            LogUtilities.info(this.getClass().getName(), "[srvc][auth][who.am.i][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
            return new ResponseService(HttpStatus.OK.value(), null, this.getModelMapper().map(user, UserResponseDTO.class)).getResponse();
        } else {
            LogUtilities.info(this.getClass().getName(), "[srvc][auth][who.am.i][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
            return new ResponseService(HttpStatus.NOT_FOUND.value(), null, new ErrorDTO(null, Desc.USER_NOT_FOUND)).getError();
        }
    }

    /**
     * do.Logout
     **/

    public void doLogout(HttpServletRequest req) {
        LogUtilities.info(this.getClass().getName(), "[srvc][auth][logout][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        LogUtilities.info(this.getClass().getName(), "[srvc][auth][logout][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
    }

    /**
     * do.Sign.Up
     **/

    public ResponseDTO doSignUp(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return this.doGenerate(user.getUsername());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * do.Generate.Token
     **/

    private ResponseDTO doGenerate(String userName) {
        User user = userRepository.findByUsername(userName);
        String token = jwtTokenProvider.createToken(userName, user.getRoles());
        this.doValueClean();
        this.getLoginDTO().setToken(token);
        this.getLoginDTO().setUser(this.getModelMapper().map(userRepository.findByUsername(userName), UserResponseDTO.class));
        LogUtilities.info(this.getClass().getName(), "[srvc][auth][login][end][" + userName.toUpperCase() + "][" + token + "][successful]");
        return new ResponseService(HttpStatus.OK.value(), null, this.getLoginDTO()).getResponse();
    }

    /**
     * Invalid.Credentials.Expired
     **/

    private ResponseDTO error(String userName) {
        this.doValueClean();
        this.getErrorDTO().setErrorDesc(Desc.USERNAME_PASSWORD_INVALID);

        LogUtilities.info(this.getClass().getName(), "[srvc][auth][login][end][" + userName.toUpperCase() + "][unsuccessful]");
        return new ResponseService(HttpStatus.UNAUTHORIZED.value(), null, this.getErrorDTO()).getError();
    }

    /**
     * Getter.Setter
     **/

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    /**
     * do.Clean.Value
     **/

    private void doValueClean() {
        this.setLoginDTO(null);
        this.setErrorDTO(null);
    }
}