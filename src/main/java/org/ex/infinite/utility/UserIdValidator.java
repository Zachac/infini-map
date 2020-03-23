package org.ex.infinite.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.validation.ValidationException;

public class UserIdValidator {

	private static MessageDigest digest; static {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void validate(String secret, String userId) {
		var secretBytes = Base64.getMimeDecoder().decode(secret);
		var userIdBytes = Base64.getMimeDecoder().decode(userId);
		var expectedBytes = digest.digest(secretBytes);

		if (!Arrays.equals(expectedBytes, userIdBytes)) {
			throw new ValidationException("Secret doesn't match userId.");
		}
	}
	
}
