package soulfoam.arena.entities.gameshop;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

public class StatUI {

	private float x, y;

	public StatUI(float x, float y) {
		this.x = x;
		this.y = y;

	}

	public void update(int delta, GameContainer gc) {

	}

	public void render(Graphics g, GameContainer gc) {

		g.setColor(new Color(28, 61, 113));
		g.fillRect(x, y, 45, 51);
		g.fillRect(x, y + 53, 45, 9);

		g.setColor(new Color(0, 0, 0, 150));

		g.fillRect(x, y + 3, 45, 9);
		g.fillRect(x, y + 15, 45, 9);
		g.fillRect(x, y + 27, 45, 9);
		g.fillRect(x, y + 39, 45, 9);

		g.setColor(Color.white);
		g.drawRect(x, y, 45, 51);
		g.drawRect(x, y + 53, 45, 9);

		Res.bitFont.drawString(x + 14, y + 55, Game.getGame().getPlayer().getGold() + "");

		Res.bitFont.drawString(x + 14, y + 5, "+" + (int) (Game.getGame().getPlayer().getBaseHealth()
				- Game.getGame().getPlayer().getBaseHealthStart()), Color.green);


		Res.bitFont.drawString(x + 14, y + 17, "+%" + (int) Game.getGame().getPlayer().getPowerBuffAmount(),
				Color.green);


		Res.bitFont.drawString(x + 14, y + 29,
				"+" + (int) Game.getGame().getPlayer().getAttackSpeedBuffAmount() + "%", Color.green);

		Res.bitFont.drawString(x + 14, y + 41,
				"+" + Res.getMoveSpeedDisplay(Game.getGame().getPlayer().getMoveSpeedBuffAmount()), Color.green);

	}
}
