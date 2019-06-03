package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanBuffTotemInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanConfuseTotemInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanDebuffTotemInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanShieldTotemInfo;

public class ShamanToolTip extends ChallengerToolTip {

	public ShamanToolTip(float x, float y) {
		setX(x);
		setY(y);

		si1 = Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[0];
		si2 = Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[1];
		si3 = Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[2];
		si4 = Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[3];

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

		Res.bitFont.drawString(getX() + 24, getY() + 9, "Rage Totem", new Color(200, 159, 45));

		if (Game.getGame().isInGame()) {
			Res.bitFont.drawString(getX() + 32, getY() + 16,
					"+" + Res.oneDecimal(Res.percentOf(Game.getGame().getPlayer().getBaseHealth(),
							ShamanBuffTotemInfo.DAMAGE_BUFF_AMOUNT)) + "%");
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 16, "+" + ShamanBuffTotemInfo.DAMAGE_BUFF_AMOUNT + "%",
					Color.green);
		}
		Res.bitFont.drawString(getX() + 100, getY() + 16, Math.round(ShamanBuffTotemInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 66, getY() + 16, "+" + ShamanBuffTotemInfo.ATTACK_SPEED_BUFF_AMOUNT + "%",
				Color.green);

		Res.bitFont.drawString(getX() + 111, getY() + 9, "Gives allies in range +2% of your base HP in", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 15,
				"PWR, and +" + ShamanBuffTotemInfo.ATTACK_SPEED_BUFF_AMOUNT + "% AS. Amounts doubled for Shaman.",
				Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 26, "Stone Skin Totem", Color.white);

		Res.GENERAL_RESOURCE.SHIELD_ANIMATION[0].draw(getX() + 23, getY() + 31, 8, 8);

		Res.bitFont.drawString(getX() + 32, getY() + 33, "+" + ShamanShieldTotemInfo.SHIELD_AMOUNT + "%",
				Color.lightGray);

		Res.bitFont.drawString(getX() + 100, getY() + 33f, Math.round(ShamanShieldTotemInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 26,
				"Shields allies in range for " + ShamanShieldTotemInfo.SHIELD_AMOUNT + "% of their base", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 32,
				"HP. The shield lasts for " + ShamanShieldTotemInfo.SHIELD_TIMER + " seconds.", Color.white,
				0.5f);

		Res.bitFont.drawString(getX() + 24, getY() + 43, "Blood Boil Totem", new Color(192, 67, 47));

		Res.bitFont.drawString(getX() + 32, getY() + 50, "-" + ShamanDebuffTotemInfo.SLOW_AMOUNT + "%", Color.red);

		Res.bitFont.drawString(getX() + 100, getY() + 50f, Math.round(ShamanDebuffTotemInfo.COOLDOWN) + "", Color.red);

		Res.SHAMAN_RESOURCE.IDLE_DOWN[0].drawFlash(getX() + 54, getY() + 46, 12, 12);
		Res.bitFont.drawString(getX() + 64, getY() + 50, ShamanDebuffTotemInfo.STUN_DURATION + "", Color.yellow);

		Res.bitFont.drawString(getX() + 111, getY() + 43, "Slows enemies in range, if the Shaman is in", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 49, "range, enemies are stunned instead of slowed.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 60, "Totem Of The Lost", new Color(171, 58, 194));

		Res.bitFont.drawString(getX() + 100, getY() + 67f, Math.round(ShamanConfuseTotemInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 24, getY() + 65.5f, "?", 0.75f);
		Res.bitFont.drawString(getX() + 32, getY() + 67, ShamanConfuseTotemInfo.CONFUSE_TIME + "", Color.magenta);
		Res.bitFont.drawString(getX() + 111, getY() + 60, "Confuses enemies in range, making them unable", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 66, "to attack or move properly.", Color.white);
	}
}
