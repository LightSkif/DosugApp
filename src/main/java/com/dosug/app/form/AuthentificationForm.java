package com.dosug.app.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by radmir on 24.03.17.
 */


public class AuthentificationForm {

    @NotNull
    @Size(max = 256)
    private String username;


    @NotNull
    @Size(max = 256)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
