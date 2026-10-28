package com.thangamfrm.playwrightjavaautomation.webui.core;

import com.thangamfrm.playwrightjavaautomation.utils.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseWebUIAutomation {

  private final static Logger LOG = LoggerFactory.getLogger(BaseWebUIAutomation.class);

  private final TestConfig testConfig;
  protected static final String BROWSER_NAME = "browser_name";
  protected static final String BROWSER_HEADLESS_MODE = "browser_headless_mode";
  protected static final String CHORMIUM_BROWSER = "chromium";
  protected static final String SAFARI_BROWSER = "safari";
  protected static final String FIREFOX_BROWSER = "firefox";

  public BaseWebUIAutomation() {
    testConfig = TestConfig.getInstance();
  }

  protected abstract void launchBrowser();
  protected abstract void closeBrowser();
  protected TestConfig getTestConfig() {
    return testConfig;
  }

}
