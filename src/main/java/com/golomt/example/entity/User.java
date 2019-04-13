package com.golomt.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.golomt.example.dto.IGeneralDTO;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * User Entity @author Tushig
 */

@Entity
@Table(name = "USERS")
public class User implements IGeneralDTO {

    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "USER_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userSeq")
    @Column(name = "ID")
    private Integer id;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false, name = "USERNAME")
    private String username;

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(unique = true, nullable = false, name = "EMAIL")
    private String email;

    @Size(min = 8, message = "Minimum password length: 8 characters")
    @JsonIgnore
    private String password;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @ElementCollection(fetch = FetchType.EAGER)
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}