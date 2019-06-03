package soulfoam.arena.main.menu.chat;

import org.newdawn.slick.Color;

public class ChatUIHistoryMessage {

	private String message = "";
	private Color color;
	
	public ChatUIHistoryMessage(String message, Color color){
		this.message = message;
		this.color = color;
	}

	public String getMessage() {
		return message;
	}

	public Color getColor() {
		return color;
	}
}
