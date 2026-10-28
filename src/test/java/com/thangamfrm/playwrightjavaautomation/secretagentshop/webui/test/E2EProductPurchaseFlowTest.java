package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.annotations.Test;
import java.util.Map;


public class E2EProductPurchaseFlowTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(E2EProductPurchaseFlowTest.class);

  @Test(groups = {"smoke", "regression"})
  public void testE2EProductPurchaseFlow() {
    /*
     * Step 1: Home Page
     * Step 2: ShoppingCartReview Page
     * Step 3: ShippingAddress Page
     * Step 4: ShippingMethod Page
     * Step 5: Billing Page
     * Step 6: Order Summary Page
     */

    // Step 1: Verify Home Page
    verifyPageHeader();
    verifyPageFooter();

    // Add Product to Cart
    assertFalse(homePage.doesHeaderExist("Loadout"), "Loadout Section should NOT be present in this page!");
    String productName = "Aqua Ace GT";
    homePage.addProductToLoadout(productName);
    assertTrue(homePage.doesHeaderExist("Loadout"), "Loadout Section NOT found!");
    assertTrue(homePage.isCheckoutButtonExist(), "Checkout Button NOT found!");
    LOG.info("We're in now Checkout Page!");

    // Step 2: Shopping Cart Review Page
    ShoppingCartReviewPage shoppingCartReviewPagePage = new ShoppingCartReviewPage(app.getCurrentPage());
    assertEquals(shoppingCartReviewPagePage.getCountOfProductsInCart(), 1, "The count of items in the card didn't match!");
    assertEquals(shoppingCartReviewPagePage.getShoppingCartSize(), "1", "Shopping Cart size didn't match!");

    String cartTextForProduct = shoppingCartReviewPagePage.getCartTextForProduct(productName);
    LOG.info(String.format("Product: %s , Cart text: %s", productName, cartTextForProduct));
    assertEquals(shoppingCartReviewPagePage.getCartSubTotal(), "$10000", "Cart sub total didn't match!");

    String step = "Rendezvous Location";
    assertFalse(shoppingCartReviewPagePage.doesHeaderExist(step), String.format("Header: %s should NOT be present in this page!", step));
    assertTrue(shoppingCartReviewPagePage.clickCheckout(), "Click checkout button failed!");
    assertTrue(shoppingCartReviewPagePage.doesHeaderExist(step), String.format("Header: %s NOT found!", step));

    // Step 3: Shipping Address Page
    ShippingAddressPage shippingAddressPage = new ShippingAddressPage(app.getCurrentPage());
    Map<String,String> fsbd = generateFakeShippingAndBillingDetails();
    shippingAddressPage.fillShippingDetails(fsbd);

    // Update Product Details
    fsbd.put("product_name_one", productName);
    fsbd.put(productName+"_quantity", "Quantity: 1");
    fsbd.put(productName+"_product_total_price", "$10000");
    fsbd.put(productName+"_price_per_product", "$10000 each");

    step = "Covert Delivery";
    assertFalse(shoppingCartReviewPagePage.doesHeaderExist(step), String.format("Header: %s should NOT be present in this page!", step));
    shippingAddressPage.clickContinueButton();
    assertTrue(shoppingCartReviewPagePage.doesHeaderExist(step), String.format("Header: %s NOT found!", step));

    // Step 4: Shipping Method Page
    ShippingMethodPage shippingMethodPage = new ShippingMethodPage(app.getCurrentPage());
    verifyShippingMethodPage(shippingMethodPage);

    // Verify Shopping Cart size is 1
    assertEquals(shippingMethodPage.getShoppingCartSize(), "1", "Shopping Cart size didn't match!");

    String shippingMethod = "Priority Mail";
    assertTrue(shippingMethodPage.isShippingMethodSelected(shippingMethod), String.format("Shipping Method: %s should be Selected!", shippingMethod));

    shippingMethod = "Priority Mail Express";
    assertFalse(shippingMethodPage.isShippingMethodSelected(shippingMethod), String.format("Shipping Method: %s should NOT be Selected!", shippingMethod));

    shippingMethod = "Priority Mail Express";
    assertTrue(shippingMethodPage.selectShippingMethod(shippingMethod));
    assertTrue(shippingMethodPage.isShippingMethodSelected(shippingMethod), String.format("Shipping Method: %s should be Selected!", shippingMethod));

    step = "Mission Funding";
    assertFalse(shippingMethodPage.doesHeaderExist(step), String.format("Header: %s should NOT be present in this page!", step));
    shippingMethodPage.clickContinueButton();
    assertTrue(shippingMethodPage.doesHeaderExist(step), String.format("Header: %s NOT found!", step));

    // Step 5: Billing Page
    BillingPage billingPage = new BillingPage(app.getCurrentPage());
    assertTrue(billingPage.isBillingPage(), "Billing is NOT the current page!");
    assertEquals(shoppingCartReviewPagePage.getShoppingCartSize(), "1", "Shopping Cart size didn't match!");
    verifyBillingPage(billingPage);

    billingPage.fillBillingDetails(fsbd);

    step = "Mission Authorized!";
    assertFalse(billingPage.doesHeaderExist(step), String.format("Header: %s should NOT be found!", step));
    billingPage.clickPurchaseButton();
    assertTrue(billingPage.doesHeaderExist(step), String.format("Header: %s NOT found!", step));

    // Step 6: Order Summary
    OrderSummaryPage orderSummaryPage = new OrderSummaryPage(app.getCurrentPage());
    verifyOrderSummaryPage(orderSummaryPage);

    assertEquals(orderSummaryPage.getShoppingCartSize(), "1", "Shopping Cart size didn't match!");
    assertTrue(orderSummaryPage.isOrderIDAValidUUID(), "Generated Order ID is NOT a valid UUID!");

    fsbd.put("order_subtotal", "$10000");
    fsbd.put("order_shipping_charges", "$25");
    fsbd.put("order_taxes", "$5");
    fsbd.put("order_total", "$10030");

    verifyProductInOrderDetails(orderSummaryPage, productName, fsbd);
    verifyOrderDetails(orderSummaryPage, fsbd);

    orderSummaryPage.clickShoppingCart();
    assertEquals(orderSummaryPage.getShoppingCartSize(), "0", "Shopping Cart size didn't match!");
    assertEquals(shoppingCartReviewPagePage.getBasketText(), "You don't have any items!", "Shopping Cart Basket Empty Message didn't match!");

    LOG.info("Still in Checkout Page!");
  }

}
