package com.woowahan.woowahanbatchservice.domain;

public class CommentCount {

    private String userId;

    private long commentCount;

    public CommentCount(String userId, long commentCount) {
        this.userId = userId;
        this.commentCount = commentCount;
    }

    public String getUserId() {
        return userId;
    }

    public long getCommentCount() {
        return commentCount;
    }
}
