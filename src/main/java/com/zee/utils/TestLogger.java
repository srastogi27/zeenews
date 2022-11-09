package com.zee.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger {

	private static final Logger s_logger = LoggerFactory.getLogger(TestLogger.class);

	private static TestLogger testLogger;

	public static TestLogger getInstance() {
		if (testLogger == null)
			testLogger = new TestLogger();
		return testLogger;
	}

	public synchronized void log(String step) {
		s_logger.info("Executing : {}", step);
	}

	public synchronized void info(String step) {
		s_logger.info("Information : {}", "\t" + step);
	}

	public synchronized void error(String step) {
		s_logger.error("Error : {}", step);
	}

}
