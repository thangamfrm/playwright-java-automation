package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import com.thangamfrm.playwrightjavaautomation.webui.core.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SecretAgentShopAppBasePage extends BasePage {

  private final static Logger LOG = LoggerFactory.getLogger(SecretAgentShopAppBasePage.class);

  public SecretAgentShopAppBasePage(Page currentPage) {
    super(currentPage);
  }

  public boolean isBriefCaseIconDisplayed() {
    LOG.info("Checking briefcase icon displayed");
    try {
      Locator briefcaseElement = currentPage.locator("i.fa-briefcase");
      LOG.info(String.format("Briefcase Icon Count: %s , Visible: %s", briefcaseElement.count(), briefcaseElement.isVisible()));
      return briefcaseElement.count() == 1 && briefcaseElement.first().isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking briefcase icon displayed! Msg: %s", e.getMessage()));
    }
  }

  public boolean isHomeLinkDisplayed() {
    LOG.info("Checking Home Link displayed in page header");
    try {
      Locator locator = currentPage.locator("a[id=\"menu-home\"]");
      return locator.all().size()  == 1 && locator.isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking home link displayed in page header, Msg: %s", e.getMessage()));
    }
  }

  public boolean isGadgetRepositoryLinkDisplayed() {
    LOG.info("Checking Gadget Repository Link displayed in page header");
    try {
      Locator locator = currentPage.locator("a[id=\"menu-home\"]");
      return locator.all().size()  == 1 && locator.isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking Gadget Repository Link displayed in page header, Msg: %s", e.getMessage()));
    }
  }

  public Locator getShoppingCartLocator() {
    return  currentPage.locator("a[id=\"go-cart\"]");
  }

  public boolean isShoppingCartDisplayed() {
    LOG.info("Checking Shopping Cart Icon displayed in page header");
    try {
      Locator locator = getShoppingCartLocator();
      return locator.all().size()  == 1 && locator.isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking shopping cart icon displayed in page header, Msg: %s", e.getMessage()));
    }
  }

  public void clickShoppingCart() {
    LOG.info("Clicking Shopping Cart Icon in page header");
    try {
      getShoppingCartLocator().click();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while clicking shopping cart icon displayed in page header, Msg: %s", e.getMessage()));
    }
  }

  public String getShoppingCartSize() {
    return getShoppingCartLocator().textContent().trim();
  }

  public boolean isProductDisplayed(String productName) {
    LOG.info(String.format("Checking Product exist with name: %s", productName));
    try {
      Locator productElement = currentPage.locator(".product-name", new Page.LocatorOptions().setHasText(productName));
      LOG.info(String.format("Product Count: %s , Visible: %s", productElement.count(), productElement.isVisible()));
      return productElement.count() == 1 && productElement.first().isVisible();
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking header: %s exist! Msg: %s", productName, e.getMessage()));
      return false;
    }
  }

  public Locator getProductCardLocator(String productName) {
    return currentPage.locator(".product-card", new Page.LocatorOptions().setHasText(productName));
  }

  public boolean isProductCardExist(String productName) {
    LOG.info(String.format("Checking Product Card Exist: %s", productName));
    try {
      Locator productCard = getProductCardLocator(productName);
      LOG.info(String.format("Product Card Count: %s , Visible: %s", productCard.count(), productCard.isVisible()));
      return productCard.count() == 1 && productCard.isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking product card exist! Product: %s , Msg: %s", productName, e.getMessage()));
    }
  }

  public String getProductCardDetails(String productName) {
    LOG.info(String.format("Getting Product Card Details: %s", productName));
    try {
      Locator productCard = getProductCardLocator(productName);
      LOG.info(String.format("Product Card Count: %s , Visible: %s", productCard.count(), productCard.isVisible()));
      return productCard.textContent().trim();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting product card details! Product: %s , Msg: %s", productName, e.getMessage()));
    }
  }

  public void addProductToLoadout(String productName) {
    LOG.info(String.format("Add the Product name: %s to Loadout", productName));
    try {
      Locator productCard = getProductCardLocator(productName);
      LOG.info(String.format("Product Card Count: %s , Visible: %s , text: %s", productCard.count(), productCard.isVisible(), productCard.textContent()));
      productCard.locator("button", new Locator.LocatorOptions().setHasText(Pattern.compile("Add to Loadout"))).click();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while add product to loadout. Product: %s exist! Msg: %s", productName, e.getMessage()));
    }
  }

  public boolean isCheckoutButtonExist() {
    LOG.info("Checking Checkout Button present on this page!");
    try {
      Locator element = currentPage.locator("#checkout", new Page.LocatorOptions().setHasText("Start Equipment Requisition"));
      LOG.info(String.format("Checkout Button Count: %s , Visible: %s", element.count(), element.isVisible()));
      return element.count() == 1 && element.first().isVisible();
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking Checkout Button exist! Msg: %s", e.getMessage()));
      return false;
    }
  }

  public Locator getButtonLocatorByText(String text) {
    return currentPage.locator("#checkout-submit", new Page.LocatorOptions().setHasText(text));
  }

  public Locator getContinueButtonLocator() {
    //return currentPage.locator("#checkout-submit", new Page.LocatorOptions().setHasText("Continue"));
    return getButtonLocatorByText("Continue");
  }

  public void clickContinueButton() {
    LOG.info("Click Continue Button on this page!");
    try {
      Locator element = getContinueButtonLocator();
      LOG.info(String.format("Continue Button Count: %s , Visible: %s", element.count(), element.isVisible()));
      //@TODO: Enhance by checking count of button is one and visible
      element.click();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while clicking Continue Button! Msg: %s", e.getMessage()));
    }
  }

  public boolean isContinueButtonExist() {
    LOG.info("Checking Continue Button present on this page!");
    try {
      Locator element = getContinueButtonLocator();
      LOG.info(String.format("Continue Button Count: %s , Visible: %s", element.count(), element.isVisible()));
      return element.count() == 1 && element.first().isVisible();
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking Continue Button exist! Msg: %s", e.getMessage()));
      return false;
    }
  }

  public List<Locator> getBreadcrumbTrailStepLocators() {
    return currentPage.locator("div.step-item").all();
  }

  public List<String> getBreadcrumbTrailStepTexts() {
    LOG.info("Get BreadcrumbTrail Step Texts");
    List<Locator> breadcrumbTrailStepLocators = getBreadcrumbTrailStepLocators();
    List<String> stepTexts = new ArrayList<>();
    for (Locator locator : breadcrumbTrailStepLocators) {
      String stepText = locator.textContent().trim();
      LOG.info(String.format("BreadcrumbTrail Step Text: %s", stepText));
      stepTexts.add(stepText);
    }
    LOG.info(String.format("Count of BreadcrumbTrail Step Texts: %s", stepTexts.size()));
    return stepTexts;
  }

  public boolean isBreadcrumbStepHighlighted(String step) {
    LOG.info(String.format("Checking BreadcrumbTrail Step Highlighted! Step: %s", step));
    try {
      List<Locator> locators = currentPage.locator("div.text-primary-500").all();
      for (Locator locator : locators) {
        if (locator.textContent().trim().equals(step)) {
          return true;
        }
      }
      throw new RuntimeException(String.format("Checked all highlighted steps! Step: %s NOT highlighted!", step));
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking BreadcrumbTrail Step Highlighted! Step: %s", step));
      return false;
    }
  }

  public boolean isBackToCartButtonPresent() {
    LOG.info("Checking Back to Cart Button present!");
    try {
      return getBackToCartButtonLocator().isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred when checking Back to Cart button present! Msg: %s", e.getMessage()));
    }
  }

  public boolean clickBackToCartButton() {
    LOG.info("Clicking Back to Cart Button!");
    try {
      Locator buttonLocator = getBackToCartButtonLocator();
      if (buttonLocator.isVisible()) {
        buttonLocator.click();
        return true;
      }
      return false;
      //TODO: remove return boolean
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred when checking Back to Cart button present! Msg: %s", e.getMessage()));
    }
  }

  public Locator getBackToCartButtonLocator() {
    return currentPage.locator(String.format("xpath=//button[normalize-space()='%s']", "Back to Cart"));
  }

  public Locator getWarningMessageLocator() {
    return currentPage.locator("div[role=\"alert\"]");
  }

  public boolean isWarningMessageDisplayed() {
    LOG.info("Checking warning message displayed!");
    try {
      List<Locator> warningMsgLocators = getWarningMessageLocator().all();
      if (warningMsgLocators.size() > 1){
        LOG.info("More than one warning message element displayed! Expected: Only one warning message should be displayed!");
        return false;
      }
      return warningMsgLocators.getFirst().isVisible();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking warning message displayed! Msg: %s", e.getMessage()));
    }
  }

  public String getWarningMessage() {
    LOG.info("Getting warning message!");
    try {
      String warningText = getWarningMessageLocator().textContent().trim();
      List<String> warningTextList = warningText.lines()
          .map(String::strip)
          .toList();
      List<String> filteredList = warningTextList.stream()
          .filter(str -> str != null && !str.isEmpty())
          .toList();
      return filteredList.getFirst() + " " + filteredList.get(1) + " " + filteredList.get(2);
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking warning message displayed! Msg: %s", e.getMessage()));
    }
  }

  public List<Locator> getLinksUnderSectionInPageFooter(String sectionTitle) {
    return currentPage.locator(String.format("//h3[contains(text(), '%s')]/..//a", sectionTitle)).all();
  }

  public List<String> getLinkTextsUnderSectionInPageFooter(String sectionTitle) {
    LOG.info(String.format("Getting list of link texts under section: %s in page footer!", sectionTitle));
    List<String> linkTexts = new ArrayList<>();
    try {
      List<Locator> locators = getLinksUnderSectionInPageFooter(sectionTitle);
      for (Locator locator : locators) {
        String linkText = locator.textContent().trim();
        LOG.info(String.format("Link Text: %s", linkText));
        linkTexts.add(linkText);
      }
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting link texts under section: %s in page footer!, Msg: %s", sectionTitle, e.getMessage()));
    }
    return linkTexts;
  }

  public List<String> getContactMethodsInPageFooter() {
    LOG.info("Getting list of contact methods in page footer!");
    List<String> contactMethods = new ArrayList<>();
    try {
      List<Locator> locators = currentPage.locator(String.format("//h3[contains(text(), '%s')]/..//li", "Contact")).all();
      for (Locator locator : locators) {
        String contactMethod = locator.textContent().trim();
        LOG.info(String.format("Contact Method: %s", contactMethod));
        contactMethods.add(contactMethod);
      }
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting contact methods under section: %s in page footer!, Msg: %s", "Contact", e.getMessage()));
    }
    return contactMethods;
  }

}
