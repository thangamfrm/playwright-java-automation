package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Map;

public class OrderSummaryPageTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(OrderSummaryPageTest.class);

  @Test(groups = {"regression"})
  public void testOrderSummaryPage() {
    // Verify Home Page
    verifyPageHeader();
    verifyPageFooter();

    // Add Product to Cart
    String productNameOne = "Aqua Ace GT";
    homePage.addProductToLoadout(productNameOne);

    // Navigate to Shipping Address Page
    ShoppingCartReviewPage shoppingCartReviewPagePage = new ShoppingCartReviewPage(app.getCurrentPage());
    shoppingCartReviewPagePage.clickCheckout();

    // Step 3: Shipping Address Page
    ShippingAddressPage shippingAddressPage = new ShippingAddressPage(app.getCurrentPage());
    Map<String,String> fsbd = generateFakeShippingAndBillingDetails();
    shippingAddressPage.fillShippingDetails(fsbd);
    shippingAddressPage.clickContinueButton();

    // Update Product One Details
    fsbd.put("product_name_one", productNameOne);
    fsbd.put(String.format("%s_quantity", productNameOne), "Quantity: 1");
    fsbd.put(String.format("%s_product_total_price", productNameOne), "$10000");
    fsbd.put(String.format("%s_price_per_product", productNameOne), "$10000 each");

    // Step 4: Shipping Method Page
    ShippingMethodPage shippingMethodPage = new ShippingMethodPage(app.getCurrentPage());
    assertTrue(shippingMethodPage.isShippingMethodPage(), "Shipping Methods is NOT the current page!");
    verifyShippingMethodPage(shippingMethodPage);

    // Add Second Product to Cart
    shippingMethodPage.clickLink("Home");
    String productNameTwo = "Audio-Illusion Spinner";
    homePage.addProductToLoadout(productNameTwo);

    // Proceed to Shipping & Billing
    shoppingCartReviewPagePage.clickCheckout();
    shippingAddressPage.clickContinueButton();
    shippingMethodPage.clickContinueButton();

    // Update Product Two Details
    fsbd.put("product_name_two", productNameTwo);
    fsbd.put(String.format("%s_quantity", productNameTwo), "Quantity: 1");
    fsbd.put(String.format("%s_product_total_price", productNameTwo), "$190");
    fsbd.put(String.format("%s_price_per_product", productNameTwo), "$190 each");

    // Step 5: Billing Page
    BillingPage billingPage = new BillingPage(app.getCurrentPage());
    verifyBillingPage(billingPage);
    billingPage.fillBillingDetails(fsbd);

    // Purchase
    billingPage.clickPurchaseButton();

    OrderSummaryPage orderSummaryPage = new OrderSummaryPage(app.getCurrentPage());
    verifyOrderSummaryPage(orderSummaryPage);

    assertEquals(shoppingCartReviewPagePage.getShoppingCartSize(), "2", "Shopping Cart size didn't match!");
    assertTrue(orderSummaryPage.isOrderIDAValidUUID(), "Generated Order ID is NOT a valid UUID!");

    fsbd.put("order_subtotal", "$10190");
    fsbd.put("order_shipping_charges", "$10");
    fsbd.put("order_taxes", "$5");
    fsbd.put("order_total", "$10205");

    verifyProductInOrderDetails(orderSummaryPage, productNameOne, fsbd);
    verifyProductInOrderDetails(orderSummaryPage, productNameTwo, fsbd);
    verifyOrderDetails(orderSummaryPage, fsbd);

    orderSummaryPage.clickShoppingCart();
    assertEquals(orderSummaryPage.getShoppingCartSize(), "0", "Shopping Cart size didn't match!");
    assertEquals(shoppingCartReviewPagePage.getBasketText(), "You don't have any items!", "Shopping Cart Basket Empty Message didn't match!");

    LOG.info("Order Summary Page test complete!");
  }

}