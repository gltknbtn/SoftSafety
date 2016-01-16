
package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String passToHash = "3113327";
		createPasswordHash(passToHash);
		
		
	}
	
	public static String createPasswordHash(String pass) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(pass.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i]&0xff)+0x100,16).substring(1));
		}
		String passwordHashCode = sb.toString();
		System.out.println(passwordHashCode);
		return passwordHashCode;
	}

}
