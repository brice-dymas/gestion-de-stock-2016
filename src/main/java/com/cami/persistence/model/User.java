/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Entity
@Table(name = "users")
public class User implements Serializable
{

    @NotBlank(message = "{blank.message}")
    private String nom;

    @NotBlank(message = "{blank.message}")
    @Email
    private String email;

    @Id
    @Column(name = "username", length = 45, nullable = false)
    @NotBlank(message = "{blank.message}")
    private String username;

    @NotBlank(message = "{blank.message}")
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    public User()
    {
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "User{" + "nom=" + nom + ", email=" + email + ", username=" + username + ", password=" + password + ", confirmPassword=" + confirmPassword + ", enabled=" + enabled + '}';
    }

}
