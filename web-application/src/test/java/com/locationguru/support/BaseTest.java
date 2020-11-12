package com.locationguru.support;

import config.TestApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = { "common", "test" })
@SpringBootTest(classes = TestApplication.class)
public abstract class BaseTest
{
	private static final Logger logger = LogManager.getLogger(BaseTest.class);
}