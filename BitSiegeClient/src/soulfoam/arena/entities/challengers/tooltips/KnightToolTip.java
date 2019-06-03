package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.knight.KnightDashInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightInvincibleBuffInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightMeleeSpinInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightShieldThrowInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class KnightToolTip extends ChallengerToolTip {

	public KnightToolTip(float x, float y) {
		setX(x);
		setY(y);

		si1 = Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[0];
		si2 = Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[1];
		si3 = Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[2];
		si4 = Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[3];

	}

	public void update(GameContainer gc) {

	}

	public void render(Graphics g) {
		Res.UI_RESOURCE.SCOREBOARD.draw(getX(), getY());

		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(getX() + 7, getY() + 7, 286, 16);
		g.fillRect(getX() + 7, getY() + 24, 286, 16);
		g.fillRect(getX() + 7, getY() + 41, 286, 16);
		g.fillRect(getX() + 7, getY() + 58, 286, 16);

		g.setColor(new Color(0, 0, 0, 60));
		g.fillRect(getX() + 7, getY() + 7, 102, 16);
		g.fillRect(getX() + 7, getY() + 24, 102, 16);
		g.fillRect(getX() + 7, getY() + 41, 102, 16);
		g.fillRect(getX() + 7, getY() + 58, 102, 16);

		g.setColor(Color.white);
		g.drawRect(getX() + 7, getY() + 7, 102, 16);
		g.drawRect(getX() + 7, getY() + 7, 286, 16);

		g.drawRect(getX() + 7, getY() + 24, 102, 16);
		g.drawRect(getX() + 7, getY() + 24, 286, 16);

		g.drawRect(getX() + 7, getY() + 41, 102, 16);
		g.drawRect(getX() + 7, getY() + 41, 286, 16);

		g.drawRect(getX() + 7, getY() + 58, 102, 16);
		g.drawRect(getX() + 7, getY() + 58, 286, 16);

		g.setColor(Color.black);
		g.fillRect(getX() + 6, getY() + 75, Res.UI_RESOURCE.SCOREBOARD.getWidth() - 12, 1);

		si1.draw(getX() + 10, getY() + 9, 12, 12);
		si2.draw(getX() + 10, getY() + 26, 12, 12);
		si3.draw(getX() + 10, getY() + 43, 12, 12);
		si4.draw(getX() + 10, getY() + 60, 12, 12);

		Res.bitFont.drawString(getX() + 24, getY() + 9, "Shield Throw", Color.yellow);


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 16,
							"" + Res.oneDecimal(EntityInfo.getOverallPower(KnightShieldThrowInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 16, "" + KnightShieldThrowInfo.DAMAGE, Color.orange);
		}

		Res.bitFont.drawString(getX() + 100, getY() + 16, Math.round(KnightShieldThrowInfo.COOLDOWN) + "", Color.red);

		Res.KNIGHT_RESOURCE.IDLE_DOWN[0].drawFlash(getX() + 54, getY() + 12, 12, 12);
		Res.bitFont.drawString(getX() + 64, getY() + 16, KnightShieldThrowInfo.STUN_DURATION + "", Color.yellow);

		Res.bitFont.drawString(getX() + 111, getY() + 13, "Throws a shield stunning the first enemy hit.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 26, "Reckless Spin", Color.yellow);


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 33,
							Res.oneDecimal(EntityInfo.getOverallPower(KnightMeleeSpinInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 33, KnightMeleeSpinInfo.DAMAGE + "", Color.orange);
		}

		Res.bitFont.drawString(getX() + 100, getY() + 33f, Math.round(KnightMeleeSpinInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 26, "Begin recklessly spinning doing AOE damage to", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 32, "all surrounding enemies.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 43, "Power Dash", Color.yellow);

		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 50,
							Res.oneDecimal(EntityInfo.getOverallPower(KnightDashInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 50, KnightDashInfo.DAMAGE + "", Color.orange);
		}


		Res.bitFont.drawString(getX() + 100, getY() + 50f, Math.round(KnightDashInfo.COOLDOWN) + "", Color.red);

		Res.KNIGHT_RESOURCE.IDLE_DOWN[0].drawFlash(getX() + 54, getY() + 46, 12, 12);
		Res.bitFont.drawString(getX() + 64, getY() + 50, KnightDashInfo.STUN_DURATION + "", Color.yellow);

		Res.bitFont.drawString(getX() + 111, getY() + 43, "Dash and knock back, stun, and damage the", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 49, "first enemy hit.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 60, "Indestructable", Color.yellow);


		Res.bitFont.drawString(getX() + 100, getY() + 67f, Math.round(KnightInvincibleBuffInfo.COOLDOWN) + "",
				Color.red);

		Res.KNIGHT_RESOURCE.IDLE_DOWN[0].draw(getX() + 21, getY() + 63, 12, 12, new Color(Color.yellow));
		Res.bitFont.drawString(getX() + 32, getY() + 67.5f, KnightInvincibleBuffInfo.DESTROY_TIMER / 1000 + "",
				Color.pink);
		Res.bitFont.drawString(getX() + 111, getY() + 60, "Become invulernable to all damage and CC and", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 66, "a shadow forms around you doubling your size.", Color.white);
	}
}
