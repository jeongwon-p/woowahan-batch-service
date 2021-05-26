package com.woowahan.woowahanbatchservice;

public class UserScore {

    private String userId;

    private int score;

    public UserScore() {
    }

    public UserScore(String userId, int score) {
        this.userId = userId;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
