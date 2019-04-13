package com.golomt.example.service.utilities;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Validation Service @author Tushig
 */

@Component
public class ValidationService {

    public boolean doValidatePassword(String password) {
        return StringUtils.isNotBlank(password) && password.length() > 4;
    }

}
