package com.thangamfrm.playwrightjavaautomation.utils;

import com.thangamfrm.playwrightjavaautomation.exception.TestAutomationException;
import groovy.beans.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

public class TestConfig {

  private final static Logger LOG = LoggerFactory.getLogger(TestConfig.class);

  protected static TestConfig instance;
  protected static final String PROPERTIES_FILE_DIRECTORY = "config";
  protected static final String PROPERTIES_FILE_PATH = String.format("%s/testautomation.properties",
      PROPERTIES_FILE_DIRECTORY);
  protected static final String DEFAULT_OVERRIDES_FILE_PATH = String.format("%s/overrides.properties",
      PROPERTIES_FILE_DIRECTORY);

  public static final String TESTAUTOMATION_UNIQUEID_PROPERTY = "unique_id";
  public static final String TESTAUTOMATION_ENVIRONMENT_PROPERTY = "environment";

  protected Properties properties;

  private TestConfig() {
    try {
      readProperties();
    } catch (Exception e) {
      throw new TestAutomationException("Unable to read properties!", e);
    }
  }

  public static TestConfig getInstance() {
    if (instance == null) {
      instance = new TestConfig();
    }

    return instance;
  }

  private boolean isFileExist(final String filePath) {
    try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(filePath)) {
      return input != null ;
    } catch (Exception ex) {
      return false;
    }
  }

  private void readProperties() throws TestAutomationException {
    properties = new Properties();

    // Check config file exist
    if (!isFileExist(PROPERTIES_FILE_PATH)) {
      throw new TestAutomationException(String.format("Unable to load properties: %s", PROPERTIES_FILE_PATH));
    }

    // Load the base properties file
    try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
      properties.load(input);
    } catch (Exception ex) {
      throw new TestAutomationException(String.format("Unable to load properties! ex: %s", ex));
    }

    // configure the environment ( AUT - Application Under Test )
    String environment = System.getProperty(TESTAUTOMATION_ENVIRONMENT_PROPERTY);
    if (StringUtils.isBlank(environment)) {
      // Environment Variable is not set! set default environment ie. uat
      environment = getProperty(TESTAUTOMATION_ENVIRONMENT_PROPERTY);
    }
    LOG.info(String.format("Environment: %s", environment));

    if (StringUtils.isNotBlank(environment)) {
      // environment properties
      loadProperties(String.format("%s/%s.properties", PROPERTIES_FILE_DIRECTORY, environment));
      // environment override properties
      loadProperties(String.format("%s/%s_overrides.properties", PROPERTIES_FILE_DIRECTORY, environment));
    } else {
      throw new TestAutomationException("Invalid Parameters!!! Specify Environment! of Application Under Test.");
    }

    // overall overrides
    loadProperties(DEFAULT_OVERRIDES_FILE_PATH);

    // set unique id
    String uniqueId = properties.getProperty(TESTAUTOMATION_UNIQUEID_PROPERTY);
    if (StringUtils.isBlank(uniqueId)) {
      uniqueId = getRandomText();
      properties.setProperty(TESTAUTOMATION_UNIQUEID_PROPERTY, uniqueId);
    }

  }

  public String getUniqueId() {
    return properties.getProperty(TESTAUTOMATION_UNIQUEID_PROPERTY);
  }

  private void loadProperties(final String filePath) {
    if (!isFileExist(filePath)) {
      return;
    }
    // Load the properties file
    try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(filePath)) {
      Properties tmpProperties = new Properties();
      tmpProperties.load(input);
      properties.putAll(tmpProperties);
    } catch (Exception ex) {
      throw new TestAutomationException(String.format("Unable to load properties:%s , ex: %s", filePath, ex));
    }
  }

  private void readSystemProperty(String propertyName) {
    String propertyValue = System.getProperty(propertyName);
    if (StringUtils.isNotBlank(propertyValue)) {
      properties.setProperty(propertyName, propertyValue);
    }
  }

  public int getPropertyAsInt(String property) {
    return Integer.parseInt(getProperty(property));
  }

  public boolean getPropertyAsBoolean(String property) {
    return Boolean.parseBoolean(getProperty(property));
  }

  public String getProperty(String property) {
    String value = properties.getProperty(property);
    if (StringUtils.isBlank(value)) {
      return new String();
    }
    StringBuilder sb = new StringBuilder(value);
    while (true) {
      if (sb.indexOf("${") > -1) {
        int start = value.indexOf("${") + 2;
        int end = value.indexOf("}");
        String tmpValue = getProperty(value.substring(start, end));
        sb.replace(start - 2, end + 1, tmpValue);
      } else {
        break;
      }
    }
    return sb.toString().trim();
  }

  public String getRandomText() {
    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMyy", Locale.ENGLISH);
    String textDate = date.format(formatter);
    return textDate + "-" + getRandomText(4);
  }

  public String getRandomText(int length) {
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      // Pick a random index from the alphabet string
      int index = random.nextInt(alphabet.length());
      sb.append(alphabet.charAt(index));
    }
    return sb.toString();
  }


}