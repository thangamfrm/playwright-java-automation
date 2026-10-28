package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BillingPage extends SecretAgentShopAppBasePage {

  private final static Logger LOG = LoggerFactory.getLogger(BillingPage.class);

  public BillingPage(Page currentPage) {
    super(currentPage);
  }

  public boolean isBillingPage() {
    String step = "Mission Funding";
    return doesHeaderExist(step);
  }

  public void setCardHolderName(String fullName) {
    LOG.info(String.format("Entering Card Holder Name: %s", fullName));
    enterTextInFieldUsingNameAttr("cardHolder", fullName);
  }

  public void setCardNumber(String cardNumber) {
    LOG.info(String.format("Entering Card Number: %s", cardNumber));
    enterTextInFieldUsingNameAttr("cardNumber", cardNumber);
  }

  public void setExpiryDate(String expiryDate) {
    LOG.info(String.format("Entering Expiry Date: %s", expiryDate));
    enterTextInFieldUsingNameAttr("expiryDate", expiryDate);
  }

  public void fillBillingDetails(Map<String,String> fsbd) {
    setCardHolderName(fsbd.get("full_name"));
    setCardNumber(fsbd.get("card_number"));
    setExpiryDate(fsbd.get("expiry_date"));
    enterTextInField("CVC/CVV", fsbd.get("cvv"));
  }

  public boolean isPurchaseButtonPresent() {
    LOG.info("Is Purchase Button present on this page!");
    try {
      Locator element = getButtonLocatorByText("Purchase");
      LOG.info(String.format("Purchase Button Count: %s , Visible: %s", element.count(), element.isVisible()));
      return element.isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking Purchase Button present! Msg: %s", e.getMessage()));
    }
  }

  public void clickPurchaseButton() {
    LOG.info("Click Purchase Button on this page!");
    try {
      Locator element = getButtonLocatorByText("Purchase");
      LOG.info(String.format("Continue Button Count: %s , Visible: %s", element.count(), element.isVisible()));
      //@TODO: Enhance by checking count of button is one and visible
      element.click();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while clicking Purchase Button! Msg: %s", e.getMessage()));
    }
  }

}
