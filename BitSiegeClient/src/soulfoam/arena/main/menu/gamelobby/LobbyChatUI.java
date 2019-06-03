package soulfoam.arena.main.menu.gamelobby;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.main.misc.LobbyChatMessage;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GameUtil;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;

public class LobbyChatUI {

	private float x, y;
	private TextField chatTextField;

	private CopyOnWriteArrayList<LobbyChatMessage> chatList = new CopyOnWriteArrayList<LobbyChatMessage>();
	
	public LobbyChatUI(float x, float y, GameContainer gc) {

		this.x = x;
		this.y = y;

		chatTextField = new TextField(gc, Res.bitFont, x, y + 189, 159, 8);
		chatTextField.makeOpaque();
		chatTextField.setFocus(true);
	}

	public void enter() {
		chatTextField.setText("");
	}

	public void update(int delta, GameContainer gc, StateBasedGame s) {
		
		if (gc.getInput().isKeyDown(Input.KEY_LCONTROL) && gc.getInput().isKeyDown(Input.KEY_ENTER)) {
			chatTextField.setText("/all ");
			chatTextField.setCursorPos(5);
		}

		if (chatList.size() >= 26) {
			chatList.remove(0);
		}

		if (!chatTextField.hasFocus()) {
			chatTextField.setFocus(true);
		}

		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			String msgPrefix = "MT";
			String chatMessage = chatTextField.getText().trim();
			if (chatMessage.startsWith("/all")) {
				msgPrefix = "MA";
				chatMessage = chatMessage.substring(4).trim();
			}

			if (chatMessage.isEmpty()) {
				return;
			}

			LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
			LobbyManager.getManager().getLobbyClient().getOutput().println(msgPrefix + chatMessage);
			chatTextField.setText("");
		}

		int chatDisplay = 0;
		for (int i = 0; i < chatList.size(); i++) {
			chatDisplay += 7;
			chatList.get(i).x = x;
			chatList.get(i).y = y + chatDisplay - 1;
		}

	}

	public void render(Graphics g, GameContainer gc) {

		chatTextField.render(gc, g);

		for (LobbyChatMessage lcm : chatList) {
			lcm.render(g, 1, 255);
		}
	}
	
	public void addNewChatMessage(String msg, boolean all){
		Color msgColor = Color.green;
		
		if (all){
			msgColor = Color.orange;
			msg = "[All] " + msg;
		}
		
		for (String chatTextPart : GameUtil.getStringParts(msg, 26)) {
			if (chatTextPart != null) {
				chatList.add(new LobbyChatMessage(chatTextPart.trim(), msgColor));
			}
		}
		
	}

	public CopyOnWriteArrayList<LobbyChatMessage> getChatList() {
		return chatList;
	}

	public TextField getChatTextField() {
		return chatTextField;
	}

}
