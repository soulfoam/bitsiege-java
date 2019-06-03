package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;

public class ChatManager {

	public void sendPrivateChat(int friendID, String text) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_PRIVATECHAT);
		LobbyManager.getManager().getLobbyClient().getOutput().println(friendID);
		LobbyManager.getManager().getLobbyClient().getOutput().println(text);
	}

	public void sendGlobalChat(String text) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_GLOBALCHAT);
		LobbyManager.getManager().getLobbyClient().getOutput().println(text);
	}


}
