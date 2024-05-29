package com.app.util;

import com.app.dto.*;

public class RequestValidator {

    public static Boolean isSignUpRequestValid(SignupRequest signupRequest) {
        if(signupRequest == null) {
            return false;
        }
        return CommonUtil.isValueNotNullAndEmpty(signupRequest.getFirstName())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getEmail())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getPassword());
    }

    public static Boolean isLoginUpRequestValid(LoginRequest loginRequest) {
        if(loginRequest == null) {
            return false;
        }
        return CommonUtil.isValueNotNullAndEmpty(loginRequest.getUsername())
                && CommonUtil.isValueNotNullAndEmpty(loginRequest.getPassword());
    }


    public static boolean isBookAddRequestValid(BookDTO bookDTO) {
        try {
            if(bookDTO == null) {
                return false;
            }
            if(!CommonUtil.isValueNotNullAndEmpty(bookDTO.getTitle()) || !CommonUtil.isValueNotNullAndEmpty(bookDTO.getDescription())
                    || !CommonUtil.isValueNotNullAndEmpty(bookDTO.getAuthors()) ||
                    !CommonUtil.isValueNotNullAndEmpty(bookDTO.getCategories()) || !CommonUtil.isValueNotNullAndEmpty(bookDTO.getImage())
                    || !CommonUtil.isValueNotNullAndEmpty(bookDTO.getPreviewLink()) || !CommonUtil.isValueNotNullAndEmpty(bookDTO.getInfoLink())
                    || !CommonUtil.isValueNotNullAndEmpty(bookDTO.getRatingsCount()) || !CommonUtil.isValueNotNullAndEmpty(bookDTO.getPublishedDate())) {
                return false;
            }

            if(bookDTO.getRatingsCount() < 0) {
                return false;
            }

            if(!CommonUtil.isValueNotNullAndEmpty(bookDTO.getPublishedDate())) {
                CommonUtil.getDateFromString(bookDTO.getPublishedDate());
            }

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
