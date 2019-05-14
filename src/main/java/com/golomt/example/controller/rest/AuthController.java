package com.golomt.example.controller.rest;

import com.golomt.example.constant.Constants;
import com.golomt.example.dto.ErrorDTO;
import com.golomt.example.dto.ResponseDTO;
import com.golomt.example.exception.NotFoundException;
import com.golomt.example.exception.RestrictionException;
import com.golomt.example.exception.ValidationException;
import com.golomt.example.security.JwtTokenProvider;
import com.golomt.example.service.auth.AuthService;
import com.golomt.example.service.utilities.ResponseService;
import com.golomt.example.utilities.LogUtilities;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Authentication Controller @author Tushig
 */

@RestController
@RequestMapping("/auth}")
@Api(tags = "AUTHENTICATION")
public class AuthController implements Constants {

    /**
     * Autowire
     **/

    @Autowired
    AuthService service;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    /**
     * do.Login
     **/

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseDTO doLogin(@RequestBody HashMap<String, String> map) {
        try {

            LogUtilities.info(this.getClass().getName(), "[ctrl][auth][login][ini][" + map.get("username") + "]");

            ResponseDTO responseDTO = service.doLogin(map.get("username"), map.get("password"));

            LogUtilities.info(this.getClass().getName(), "[ctrl][auth][login][end][" + map.get("username") + "]");

            return responseDTO;

        } catch (ValidationException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][auth][login][validation][ " + ex.getMessage() + "]");
            return new ResponseService(HttpStatus.BAD_REQUEST.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.VALIDATION)).getError();
        } catch (NotFoundException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][auth][login][not.found][ " + ex.getMessage() + "]");
            return new ResponseService(HttpStatus.NOT_FOUND.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.NOT_FOUND)).getError();
        } catch (RestrictionException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][auth][login][restriction][ " + ex.getMessage() + "]");
            return new ResponseService(HttpStatus.BAD_REQUEST.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.RESTRICTION)).getError();
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[ctrl][auth][login][unknown][ " + ex.getMessage() + "]", ex);
            return new ResponseService(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.UNKNOWN)).getError();
        }
    }

    /**
     * do.Refresh
     **/

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERIOR') or hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT') or hasRole('ROLE_COMPLAIN')")
    public ResponseDTO doRefresh(HttpServletRequest req) {
        try {
            LogUtilities.info(this.getClass().getName(), "[ctrl][auth][refresh][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            ResponseDTO responseDTO = service.doRefresh(req);

            LogUtilities.info(this.getClass().getName(), "[ctrl][auth][refresh][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            return responseDTO;

        } catch (NotFoundException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][auth][login][not.found][ " + ex.getMessage() + "]");
            return new ResponseService(HttpStatus.NOT_FOUND.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.NOT_FOUND)).getError();
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[ctrl][auth][refresh][unknown][ " + ex.getMessage() + "]", ex);
            return new ResponseService(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.UNKNOWN)).getError();
        }
    }

    /**
     * do.Check.Who.Am.I
     **/

    @RequestMapping(value = "check", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERIOR') or hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT') or hasRole('ROLE_COMPLAIN')")
    public ResponseDTO doCheckWhoAmI(HttpServletRequest req) {
        try {
            LogUtilities.info(this.getClass().getName(), "[ctrl][auth][who.am.i][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            ResponseDTO responseDTO = service.doCheckWhoAmI(req);

            LogUtilities.info(this.getClass().getName(), "[ctrl][auth][who.am.i][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

            return responseDTO;

        } catch (NotFoundException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][auth][who.am.i][not.found][ " + ex.getMessage() + "]");
            return new ResponseService(HttpStatus.NOT_FOUND.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.NOT_FOUND)).getError();
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[ctrl][auth][who.am.i][unknown][ " + ex.getMessage() + "]", ex);
            return new ResponseService(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.UNKNOWN)).getError();
        }
    }

    /**
     * do.Logout
     **/

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERIOR') or hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT') or hasRole('ROLE_COMPLAIN')")
    public void doLogout(HttpServletRequest req) {

        LogUtilities.info(this.getClass().getName(), "[ctrl][auth][logout][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        service.doLogout(req);

        LogUtilities.info(this.getClass().getName(), "[ctrl][auth][logout][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");

    }

}