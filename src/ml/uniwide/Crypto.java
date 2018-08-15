/**
 * The Crypto class for IPBin
 * @author BowStar
 * @version 1.0
 */

package ml.uniwide;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	//generate random key
	public String generateKey() {
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance("AES");
			keygen.init(256);
			Key key = keygen.generateKey();
			
			//convert key to string
			return Base64.getEncoder().encodeToString(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//encode
	public String encodeMessage(String key, String message) {
		//convert string to key
		byte[] decodedKey = Base64.getDecoder().decode(key);
		SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		
		//encode message
		Cipher cipher;
		byte[] encrypted = null;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encrypted = cipher.doFinal(message.getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		//convert encoded message to string
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	//decode message
	public String decodeMessage(String key, String message) {
		//convert string to key
		byte[] decodedKey = Base64.getDecoder().decode(key);
		SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		byte[] crypted = Base64.getDecoder().decode(message);
		
		//decode
		String decodedMessage = "Something went wrong.";
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] cipherData2 = cipher.doFinal(crypted);
			decodedMessage = new String(cipherData2);
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		} catch (InvalidKeyException e) {
		}
		return decodedMessage;
	}
}
