package com.thangamfrm.playwrightjavaautomation.webui.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {

  private final static Logger LOG = LoggerFactory.getLogger(BasePage.class);

  protected final Page currentPage;

  public BasePage(Page currentPage) {
    this.currentPage = currentPage;
  }

  public void gotoUrl(String url) {
    LOG.info(String.format("Navigating to URL: %s", url));
    currentPage.navigate(url);
  }

  public String getPageTitle() {
    return currentPage.title().trim();
  }

  public boolean doesLinkExist(String linkText) {
    LOG.info(String.format("Checking link exist with text: %s", linkText));
    try {
      Locator link = currentPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(linkText));
      boolean isPresent = link.count() > 0;
      LOG.info(String.format("Link: %s exist!", linkText));
      return isPresent;
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking link: %s exist! Error: %s", linkText, e.getMessage()));
      return false;
    }
  }

  public boolean doesHeaderExist(String headerText) {
    LOG.info(String.format("Checking header exist with text: %s", headerText));
    try {
      Locator header = currentPage.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(headerText)
          .setExact(true));
      boolean isPresent = header.count() > 0;
      if (isPresent) {
        LOG.info(String.format("Header: %s exist!", headerText));
      } else {
        LOG.info(String.format("Header: %s does NOT exist!", headerText));
      }
      return isPresent;
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking header: %s exist! Error: %s", headerText, e.getMessage()));
      return false;
    }
  }

  public Locator getLabelLocator(String labelText) {
    Locator locator = currentPage.locator(String.format("xpath=//label[normalize-space()='%s']", labelText));
    LOG.info(String.format("Locator: %s", locator));
    return locator;
  }

  public void clickLink(String linkText) {
    currentPage.getByText(linkText).click();
  }

  public void enterTextInField(String label, String text) {
    LOG.info(String.format("In Field: %s , entering text: %s", label, text));
    try {
      currentPage.getByLabel(label, new Page.GetByLabelOptions().setExact(false)).fill(text);
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while entering text in field: %s with value: %s  , Error: %s", label, text, e.getMessage()));
    }
  }

  public Locator getButtonLocatorByText(String buttonText) {
    return currentPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(buttonText));
  }

  public void enterTextInFieldUsingNameAttr(String fieldNameAttr, String text) {
    LOG.info(String.format("In Field with name attribute: %s , Entering text: %s", fieldNameAttr, text));
    try {
      currentPage.locator(String.format("input[name='%s']", fieldNameAttr)).fill(text);
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while entering text in field with name attribute: %s , Msg: %s", fieldNameAttr, e.getMessage()));
    }
  }

}
