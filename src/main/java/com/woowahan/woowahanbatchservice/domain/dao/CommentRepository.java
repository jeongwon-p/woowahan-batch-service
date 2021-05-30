package com.woowahan.woowahanbatchservice.domain.dao;

import com.woowahan.woowahanbatchservice.domain.CommentCount;
import com.woowahan.woowahanbatchservice.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    @Query(
            " select new com.woowahan.woowahanbatchservice.domain.CommentCount(a.userId, count(a)) " +
                    "from Comment a " +
                    "where a.createDateTime >= :beginDateTime " +
                    "and a.createDateTime < :endDateTime " +
                    "group by a.userId ")
    List<CommentCount> getCommentCountGroupByUserIdBetween(LocalDateTime beginDateTime, LocalDateTime endDateTime);
}
