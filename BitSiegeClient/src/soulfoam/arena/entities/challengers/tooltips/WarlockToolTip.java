package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockLifeDrainInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockStormBitInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockStormInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTeleportInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTowerAttackInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTowerInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class WarlockToolTip extends ChallengerToolTip {

	public WarlockToolTip(float x, float y) {
		setX(x);
		setY(y);

		si1 = Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[0];
		si2 = Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[1];
		si3 = Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[2];
		si4 = Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[3];

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

		Res.bitFont.drawString(getX() + 24, getY() + 9, "Dark Tower", Color.magenta);

		if (Game.getGame().isInGame()) {
			Res.bitFont.drawString(getX() + 32, getY() + 16,
					"" + EntityInfo.getOverallPower(WarlockTowerAttackInfo.DAMAGE,
							Game.getGame().getPlayer().getPowerBuffAmount()) + "/hit",
					Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 16, WarlockTowerAttackInfo.DAMAGE + "/hit", Color.orange);
		}

		Res.bitFont.drawString(getX() + 100, getY() + 16, Math.round(WarlockTowerInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 9, "Spawns a tower that shoots at your mouse", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 15, "cursor.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 26, "Life Drain", Color.magenta);


		if (Game.getGame().isInGame()) {
			Res.bitFont.drawString(getX() + 32, getY() + 33,
					Res.oneDecimal(EntityInfo.getOverallPower(WarlockLifeDrainInfo.DAMAGE,
							Game.getGame().getPlayer().getPowerBuffAmount())
							/ (WarlockLifeDrainInfo.DESTROY_TIMER / 1000)) + "/sec",
					Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 33,
					WarlockLifeDrainInfo.DAMAGE / (WarlockLifeDrainInfo.DESTROY_TIMER / 1000) + "/sec", Color.orange);
		}


		Res.bitFont.drawString(getX() + 100, getY() + 33f, Math.round(WarlockLifeDrainInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 73, getY() + 33f, "-" + WarlockLifeDrainInfo.SLOW_AMOUNT + "%", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 26, "Drain enemies health in an AOE while healing", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 32, "yourself for the damage dealt and slowing them.",
				Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 43, "Teleport", Color.magenta);

		Res.bitFont.drawString(getX() + 100, getY() + 50f, Math.round(WarlockTeleportInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 46, "Teleport to the mouse cursor.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 60, "Energy Storm", Color.magenta);

		if (Game.getGame().isInGame()) {
			Res.bitFont.drawString(getX() + 32, getY() + 67,
					EntityInfo.getOverallPower(WarlockStormBitInfo.DAMAGE,
							Game.getGame().getPlayer().getPowerBuffAmount()) + "/hit",
					Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 67, WarlockStormBitInfo.DAMAGE + "/hit", Color.orange);
		}

		Res.bitFont.drawString(getX() + 100, getY() + 67f, Math.round(WarlockStormInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 60, "Spawns a large energy storm at the mouse", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 66, "cursor.", Color.white);
	}
}
