package SimpleEncrypt;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SimpleEncodeDecode
{
	private static Cipher cipher;
	private static final String cipherType = "AES";
	
	private static SecretKey getSecretKey(String key)
	{
		MessageDigest digest = null;
		try {
			 digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		digest.update(key.getBytes());
		byte[] byteKey = digest.digest();
		SecretKey secretKey = new SecretKeySpec(byteKey, cipherType);
		return secretKey;
	}
	
	public static String encrypt(String input, String passphrase)
	{
		try {
			cipher = Cipher.getInstance(cipherType);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(passphrase));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes()));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String decrypt(String encryptedText, String passphrase) 
	{
		try {
			cipher = Cipher.getInstance(cipherType);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(passphrase));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}




