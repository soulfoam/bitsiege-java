package soulfoam.bitsiegemainserver.main.gameserver;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import soulfoam.bitsiegemainserver.main.GameServerThread;

public class ActiveGameServerManager {
	
	private ArrayList<GameServerThread> servers = new ArrayList<GameServerThread>();

	public ArrayList<GameServerThread> getServers() {
		return servers;
	}
	
	public GameServerThread getAvailableServer(){
		for (GameServerThread gst : servers){
			if (gst.isAvailable()){
				return gst;
			}
		}
		
		return null;
	}

}
