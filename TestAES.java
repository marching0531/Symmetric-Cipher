package com.whykeykey.it;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.whykeykey.it.ciper.SymCipher;
import com.whykeykey.it.util.WKKHex;

public class TestAES {

	public static void main(String[] args) throws Exception {
		
		byte[] key = WKKHex.decode("01010101010101010101010101010101");
		byte[] iv = WKKHex.decode("02020202020202020202020202020202");
		byte[] enc = WKKHex.decode("7065AB7B96594B2ABC0801B26A32F25F7B94E007EB175BF05436364C5CF0B653FEE1958847C7379BDCEDB38EEB2E8922");
		//byte[] plain = WKKHex.decode("000102030405060708090A0B0C0D0E0F000102030405060708090A0B0C0D0E0F00010203040506070808080808080808");
		
		//byte[] enc = SymCipher.AESEncrypt(plain, key, iv);
		
		byte[] plain = SymCipher.AESDecrypt(enc, key, iv);
		System.out.println(WKKHex.encode(plain));
		
	}

}
