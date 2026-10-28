package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShippingAddressPage;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShippingMethodPage;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShoppingCartReviewPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ShippingMethodPageTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(ShippingMethodPageTest.class);

  @Test(groups = {"regression"})
  public void testShippingMethodPage() {
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
    shippingAddressPage.clickContinueButton();

    ShippingMethodPage shippingMethodPage = new ShippingMethodPage(app.getCurrentPage());
    assertTrue(shippingMethodPage.isShippingMethodPage(), "Shipping Methods is NOT the current page!");
    verifyShippingMethodPage(shippingMethodPage);

    // Verify Back Button - Navigate back to Shopping Cart Review Page
    shippingMethodPage.clickBackToCartButton();
    verifyShoppingCartReviewPage(cartReviewPagePage);
    shippingAddressPage.clickShoppingCart();

    LOG.info("Shipping Method Page Test Complete!");
  }


}