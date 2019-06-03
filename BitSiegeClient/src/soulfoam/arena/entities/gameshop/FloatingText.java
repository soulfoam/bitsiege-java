package soulfoam.arena.entities.gameshop;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.ShopInfo;

public class FloatingText {

	private Image renderImage;
	private String text;
	private int x, y;
	private int type;
	private float removeTimer = 1.5f * 1000;

	public FloatingText(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;

		if (type == 0) {

			text = "+" + Math.round(ShopInfo.HP_ADD_VALUE);
		}
		if (type == 1) {

			text = "+" + Math.round(ShopInfo.POWER_ADD_VALUE) + "%";
		}
		if (type == 2) {

			text = "+" + Math.round(ShopInfo.ATTACKSPEED_ADD_VALUE) + "%";
		}
		if (type == 3) {

			text = "+" + Res.getMoveSpeedDisplay(ShopInfo.MOVESPEED_ADD_VALUE);
		}
	}

	public void update(GameContainer gc) {
		y -= 1;
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(x + 205, y - 54, 58, 11);
		Res.bitFont.drawString(x + 220, y - 51, text, Color.green);
		renderImage.draw(x + 208, y - 52, 8, 8);
	}

	public Image getRenderImage() {
		return renderImage;
	}

	public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public float getRemoveTimer() {
		return removeTimer;
	}

	public void setRemoveTimer(float removeTimer) {
		this.removeTimer = removeTimer;
	}
}
