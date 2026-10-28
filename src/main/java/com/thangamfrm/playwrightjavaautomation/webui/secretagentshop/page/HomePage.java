package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends SecretAgentShopAppBasePage {

  private final static Logger LOG = LoggerFactory.getLogger(HomePage.class);

  public HomePage(Page currentPage) {
    super(currentPage);
  }

  public Locator getExploreButtonLocator() {
    return currentPage.locator("a[id=\"shop-now\"]");
  }

  public void clickExploreButton() {
    LOG.info("Clicking explore button");
    try {
      getExploreButtonLocator().click();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while clicking explore button! Msg: %s", e.getMessage()));
    }
  }

  public boolean isExploreButtonDisplayed() {
    LOG.info("Checking explore button displayed");
    try {
      Locator exploreElement = getExploreButtonLocator();
      LOG.info(String.format("Explore Button Count: %s , Visible: %s", exploreElement.count(), exploreElement.isVisible()));
      return exploreElement.count() == 1 && exploreElement.first().isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking Explore button displayed! Msg: %s", e.getMessage()));
    }
  }

  public List<String> getReasonToChooseUs() {
    LOG.info("Getting Reason to Choose Us!");
    try {
      List<String> reasonTexts = new ArrayList<>();
      Locator childLocator = currentPage.locator(String.format("div.grid-cols-1:has-text('%s') > *", "Covert Shipping"));
      for (int i = 0; i < childLocator.count(); i++) {
        Locator child = childLocator.nth(i);
        LOG.info(String.format("Reason Text: %s", child.innerText().trim()));
        reasonTexts.add(child.innerText().trim());
      }
      return reasonTexts;
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting reason to choose us! Msg: %s", e.getMessage()));
    }
  }
}
