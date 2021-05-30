package com.woowahan.woowahanbatchservice.domain;

public class ArticleCount {

    private String userId;

    private long articleCount;

    public ArticleCount(String userId, long articleCount) {
        this.userId = userId;
        this.articleCount = articleCount;
    }

    public String getUserId() {
        return userId;
    }

    public long getArticleCount() {
        return articleCount;
    }
}
