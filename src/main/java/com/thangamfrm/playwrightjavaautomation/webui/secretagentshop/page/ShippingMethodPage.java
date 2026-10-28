package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ShippingMethodPage extends SecretAgentShopAppBasePage {

  private final static Logger LOG = LoggerFactory.getLogger(ShippingMethodPage.class);

  public ShippingMethodPage(Page currentPage) {
    super(currentPage);
  }

  public boolean isShippingMethodPage() {
    return doesHeaderExist("Covert Delivery");
  }

  public List<Locator> getShippingMethodLocators() {
    return currentPage.locator("input[name=\"token\"]").all();
  }

  public BiMap<String, String> getShippingMethodBiMap() {
    return new ImmutableBiMap.Builder<String, String>()
        .put("Priority Mail", "priority-mail")
        .put("Priority Mail Express", "priority-mail-express")
        .build();
  }

  public List<String> getShippingMethods() {
    LOG.info("Getting shipping methods!");
    try {
      List<Locator> locators = getShippingMethodLocators();
      List<String> shippingMethods = new ArrayList<>();
      BiMap<String, String> shippingMethodBiMap = getShippingMethodBiMap();
      for (Locator locator : locators) {
        String shippingMethod = shippingMethodBiMap.inverse().get(locator.getAttribute("value"));
        shippingMethods.add(shippingMethod);
        LOG.info(String.format("Shipping method: %s", shippingMethod));
      }
      return shippingMethods;
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting shipping methods! Msg: %s", e.getMessage()));
    }
  }

  public boolean isShippingMethodSelected(String shippingMethod) {
    LOG.info(String.format("Checking is shipping method selected: %s", shippingMethod));
    try {
        List<Locator> locators = getShippingMethodLocators();
      BiMap<String, String> shippingMethodBiMap = getShippingMethodBiMap();
      String shippingMethodCode = shippingMethodBiMap.get(shippingMethod);
      for (Locator locator : locators) {
        if (locator.getAttribute("value").equals(shippingMethodCode)) {
          boolean isSelected = locator.isChecked();
          LOG.info(String.format("Shipping Method: %s , Selected: %s", shippingMethod, isSelected));
          return isSelected;
        }
      }
      throw new RuntimeException(String.format("Unable to find Shipping Method: %s", shippingMethod));
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting shipping methods! Msg: %s", e.getMessage()));
    }
  }

  public boolean selectShippingMethod(String shippingMethod) {
    LOG.info(String.format("Set shipping method: %s", shippingMethod));
    try {
      BiMap<String, String> shippingMethodBiMap = getShippingMethodBiMap();
      String shippingMethodCode = shippingMethodBiMap.get(shippingMethod);
      List<Locator> shippingMethodLocators = currentPage.locator("label.flex").all();
      for (Locator locator : shippingMethodLocators) {
        if (locator.textContent().trim().contains(shippingMethod)) {
          locator.click();
          LOG.info(String.format("Selected Shipping Method: %s", shippingMethod));
          return true;
        }
      }
      throw new RuntimeException(String.format("Shipping Method: %s NOT found!", shippingMethod));
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while selecting shipping method: %s , Msg: %s", shippingMethod, e.getMessage()));
    }
  }

}
