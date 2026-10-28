package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.microsoft.playwright.Locator;
import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

public class FooterLinksTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(FooterLinksTest.class);

  @Test(groups = {"smoke", "regression"})
  public void testFooterLinks() {
    LOG.info("Test Footer Links!");

    verifyPageHeader();
    verifyPageFooter();

    String sectionTitle = "Quick Links";
    List<Locator> linksUnderSection = homePage.getLinksUnderSectionInPageFooter(sectionTitle);
    linksUnderSection.getFirst().click();
    assertTrue(homePage.isGadgetRepositoryLinkDisplayed(), String.format("Invalid first link: %s under 'Quick Links'", linksUnderSection.getFirst().textContent().trim()));


  }

}
