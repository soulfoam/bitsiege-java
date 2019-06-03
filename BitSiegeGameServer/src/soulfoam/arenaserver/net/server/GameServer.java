package soulfoam.arenaserver.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.command.server.BuyItemCommand;
import soulfoam.arenaserver.main.command.server.ChatMessageCommand;
import soulfoam.arenaserver.main.command.server.PingUpdateCommand;
import soulfoam.arenaserver.main.command.server.RemoveEntityCommand;
import soulfoam.arenaserver.main.command.server.CastAbilityCommand;
import soulfoam.arenaserver.main.command.server.SpawnRequestCommand;
import soulfoam.arenaserver.main.command.server.UpdateEntityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.arenashared.main.opcode.OPCode;

public class GameServer extends Thread {
	
	private DatagramSocket socket;
	private int serverPort;
	
	private boolean running;
	
	private int sequenceNumber;
	
	public GameServer(int port){
		
		
		this.running = true;
		this.serverPort = port;
		
		try {
			this.socket = new DatagramSocket(serverPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		
		while (running)
		{
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			try {
				if (socket != null) socket.receive(packet);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			String message;
			message = new String(packet.getData()).trim();
			
			if (!message.trim().isEmpty() && message.length() >= 2){
				
				String reliableMessage = message;
				
				if (message.trim().substring(0, 2).equals(OPCode.OP_RELIABLEDATA)){
					int pos = message.lastIndexOf("~");
					String payload = message.trim().substring(0, pos);
					int seqNumber = LobbyUtil.parseInt(message.trim().substring(pos + 1));
					
					reliableMessage = payload.substring(2).trim(); 
					
					sendData(OPCode.OP_ACKFORRELIABLEDATA + seqNumber, packet.getAddress(), packet.getPort());
				}
				
				if (message.trim().substring(0, 2).equals(OPCode.OP_ACKFORRELIABLEDATA)){
					int seqNumber = LobbyUtil.parseInt(message.trim().substring(2));
					try {
						Game.getGame().getServerFunctions().getCommandHandler().getReliablePacketsQueue().put(new AckPacket(packet.getAddress(), packet.getPort(), seqNumber));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
				processPacket(reliableMessage, packet);
			}
			
		}
		
	}
	
	public void processPacket(String message, DatagramPacket packet){
		String[] messageData = message.substring(2).split(",");
		
		if (message.trim().substring(0, 2).equals(OPCode.OP_SPAWNREQUEST))
		{	
			int accountID = LobbyUtil.parseInt(message.substring(2));
			
			try {
				Game.getGame().getServerFunctions().getCommandHandler().getCommandQueue().put(new SpawnRequestCommand(accountID, packet.getAddress(), packet.getPort()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	

		if (message.trim().substring(0, 2).equals(OPCode.OP_REMOVEPLAYER))
		{	
			try {
				Game.getGame().getServerFunctions().getCommandHandler().getCommandQueue().put(new RemoveEntityCommand(packet.getAddress(), packet.getPort()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (message.trim().substring(0, 2).equals(OPCode.OP_PLAYERUPDATE))
		{

			if (messageData.length == 5){
				byte pcd = Byte.parseByte(messageData[0]);
				boolean pia = Boolean.parseBoolean(messageData[1]);
				int mX = Integer.parseInt(messageData[2]);
				int mY = Integer.parseInt(messageData[3]);
				byte pcv = Byte.parseByte(messageData[4]);
				
				try {
					Game.getGame().getServerFunctions().getCommandHandler().getCommandQueue().put(new UpdateEntityCommand(packet.getAddress(), packet.getPort(), pcd, pia, mX, mY, pcv));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		if (message.trim().substring(0, 2).equals(OPCode.OP_BUYITEM))
		{
			if (messageData.length == 1){
				int item = LobbyUtil.parseInt(messageData[0]);
	
				try {
					Game.getGame().getServerFunctions().getCommandHandler().getCommandQueue().put(new BuyItemCommand(packet.getAddress(), packet.getPort(), item));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (message.trim().substring(0, 2).equals(OPCode.OP_ADDABILITY))
		{
			if (messageData.length == 3){
				byte slotID = Byte.parseByte(messageData[0]);
				float x = Float.parseFloat(messageData[1]);
				float y = Float.parseFloat(messageData[2]);

				try {
					Game.getGame().getServerFunctions().getCommandHandler().getCommandQueue().put(new CastAbilityCommand(packet.getAddress(), packet.getPort(), x, y, slotID));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (message.trim().substring(0, 2).equals(OPCode.OP_PINGINGAME))
		{
			try {
				Game.getGame().getServerFunctions().getCommandHandler().getCommandQueue().put(new PingUpdateCommand(packet.getAddress(), packet.getPort()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (message.trim().substring(0, 2).equals(OPCode.OP_CHATMESSAGE))
		{
			String msgText = message.trim().substring(2);
			
			try {
				Game.getGame().getServerFunctions().getCommandHandler().getCommandQueue().put(new ChatMessageCommand(packet.getAddress(), packet.getPort(), msgText));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void sendReliableData(String stringData, InetAddress ipAddress, int port){
		String packetString = OPCode.OP_RELIABLEDATA;
		packetString += stringData += "~" + sequenceNumber;
		
		System.out.println("SERVER: sending reliable data " + stringData + "  -  " + sequenceNumber);
		try {
			Game.getGame().getServerFunctions().getCommandHandler().getReliablePacketsQueue().put(new ReliablePacket(ipAddress, port, sequenceNumber, stringData));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		sequenceNumber++;
		
		byte[] data = packetString.getBytes();
		
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		
		try {
			if (socket != null) socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void resendReliableData(String stringData, int sequenceNumber, InetAddress ipAddress, int port){
		String packetString = OPCode.OP_RELIABLEDATA;
		packetString += stringData;
		
		System.out.println("SERVER: resending reliable data " + stringData + "  -  " + sequenceNumber);
		
		byte[] data = packetString.getBytes();
		
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, serverPort);
		
		try {
			if (socket != null) socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendData(String stringData, InetAddress ipAddress, int port){

		byte[] data = stringData.getBytes();
		
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		
		try {
			if (socket != null) socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendData(byte[] data, InetAddress ipAddress, int port){
		
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		
		try {
			if (socket != null) socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToEveryone(String stringData){
		
		for (Entity p : Game.getGame().getPlayers()){
			if (!p.isBot()){
				sendData(stringData, p.getIpAddress(), p.getPort());	
			}
		}
		
	}
	
	public void sendReliableDataToEveryone(String stringData){
		
		for (Entity p : Game.getGame().getPlayers()){
			if (!p.isBot()){
				sendReliableData(stringData, p.getIpAddress(), p.getPort());	
			}
		}
		
	}
	
	public void sendDataToEveryone(byte[] data){
		
		for (Entity p : Game.getGame().getPlayers()){
			if (!p.isBot()){
				sendData(data, p.getIpAddress(), p.getPort());	
			}
		}
		
	}
	
	public DatagramSocket getSocket() {
		return socket;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean isRunning) {
		this.running = isRunning;
	}

	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}
	
}
