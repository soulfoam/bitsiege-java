package soulfoam.arenaserver.main;

import java.io.File;
import java.util.Scanner;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arenaserver.main.game.ServerGameState;
import soulfoam.arenaserver.main.resources.HelperRequest;
import soulfoam.arenaserver.main.resources.NativeLoader;
import soulfoam.arenaserver.main.resources.Res;



public class ServerEngine extends StateBasedGame {
	
	public static AppGameContainer app;
	
	public ServerEngine(String name) {
		super(name);
	}
	
	public static void main(String[] args) {

		NativeLoader.load();
		
		File f = new File("bitsiegeserver.properties");
		
		if(!f.exists() && !f.isDirectory()) {
			SettingManager.makeConfigFile();
			SettingManager.readConfigFile();
		}
		else{
			SettingManager.readConfigFile();
		}
		
		if (args.length == 1){
			SettingManager.SERVER_PORT = Integer.parseInt(args[0]);
		}
		if (args.length == 3){
			SettingManager.SERVER_NAME = args[0].replace('_', ' ');
			SettingManager.SERVER_PORT = Integer.parseInt(args[1]);
			SettingManager.ADDTOSERVERLIST = Boolean.parseBoolean(args[2]);
		}
		
		if (args.length == 4){
			SettingManager.SERVER_NAME = args[0].replace('_', ' ');
			SettingManager.SERVER_PORT = Integer.parseInt(args[1]);
			SettingManager.ADDTOSERVERLIST = Boolean.parseBoolean(args[2]);
			boolean saveToConfig = Boolean.parseBoolean(args[3]);
			if (saveToConfig){
				SettingManager.setConfigFile("roomname", SettingManager.SERVER_NAME);
				SettingManager.setConfigFile("port", SettingManager.SERVER_PORT + "");
				SettingManager.setConfigFile("addtoserverlist", SettingManager.ADDTOSERVERLIST + "");
			}
		}
		
		if (SettingManager.SERVER_NAME.length() >= 41){
			SettingManager.SERVER_NAME = SettingManager.SERVER_NAME.substring(0, 40);
		}

		SettingManager.SERVER_NAME = SettingManager.SERVER_NAME.replace("~", " ");
		
		System.out.println("Bit Siege Server started on port: " + SettingManager.SERVER_PORT + " | Room Name: " + SettingManager.SERVER_NAME 
				+ " | Public Server: " + SettingManager.ADDTOSERVERLIST + "\n" + "Type 'e' or 'exit' and press ENTER to end the server process.");

		
		createGame();
		
	}
	
	public static void createGame(){
		try {
			app = new AppGameContainer(new ServerEngine("Bit Siege"), true);
			app.setMinimumLogicUpdateInterval(15);
			app.setMaximumLogicUpdateInterval(15);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	public void initStatesList(GameContainer gc) throws SlickException {
		new Res();
		this.addState(new ServerGameState());
	}


}
