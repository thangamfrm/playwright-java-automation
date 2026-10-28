package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import com.thangamfrm.playwrightjavaautomation.webui.core.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopologyPage extends BasePage {

  private final static Logger LOG = LoggerFactory.getLogger(TopologyPage.class);

  public TopologyPage(Page currentPage) {
    super(currentPage);
  }

  public String getSectionTitle() {
    LOG.info("Getting Section Title!");
    try {
      Locator parentDiv = currentPage.locator("div", new Page.LocatorOptions()
          .setHas(currentPage.locator("i.fa-cubes")));
      return parentDiv.textContent().trim();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting section title! Msg: %s", e.getMessage()));
    }
  }

  public List<Locator> getAppServiceLocators() {
    return currentPage.locator("div.rounded-lg").all();
  }

  public Map<String, List<String>> getAppServiceDetails() {
    LOG.info("Getting App Service Details");
    try {
      Map<String, List<String>> serviceDetailsMap = new HashMap<>();
      List<Locator> serviceLocators = getAppServiceLocators();
      for ( Locator locator : serviceLocators) {
        List<String> lines = locator.textContent().trim().lines()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .toList();
        String serviceName = lines.getFirst();
        LOG.info(String.format("Service Name: %s , Status: %s", serviceName, lines.get(1)));
        serviceDetailsMap.put(serviceName, lines);
      }
      return serviceDetailsMap;
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting App Service Details! Msg: %s", e.getMessage()));
    }
  }
}
