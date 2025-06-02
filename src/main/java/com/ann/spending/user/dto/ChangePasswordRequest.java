package com.ann.spending.user.dto;

public record ChangePasswordRequest(

        String currentPassword,
        String newPassword

) {

}
