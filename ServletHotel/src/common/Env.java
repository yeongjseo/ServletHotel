package common;

import java.util.HashMap;
import java.util.Map;


public class Env {
	private static Map<String, String> envMap = new HashMap<>();
	public static synchronized void put(String key, String value) {
		envMap.put(key, value);
	}
	public static synchronized String get(String key) {
		return envMap.get(key);
	}
	
}
