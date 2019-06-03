package soulfoam.bitsiegemainserver.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import soulfoam.arenashared.main.lobbyutil.BSTimer;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.arenasharedserver.main.opcode.ServerKey;
import soulfoam.arenasharedserver.main.opcode.ServerOPCode;
import soulfoam.bitsiegemainserver.main.accounts.ActiveAccountChangeCommand;
import soulfoam.bitsiegemainserver.main.gameserver.GameServerChangeCommand;
import soulfoam.bitsiegemainserver.main.logs.LogType;

public class GameServerThread extends Thread{
	
	private boolean isRunning = false;

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	private boolean authorized; 
	private boolean available;
	
	private int port;
	
	private BSTimer connectionCheckTimer;
	
	public GameServerThread(Socket socket){
		init(socket);
	}
	
	public void init(Socket socket){

		this.socket = socket;
		
		try {
			
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		connectionCheckTimer = new BSTimer(30000);
		
		isRunning = true;
	}
	
	public void run() {
		
		while (isRunning){
			try {

				connectionCheck();
				
				if (input.ready()){
	
					String OPCode = input.readLine().trim();

					if (OPCode.startsWith(String.valueOf(ServerOPCode.OP_AUTHORIZE))){
						String authKey = input.readLine().trim();
						if (authKey.equals(ServerKey.KEY)){
							authorized = true;
							available = true;
						}
						else{
							NetworkManager.getManager().getLogManager().addLog(LogType.ERROR_GAMESERVERTHREAD, "Game Server " + socket.getRemoteSocketAddress() + " had wrong auth key... HACKER!");
							isRunning = false;
							return;
						}
						
						if (authorized){
							String sPort = input.readLine().trim();
							port = LobbyUtil.parseInt(sPort);
						}
					}

					

				}
			} 
			catch (IOException e) {
				NetworkManager.getManager().getLogManager().addLog(LogType.ERROR_GAMESERVERTHREAD, e.getMessage());
				//e.printStackTrace();
			}
			
			 try { Thread.sleep(10); } catch (InterruptedException e) {}
		}
		
		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	public void connectionCheck(){
		if (connectionCheckTimer.update()){
			output.println("");
			if (output.checkError()){
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new GameServerChangeCommand(this, ActiveAccountChangeCommand.REMOVE));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NetworkManager.getManager().getLogManager().addLog(LogType.ERROR_GAMESERVERTHREAD, "Game Server lost connection... " + socket.getRemoteSocketAddress());
				isRunning = false;
			}
			else{
				connectionCheckTimer.reset();
			}

		}
	}

	public PrintWriter getOutput() {
		return output;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available){
		this.available = available;
	}

	public int getPort() {
		return port;
	}

	public Socket getSocket() {
		return socket;
	}

	
}