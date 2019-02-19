package com.shk.entity;

import lombok.Data;

@Data
public class MbUser extends BaseEntity {
    private String username;
    private String password;
    private String phone;
    private String email;
}
