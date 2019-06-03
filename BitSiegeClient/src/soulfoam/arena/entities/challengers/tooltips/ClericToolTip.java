package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericBasicAttackHealInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericBasicAttackInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericBlindInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericHealInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericSwitchInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericTeamHealInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class ClericToolTip extends ChallengerToolTip {

	public ClericToolTip(float x, float y) {
		setX(x);
		setY(y);

		si1 = Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[0];
		si2 = Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[1];
		si3 = Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[2];
		si4 = Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[3];

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

		Res.bitFont.drawString(getX() + 24, getY() + 9, "Holy Restore", Color.green);

		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 16,
							"" + Res.oneDecimal(EntityInfo.getOverallPower(ClericHealInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount()
											/ (ClericHealInfo.DESTROY_TIMER / 1000)))
									+ "/sec",
							Color.green);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 16,
					Res.oneDecimal(ClericHealInfo.DAMAGE / (ClericHealInfo.DESTROY_TIMER / 1000)) + "/sec", Color.green);
		}

		Res.bitFont.drawString(getX() + 94, getY() + 16, Math.round(ClericHealInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 9, "Spawns a healing zone at the mouse cursor", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 15, "that heals all allies within the area.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 26, "Blinding Light", Color.white);


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 33,
							Res.oneDecimal(EntityInfo.getOverallPower(ClericBlindInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 33, ClericBlindInfo.DAMAGE + "", Color.orange);
		}

		g.setColor(Color.white);
		g.fillRect(getX() + 56, getY() + 32, 6, 6);

		Res.bitFont.drawString(getX() + 64, getY() + 33f, ClericBlindInfo.BLIND_DURATION + "", Color.cyan);


		Res.bitFont.drawString(getX() + 94, getY() + 33f, Math.round(ClericBlindInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 29, "Blinds and damages the first enemy hit.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 43, "Divine Switch", Color.pink);


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 50,
							Res.oneDecimal(EntityInfo.getOverallPower(ClericBasicAttackInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 50, ClericBasicAttackInfo.DAMAGE + "", Color.orange);
		}


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 64, getY() + 50,
							Res.oneDecimal(EntityInfo.getOverallPower(ClericBasicAttackHealInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.green);
		} else {
			Res.bitFont.drawString(getX() + 64, getY() + 50, ClericBasicAttackHealInfo.DAMAGE + "", Color.green);
		}


		Res.bitFont.drawString(getX() + 94, getY() + 50f, Math.round(ClericSwitchInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 43, "Switch auto attacks between healing and", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 49, "damage.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 60, "Holy Might", Color.green);


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 67,
							Res.oneDecimal(EntityInfo.getOverallPower(ClericTeamHealInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.green);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 67, ClericTeamHealInfo.DAMAGE + "", Color.green);
		}

		Res.bitFont.drawString(getX() + 94, getY() + 67f, Math.round(ClericTeamHealInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 63, "Heals all allies on cast.", Color.white);
	}
}
