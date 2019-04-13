package com.golomt.example.controller.rest;

import com.golomt.example.constant.Constants;
import com.golomt.example.dto.ErrorDTO;
import com.golomt.example.dto.ResponseDTO;
import com.golomt.example.exception.ValidationException;
import com.golomt.example.security.JwtTokenProvider;
import com.golomt.example.service.auth.AuthService;
import com.golomt.example.service.utilities.ResponseService;
import com.golomt.example.utilities.LogUtilities;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseDTO doLogin(@ApiParam("Username") @RequestParam String userName,
                               @ApiParam("Password") @RequestParam String password) {
        try {
            LogUtilities.info(this.getClass().getName(), "[ctrl][auth][login][ini][" + userName + "]");
            ResponseDTO responseDTO = service.doLogin(userName, password);
            LogUtilities.info(this.getClass().getName(), "[ctrl][auth][login][end][" + userName + "]");
            return responseDTO;
        } catch (ValidationException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][auth][login][validation][ " + ex.getMessage() + "]");
            return new ResponseService(HttpStatus.BAD_REQUEST.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.VALIDATION)).getError();
        } catch (Exception ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][auth][login][unknown][ " + ex.getMessage() + "]");
            return new ResponseService(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ErrorDTO(null, ex.getMessage(), ErrorType.UNKNOWN)).getError();
        }
    }

    /**
     * do.Refresh
     **/

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERIOR') or hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT') or hasRole('ROLE_COMPLAIN')")
    public ResponseDTO doRefresh(HttpServletRequest req) {
        LogUtilities.info(this.getClass().getName(), "[ctrl][auth][refresh][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        ResponseDTO responseDTO = service.doRefresh(req);
        LogUtilities.info(this.getClass().getName(), "[ctrl][auth][refresh][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        return responseDTO;
    }

    /**
     * do.Check.Who.Am.I
     **/

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERIOR') or hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT') or hasRole('ROLE_COMPLAIN')")
    public ResponseDTO doCheckWhoAmI(HttpServletRequest req) {
        LogUtilities.info(this.getClass().getName(), "[ctrl][auth][who.am.i][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        ResponseDTO responseDTO = service.doCheckWhoAmI(req);
        LogUtilities.info(this.getClass().getName(), "[ctrl][auth][whi.am.i][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        return responseDTO;
    }

    /**
     * do.Logout
     **/

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERIOR') or hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT') or hasRole('ROLE_COMPLAIN')")
    public void doLogout(HttpServletRequest req) {
        LogUtilities.info(this.getClass().getName(), "[ctrl][auth][logout][ini][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
        service.doLogout(req);
        LogUtilities.info(this.getClass().getName(), "[ctrl][auth][logout][end][" + req.getRemoteUser() + "][" + jwtTokenProvider.resolveToken(req) + "]");
    }

}