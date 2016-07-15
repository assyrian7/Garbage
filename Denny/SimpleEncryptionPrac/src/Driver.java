import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import SimpleEncrypt.SimpleEncodeDecode;


public class Driver {

	public static void main(String[] args) throws UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {

		Scanner reader = new Scanner(System.in);
		System.out.print("Enter text to be encrypted: ");
		String plainText = reader.nextLine();
		System.out.print("Enter passphrase: ");
		String passphrase = reader.nextLine();
		SecretKey myKey = SimpleEncodeDecode.getSecretKey(passphrase);
		
		System.out.println("Key: " + myKey);
		System.out.println("Original Plain Text: " + plainText);
		
		String encryptedText = SimpleEncodeDecode.encrypt(plainText, myKey);
		System.out.println("Encrypted Text: " + encryptedText);
		
		String decryptedText1 = SimpleEncodeDecode.decrypt(encryptedText, myKey);
		System.out.println("Decoded: " + decryptedText1);
		
		reader.close();
	}
}
