package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.ShoppingCartReviewPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ShoppingCartReviewPageTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(ShoppingCartReviewPageTest.class);

  @Test(groups = {"regression"})
  public void testShoppingCartReviewPage() {
    // Verify Home Page
    verifyPageHeader();
    verifyPageFooter();

    String productName = "Aqua Ace GT";
    homePage.addProductToLoadout(productName);

    // Verify ShoppingCartReview Page
    ShoppingCartReviewPage shoppingCartReviewPagePage = new ShoppingCartReviewPage(app.getCurrentPage());
    verifyShoppingCartReviewPage(shoppingCartReviewPagePage);
    assertTrue(shoppingCartReviewPagePage.isRemoveIconPresentForProduct(productName), String.format("Remove Icon NOT present for Product: %s", productName));
    assertEquals(shoppingCartReviewPagePage.getCountOfProductsInCart(), 1, "The count of items in the card didn't match!");
    assertEquals(shoppingCartReviewPagePage.getShoppingCartSize(), "1", "Shopping Cart size didn't match!");

    // Remove the Product from the Cart
    boolean removeProductFromCart = shoppingCartReviewPagePage.removeProductFromCart(productName);
    assertTrue(removeProductFromCart,String.format("Remove Product from Cart Failed! Product Name: %s", productName));
    assertEquals(shoppingCartReviewPagePage.getBasketText(), "You don't have any items!", "Basket message didn't match!");
    assertEquals(shoppingCartReviewPagePage.getShoppingCartSize(), "0", "Shopping Cart size didn't match!");

    // Move back to Home Page
    shoppingCartReviewPagePage.clickLink("Home");

    // Add the Product Again & Verify
    homePage.addProductToLoadout(productName);
    verifyShoppingCartReviewPage(shoppingCartReviewPagePage);
    assertEquals(shoppingCartReviewPagePage.getShoppingCartSize(), "1", "Shopping Cart size didn't match!");
  }


}
