package soulfoam.arenaserver.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


public class SettingManager {
	
	public static boolean ADDTOSERVERLIST = true;
	public static int SERVER_PORT = 1337;
	public static String SERVER_NAME = "Bit Siege Game";

    public static void makeConfigFile(){
    	Properties prop = new Properties();
    	OutputStream output = null;
     
    	try {
     
    		output = new FileOutputStream("bitsiegeserver.properties");
     
       		prop.setProperty("addtoserverlist", "true");
       		prop.setProperty("roomname", "Bit Siege Server");
       		prop.setProperty("port", "1337");
    		
    		
    		prop.store(output, null);
     
    	} catch (IOException io) {
    		io.printStackTrace();
    	} finally {
    		if (output != null) {
    			try {
    				output.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
     
    	}
    }
    
    public static void readConfigFile(){
    	Properties prop = new Properties();
    	InputStream input = null;

     
    	try {

    		input = new FileInputStream("bitsiegeserver.properties");

    		prop.load(input);

    		ADDTOSERVERLIST = Boolean.parseBoolean(prop.getProperty("addtoserverlist"));
    		
    		if (!prop.getProperty("roomname").isEmpty()){
    			SERVER_NAME = prop.getProperty("roomname");
    		}
    		
    		if (!prop.getProperty("port").isEmpty()){
    			SERVER_PORT = Integer.parseInt(prop.getProperty("port"));
    		}
    		
    		
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    }
    
    public static void setConfigFile(String property, String value)
    {
    	FileInputStream in = null;
		try {
			in = new FileInputStream("bitsiegeserver.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	Properties props = new Properties();
    	try {
			props.load(in);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
    	try {
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

    	FileOutputStream out = null;
		try {
			out = new FileOutputStream("bitsiegeserver.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
    	props.setProperty(property, value);
    	
    	try {
			props.store(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}
