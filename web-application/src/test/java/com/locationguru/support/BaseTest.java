package com.locationguru.support;

import config.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = Application.class)
public abstract class BaseTest
{
	private static final Logger logger = LogManager.getLogger(BaseTest.class);
}