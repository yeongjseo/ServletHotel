package common;

import java.util.HashMap;
import java.util.Map;


public class MimeType {
	
	private static Map<String, String> mimeMap;
	
	static {
		mimeMap = new HashMap<String, String>();		
		mimeMap.put("JPG", "image/jpeg");
		mimeMap.put("GIF", "image/gif");
		mimeMap.put("BMP", "image/bmp");
		mimeMap.put("PNG", "image/png");

	}
	
	public static String getMediaType(String ext){
		return mimeMap.get(ext.toUpperCase());
	}
}



