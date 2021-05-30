package com.woowahan.woowahanbatchservice.domain;

import java.math.BigDecimal;

public class ArticleCount implements UserScoreAggregateItem{

    private final String userId;

    private final long articleCount;

    public ArticleCount(String userId, long articleCount) {
        this.userId = userId;
        this.articleCount = articleCount;
    }

    @Override
    public BigDecimal baseCount() {
        return BigDecimal.valueOf(articleCount);
    }

    @Override
    public String getUserId() {
        return userId;
    }
}
