package org.tat.api.core.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.util.SerializationUtils;

/**
 * Utility Class
 * 
 * @author satish
 *
 */
public class MD5Util {
	private static final Logger logger = Logger.getLogger(MD5Util.class);

	/**
	 * This method responsible to generate the unique hash value for a object.
	 * This will be useful while comparing to pojo object for any change. This
	 * can be used in case of concurrent update scenario.
	 * 
	 * @param object
	 *            - Serializable Object
	 * @return - Hash value using MD5 Digest
	 * @throws NoSuchAlgorithmException
	 */
	public static String hash(Object object) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] data = SerializationUtils.serialize(object);
		md.update(data);

		byte byteData[] = md.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		if (logger.isDebugEnabled())
			logger.debug("Digest(in hex format):: " + hexString.toString());

		return hexString.toString();
	}

	/**
	 * This method is to generate the hash value depending on a string value.
	 * 
	 * @param string
	 *            - input string
	 * @return - Hash value using MD5 Digest
	 * @throws NoSuchAlgorithmException
	 */
	public static String hash(String string) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(string.getBytes());

		byte byteData[] = md.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		if (logger.isDebugEnabled())
			logger.debug("Digest(in hex format):: " + hexString.toString());

		return hexString.toString();
	}

	/**
	 * Generate the checksum value depending on the file content
	 * 
	 * @param file
	 *            - File Object
	 * @return - Checksum value depending on the file content.
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String checksum(File file) throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream fis = new FileInputStream(file);

		byte[] dataBytes = new byte[1024];

		int nread = 0;
		while ((nread = fis.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}
		;
		fis.close();
		byte[] mdbytes = md.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			String hex = Integer.toHexString(0xff & mdbytes[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		if (logger.isDebugEnabled())
			logger.debug("Digest(in hex format):: " + hexString.toString());
		return hexString.toString();
	}
}
