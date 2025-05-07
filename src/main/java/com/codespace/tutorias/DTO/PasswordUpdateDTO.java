package com.codespace.tutorias.DTO;

import jakarta.validation.constraints.NotBlank;

public class PasswordUpdateDTO {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;

    public PasswordUpdateDTO() {}

    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
