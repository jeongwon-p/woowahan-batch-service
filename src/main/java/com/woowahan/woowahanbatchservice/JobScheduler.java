package com.woowahan.woowahanbatchservice;

import com.woowahan.woowahanbatchservice.job.RankingJobConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JobScheduler {

    private final Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    private final JobLauncher jobLauncher;
    private final RankingJobConfiguration rankingJobConfiguration;

    public JobScheduler(JobLauncher jobLauncher, RankingJobConfiguration rankingJobConfiguration) {
        this.jobLauncher = jobLauncher;
        this.rankingJobConfiguration = rankingJobConfiguration;
    }

    // 매일 새벽 1시 job 실행
    @Scheduled(cron = "0 0 01 * * ?")
    public void runJob() {

        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(rankingJobConfiguration.rankingJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            logger.error(e.getMessage());
        }
    }
}
