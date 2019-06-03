package soulfoam.arenaserver.net;

import java.util.concurrent.CopyOnWriteArrayList;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.net.mainserver.LobbyEntity;
import soulfoam.arenaserver.net.mainserver.MainServerClient;

public class NetworkManager {

	private static NetworkManager nm = null;
	
	private MainServerClient msc = new MainServerClient();
	
	private boolean readyToStartGame;
	private CopyOnWriteArrayList<LobbyEntity> players = new CopyOnWriteArrayList<LobbyEntity>();
	private int mapID = 0;
	
	public static NetworkManager getNetworkManager(){
		if (nm == null){
			nm = new NetworkManager();
		}
		
		return nm;
	}
	
	public void init(){
		msc.init();
		if (msc.init()){
			msc.start();
		}
	}
	
	public LobbyEntity getPlayer(int accountID){
		for (LobbyEntity le : players){
			if (le.getAccountID() == accountID){
				return le;
			}
		}
		
		return null;
	}

	public boolean isReadyToStartGame() {
		return readyToStartGame;
	}

	public void setReadyToStartGame(boolean readyToStartGame) {
		this.readyToStartGame = readyToStartGame;
	}

	public CopyOnWriteArrayList<LobbyEntity> getPlayers() {
		return players;
	}

	public void setPlayers(CopyOnWriteArrayList<LobbyEntity> players) {
		this.players = players;
	}

	public int getMapID() {
		return mapID;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}
}
