package soulfoam.arenaserver.main.resources;

import java.io.File;

public class NativeLoader {

	public static void load(){
		String systemType = System.getProperty("os.name");
		
		String path = null;
		path = new File("natives").getAbsolutePath();
		
		if (path == null){
			System.out.println("You are missing the natives folder for the server! The natives folder must be in the same directory as the server file.");
			System.exit(0);
			return;
		}

		if (systemType.contains("Windows")){
			System.setProperty("org.lwjgl.librarypath", path + "\\Windows");
		}
		if (systemType.contains("Mac")){
			System.setProperty("org.lwjgl.librarypath", path + "/Mac");
		}
		if (systemType.contains("Linux")){
			System.setProperty("org.lwjgl.librarypath", path + "/Linux");
		}
		
	}
	
}
