package com.whykeykey.it.ciper;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SymCipher {

	public static byte[] AESEncrypt(byte[] plain, byte[] key, byte[] iv) throws Exception {
		
		if(plain == null) throw new Exception("Plain MSG is null");
		if(key.length != 16 || iv.length != 16) throw new Exception("Invalid length of key or iv");
		
		byte[] padPlain = padding(plain), finalEnc = new byte[padPlain.length];
		
		for(int i = 0; i < padPlain.length; i += 16) {
			
			byte[] tmp = new byte[16];
			
			System.arraycopy(padPlain, i, tmp, 0, tmp.length);
			
			byte[] encBlock = blockCipher(tmp, key, iv);
			iv = encBlock;
			
			System.arraycopy(encBlock, 0, finalEnc, i, encBlock.length);
		}
		
		return finalEnc;
	}
	
	public static byte[] AESDecrypt(byte[] enc, byte[] key, byte[] iv) throws Exception {
		
		if(enc == null) throw new Exception("Encrypted MSG is null");
		if(key.length != 16 || iv.length != 16) throw new Exception("Invalid length of key or iv");
		
		byte[] plain = new byte[enc.length];
		
		for(int i = 0; i < enc.length; i += 16) {
			
			byte[] tmp = new byte[16];
			
			System.arraycopy(enc, i, tmp, 0, tmp.length);
			
			byte[] tmpiv = tmp;
			
			byte[] decBlock = blockDecrypt(tmp, key, iv);
			iv = tmpiv;
			
			System.arraycopy(decBlock, 0, plain, i, decBlock.length);
		}

		byte[] unpadPlain = unpadding(plain);
		
		return unpadPlain;
	}
	
	private static byte[] padding(byte[] plain) {
		
		int padLen = 16 - (plain.length % 16);
		
		if(padLen == 0) padLen = 16;
		
		byte[] padPlain = new byte[plain.length + padLen];
		
		System.arraycopy(plain, 0, padPlain, 0, plain.length);
		
		for(int i = plain.length; i < padPlain.length; i++)
			padPlain[i] = (byte)padLen;
		
		return padPlain;
	}
	
	private static byte[] unpadding(byte[] plain) {
		
		int unpadLen = plain[plain.length-1];
		byte[] unpadPlain = new byte[plain.length - unpadLen];
		
		System.arraycopy(plain, 0, unpadPlain, 0, unpadPlain.length);
		
		return unpadPlain;
	}
	
	private static byte[] blockCipher(byte[] block, byte[] key, byte[] iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		byte[] tmp = new byte[16];
		int i = 0;
		
		for(byte b : block)
			tmp[i] = (byte) (b ^ iv[i++]);
		
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		byte[] encBlock = cipher.doFinal(tmp);
		
		return encBlock;
	}
	
	private static byte[] blockDecrypt(byte[] block, byte[] key, byte[] iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		SecretKey secretKey = new SecretKeySpec(key, "AES");
		
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		byte[] tmp = cipher.doFinal(block), decBlock = new byte[16];
		int i = 0;
		
		for(byte b : tmp)
			decBlock[i] = (byte) (b ^ iv[i++]);

		return decBlock;
	}
}
