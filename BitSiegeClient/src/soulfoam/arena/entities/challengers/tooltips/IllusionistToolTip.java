package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistCloneAttackInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistCloneInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistRemoteSpawnInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistSpawnCloneInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistSwapPositionInfo;

import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class IllusionistToolTip extends ChallengerToolTip {

	public IllusionistToolTip(float x, float y) {
		setX(x);
		setY(y);

		si1 = Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[0];
		si2 = Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[1];
		si3 = Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[2];
		si4 = Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[3];

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

		Res.bitFont.drawString(getX() + 24, getY() + 9, "Spawn Clone", new Color(219, 0, 172));


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 16,
							Res.oneDecimal(EntityInfo.getOverallPower(IllusionistCloneInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 16, IllusionistCloneInfo.DAMAGE + "", Color.orange);
		}

		Res.ILLUSIONIST_RESOURCE.IDLE_DOWN[0].drawFlash(getX() + 54, getY() + 12, 12, 12);
		Res.bitFont.drawString(getX() + 64, getY() + 16, IllusionistCloneInfo.STUN_DURATION + "", Color.yellow);


		Res.bitFont.drawString(getX() + 100, getY() + 16, Math.round(IllusionistSpawnCloneInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 9, "Spawns a clone of yourself at your current", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 15, "position.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 26, "Clone Attack", new Color(219, 0, 172));


		Res.bitFont.drawString(getX() + 100, getY() + 33f, Math.round(IllusionistCloneAttackInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 26, "Commands all clones to attack. Damages and", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 32, "stuns the first enemy the clone hits.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 43, "Swap Clone", new Color(219, 0, 172));


		Res.bitFont.drawString(getX() + 100, getY() + 50f, Math.round(IllusionistSwapPositionInfo.COOLDOWN) + "",
				Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 46, "Swap positions with your last placed clone.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 60, "Remote Clone", new Color(219, 0, 172));


		Res.bitFont.drawString(getX() + 100, getY() + 67f, Math.round(IllusionistRemoteSpawnInfo.COOLDOWN) + "",
				Color.red);

		Res.ILLUSIONIST_RESOURCE.IDLE_DOWN[0].draw(getX() + 21, getY() + 63, 12, 12);
		Res.bitFont.drawString(getX() + 32, getY() + 67, (float) IllusionistRemoteSpawnInfo.CHARGES + "",
				Color.green);
		
		Res.bitFont.drawString(getX() + 111, getY() + 60, "Spawn a clone at your cursor. Holds " + IllusionistRemoteSpawnInfo.CHARGES + " charges", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 66, "per round.", Color.white);

	}
}
