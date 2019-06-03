package soulfoam.arenaserver.main.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import soulfoam.arenaserver.main.SettingManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class HelperRequest extends Thread{

	public boolean isRunning;
	public int updateType;

	public HelperRequest(int updateType) {
		this.isRunning = true;
		this.updateType = updateType;
	}


	public void run(){

		while (isRunning){

			if (updateType == 0){
				if (addToServerList()){
					isRunning = false;
				}
			}
			if (updateType == 1){
				if (getGameVer()){
					isRunning = false;
				}
			}
		   try {
			   Thread.sleep(1000);
			   } catch (Exception e) {
			   System.out.println(e);
		   }
		}
	}
	
   public static boolean getGameVer(){
    	
	    URL bitSiegeSite = null;
	    
		try {
			bitSiegeSite = new URL("http://bitsiege.com/gameversion.txt");
		} catch (MalformedURLException e3) {
			e3.printStackTrace();
		}
		
	    URLConnection connection = null;
		try {
			connection = bitSiegeSite.openConnection();
		    connection.setUseCaches(false);
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
	    BufferedReader in = null;
	    
		try {
			in = new BufferedReader(
			new InputStreamReader(connection.getInputStream()));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	    String inputLine;

	    try {
			while ((inputLine = in.readLine()) != null){
				if (!inputLine.trim().equalsIgnoreCase(GameInfo.GAME_VERSION)){
					System.out.println("\nSERVER UPDATE AVAILABLE! Your server version is: " + GameInfo.GAME_VERSION + " and the newest version is: " + inputLine.trim() + "\n" +
							"Clients are forced to update their versions, so your server unusable. Please update your server to the newest version!" + "\n" +
							"Update is available at: http://www.bitsiege.com/download.php" + "\n" + "Server will now exit...");
					System.exit(0);
				}
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    try {
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
	    return true;
    	
    }

	public static boolean addToServerList(){

	    URL bitSiegeSite = null;
	    
		try {
			String roomName = SettingManager.SERVER_NAME.replaceAll(" ", "%20");
			bitSiegeSite = new URL("http://bitsiege.com/hostgame.php?server=" + whatsMyIP() + "&serverport=" + SettingManager.SERVER_PORT + "&servername=" + roomName);
		} catch (MalformedURLException e3) {
			e3.printStackTrace();
		}
		
	    URLConnection connection = null;
		try {
			connection = bitSiegeSite.openConnection();
		    connection.setUseCaches(false);
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
	    BufferedReader in = null;
	    
		try {
			in = new BufferedReader(
			new InputStreamReader(connection.getInputStream()));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	    String inputLine;

	    try {
			while ((inputLine = in.readLine()) != null){
			
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    try {
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		return true;
	}
	
    public static String whatsMyIP() {
    	
		String myIP = null;
		
	    URL bitSiegeSite = null;
	    
		try {
			bitSiegeSite = new URL("http://bitsiege.com/grabip.php");
		} catch (MalformedURLException e3) {
			e3.printStackTrace();
		}
	    BufferedReader in = null;
		try {
			in = new BufferedReader(
			new InputStreamReader(bitSiegeSite.openStream()));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	    String inputLine;
	    try {
			while (in != null && (inputLine = in.readLine()) != null){
				myIP = new String(inputLine);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    try {
			if (in != null) in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
	    return myIP;
    }
}
