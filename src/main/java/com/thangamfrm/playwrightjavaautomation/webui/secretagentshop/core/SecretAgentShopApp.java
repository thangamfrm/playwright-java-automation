package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.core;

import com.thangamfrm.playwrightjavaautomation.webui.core.PlaywrightWebUIAutomation;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.HomePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecretAgentShopApp extends PlaywrightWebUIAutomation {

  private final static Logger LOG = LoggerFactory.getLogger(SecretAgentShopApp.class);
  public HomePage homePage;

  private static final String APP_UNDER_TEST_URL = "aws_secretagentshop_url";

  public SecretAgentShopApp() {
    homePage = new HomePage(getCurrentPage());
    homePage.gotoUrl(getTestConfig().getProperty(APP_UNDER_TEST_URL));
  }

  public HomePage getHomePage() {
    return homePage;
  }

}
