package com.william.news.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userconnection")
public class UserConnection implements Serializable {

    @Id
    @Column(name = "userid")
    private String userId;
    @Id
    @Column(name = "providerid")
    private String providerId;
    @Id
    @Column(name = "provideruserid")
    private String providerUserId;
    private int rank;
    @Column(name = "displayname")
    private String displayName;
    @Column(name = "profileurl")
    private String profileUrl;
    @Column(name = "imageurl")
    private String imageUrl;
    @Column(name = "accesstoken")
    private String accessToken;
    private String secret;
    @Column(name = "refreshtoken")
    private String refreshToken;
    @Column(name = "expiretime")
    private long expireTime;
}
