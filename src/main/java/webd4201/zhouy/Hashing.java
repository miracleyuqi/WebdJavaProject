package webd4201.zhouy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing 
{
	public static String hashString(String originalPassword) throws NoSuchAlgorithmException 
	{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(originalPassword.getBytes());
        return bytesToHex(digest);
    }

	
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

