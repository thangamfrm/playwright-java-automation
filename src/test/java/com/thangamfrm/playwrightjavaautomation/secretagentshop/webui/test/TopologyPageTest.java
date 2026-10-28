package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.microsoft.playwright.Locator;
import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.TopologyPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class TopologyPageTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(TopologyPageTest.class);

  @Test(groups = {"smoke", "regression"})
  public void testTopologyPage() {
    LOG.info("Test Topology Page!");

    verifyPageHeader();
    verifyPageFooter();

    String footerSectionTitle = "Tools";
    List<Locator> linksUnderSection = homePage.getLinksUnderSectionInPageFooter(footerSectionTitle);
    linksUnderSection.get(1).click();
    TopologyPage topologyPage = new TopologyPage(app.getCurrentPage());
    String sectionTitle = topologyPage.getSectionTitle();
    LOG.info(String.format("Topology Page - Section Title: %s", sectionTitle));
    assertEquals(sectionTitle, "Application Services", "Topology Page Section Title didn't match!");

    String[] expectedServices = "Catalog, Carts, Checkout, Recommendations, Orders".split("\\s*,\\s*");
    Map<String, List<String>> serviceDetailsMap = topologyPage.getAppServiceDetails();
    for (String service : expectedServices) {
      LOG.info(String.format("Verifying Service: %s", service));
      assertTrue(serviceDetailsMap.containsKey(service), String.format("Service: %s NOT found!", service));
    }
    assertEquals(serviceDetailsMap.get("Catalog").get(1), "HEALTHY", "Catalog Service Status is NOT HEALTHY!");
    assertEquals(serviceDetailsMap.get("Carts").get(1), "HEALTHY", "Carts Service Status is NOT HEALTHY!");
    assertEquals(serviceDetailsMap.get("Checkout").get(1), "HEALTHY", "Checkout Service Status is NOT HEALTHY!");
    assertEquals(serviceDetailsMap.get("Recommendations").get(1), "NONE", "Recommendations Service Status is NOT NONE!");
    assertEquals(serviceDetailsMap.get("Orders").get(1), "HEALTHY", "Orders Service Status is NOT HEALTHY!");

    LOG.info("Verify Topology Page Complete!");
  }

}
