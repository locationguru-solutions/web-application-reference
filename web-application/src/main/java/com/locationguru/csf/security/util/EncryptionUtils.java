package com.locationguru.csf.security.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.digest.PooledStringDigester;

/**
 * Provides utilities for hashing, encryption and decryption.
 * <p>
 * - Hashing is performed using 512-bit version of SHA-2 algorithm.
 * - Encryption and decryption is performed using AES algorithm.
 *
 * @since 0.1.0
 */
public abstract class EncryptionUtils
{
	private static final Logger logger = LogManager.getLogger(EncryptionUtils.class);

	private static final String SECRET_KEY = "Fd?.P2d75y)Ye6SrRJC9no#%n_E#QCAVnmO()ZAIp[g>VDGGR}<M5Nt>EM4tTBkW!^HXxG.7VI=x[";
	private static final byte[] SECRET_KEY_BYTES = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
	private static final String CIPHER = "AES/ECB/PKCS5Padding";
	private static final String ENCRYPTION_ALGORITHM = "AES";

	private static final PooledStringDigester primaryDigester = new PooledStringDigester();

	static
	{
		primaryDigester.setPoolSize(16);

		primaryDigester.setAlgorithm("SHA-512");
		primaryDigester.setIterations(40);
		primaryDigester.setSaltSizeBytes(16);
	}

	/**
	 * Generates a 512-bit hash of the provided {@code value}.
	 *
	 * @param value for which hash needs to be generated.
	 * @return hash for provided {@code value}.
	 */
	public static String hash(final String value)
	{
		return primaryDigester.digest(value);
	}

	/**
	 * Compares and verifies if provided {@code value} matches provided {@code hash}.
	 *
	 * @param value which needs to be compared with its {@code hash}.
	 * @param hash  which needs to be compared with its {@code value}.
	 * @return {@code true} if provided {@code value} and {@code hash} matches; {@code false} otherwise.
	 */
	public static boolean match(final String value, final String hash)
	{
		return primaryDigester.matches(value, hash);
	}

	/**
	 * Generates an AES encrypted representation of provided {@code value}.
	 *
	 * @param value for which needs to be encrypted.
	 * @return AES encrypted representation of provided {@code value}.
	 */
	public static String encrypt(final String value)
	{
		try
		{
			final SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY_BYTES, ENCRYPTION_ALGORITHM);
			final Cipher cipher = Cipher.getInstance(CIPHER);

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
		}
		catch (final Exception e)
		{
			logger.debug(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Generates an original value from its provided AES encrypted representation {@code value}.
	 *
	 * @param value AES encrypted representation of the original value.
	 * @return original value from its provided AES encrypted representation {@code value}.
	 */
	public static String decrypt(final String value)
	{
		try
		{
			final SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY_BYTES, ENCRYPTION_ALGORITHM);
			final Cipher cipher = Cipher.getInstance(CIPHER);

			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
		}
		catch (final Exception e)
		{
			logger.debug(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void main(final String[] args)
	{
		logger.info("{}", hash(""));
	}

}
