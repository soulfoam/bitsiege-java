package soulfoam.arena.main.misc;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

public class LobbyChatMessage {

	public float x, y;

	public String text;
	public Color textColor;
	public boolean isAllMessage;

	public LobbyChatMessage(String text, Color textColor) {
		this.text = text;
		this.textColor = textColor;
	}

	public LobbyChatMessage(String text, Color col, boolean system) {
		this.text = text;
		isAllMessage = false;

		textColor = col;

		String newText = "[System] " + text;
		this.text = newText;

	}

	public void render(Graphics g, float textScale, int opacity) {
		textColor.a = opacity;
		Res.bitFont.drawString(x, y, text, textColor, textScale);
		
	}
	
}
