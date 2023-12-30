package Utilities;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

public class Property {

    public String getPropertyPath(String propertyFileName) throws FileNotFoundException {
        String returnValue;
        String basePath = System.getProperty("user.dir") + "\\src\\main\\java";
        File manual = new File(basePath + File.separator + "Properties" + File.separator + propertyFileName);
        // find the valid directory path for this property file
        if (manual.exists()) {
            returnValue = manual.getAbsolutePath();
        } else {
            throw new FileNotFoundException("File not found:  " + propertyFileName);
        }
        // now, take out the property file name out of returnValue -- we just want to
        // return the path
        returnValue = returnValue.replace(File.separator + propertyFileName, "");
        return returnValue;

    }

    public String getPropertyValue(String fileName, String propertyName) {
        try {
            // declaring local variables
            Properties prop = new Properties();
            String propertyDir = getPropertyPath(fileName);
            // open file
            InputStream input = new FileInputStream(propertyDir + File.separator + fileName);
            // load the file
            prop.load(input);
            // find the value with the listName
            return prop.getProperty(propertyName);
        } catch (Exception e) {
            Log.printError("Unable to get property value");
            return "";
        }

    }

    public static void setReportPropertyValue(String propertyName, String propertyValue) {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(System.getProperty("user.dir") + "\\src\\main\\java\\Properties\\Report.properties");
            config.setProperty(propertyName, propertyValue);
            config.save();
        } catch (Exception e) {
            Log.printInfo("Error while reading/writing to report property file");
        }
    }

    public static String getReportPropertyValue(String propertyName) {
        String value = "";
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(System.getProperty("user.dir") + "\\src\\main\\java\\Properties\\Report.properties");
            value = config.getString(propertyName);
            config.save();
            return value;
        } catch (Exception e) {
            Log.printInfo("Error while reading/writing to report property file");
            return value;
        }
    }

    public static void clearReportPropertyFile() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(System.getProperty("user.dir") + "\\src\\main\\java\\Properties\\Report.properties");
            config.clear();
            config.save();
        } catch (Exception e) {
            Log.printInfo("Error while reading/writing to report property file");
        }
    }

    public static List<String> getReportProperties() {
        List<String> propertyKeys = new ArrayList<>();
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(System.getProperty("user.dir") + "\\src\\main\\java\\Properties\\Report.properties");
            Iterator<String> keys = config.getKeys();
            while (keys.hasNext()) {
                propertyKeys.add(keys.next());
            }
            return propertyKeys;
        } catch (Exception e) {
            Log.printInfo("Error while reading/writing to report property file");
            return propertyKeys;
        }
    }
}
