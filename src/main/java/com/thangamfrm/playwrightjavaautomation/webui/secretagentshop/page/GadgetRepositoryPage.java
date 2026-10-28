package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GadgetRepositoryPage extends SecretAgentShopAppBasePage {

  private final static Logger LOG = LoggerFactory.getLogger(GadgetRepositoryPage.class);

  public GadgetRepositoryPage(Page currentPage) {
    super(currentPage);
  }

  public Locator getProductCategoriesLocator() {
    return currentPage.locator("ul.category-menu li");
  }

  public List<String> getProductCategories() {
    LOG.info("Getting product categories!");
    try {
      Locator locator = getProductCategoriesLocator();
      List<String> categories = new ArrayList<>();
      for (int i = 0; i < locator.count(); i++) {
        String category = locator.nth(i).textContent().trim();
        LOG.info(String.format("Product Category: %s", category));
        categories.add(category);
      }
      LOG.info(String.format("Count of product categories: %s", categories.size()));
      return categories;
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while getting all product categories! Msg: %s", e.getMessage()));
    }
  }

  public void clickProductCategory(String category) {
    LOG.info("Clicking product category!");
    try {
      Locator locator = getProductCategoriesLocator();
      for (int i = 0; i < locator.count(); i++) {
        String currCategory = locator.nth(i).textContent().trim();
        if (currCategory.equals(category)) {
          LOG.info(String.format("Under Categories, found Product Category: %s", currCategory));
          locator.nth(i).click();
          return;
        }
      }
      throw new TestAutomationException(String.format("Unable to find category link: %s", category));
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while clicking product category! Msg: %s", e.getMessage()));
    }
  }

  public Locator getPaginationLinksLocator() {
    return currentPage.locator("nav[aria-label=\"Pagination\"] a");
  }

  public boolean clickPageNumberLinkInPagination(String pageNumber) {
    LOG.info(String.format("In Pagination, clicking link with page number: %s",pageNumber));
    try {
      Locator locator = getPaginationLinksLocator();
      for (int i = 0; i < locator.count(); i++) {
        String currLink = locator.nth(i).textContent().trim();
        if (currLink.equals(pageNumber)) {
          LOG.info(String.format("In Pagination: found link with number: %s", pageNumber));
          locator.nth(i).click();;
          return true;
        }
      }
      throw new RuntimeException(String.format("In Pagination, unable to find page number link: %s", pageNumber));
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking pagination link exist with number: %s , Msg: %s", pageNumber, e.getMessage()));
    }
  }

  public boolean isProductCardExistInCategory(String category, String productName) {
    LOG.info(String.format("Checking Product: %s exist in Category: %s", productName, category));
    try {
      // Click the Category Link
      Locator locator = getProductCategoriesLocator();
      for (int i = 0; i < locator.count(); i++) {
        String currCategory = locator.nth(i).textContent().trim();
        if (currCategory.equals(category)) {
          LOG.info(String.format("Under Categories, found Product Category: %s", currCategory));
          locator.nth(i).click();
        }
      }

      // Check Product Exist
      int nextPageNumber = 1;
      do {
        if (isProductCardExist(productName)) {
          return true;
        }
        nextPageNumber++;
      } while (clickPageNumberLinkInPagination(String.format("%s", nextPageNumber)));

      return false;
    } catch (Exception e) {
      throw new TestAutomationException(String.format("Error occurred while checking Product: %s exist in Category:%s , Msg: %s", productName, category, e.getMessage()));
    }
  }

}
