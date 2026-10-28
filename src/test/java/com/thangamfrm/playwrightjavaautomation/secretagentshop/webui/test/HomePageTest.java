package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

public class HomePageTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(HomePageTest.class);

  @Test(groups = {"smoke", "regression"})
  public void testHomePage() {
    // Verify Home Page
    verifyPageHeader();
    verifyPageFooter();

    assertTrue(homePage.isExploreButtonDisplayed(), "Explore Button NOT found!");

    // Verify Links are Present
    String linkText = "View Gadget Repository";
    assertTrue(homePage.doesLinkExist(linkText), String.format("Link: %s NOT Found!", linkText));
    linkText = "Explore";
    assertTrue(homePage.doesLinkExist(linkText), String.format("Link: %s NOT Found!", linkText));

    // Verify Section Title are Present
    assertTrue(homePage.doesHeaderExist("Mission Critical Gear"), "Header: 'Mission Critical Gear' NOT Found!");

    // Verify Products are displayed!
    String productName = "Aqua Ace GT";
    assertTrue(homePage.isProductDisplayed(productName), String.format("Product Name: %s NOT found / visible!", productName));
    productName = "Audio-Illusion Spinner";
    assertTrue(homePage.isProductDisplayed(productName), String.format("Product Name: %s NOT found / visible!", productName));
    productName = "Facechanger Formal Wear";
    assertTrue(homePage.isProductDisplayed(productName), String.format("Product Name: %s NOT found / visible!", productName));

    String appTitle = "The most public Secret Shop";
    assertTrue(homePage.doesHeaderExist(appTitle), String.format("App Title: %s NOT found!", appTitle));

    String subSectionTitle = "Why Choose Us";
    assertTrue(homePage.doesHeaderExist(subSectionTitle), String.format("Sub Section Title: %s NOT found!", subSectionTitle));

    List<String> reasonTexts = homePage.getReasonToChooseUs();
    assertEquals(reasonTexts.size(), 3, "Incorrect number of reasons to choose us!");
    String reason = "Covert Shipping";
    assertTrue(reasonTexts.getFirst().startsWith(reason), String.format("Reason to choose us: %s NOT found!", reason));
    reason = "Mission Guarantee";
    assertTrue(reasonTexts.get(1).startsWith(reason), String.format("Reason to choose us: %s NOT found!", reason));
    reason = "Support";
    assertTrue(reasonTexts.get(2).startsWith(reason), String.format("Reason to choose us: %s NOT found!", reason));
  }

}
