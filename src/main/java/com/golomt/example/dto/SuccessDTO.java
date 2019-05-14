package com.golomt.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/**
 * Success DTO @author Tushig
 */

public class SuccessDTO implements IGeneralDTO {

    @ApiModelProperty()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    /**
     * Getter.Setter
     **/

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
