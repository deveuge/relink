package com.deveuge.relink.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deveuge.relink.api.LinkCleaner;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledTasks {

	@Autowired
	private LinkCleaner linkCleaner;

	@Value("${scheduling.expired.links.active}")
	boolean isSchedulingActive;

	@Scheduled(cron = "${scheduling.expired.links.cron}")
	public void initCleanExpiredLinks() {
		if (isSchedulingActive) {
			log.info("Expired links cleaning process: INITIALIZED");
			linkCleaner.execute();
			log.info("Expired links cleaning process: ENDED");
		}
	}
}
