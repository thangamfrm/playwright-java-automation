package com.thangamfrm.playwrightjavaautomation.webui.core;

import com.microsoft.playwright.*;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class  PlaywrightWebUIAutomation extends BaseWebUIAutomation {

  private final static Logger LOG = LoggerFactory.getLogger(PlaywrightWebUIAutomation.class);
  protected Playwright playwright;
  protected Browser browser;
  protected Page currentPage;

  public PlaywrightWebUIAutomation() {
    launchBrowser();
  }

  protected void launchBrowser() {
    String browserName = getTestConfig().getProperty(BROWSER_NAME);
    LOG.info(String.format("Launching Browser Type: %s", browserName));
    // By default, Playwright runs the browsers in headless mode. To see the browser UI, setHeadless option to false.
    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(getTestConfig().getPropertyAsBoolean(
        BROWSER_HEADLESS_MODE));
    try {
      playwright = Playwright.create();
      if (browserName.equals(CHORMIUM_BROWSER)) {
        browser = playwright.chromium().launch(launchOptions);
      } else if (browserName.equals(SAFARI_BROWSER)) {
        LOG.info("Safari");
        browser = playwright.webkit().launch(launchOptions);
      } else if (browserName.equals(FIREFOX_BROWSER)) {
        LOG.info("Firefox");
        browser = playwright.firefox().launch(launchOptions);
      } else {
        throw new Exception(String.format("Undefined browser name: %s", browserName));
      }
      LOG.info("Browser Launched! Opening new page!");
      currentPage = browser.newPage();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Unable to launch browser type: %s , Error: %s", browserName,
          e.getMessage()));
    }
  }

  public Page getCurrentPage() {
    return currentPage;
  }

  public void closeBrowser() {
    try {
      browser.close();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Unable to close the browser: %s, Error: %s", getTestConfig().getProperty(BROWSER_NAME), e.getMessage()));
    }
  }

}
