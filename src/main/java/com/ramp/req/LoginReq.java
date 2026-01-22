package com.ramp.req;

import javax.validation.constraints.NotBlank;

public class LoginReq {

    @NotBlank(message = "Username or Email is required")
    private String login;

    @NotBlank(message = "Password is required")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginReq [login=" + login + "]";
        // ❌ never log password
    }
}
