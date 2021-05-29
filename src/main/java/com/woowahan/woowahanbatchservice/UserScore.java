package com.woowahan.woowahanbatchservice;

import java.io.Serializable;

public class UserScore implements Serializable {

    private String userId;

    private String name;

    private String password;

    private String rank;

    private String hidden;

    private int score;

    public UserScore() {
    }

    public UserScore(String userId, String name, String password, String rank, String hidden, int score) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.rank = rank;
        this.hidden = hidden;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
