package soulfoam.arenaserver.main.command.server;

import java.net.InetAddress;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;

import soulfoam.arenashared.main.opcode.OPCode;


public class ChatMessageCommand extends Command{

	private InetAddress ipAddress = null;
	private int port;
	
	private String messageText;
	
	public ChatMessageCommand(int playerID, String messageText){
		super(Command.COMMAND_CHAT_MESSAGE, playerID);
		this.messageText = messageText;
	}
	
	public ChatMessageCommand(InetAddress ip, int port, String messageText){
		super(Command.COMMAND_CAST_ABILITY);
		this.ipAddress = ip;
		this.port = port;
		this.messageText = messageText;
	}

	public boolean execute(int delta) {
		
		Entity player = null;
		
		if (ipAddress == null){
			player = Game.getGame().getPlayerObject(playerID);
		}
		else{
			player = Game.getGame().getPlayerObject(ipAddress, port);
		}
		
		if (player == null){
			return true;
		}
		
		
		Game.getGame().getServerSettings().handleCommand(messageText);
		
		byte chatColor = 0;
		
		boolean isAll = false;

		if (messageText.trim().toLowerCase().startsWith("/all")){
			isAll = true;

			messageText = messageText.replace("/all", "").trim();
			messageText = "[All] " + player.getUsername() + ": " + messageText;
			
		}
		else{
			messageText = player.getUsername() + ": " + messageText;
		}

		for (Entity p: Game.getGame().getPlayers()){
			if (!p.isBot()){
				if (isAll){
					if (player.getTeam() == p.getTeam()){
						chatColor = 0;
					}
					else{
						chatColor = 1;
					}
					Game.getGame().getServer().sendReliableData(OPCode.OP_CHATMESSAGE + chatColor + messageText.trim(), p.getIpAddress(), p.getPort());	
				}
				else{
					if (player.getTeam() == p.getTeam()){
						chatColor = 0;
						Game.getGame().getServer().sendReliableData(OPCode.OP_CHATMESSAGE + chatColor + messageText.trim(), p.getIpAddress(), p.getPort());	
					}
				}
			}
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}
