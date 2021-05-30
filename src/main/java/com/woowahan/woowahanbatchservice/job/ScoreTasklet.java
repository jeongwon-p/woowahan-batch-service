package com.woowahan.woowahanbatchservice.job;

import com.woowahan.woowahanbatchservice.UserScore;
import com.woowahan.woowahanbatchservice.domain.ArticleCount;
import com.woowahan.woowahanbatchservice.domain.CommentCount;
import com.woowahan.woowahanbatchservice.domain.dao.ArticleRepository;
import com.woowahan.woowahanbatchservice.domain.dao.CommentRepository;
import com.woowahan.woowahanbatchservice.domain.dao.UserRepository;
import com.woowahan.woowahanbatchservice.domain.entity.User;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ScoreTasklet implements Tasklet {

    private final ArticleRepository articleDao;
    private final CommentRepository commentDao;
    private final UserRepository userDao;

    public ScoreTasklet(
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
        LocalDateTime beginDateTime = LocalDate.now().atStartOfDay().minusDays(30);
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();

        Map<String, ArticleCount> articleCountByUserId = articleDao
                .getArticleCountGroupByUserIdBetween(beginDateTime, endDateTime).stream()
                .collect(Collectors.toMap(ArticleCount::getUserId, Function.identity()));
        Map<String, CommentCount> commentCountByUserId = commentDao
                .getCommentCountGroupByUserIdBetween(beginDateTime, endDateTime).stream()
                .collect(Collectors.toMap(CommentCount::getUserId, Function.identity()));

        List<UserScore> userScores = articleCountByUserId.keySet().stream()
                .map(userId -> new UserScore(
                        userId,
                        articleCountByUserId.get(userId).getArticleCount() * 10
                                + commentCountByUserId.get(userId).getCommentCount() * 5
                ))
                .sorted(Comparator.comparing(UserScore::getScore))
                .collect(Collectors.toList());

        List<User> scoredUser = userScores.stream()
                .map(user -> userDao.findById(user.getUserId()).get()
                        .updateScore(user.getScore()))
                .collect(Collectors.toList());
        userDao.saveAll(scoredUser);
        return RepeatStatus.FINISHED;
    }
}
