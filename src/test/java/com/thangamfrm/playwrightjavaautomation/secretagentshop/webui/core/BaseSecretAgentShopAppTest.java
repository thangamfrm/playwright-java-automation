package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core;

import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test.E2EProductPurchaseFlowTest;
import com.thangamfrm.playwrightjavaautomation.test.base.BaseTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.core.SecretAgentShopApp;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.*;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseSecretAgentShopAppTest extends BaseTest {

  private final static Logger LOG = LoggerFactory.getLogger(E2EProductPurchaseFlowTest.class);

  protected SecretAgentShopApp app;
  protected HomePage homePage;

  @BeforeClass
  public void setUp() {
    app = new SecretAgentShopApp();
    homePage = app.getHomePage();
  }

  protected void verifyPageHeader() {
    // Verify Page Title
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(homePage.getPageTitle(), "Demo Store","Page title didn't match!");
    softAssert.assertTrue(homePage.isBriefCaseIconDisplayed(), "Briefcase Icon NOT found!");
    softAssert.assertTrue(homePage.isHomeLinkDisplayed(), "Home link NOT found in Page Header!");
    softAssert.assertTrue(homePage.isGadgetRepositoryLinkDisplayed(), "Gadget Repository link NOT found in Page Header!");
    softAssert.assertTrue(homePage.isShoppingCartDisplayed(), "Shopping Cart NOT found in Page Header!");
    softAssert.assertAll();
  }

  protected void verifyBreadcrumb(SecretAgentShopAppBasePage page) {
    List<String> stepTexts = page.getBreadcrumbTrailStepTexts();
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(stepTexts.size(), 3, "Breadcrumb steps count didn't match!");
    softAssert.assertEquals(stepTexts.getFirst(), "Rendezvous Location", "Breadcrumb step (Rendezvous Location) didn't match!");
    softAssert.assertEquals(stepTexts.get(1), "Covert Delivery", "Breadcrumb step (Covert Delivery) didn't match!");
    softAssert.assertEquals(stepTexts.get(2), "Mission Funding", "Breadcrumb step (Mission Funding) didn't match!");

    String step = "Rendezvous Location";
    softAssert.assertTrue(page.isBreadcrumbStepHighlighted(step), String.format("Step: %s NOT highlighted!", step));
    step = "Covert Delivery";
    softAssert.assertFalse(page.isBreadcrumbStepHighlighted(step), String.format("Step: %s should NOT be highlighted!", step));
    step = "Mission Funding";
    softAssert.assertFalse(page.isBreadcrumbStepHighlighted(step), String.format("Step: %s should NOT be highlighted!", step));
    softAssert.assertAll();
  }

  protected void verifyShoppingCartReviewPage(ShoppingCartReviewPage page) {
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(page.isShoppingCartReviewPage(), "Shopping Cart Review is NOT the current page!");
    softAssert.assertTrue(page.isCheckoutButtonPresent(), "Checkout Button NOT found!");
    softAssert.assertEquals(page.getCheckoutButtonText(), "Start Equipment Requisition", "Checkout Button Text didn't match!");
    softAssert.assertAll();
  }

  protected void verifyShippingMethodPage(ShippingMethodPage page) {
    // Verify Warning Message in Shipping Method Page
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(page.isWarningMessageDisplayed(), "Warning Message NOT displayed in Shipping Method Page!");
    softAssert.assertEquals(page.getWarningMessage(), "Warning This site is for demonstration/educational purposes only, do not enter personal details", "Warning message NOT matching in Shipping Method Page!");

    List<String> shippingMethods = page.getShippingMethods();
    softAssert.assertEquals(shippingMethods.size(), 2, "Shipping Method Count didn't match!");

    String shippingMethod = "Priority Mail";
    softAssert.assertEquals(shippingMethods.getFirst(), shippingMethod, String.format("Shipping Method: %s NOT found!", shippingMethod));

    shippingMethod = "Priority Mail Express";
    softAssert.assertEquals(shippingMethods.get(1), shippingMethod, String.format("Shipping Method: %s NOT found!", shippingMethod));
    softAssert.assertAll();
  }

  protected void verifyBillingPage(BillingPage page) {
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(page.isPurchaseButtonPresent(), "Purchase Button NOT found!");
    softAssert.assertTrue(page.isBackToCartButtonPresent(), "Back to Cart Button NOT found!");

    // Verify Warning Message in Billing Page
    assertTrue(page.isWarningMessageDisplayed(), "Warning Message NOT displayed in Shipping Method Page!");
    softAssert.assertEquals(page.getWarningMessage(), "Warning This site is for demonstration/educational purposes only, do not enter personal details", "Warning message NOT matching in Shipping Method Page!");

    String step = "Rendezvous Location";
    softAssert.assertTrue(page.isBreadcrumbStepHighlighted(step), String.format("Step: %s NOT highlighted!", step));
    step = "Covert Delivery";
    softAssert.assertTrue(page.isBreadcrumbStepHighlighted(step), String.format("Step: %s should NOT be highlighted!", step));
    step = "Mission Funding";
    softAssert.assertTrue(page.isBreadcrumbStepHighlighted(step), String.format("Step: %s should NOT be highlighted!", step));
    softAssert.assertAll();
  }

  protected void verifyProductInOrderDetails(OrderSummaryPage page, String productName, Map<String,String> fsbd) {
    SoftAssert softAssert = new SoftAssert();
    Map<String, List<String>> map = page.getProductDetailsInOrderSummary();
    List<String> filteredList = map.get(productName);
    // The first element in the list: Product Name. We can ignore!
    String quantity = filteredList.get(1);
    String productTotalPrice = filteredList.get(2);
    String pricePerProduct = filteredList.get(3);

    String expectedQuantity = fsbd.get(String.format("%s_quantity", productName));
    String expectedProductTotalPrice = fsbd.get(String.format("%s_product_total_price", productName));
    String expectedPricePerProduct = fsbd.get(String.format("%s_price_per_product", productName));

    softAssert.assertEquals(quantity, expectedQuantity, "Product Quantity didn't match!");
    softAssert.assertEquals(productTotalPrice, expectedProductTotalPrice, "Product price didn't match!");
    softAssert.assertEquals(pricePerProduct, expectedPricePerProduct, "Price per product didn't match!");
    softAssert.assertAll();
  }

  protected void verifyOrderDetails(OrderSummaryPage page, Map<String,String> fsbd) {
    SoftAssert softAssert = new SoftAssert();
    String subTotal = fsbd.get("order_subtotal");
    String shippingCharges = fsbd.get("order_shipping_charges");
    String taxes = fsbd.get("order_taxes");
    String orderTotal = fsbd.get("order_total");
    softAssert.assertEquals(page.getOrderSubTotal(), subTotal, String.format("Sub Total: %s is NOT matching!", subTotal));
    softAssert.assertEquals(page.getOrderShipping(), shippingCharges, String.format("Shipping Charges: %s is NOT matching!", shippingCharges));
    softAssert.assertEquals(page.getOrderTax(), taxes, String.format("Taxes: %s is NOT matching!", taxes));
    softAssert.assertEquals(page.getOrderTotal(), orderTotal, String.format("Order Total: %s is NOT matching!", orderTotal));
    softAssert.assertAll();
  }

  protected void verifyPageFooter() {
    SoftAssert softAssert = new SoftAssert();
    // Verify Page Footer
    String sectionTitle = "Quick Links";
    List<String> linkTexts = homePage.getLinkTextsUnderSectionInPageFooter(sectionTitle);
    softAssert.assertEquals(linkTexts.getFirst(), "Gadget Repository", String.format("First link under section: %s in page footer didn't match!", sectionTitle));
    softAssert.assertEquals(linkTexts.get(1), "About", String.format("Second link under section: %s in page footer didn't match!", sectionTitle));

    sectionTitle = "Tools";
    linkTexts = homePage.getLinkTextsUnderSectionInPageFooter(sectionTitle);
    softAssert.assertEquals(linkTexts.getFirst(), "Information", String.format("First link under section: %s in page footer didn't match!", sectionTitle));
    softAssert.assertEquals(linkTexts.get(1), "Topology", String.format("Second link under section: %s in page footer didn't match!", sectionTitle));

    List<String> contactMethods = homePage.getContactMethodsInPageFooter();
    softAssert.assertEquals(contactMethods.getFirst(), "Email", "First contact method section: CONTACT in page footer didn't match!");
    softAssert.assertEquals(contactMethods.get(1), "Phone", "Second contact method under section: CONTACT in page footer didn't match!");
    softAssert.assertAll();
  }

  public Map<String,String> generateFakeShippingAndBillingDetails() {
    Map<String,String> fsbd = new HashMap<>();

    // Shipping Details
    Faker faker = new Faker();
    fsbd.put("first_name", faker.name().firstName());
    fsbd.put("last_name", faker.name().lastName());
    fsbd.put("email_address", String.format("%s_%s@sdet.com", fsbd.get("first_name"), fsbd.get("last_name")));
    fsbd.put("street_address", faker.address().streetAddress());
    fsbd.put("city",faker.address().city());
    fsbd.put("state",faker.address().stateAbbr());
    fsbd.put("zip",faker.address().zipCode());

    // Billing Details
    fsbd.put("full_name", String.format("%s %s",fsbd.get("first_name"), fsbd.get("last_name")));
    fsbd.put("card_number","1234567890123456");
    fsbd.put("expiry_date", "12/39");
    fsbd.put("cvv","654");

    return fsbd;
  }

  protected void verifyOrderSummaryPage(OrderSummaryPage page) {
    SoftAssert softAssert = new SoftAssert();
    // Verify Warning Message in Order Summary Page
    softAssert.assertTrue(page.isWarningMessageDisplayed(), "Warning Message NOT displayed in Order Summary Page!");
    softAssert.assertEquals(page.getWarningMessage(), "Warning This site is for demonstration/educational purposes only, this order is not genuine", "Warning message NOT matching in Order Summary Page!");

    List<String> stages = page.getProgressBarStages();
    softAssert.assertEquals(stages.size(), 3, "Stages size count is NOT 3");
    String stage = "Order placed";
    softAssert.assertEquals(stages.getFirst(), stage, String.format("First stage: %s is NOT matching!", stage));
    softAssert.assertTrue(page.isProgressBarStageHighlighted(stage), String.format("Stage: %s is NOT Highlighted!", stage));
    stage = "Processing";
    softAssert.assertEquals(stages.get(1), stage, String.format("Second stage: %s is NOT matching!", stage));
    softAssert.assertFalse(page.isProgressBarStageHighlighted(stage), String.format("Stage: %s should NOT be Highlighted!", stage));
    stage = "Shipped";
    softAssert.assertEquals(stages.get(2), stage, String.format("Third stage: %s is NOT matching!", stage));
    softAssert.assertFalse(page.isProgressBarStageHighlighted(stage), String.format("Stage: %s should NOT be Highlighted!", stage));
    softAssert.assertAll();
  }

  @AfterClass
  public void tearDown() {
    app.closeBrowser();
  }

}
