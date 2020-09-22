package cn.seczone.ssdlc.gateway.core.common.tools;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * @author XieShaoping
 */
@Slf4j
public class Utils {

	public static boolean isEmpty(String str) {
		return str == null || str.length() < 1;
	}
	public static boolean isEmpty(List<?> list) {
		return list == null || list.size() < 1;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String loadPropertiesByKey(String key) {
		Properties properties = loadProperties("application.yml");
		return properties.get(key).toString();
	}
	public static Properties loadProperties(String propertiesFileName) {
		try {
			Properties properties = new Properties();
			InputStream fis = Utils.class.getClassLoader().getResourceAsStream(propertiesFileName);
			properties.load(fis);
			return properties;
		} catch (IOException e) {
			log.error("Configuration file " + propertiesFileName + " is not found");
			return new Properties();
		}
	}

	public static String getUUID() {
		return replaceUUID(UUID.randomUUID());
	}

	public static String replaceUUID(Object uuid) {
		return uuid.toString().replaceAll("\\-", "");
	}

}
