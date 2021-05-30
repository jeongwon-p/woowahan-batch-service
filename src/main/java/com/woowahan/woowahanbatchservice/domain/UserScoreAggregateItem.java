package com.woowahan.woowahanbatchservice.domain;

import java.math.BigDecimal;

public interface UserScoreAggregateItem {
    String getUserId();

    BigDecimal baseCount();
}
