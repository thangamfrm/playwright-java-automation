package com.thangamfrm.playwrightjavaautomation.webui.secretagentshop.page;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ShippingAddressPage extends SecretAgentShopAppBasePage {

  private final static Logger LOG = LoggerFactory.getLogger(ShippingAddressPage.class);

  public ShippingAddressPage(Page currentPage) {
    super(currentPage);
  }

  public boolean isShippingAddressPage() {
    String step = "Rendezvous Location";
    return doesHeaderExist(step);
  }

  public void enterStreetAddress(String streetAddress) {
    LOG.info(String.format("Entering Street Address: %s", streetAddress));
    enterTextInFieldUsingNameAttr("address1", streetAddress);
  }

  public void fillShippingDetails(Map<String,String> fsbd) {
    enterTextInField("First Name", fsbd.get("first_name"));
    enterTextInField("Last Name", fsbd.get("last_name"));
    // Note: The label for Street Address has newline at the end
    enterStreetAddress(fsbd.get("street_address"));
    enterTextInField("City", fsbd.get("city"));
    enterTextInField("State/Province", fsbd.get("state"));
    enterTextInField("ZIP", fsbd.get("zip"));
    enterTextInField("E-mail", fsbd.get("email_address"));
  }

}
