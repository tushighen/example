package com.golomt.example.cron;

import com.golomt.example.constant.Constants;
import com.golomt.example.service.utilities.HelperService;
import com.golomt.example.utilities.LogUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Cron Job @author Tushig
 */

@Component
public class CronJob implements Constants {

    /**
     * Autowire
     **/

    @Autowired
    HelperService helperService;

    @Scheduled(cron = "*/10 * * * * *")
    public void doRunCron() {
        LogUtilities.info(this.getClass().getName(), "[cron.job][ini][" + helperService.getLocalDateTime() + "]");
        LogUtilities.info(this.getClass().getName(), "[cron.job][end][" + helperService.getLocalDateTime() + "]");
    }

}
