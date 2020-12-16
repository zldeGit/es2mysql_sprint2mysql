package wg_media_data.es2ka.util;

import java.security.MessageDigest;

public class MD5Util {

	private static MessageDigest digest;

	static {
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static String createMd5(String url) {
		if (url == null || url.trim().equals("")) {
			return null;
		}
		byte[] bytes = digest.digest(url.getBytes());
		StringBuilder output = new StringBuilder(bytes.length);
		for (Byte entry : bytes) {
			output.append(String.format("%02x", entry));
		}
		digest.reset();
		return output.toString();
	}

	public static void main(String[] args) {
		String str = "https://www.youtube.com/watch?v=2qKrGQVMTPg";
		
		System.out.println(MD5Util.createMd5(str));
	}

}
