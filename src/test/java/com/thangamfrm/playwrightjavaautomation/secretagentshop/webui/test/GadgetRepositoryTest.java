package com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.test;

import com.opencsv.CSVReader;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import com.thangamfrm.playwrightjavaautomation.secretagentshop.webui.core.BaseSecretAgentShopAppTest;
import com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page.GadgetRepositoryPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GadgetRepositoryTest extends BaseSecretAgentShopAppTest {

  private final static Logger LOG = LoggerFactory.getLogger(GadgetRepositoryTest.class);

  protected String[] expectedCategories = "All Products, Accessories, Clothing, Food, Vehicles".split("\\s*,\\s*");

  @Test(groups = {"smoke", "regression"})
  public void testGadgetRepository() {
    LOG.info("Test Gadget Repository");
    // Step 1: Verify Home Page
    verifyPageHeader();
    verifyPageFooter();
    homePage.clickExploreButton();

    GadgetRepositoryPage gadgetRepositoryPage = new GadgetRepositoryPage(app.getCurrentPage());
    List<String> categories = gadgetRepositoryPage.getProductCategories();

    // Step 2: Verify all Category links are loading products
    for (String actualCategory : categories) {
      assertTrue(Arrays.asList(expectedCategories).contains(actualCategory), String.format("Category: %s is NOT recognized! Known Categories: %s", actualCategory, expectedCategories));
      gadgetRepositoryPage.clickProductCategory(actualCategory);
    }
  }

  @DataProvider(name = "staticCatalogProvider", parallel = false)
  public Object[][] staticCatalogProvider() {
    return new Object[][] {
        { "Accessories", "The Forgetter MK-II", "$225" },
        { "Clothing",  "Up & Away Parasol", "$125" },
        { "Vehicles", "SkyCycle X-1000", "$9000" },
        { "All Products", "The Morning Teleporter", "$40" }
    };
  }

  @DataProvider(name = "externalCatalogProvider")
  public Object[][] externalCatalogProvider() {
    String csvFile = "testdata/product_catalog.csv";
    List<Object[]> dataList = new ArrayList<>();
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csvFile)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("CSV File not found!");
      }
      // CSVReader parses records sequentially line-by-line
      try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
           CSVReader csvReader = new CSVReader(streamReader)) {
        String[] nextLine;

        // Skip the header row if your file contains column titles
        boolean isHeader = true;

        while ((nextLine = csvReader.readNext()) != null) {
          if (isHeader) {
            isHeader = false;
            continue;
          }
          // Add the array of string elements representing a single record row
          dataList.add(nextLine);
        }
      }
      // Convert list to Object double array required by TestNG
      return dataList.toArray(new Object[0][]);
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while reading external csv file: %s, Msg: %s", csvFile, e.getMessage()));
    }
  }

  @Test(dataProvider = "staticCatalogProvider")
  public void verifyStaticProductCatalog(String category, String productName, String price) {
    verifyProductInCatalog(category, productName, price);
  }

  @Test(dataProvider = "externalCatalogProvider")
  public void verifyExternalProductCatalog(String category, String productName, String price) {
    verifyProductInCatalog(category, productName, price);
  }

  protected void verifyProductInCatalog(String category, String productName, String price) {
    LOG.info(String.format("Verifying Product Exist in Catalog! Category: %s , Product Name: %s , Price: %s", category, productName, price));

    SoftAssert softAssert = new SoftAssert();

    // Goto Gadget Repository Page
    homePage.clickLink("Home");
    homePage.clickExploreButton();

    GadgetRepositoryPage gadgetRepositoryPage = new GadgetRepositoryPage(app.getCurrentPage());
    List<String> categories = gadgetRepositoryPage.getProductCategories();
    softAssert.assertTrue(Arrays.asList(expectedCategories).contains(category), String.format("Category: %s is NOT recognized!", category));

    // Goto Category Page
    softAssert.assertTrue(gadgetRepositoryPage.isProductCardExistInCategory(category, productName), String.format("Unable to find Product: %s in Category: %s", productName, category));
    String productDetails = gadgetRepositoryPage.getProductCardDetails(productName);
    softAssert.assertTrue(productDetails.contains(price), String.format("Product's Price NOT matched for Product: %s , Price: %s in Product Details: %s", productName, price, productDetails));
    LOG.info(String.format("Product Details: %s", productDetails));

    softAssert.assertAll();
    LOG.info("Test Verify Product In Catalog is complete!");
  }

}
