package org.ex.infinite.utils;

public class StringUtils {

	
	public static String nameCase(String value) {
		
		if (value.length() == 0) {
			return value;
		}
		
		char[] bytes = value.toCharArray();
		
		boolean capitalize = true;
		for (int i = 0; i < bytes.length; i++) {
			
			if (capitalize == true) {
				bytes[i] = Character.toUpperCase(bytes[i]);
				capitalize = false;
			} else {
				bytes[i] = Character.toLowerCase(bytes[i]);
			}
			
			if (Character.isWhitespace(bytes[i])) {
				capitalize = true;
			}
		}
		
		return new String(bytes);
	}
	
}
