package soulfoam.arena.entities.challengers.underglows;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class Underglow {

	private Entity player;
	private int underglowID;

	private int glowTimer = 0;
	private boolean addGlowTime = true;
	private int glowSpeed = 7;

	private float width = 16;
	private float height = 16;

	private Animation animation = new Animation();

	public Underglow(Entity player, int underglowID) {
		this.player = player;
		this.underglowID = underglowID;
		animation = CosmeticBook.getUnderglowAnimation(underglowID);
	}

	public void update(int delta) {

		if (addGlowTime) {
			glowTimer += glowSpeed;
			if (glowTimer >= 255) {
				addGlowTime = false;
			}
		} else {
			glowTimer -= glowSpeed;
			if (glowTimer <= 40) {
				addGlowTime = true;
			}
		}

	}

	public void render(Graphics g) {
		if (animation != null && underglowID != CosmeticLibrary.UNDERGLOW_NONE) {
			animation.draw(Res.roundForRendering(player.getX() - 4), Res.roundForRendering(player.getY() + 7), width,
					height, new Color(255, 255, 255, glowTimer));
		}
	}

	public Entity getPlayer() {
		return player;
	}

	public int getUnderglowID() {
		return underglowID;
	}

	public Animation getAnimation() {
		return animation;
	}

	public int getGlowTimer() {
		return glowTimer;
	}
}
