package com.golomt.example.service.auth;

import com.golomt.example.constant.Constants;
import com.golomt.example.dto.ErrorDTO;
import com.golomt.example.dto.LoginDTO;
import com.golomt.example.dto.ResponseDTO;
import com.golomt.example.entity.User;
import com.golomt.example.exception.*;
import com.golomt.example.repository.UserRepository;
import com.golomt.example.security.JwtTokenProvider;
import com.golomt.example.service.utilities.ResponseService;
import com.golomt.example.service.utilities.ValidationService;
import com.golomt.example.utilities.LogUtilities;
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
     * do.Login
     **/

    public ResponseDTO doLogin(String username, String password) throws ValidationException, NotFoundException, RestrictionException {
        try {
            LogUtilities.info(this.getClass().getName(), "[srvc][auth][login][ini][" + username + "]");

            if (validationService.doValidatePassword(password)) {
                if (userRepository.existsByUsername(username)) {
                    if (authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).isAuthenticated())
                        return this.doGenerate(username);
                    else {
                        throw new RestrictionException("username or password doesnt match");
                    }
                } else {
                    throw new NotFoundException("user not found");
                }
            } else {
                throw new ValidationException("password length doesnt match");
            }
        } catch (ValidationException ex) {
            LogUtilities.warn(this.getClass().getName(), "[srvc][auth][login][validation][" + ex.getMessage() + "]");
            throw ex;
        } catch (NotFoundException ex) {
            LogUtilities.warn(this.getClass().getName(), "[srvc][auth][login][not.found][" + ex.getMessage() + "]");
            throw ex;
        } catch (RestrictionException ex) {
            LogUtilities.warn(this.getClass().getName(), "[srvc][auth][login][restriction][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[srvc][auth][login][unknown][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * do.Refresh
     **/

    public ResponseDTO doRefresh(HttpServletRequest req) throws NotFoundException {
        try {
            LogUtilities.info(this.getClass().getName(), "[srvc][auth][refresh][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            if (userRepository.existsByUsername(req.getRemoteUser())) {
                String token = jwtTokenProvider.createToken(req.getRemoteUser(), userRepository.findByUsername(req.getRemoteUser()).getRoles());

                LogUtilities.info(this.getClass().getName(), "[srvc][auth][refresh][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

                return new ResponseService(HttpStatus.OK.value(), null, new LoginDTO(token, userRepository.findByUsername(req.getRemoteUser()))).getResponse();
            } else {
                throw new NotFoundException("user not found");
            }
        } catch (NotFoundException ex) {
            LogUtilities.fatal(this.getClass().getName(), "[srvc][auth][refresh][not.found][" + ex.getMessage() + "]", ex);
            throw ex;
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[srvc][auth][refresh][unknown][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * do.Check.Who.Am.I
     **/

    public ResponseDTO doCheckWhoAmI(HttpServletRequest req) throws NotFoundException {
        try {
            LogUtilities.info(this.getClass().getName(), "[srvc][auth][who.am.i][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            if (userRepository.existsByUsername(req.getRemoteUser())) {

                LogUtilities.info(this.getClass().getName(), "[srvc][auth][who.am.i][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

                return new ResponseService(HttpStatus.OK.value(), null, userRepository.findByUsername(req.getRemoteUser())).getResponse();
            } else {
                throw new NotFoundException("user not found");
            }
        } catch (NotFoundException ex) {
            LogUtilities.fatal(this.getClass().getName(), "[srvc][auth][who.am.i][not.found][" + ex.getMessage() + "]", ex);
            throw ex;
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[srvc][auth][who.am.i][unknown][" + ex.getMessage() + "]", ex);
            throw ex;
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

    private ResponseDTO doGenerate(String username) {
        try {
            User user = userRepository.findByUsername(username);
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            LogUtilities.info(this.getClass().getName(), "[srvc][auth][generate.token][end][" + username + "][" + token + "][successful]");
            return new ResponseService(HttpStatus.OK.value(), null, new LoginDTO(token, user)).getResponse();
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[srvc][auth][generate.token][unknown][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

}