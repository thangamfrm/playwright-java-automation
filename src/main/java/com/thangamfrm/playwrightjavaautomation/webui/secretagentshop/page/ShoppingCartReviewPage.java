package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShoppingCartReviewPage extends SecretAgentShopAppBasePage {

  private final static Logger LOG = LoggerFactory.getLogger(ShoppingCartReviewPage.class);

  public ShoppingCartReviewPage(Page currentPage) {
    super(currentPage);
  }

  public boolean isShoppingCartReviewPage() {
    return isCheckoutButtonPresent();
  }

  public int getCountOfProductsInCart() {
    LOG.info("Getting Count of Products in the Cart!");
    try {
      List<Locator> h2Elements = currentPage.locator("h2.item-name").all();
      return h2Elements.size();
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while getting count of products in the cart! Msg: %s", e.getMessage()));
      return -1;
    }
  }

  private Locator getCartLocatorForProduct(String productName) {
    return currentPage.locator(".cart-item", new Page.LocatorOptions().setHasText(productName));
  }

  private Locator getRemoveIconForProduct(String productName) {
    Locator cartItem = getCartLocatorForProduct(productName);
    return cartItem.locator("button[aria-label=\"Remove item\"]");
  }

  public String getCartTextForProduct(String productName) {
    LOG.info(String.format("Get cart text for product: %s", productName));
    try {
      Locator cartLocator = getCartLocatorForProduct(productName);
      return cartLocator.textContent().trim();
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting cart text for product: %s , Msg: %s", productName, e.getMessage()));
    }
  }

  public boolean isRemoveIconPresentForProduct(String productName) {
    LOG.info(String.format("Checking remove icon present for product: %s", productName));
    try {
      Locator deleteIcon = getRemoveIconForProduct(productName);
      return deleteIcon.isVisible();
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking remove icon present for product: %s", productName), e.getMessage());
      return false;
    }
  }

  public boolean removeProductFromCart(String productName) {
    LOG.info(String.format("Remove product: %s from cart!", productName));
    try {
      Locator deleteIcon = getRemoveIconForProduct(productName);
      deleteIcon.click();
      return true;
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking remove icon present for product: %s, Msg: %s", productName, e.getMessage()));
      return false;
    }
  }

  public String getBasketText() {
    LOG.info("Get basket text!");
    try {
      Locator basketLocator = currentPage.locator("div[id=\"basket\"]");
      return basketLocator.textContent().trim();
    } catch (Exception e) {
      throw new RuntimeException(String.format("Error occurred while getting basket text! Msg: %s", e.getMessage()));
    }
  }

  private Locator getCheckoutLocator() {
    return currentPage.locator("button[id=\"checkout\"]");
  }

  public boolean isCheckoutButtonPresent() {
    LOG.info("Checking is checkout button present");
    try {
      Locator checkoutLocator = getCheckoutLocator();
      boolean checkoutButtonPresent = checkoutLocator.isVisible();
      LOG.info(String.format("is checkoutButtonPresent: %s", checkoutButtonPresent));
      return checkoutButtonPresent;
    } catch (Exception e) {
      LOG.error(String.format("Error occurred while checking checkout button present! Msg: %s", e.getMessage()));
      return false;
    }
  }

  public String getCheckoutButtonText() {
    LOG.info("Getting checkout button text");
    try {
      Locator checkoutLocator = getCheckoutLocator();
      String checkoutButtonText = checkoutLocator.textContent().trim();
      LOG.info(String.format("Get checkoutButton text: %s", checkoutButtonText));
      return checkoutButtonText;
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting checkout button , Msg: %s", e.getMessage()));
    }
  }


  public boolean clickCheckout() {
    LOG.info("Click checkout");
    try {
      Locator checkoutLocator = getCheckoutLocator();
      checkoutLocator.click();
      return true;
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while clicking checkout button! Msg: %s", e.getMessage()));
    }
  }

  public String getCartSubTotal() {
    LOG.info("Get Cart Sub Total!");
    try {
      Locator cartSubTotalLocator = currentPage.locator("p.cart-subtotal");
      String subTotal = cartSubTotalLocator.textContent().trim();
      LOG.info(String.format("Cart Sub Total: %s", subTotal));
      return subTotal;
    } catch (Exception e) {
      throw new RuntimeException(String.format("Error occurred while getting cart sub total! Msg: %s", e.getMessage()));
    }
  }

}
