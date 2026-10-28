package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShippingAddressPage;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShoppingCartReviewPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ShippingAddressPageTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(ShippingAddressPageTest.class);

  @Test(groups = {"regression"})
  public void testShippingAddressPage() {
    // Verify Home Page
    verifyPageHeader();
    verifyPageFooter();

    // Add Product to Cart
    String productName = "Aqua Ace GT";
    homePage.addProductToLoadout(productName);

    // Navigate to Shipping Address Page
    ShoppingCartReviewPage cartReviewPagePage = new ShoppingCartReviewPage(app.getCurrentPage());
    cartReviewPagePage.clickCheckout();

    ShippingAddressPage shippingAddressPage = new ShippingAddressPage(app.getCurrentPage());

    // Verify Warning Message in Order Summary Page
    assertTrue(shippingAddressPage.isWarningMessageDisplayed(), "Warning Message NOT displayed in Shipping Address Page!");
    assertEquals(shippingAddressPage.getWarningMessage(), "Warning This site is for demonstration/educational purposes only, do not enter personal details", "Warning message NOT matching in Shipping Address Page!");
    verifyBreadcrumb(shippingAddressPage);
    assertTrue(shippingAddressPage.isContinueButtonExist(), "Continue Button NOT found!");

    shippingAddressPage.clickContinueButton();

    String step = "Covert Delivery";
    assertTrue(cartReviewPagePage.doesHeaderExist(step), String.format("Header: %s NOT found!", step));

  }


}