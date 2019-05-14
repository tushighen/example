package com.golomt.example.controller.rest;


import com.golomt.example.exception.RMIException;
import com.golomt.example.service.MatchService;
import com.golomt.example.utilities.LogUtilities;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Match Controller @author Tushig
 */

@RestController
@RequestMapping("api/match")
@Api(tags = "MATCH")
public class MatchController {

    /**
     * Autowire
     **/

    @Autowired
    MatchService service;

    /**
     * do.Inquire.Match
     **/

    @RequestMapping(value = "inquire", method = RequestMethod.GET)
    public void doInquireMatch() {
        try {
            LogUtilities.info(this.getClass().getName(), "[ctrl][match][inq][ini]");
            service.doInquireMatch();
            LogUtilities.info(this.getClass().getName(), "[ctrl][match][inq][ini]");
        } catch (RMIException ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][match][inq][rmi][ " + ex.getMessage() + "]");
        } catch (Exception ex) {
            LogUtilities.warn(this.getClass().getName(), "[ctrl][match][inq][unknown][ " + ex.getMessage() + "]");
        }
    }

}