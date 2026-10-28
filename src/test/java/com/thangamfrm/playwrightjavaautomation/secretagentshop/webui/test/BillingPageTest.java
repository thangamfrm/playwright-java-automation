package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.BillingPage;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShippingAddressPage;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShippingMethodPage;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShoppingCartReviewPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Map;

public class BillingPageTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(BillingPageTest.class);

  @Test(groups = {"regression"})
  public void testBillingPage() {
    // Verify Home Page
    verifyPageHeader();
    verifyPageFooter();

    // Add Product to Cart
    String productName = "Aqua Ace GT";
    homePage.addProductToLoadout(productName);

    // Navigate to Shipping Address Page
    ShoppingCartReviewPage shoppingCartReviewPagePage = new ShoppingCartReviewPage(app.getCurrentPage());
    shoppingCartReviewPagePage.clickCheckout();

    // Step 3: Shipping Address Page
    ShippingAddressPage shippingAddressPage = new ShippingAddressPage(app.getCurrentPage());
    Map<String,String> fsbd = generateFakeShippingAndBillingDetails();
    shippingAddressPage.fillShippingDetails(fsbd);
    shippingAddressPage.clickContinueButton();

    // Step 4: Shipping Method Page
    ShippingMethodPage shippingMethodPage = new ShippingMethodPage(app.getCurrentPage());
    assertTrue(shippingMethodPage.isShippingMethodPage(), "Shipping Methods is NOT the current page!");
    verifyShippingMethodPage(shippingMethodPage);
    shippingMethodPage.clickContinueButton();

    // Step 5: Billing Page
    BillingPage billingPage = new BillingPage(app.getCurrentPage());
    verifyBillingPage(billingPage);
    billingPage.fillBillingDetails(fsbd);

    // Click Back Button - Verify Shopping Cart Review Page
    billingPage.clickBackToCartButton();
    verifyShoppingCartReviewPage(shoppingCartReviewPagePage);

  }

}