package com.woowahan.woowahanbatchservice;

public class UserScore {

    private String userId;

    private long score;

    public UserScore(String userId, long score) {
        this.userId = userId;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public long getScore() {
        return score;
    }
}
