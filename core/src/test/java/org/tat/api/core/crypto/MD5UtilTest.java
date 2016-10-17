package org.tat.api.core.crypto;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;

public class MD5UtilTest {

	@Test
	public void testHashObject() throws NoSuchAlgorithmException {
		Assert.assertNotNull(MD5Util.hash(this.getClass()));
		
	}

	@Test
	public void testHashString() throws NoSuchAlgorithmException {
		Assert.assertNotNull(MD5Util.hash("satish"));
	}

	@Test
	public void testChecksum() throws NoSuchAlgorithmException, IOException {
		Assert.assertNotNull(MD5Util.checksum(new File(Thread.currentThread().getContextClassLoader().getResource("checksum.txt").getPath())));
	}

}
