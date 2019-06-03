package soulfoam.arenaserver.main.resources;

import java.util.Random;

public class Res {

    public static Random rand = new Random();
    public static int encryptOffSet = 23;
    
    public static byte TEAM_D = 0;
    public static byte TEAM_A = 1;
    public static byte TEAM_BOTH;

    
	public static final byte SLOT_0 = 0;
	public static final byte SLOT_1 = 1;
	public static final byte SLOT_2 = 2;
	public static final byte SLOT_3 = 3;
	public static final byte SLOT_4 = 4;
	public static final byte SLOT_MISC = 5;
   
	public static final int MESSAGE_PLAYERKILLEDPLAYER = 0;
	public static final int MESSAGE_TEXT = 1;
	
	public static MapResource MAP_RESOURCE;
	
	public Res(){
		
		MAP_RESOURCE = new MapResource();
	
	}
	
	public static int randInt(int min, int max) {

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	public static String getMoveSpeedDisplay(float moveSpeed){
		String moveSpeedDisplay = String.valueOf(Math.round(moveSpeed*1000));
		return moveSpeedDisplay;
	}
	
	public static boolean inBounds(int index, int size){
		return (index >= 0) && (index < size);
	}
	
	public static String decode(String enc, int offset) {
	    return encode(enc, 26-offset);
	}
	
	public static String encode(String enc, int offset) {
	    offset = offset % 26 + 26;
	    StringBuilder encoded = new StringBuilder();
	    for (char i : enc.toCharArray()) {
	        if (Character.isLetter(i)) {
	            if (Character.isUpperCase(i)) {
	                encoded.append((char) ('A' + (i - 'A' + offset) % 26 ));
	            } else {
	                encoded.append((char) ('a' + (i - 'a' + offset) % 26 ));
	            }
	        } else {
	            encoded.append(i);
	        }
	    }
	    return encoded.toString();
	}
	
	public static boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}

	public static float roundForNetwork(float num){
		return Math.round(1000f * num)/1000f;
	}

	public static float percentOf(float value, int percent){
		return (float)(value*(percent/100.0f));
	}
	
	public static float lerp(float x1, float x2, float t) {
		return x1 + (x2 - x1) * t;
    }

    public static float oneDecimal(float f) {
	   return Math.round(f*10)/10.0f;
   }
    
    public static float twoDecimal(float f) {
	   return Math.round(f*100)/100.0f;
   }
}