package com.molinacorp.simplerestapp.utility;

/**
 * String utilities.
 * @author dm05765
 *
 */
public final class StringUtil {
	
	private StringUtil() { }
	
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
	
}
