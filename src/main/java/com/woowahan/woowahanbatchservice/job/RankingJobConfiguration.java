package com.woowahan.woowahanbatchservice.job;

import com.woowahan.woowahanbatchservice.UserScore;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class RankingJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 10;

    public RankingJobConfiguration(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory,
            DataSource dataSource
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public Job rankingJob() {
        return jobBuilderFactory.get("rankingJob")
                .start(scoreArticleStep())
                .next(scoreCommentStep())
                .next(rankingStep())
                .build();
    }

    @Bean
    public Step scoreArticleStep() {
        return stepBuilderFactory.get("scoreArticleStep")
                .<UserScore, UserScore>chunk(chunkSize)
                .reader(jdbcCursorItemReader())
                .writer(jdbcCursorItemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<UserScore> jdbcCursorItemReader() {
        return new JdbcCursorItemReaderBuilder<UserScore>()
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(UserScore.class))
                .sql("select user_id as userId, count(*) * 5 as score" +
                        " from article" +
                        " where crt_tm" +
                        " between date_add(date_format(now(), '%Y-%m-%d'), interval-30 day)" +
                        " and date_format(now(), '%Y-%m-%d')" +
                        "group by user_id")
                .name("jdbcCursorItemReader")
                .build();
    }

    private ItemWriter<UserScore> jdbcCursorItemWriter() {
        return new JdbcBatchItemWriterBuilder<UserScore>()
                .dataSource(dataSource)
                .sql("update user set score = :score where email_id = :userId")
                .beanMapped()
                .build();
    }

    @Bean
    public Step scoreCommentStep() {
        return stepBuilderFactory.get("scoreCommentStep")
                .<UserScore, UserScore>chunk(chunkSize)
                .reader(jdbcCursorItemReader())
                .writer(jdbcCursorItemWriter())
                .build();
    }

    @Bean
    public Step rankingStep() {
        return stepBuilderFactory.get("rankingStep")
                .tasklet((contribution, chunkContext) -> {
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}