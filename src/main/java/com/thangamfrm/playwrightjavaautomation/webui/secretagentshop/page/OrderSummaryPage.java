package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class OrderSummaryPage extends SecretAgentShopAppBasePage {

  private final static Logger LOG = LoggerFactory.getLogger(OrderSummaryPage.class);

  public OrderSummaryPage(Page currentPage) {
    super(currentPage);
  }

  public Boolean isOrderIDAValidUUID() {
    LOG.info("Checking generated Order ID is a valid UUID!");
    String orderID = "";
    try {
      List<Locator> locators = currentPage.locator("div.grid-cols-1").filter(new Locator.FilterOptions().setHasText(("Order ID"))).all();
      String orderText = locators.get(1).textContent().trim();
      orderID = orderText.replace("Order ID", "").trim();
      LOG.info(String.format("Extracted Order ID: %s", orderID));
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking generated Order ID is valid UUID! Msg: %s", e.getMessage()));
    }

    try {
      UUID.fromString(orderID);
      LOG.info(String.format("Extracted Order ID: %s is a valid UUID!", orderID));
      return true;
    } catch (IllegalArgumentException e) {
      LOG.info(String.format("Extracted Order ID: %s is NOT a valid UUID!", orderID));
      return false;
    }
  }

  public Locator getProgressBarStageLocator() {
    return currentPage.locator("div[aria-hidden=\"true\"]");
  }

  public List<String> getProgressBarStages() {
    LOG.info("Getting progress bar stages!");
      List<String> stages = new ArrayList<>();
    try {
      String stagesText = getProgressBarStageLocator().textContent().trim();
      LOG.info(String.format("Stages Text: %s", stagesText));
      stages = stagesText.lines()
          .map(String::strip)
          .toList();
      LOG.info(String.format("Count of Stages: %s", stages.size()));
      return stages;
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting progress bar stages! Msg: %s", e.getMessage()));
    }
  }

  public boolean isProgressBarStageHighlighted(String stage) {
    LOG.info(String.format("Checking progress bar stage highlighted: %s", stage));
    try {
      List<Locator> locators = currentPage.locator("div.text-primary-500").filter(new Locator.FilterOptions().setHasText((stage))).all();
      return !locators.isEmpty();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking progress bar stage highlighted! Msg: %s", e.getMessage()));
    }
  }

  public String getOrderSubTotal() {
    return currentPage.locator("span[id=\"order-subtotal\"]").textContent().trim();
  }

  public String getOrderShipping() {
    return currentPage.locator("span[id=\"order-shipping\"]").textContent().trim();
  }

  public String getOrderTax() {
    return currentPage.locator("span[id=\"order-tax\"]").textContent().trim();
  }

  public String getOrderTotal() {
    return currentPage.locator("span[id=\"order-total\"]").textContent().trim();
  }

  public Map<String, List<String>> getProductDetailsInOrderSummary() {
    LOG.info("Getting product details in order summary!");
    Map<String, List<String>> productDetailsMap = new HashMap<>();
    try {
      List<Locator> locators = currentPage.locator("[alt='Product']").locator("xpath=..").all();
      for (Locator locator : locators) {
        String productText = locator.textContent().trim();
        LOG.info(String.format("Product Text: %s", productText));
        List<String> productDetails = productText.lines()
            .map(String::strip)
            .toList();
        List<String> filteredList = productDetails.stream()
            .filter(str -> str != null && !str.isEmpty())
            .toList();
        String productName = filteredList.getFirst();
        String quantity = filteredList.get(1);
        String productTotalPrice = filteredList.get(2);
        String pricePerProduct = filteredList.get(3);
        LOG.info(String.format("Order Details Product Name: %s , Quantity : %s , Total Price: %s , Price Per Quantity: %s", productName, quantity, productTotalPrice, pricePerProduct));
        productDetailsMap.put(productName, filteredList);
      }
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting Product Details in Order Summary! Msg: %s", e.getMessage()));
    }
    return productDetailsMap;
  }

}
