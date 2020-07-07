package com.data;

public class StringVarifier {
	public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
}
