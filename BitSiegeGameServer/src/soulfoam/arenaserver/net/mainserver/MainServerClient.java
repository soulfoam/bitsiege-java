package soulfoam.arenaserver.net.mainserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import soulfoam.arenaserver.main.SettingManager;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.net.NetworkManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.lobbyutil.BSTimer;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.arenasharedserver.main.opcode.ServerKey;
import soulfoam.arenasharedserver.main.opcode.ServerOPCode;

public class MainServerClient extends Thread{
	
	private boolean isRunning = false;

	private String serverIP = GameInfo.MAIN_SERVER_IP;
	private int serverPort = 2148;
	
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	
	private BSTimer connectionCheckTimer;

	public boolean init(){
		try {
			InetAddress iNetAddress = InetAddress.getByName(serverIP);
			
			if (!iNetAddress.isReachable(5000)){
				return false;
			}

			socket = new Socket(serverIP, serverPort);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
		} 
		
		catch (UnknownHostException e) {
			return false;
			//e.printStackTrace();
		} catch (IOException e) {
			return false;
			//e.printStackTrace();
		}
		
		connectionCheckTimer = new BSTimer(30000);
		isRunning = true;
		
		return true;
	}
	
	public void run(){
		
		output.println(ServerOPCode.OP_AUTHORIZE);
		output.println(ServerKey.KEY);
		output.println(SettingManager.SERVER_PORT);
		
		while (isRunning){
			try {
				
				connectionCheck();
				
				if (input.ready()){
					
					String OPCode = input.readLine().trim();
	
					if (OPCode.startsWith(String.valueOf(ServerOPCode.OP_STARTGAME))){
						String mapID = input.readLine().trim();
						String teamDInfo = input.readLine().trim();
						String teamAInfo = input.readLine().trim();
						int playerGameID = 0;
						
						NetworkManager.getNetworkManager().getPlayers().clear();
						NetworkManager.getNetworkManager().setMapID(LobbyUtil.parseInt(mapID));
						
						if (!teamDInfo.isEmpty()){
							String[] teamDPlayerInfo = teamDInfo.split("]");
							
							for (String playerInfo : teamDPlayerInfo){
								String[] info = playerInfo.split("~");
								
								LobbyEntity lobbyEntity = new LobbyEntity(LobbyUtil.parseInt(info[0]), info[1], info[2], Res.TEAM_D, 
										LobbyUtil.parseInt(info[3]), LobbyUtil.parseInt(info[4]), LobbyUtil.parseInt(info[5]));
								lobbyEntity.setGameID(playerGameID);
								
								NetworkManager.getNetworkManager().getPlayers().add(lobbyEntity);
								
								playerGameID++;
							}
						}
						
						if (!teamAInfo.isEmpty()){
							String[] teamAPlayerInfo = teamAInfo.split("]");
							
							for (String playerInfo : teamAPlayerInfo){
								String[] info = playerInfo.split("~");
								
								LobbyEntity lobbyEntity = new LobbyEntity(LobbyUtil.parseInt(info[0]), info[1], info[2], Res.TEAM_A, 
										LobbyUtil.parseInt(info[3]), LobbyUtil.parseInt(info[4]), LobbyUtil.parseInt(info[5]));
								lobbyEntity.setGameID(playerGameID);
								
								NetworkManager.getNetworkManager().getPlayers().add(lobbyEntity);
								
								playerGameID++;
							}
						}
						
						NetworkManager.getNetworkManager().setReadyToStartGame(true);
					}
					
				}
			} 
			catch (IOException e) {
				//e.printStackTrace();
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	
	}
	
	private void connectionCheck(){
		if (connectionCheckTimer.update()){
			output.println("");
			if (output.checkError()){
				isRunning = false;
			}
			else{
				connectionCheckTimer.reset();
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}
	
	public BufferedReader getInput() {
		return input;
	}

	public PrintWriter getOutput() {
		return output;
	}
	
	public BSTimer getConnectionCheckTimer() {
		return connectionCheckTimer;
	}
}
