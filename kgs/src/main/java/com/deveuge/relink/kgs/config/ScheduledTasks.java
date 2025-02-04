package com.deveuge.relink.kgs.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.deveuge.relink.kgs.KeyGenerator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledTasks {

	@Autowired
	KeyGenerator keyGenerator;

	@Value("${kgs.scheduling.active}")
	boolean isSchedulingActive;

	@Scheduled(initialDelayString = "${kgs.scheduling.delay.initial}", fixedDelayString = "${kgs.scheduling.delay.fixed}", timeUnit = TimeUnit.SECONDS)
	public void initKeyGeneration() {
		if (isSchedulingActive) {
			log.info("Initialising the key generation process");
			keyGenerator.generateKeys();
		}
	}
}