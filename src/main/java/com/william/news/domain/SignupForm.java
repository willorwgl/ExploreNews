package com.william.news.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupForm {

    private String username;

    private String password;

    private String confirmPassword;

}
