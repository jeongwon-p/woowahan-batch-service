package com.woowahan.woowahanbatchservice.job;

import com.woowahan.woowahanbatchservice.domain.UserScoreAggregateItem;
import com.woowahan.woowahanbatchservice.domain.dao.ArticleRepository;
import com.woowahan.woowahanbatchservice.domain.dao.CommentRepository;
import com.woowahan.woowahanbatchservice.domain.dao.UserRepository;
import com.woowahan.woowahanbatchservice.domain.entity.User;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreAndRankTasklet implements Tasklet {

    private final static BigDecimal ARTICLE_WEIGHT = new BigDecimal(10);
    private final static BigDecimal COMMENT_WEIGHT = new BigDecimal(5);

    private final ArticleRepository articleDao;
    private final CommentRepository commentDao;
    private final UserRepository userDao;

    public ScoreAndRankTasklet(
            ArticleRepository articleDao,
            CommentRepository commentDao,
            UserRepository userDao
    ) {
        this.articleDao = articleDao;
        this.commentDao = commentDao;
        this.userDao = userDao;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime beginDateTime = endDateTime.minusDays(30);

        Map<String, BigDecimal> articleScoreByUserId = this.groupingScoreItems(articleDao
                .getArticleCountGroupByUserIdBetween(beginDateTime, endDateTime), ARTICLE_WEIGHT);
        Map<String, BigDecimal> commentScoreByUserId = this.groupingScoreItems(commentDao
                .getCommentCountGroupByUserIdBetween(beginDateTime, endDateTime), COMMENT_WEIGHT);

        List<User> scoredUsers = userDao.findAll().stream()
                .map(user -> user.updateScore(
                        articleScoreByUserId.getOrDefault(user.getEmailId(), BigDecimal.ZERO)
                                .add(commentScoreByUserId.getOrDefault(user.getEmailId(), BigDecimal.ZERO))
                ))
                .sorted(Comparator.comparing(User::getScore).reversed())
                .collect(Collectors.toList());

        userDao.saveAll(this.rankUser(scoredUsers));
        return RepeatStatus.FINISHED;
    }

    private <T extends UserScoreAggregateItem> Map<String, BigDecimal> groupingScoreItems(Collection<T> items, BigDecimal scoreWeight) {
        return items.stream()
                .collect(Collectors.toMap(
                        UserScoreAggregateItem::getUserId,
                        o -> o.baseCount().multiply(scoreWeight)));
    }

    private List<User> rankUser(List<User> users){
        List<User> rankedUsers = new ArrayList<>();
        for (int i = 0, rank = 1; i < users.size(); i++) {
            if (i != 0
                    && users.get(i).getScore() != users.get(i - 1).getScore()) {
                rank++;
            }
            rankedUsers.add(users.get(i).updateRank(rank));
        }
        return rankedUsers;
    }
}
