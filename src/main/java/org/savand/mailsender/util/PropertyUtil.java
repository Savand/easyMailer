package org.savand.mailsender.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

	public static Properties getPropertyFileFromResources(String resourceName) throws IOException {
		Properties props = new Properties();
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		try(InputStream resourceStream = loader.getResourceAsStream(resourceName + ".properties")) {
			LOGGER.debug("loading " + resourceName + ".properties...");
		
		    props.load(resourceStream);
		}
		
		LOGGER.debug("file " + resourceName + ".properties is successfully loaded from resources");
		
		return props;
	}
	
	public static void main(String[] args) throws IOException {
		LOGGER.debug(getPropertyFileFromResources("connectionDb").toString());
	}

}
