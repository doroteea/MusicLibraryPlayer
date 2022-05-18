package com.lab4.demo.batch;

import com.lab4.demo.track.model.TrackBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			jdbcTemplate.query("SELECT id, artist, album,duration,explicit_lyrics,link,preview,title FROM tracks",
				(rs, row) -> new TrackBatch(
					//rs.getLong(1),
						1450317612L,
					rs.getString(2),
					rs.getString(3),
					rs.getInt(4),
					rs.getBoolean(5),
					rs.getString(6),
					rs.getString(7),
					rs.getString(8))
			).forEach(trackBatch -> log.info("Found <" + trackBatch + "> in the database."));
		}
	}
}