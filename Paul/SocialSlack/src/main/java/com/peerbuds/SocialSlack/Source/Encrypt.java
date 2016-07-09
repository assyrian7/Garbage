package com.peerbuds.SocialSlack.Source;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Encrypt {

	public static final int KEY_LENGTH = 256;
	
	public static String hash( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {
		 
	       try {
	           SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA256" );
	           PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
	           SecretKey key = skf.generateSecret( spec );
	           byte[] hash = key.getEncoded( );
	           StringBuffer hexString = new StringBuffer();

		        for (int i = 0; i < hash.length; i++) {
		            String hex = Integer.toHexString(0xff & hash[i]);
		            if(hex.length() == 1) hexString.append('0');
		            hexString.append(hex);
		        }

		        return hexString.toString();
	 
	       } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
	           throw new RuntimeException( e );
	       }
	}

}
