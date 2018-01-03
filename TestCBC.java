package com.whykeykey.it;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.whykeykey.it.util.WKKDebug;
import com.whykeykey.it.util.WKKHex;

public class TestCBC {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		byte[] enc = WKKHex.decode("ABD95687491FE29ADBE7BD067B62620CF376FAB792C0D3BC129A98EF93F7E975");
		byte[] iv = WKKHex.decode("02020202020202020202020202020202");
		byte[] key = WKKHex.decode("000102030405060708090A0B0C0D0E0F");
		
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		
		byte[] dec = cipher.doFinal(enc);
		
		System.out.println(new String(dec,"UTF-8") + "\n");
		WKKDebug.printHex("CBC Descrypt Hex Value", dec);
	}
}
