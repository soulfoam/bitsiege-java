package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordAOEInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordHoleInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordInvisibleInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordPullInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class VoidLordToolTip extends ChallengerToolTip {

	public VoidLordToolTip(float x, float y) {
		setX(x);
		setY(y);

		si1 = Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[0];
		si2 = Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[1];
		si3 = Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[2];
		si4 = Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[3];

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

		Res.bitFont.drawString(getX() + 24, getY() + 9, "Void Shift", new Color(168, 60, 222));

		Res.VOIDLORD_RESOURCE.IDLE_DOWN[0].draw(getX() + 21, getY() + 12, 12, 12, new Color(255, 255, 255, 150));

		Res.bitFont.drawString(getX() + 32, getY() + 16, VoidLordInvisibleInfo.DESTROY_TIMER / 1000 + "", Color.pink);

		Res.bitFont.drawString(getX() + 96, getY() + 16, Math.round(VoidLordInvisibleInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 9, "Shift into the void and become invisible to", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 15, "all enemies.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 26, "Void Pull", new Color(168, 60, 222));


		Res.bitFont.drawString(getX() + 96, getY() + 33f, Math.round(VoidLordPullInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 26, "Send out your void hand that teleports you to", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 32, "the first entity hit.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 43, "Void Scream", new Color(168, 60, 222));


		Res.bitFont.drawString(getX() + 96, getY() + 50f, Math.round(VoidLordAOEInfo.COOLDOWN) + "", Color.red);


		if (Game.getGame().isInGame()) {
			Res.bitFont.drawString(getX() + 32, getY() + 50,
					Res.oneDecimal(EntityInfo.getOverallPower(VoidLordAOEInfo.DAMAGE,
							Game.getGame().getPlayer().getPowerBuffAmount())
							/ (VoidLordAOEInfo.DESTROY_TIMER / 1000)) + "/sec",
					Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 50,
					Res.oneDecimal(VoidLordAOEInfo.DAMAGE / (VoidLordAOEInfo.DESTROY_TIMER / 1000)) + "/sec",
					Color.orange);
		}

		Res.bitFont.drawString(getX() + 111, getY() + 43, "Scream in a small radius doing AOE damage", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 49, "over time to enemies around you.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 60, "Call To The Void", new Color(168, 60, 222));


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 67,
							Res.oneDecimal(EntityInfo.getOverallPower(VoidLordHoleInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 67, VoidLordHoleInfo.DAMAGE + "", Color.orange);
		}

		Res.bitFont.drawString(getX() + 96, getY() + 67f, Math.round(VoidLordHoleInfo.COOLDOWN) + "", Color.red);

		Res.VOIDLORD_RESOURCE.IDLE_DOWN[0].drawFlash(getX() + 54, getY() + 63, 12, 12);
		Res.bitFont.drawString(getX() + 64, getY() + 67, VoidLordHoleInfo.STUN_DURATION + "", Color.yellow);

		Res.bitFont.drawString(getX() + 111, getY() + 60, "Spawns a portal that stuns all enemies that", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 66, "come in contact with it.", Color.white);
	}
}
