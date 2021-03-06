package com.woowahan.woowahanbatchservice.job;

import com.woowahan.woowahanbatchservice.domain.dao.ArticleRepository;
import com.woowahan.woowahanbatchservice.domain.dao.CommentRepository;
import com.woowahan.woowahanbatchservice.domain.dao.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RankingJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ArticleRepository articleDao;
    private final CommentRepository commentDao;
    private final UserRepository userDao;

    public RankingJobConfiguration(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory,
            ArticleRepository articleDao,
            CommentRepository commentDao,
            UserRepository userDao
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.articleDao = articleDao;
        this.commentDao = commentDao;
        this.userDao = userDao;
    }

    @Bean
    public Job rankingJob() {
        return jobBuilderFactory.get("rankingJob")
                .start(scoreAndRankStep())
                .build();
    }

    @Bean
    public Step scoreAndRankStep() {
        return stepBuilderFactory.get("scoreAndRankStep")
                .tasklet(scoreAndRankTasklet(articleDao, commentDao, userDao))
                .build();
    }

    @Bean
    public Tasklet scoreAndRankTasklet(ArticleRepository articleDao, CommentRepository commentDao, UserRepository userDao) {
        return new ScoreAndRankTasklet(articleDao, commentDao, userDao);
    }
}
