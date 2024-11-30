package com.ritesh.dineflow.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Random;

public final class PasswordUtils {

	private PasswordUtils() {
	}

	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "0123456789";
	private static final String SPECIAL_CHAR = "!@#$";
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private static final String ALL_CHAR = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHAR;

	private static final Random random = new SecureRandom();

	public static String generateRandomPassword(int length) {
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(ALL_CHAR.length());
			password.append(ALL_CHAR.charAt(randomIndex));
		}
		return password.toString();
	}

	public static String generateRandomEncodedPassword(int length) {
		String password = generateRandomPassword(length);
		return encodePassword(password);
	}

	public static String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public static boolean matchPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
