package soulfoam.arenashared.main.lobbyutil;

import java.security.MessageDigest;
import java.util.Random;

public class LobbyUtil {

	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static String boolToIntString(boolean bool){
		String r = bool ? "1" : "0";
		return r;
	}
	
	public static boolean stringIntToBool(String bool){
		boolean r = false;
		r = bool.equalsIgnoreCase("1") ? true : false;
		return r;
	}
	
	public static Integer parseInt(String text) {
		if (text == null){
			return -999;
		}
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return -999;
		}
	}
}
