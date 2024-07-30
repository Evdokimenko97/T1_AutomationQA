package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    public synchronized static String getProperty(String key) {
        try {
            properties.load(new FileInputStream("src\\test\\resources\\config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

}