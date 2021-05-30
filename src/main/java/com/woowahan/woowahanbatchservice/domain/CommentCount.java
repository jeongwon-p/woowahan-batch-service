package com.woowahan.woowahanbatchservice.domain;

import java.math.BigDecimal;

public class CommentCount implements UserScoreAggregateItem {

    private final String userId;

    private final long commentCount;

    public CommentCount(String userId, long commentCount) {
        this.userId = userId;
        this.commentCount = commentCount;
    }

    @Override
    public BigDecimal baseCount() {
        return BigDecimal.valueOf(commentCount);
    }

    @Override
    public String getUserId() {
        return userId;
    }
}
