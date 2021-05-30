package com.woowahan.woowahanbatchservice.domain.dao;

import com.woowahan.woowahanbatchservice.domain.ArticleCount;
import com.woowahan.woowahanbatchservice.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    @Query(
            " select new com.woowahan.woowahanbatchservice.domain.ArticleCount(a.userId, count(a)) " +
            "   from Article a " +
            "  where a.createDateTime >= :beginDateTime " +
            "    and a.createDateTime < :endDateTime " +
            "  group by a.userId ")
    List<ArticleCount> getArticleCountGroupByUserIdBetween(LocalDateTime beginDateTime, LocalDateTime endDateTime);
}
