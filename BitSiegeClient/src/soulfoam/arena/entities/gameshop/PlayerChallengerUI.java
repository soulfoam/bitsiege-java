package soulfoam.arena.entities.gameshop;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class PlayerChallengerUI {

	private float x, y;
	private int glowTimer = 0;
	private boolean addGlowTime = true;
	private int glowSpeed = 7;

	public PlayerChallengerUI(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void update(int delta, GameContainer gc) {

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

	public void render(Graphics g, GameContainer gc) {

		g.setColor(new Color(0, 0, 0, 150));

		g.setColor(Color.white);

		if (Game.getGame().getPlayer().getUnderglow().getUnderglowID() != CosmeticLibrary.UNDERGLOW_NONE) {
			Game.getGame().getPlayer().getUnderglow().getAnimation().draw(x - 2, y + 10, 16, 16,
					new Color(255, 255, 255, glowTimer));
		}

		Game.getGame().getPlayer().getAnimation().getIdleAnimation().draw(
				x - Game.getGame().getPlayer().getWidth() / 2 + 6, y - 1,
				Game.getGame().getPlayer().getWidth(), Game.getGame().getPlayer().getHeight());

		if (Game.getGame().getPlayer().getUnderglow().getUnderglowID() != CosmeticLibrary.UNDERGLOW_NONE) {
			Game.getGame().getPlayer().getUnderglow().getAnimation().draw(x + 130, y + 10, 16, 16,
					new Color(255, 255, 255, glowTimer));
		}

		Game.getGame().getPlayer().getAnimation().getIdleAnimation().draw(x + 126, y - 1,
				Game.getGame().getPlayer().getWidth(), Game.getGame().getPlayer().getHeight());

	}
}
