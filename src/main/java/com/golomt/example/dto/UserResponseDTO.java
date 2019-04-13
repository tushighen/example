package com.golomt.example.dto;

import com.golomt.example.entity.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * User Response DTO @author Tushig
 */

public class UserResponseDTO implements IGeneralDTO {

    @ApiModelProperty()
    private Integer id;

    @ApiModelProperty(position = 1)
    private String username;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String email;

    @ApiModelProperty(position = 4)
    private List<Role> roles;

    /**
     * Getter.Setter
     **/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
