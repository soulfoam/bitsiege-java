package soulfoam.bitsiegemainserver.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import soulfoam.bitsiegemainserver.main.accounts.ActiveAccountChangeCommand;
import soulfoam.bitsiegemainserver.main.gameserver.GameServerChangeCommand;

public class MainServer {
	
	private ServerSocket serverSocket;
	private ServerSocket serverGameServerSocket;
	private int port = 2147;
	private int gameServerPort = 2148;
	
	public MainServer(){
		NetworkManager.getManager().init();
		init();
	}
	
	public void init(){
		try {
			serverSocket = new ServerSocket(port, 0, InetAddress.getLocalHost());
			serverGameServerSocket = new ServerSocket(gameServerPort, 0, InetAddress.getLocalHost());
			initClientListener();
			initGameServerListener();
		}
		
		catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initClientListener(){
		new Thread(){
			public void run(){
				while(true){
					try {
						Socket socket = serverSocket.accept();
						ClientThread ct = new ClientThread(socket);
						ct.start();
						try {
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new ActiveAccountChangeCommand(ct, ActiveAccountChangeCommand.ADD));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					try { Thread.sleep(10); } catch (InterruptedException e) {}
				}
			}
		}.start();
	}
	
	public void initGameServerListener(){
		new Thread(){
			public void run(){
				while(true){
					try {
						Socket gameServerSocket = serverGameServerSocket.accept();
						GameServerThread gst = new GameServerThread(gameServerSocket);
						gst.start();
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new GameServerChangeCommand(gst, ActiveAccountChangeCommand.ADD));
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					try { Thread.sleep(10); } catch (InterruptedException e) {}
				}
			}
		}.start();
	}

	public ServerSocket getServerGameServerSocket() {
		return serverGameServerSocket;
	}

	public void setServerGameServerSocket(ServerSocket serverGameServerSocket) {
		this.serverGameServerSocket = serverGameServerSocket;
	}

	public int getGameServerPort() {
		return gameServerPort;
	}

	public void setGameServerPort(int gameServerPort) {
		this.gameServerPort = gameServerPort;
	}
	
}
