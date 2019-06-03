package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

public class MessageBox {

	public static final int MESSAGE_PLAYERKILLEDPLAYER = 0;
	public static final int MESSAGE_TEXT = 1;

	public int id;
	public Entity player1;
	public Entity player2;
	public int messageType;
	private float messageTimer;
	public boolean setMessageTimer;
	public boolean needsRemoved;
	public String messageText;
	public Color messageColor;

	public MessageBox(int id, Entity player1, Entity player2, int messageType) {
		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.messageType = messageType;
	}

	public MessageBox(String messageText, Color col, int messageTimer) {
		messageType = MESSAGE_TEXT;
		this.messageText = messageText;
		messageColor = col;
		this.messageTimer = messageTimer * 1000;
	}

	public void update(int delta) {
		if (setMessageTimer) {
			if (messageTimer > 0) {
				messageTimer -= delta;
			}
			if (messageTimer <= 0) {
				needsRemoved = true;
			}
		}
		if (messageType == MESSAGE_PLAYERKILLEDPLAYER) {
			if (!setMessageTimer) {
				messageTimer = 10 * 1000;
				setMessageTimer = true;
				Color textColor = Color.white;

				if (player1.getTeam() == Game.getGame().getPlayer().getTeam()) {
					textColor = Color.green;
				} else {
					textColor = Color.red;
				}

				Game.getGame().getHUDDisplay().getNotificationLog().getTextMessageList().add(
						new MessageBox(player1.getUsername() + " killed " + player2.getUsername() + "!", textColor, 3));
			}
		}
		if (messageType == MESSAGE_TEXT) {
			setMessageTimer = true;
		}
	}

	public void renderLogItem(Graphics g, float x, float y, float scale, int opacity) {

		Color opac = new Color(255, 255, 255, opacity);

		if (messageType == MESSAGE_PLAYERKILLEDPLAYER) {
			if (player1.getTeam() == Game.getGame().getPlayer().getTeam()) {
				Res.UI_RESOURCE.MESSAGEBAR_GREEN.draw(x, y, scale * 4, scale, opac);
			} else {
				Res.UI_RESOURCE.MESSAGEBAR_RED.draw(x, y, scale * 4, scale, opac);
			}
		}

		float smallModifier = 2.4f;
		if (scale <= 4) {
			smallModifier = 2.25f;
		}
		if (scale >= 5) {
			smallModifier = 2.35f;
		}

		player1.getAnimation().getPortrait().draw(x + scale * 4 / scale - 2, y + 1, scale - 2, scale - 2, opac);
		player2.getAnimation().getPortrait().draw(x + scale * 4 - scale, y + 1, scale - 2, scale - 2, opac);

	}

	public void renderTextItem(Graphics g, float x, float y, float textScale, int textOpacity) {
		g.setColor(new Color(28, 61, 113, textOpacity));
		g.fillRect(x - 1, y - 1, Res.bitFont.getWidth(messageText, textScale) + 1, textScale * 12 + 1);
		g.setColor(new Color(255, 255, 255, textOpacity));
		g.drawRect(x - 1, y - 1, Res.bitFont.getWidth(messageText, textScale) + 1, textScale * 12 + 1);

		messageColor.a = textOpacity;
		Res.bitFont.drawString(x, y, messageText, messageColor, textScale);
	}

}
