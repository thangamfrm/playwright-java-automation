package com.thangamfrm.playwrightjavaautomation.test.base;

import com.thangamfrm.playwrightjavaautomation.utils.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class BaseTest extends Assert {

  private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

  protected TestConfig testProperties;

  protected void setUp() {
    testProperties = TestConfig.getInstance();
  }

  protected String getUniqueId() {
    return testProperties.getUniqueId();
  }

  protected String getProperty(String propName) {
    return testProperties.getProperty(propName);
  }

}
