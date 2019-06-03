package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenAbsorbInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenBuffInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenWaterBallInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenWaveInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.WaterQueenInfo;

public class WaterQueenToolTip extends ChallengerToolTip {

	public WaterQueenToolTip(float x, float y) {
		setX(x);
		setY(y);

		si1 = Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[0];
		si2 = Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[1];
		si3 = Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[2];
		si4 = Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[3];

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

		Res.bitFont.drawString(getX() + 24, getY() + 9, "Tidal Wave", new Color(28, 154, 237));

		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 16,
							"" + Res.oneDecimal(EntityInfo.getOverallPower(WaterQueenWaveInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 16, WaterQueenWaveInfo.DAMAGE + "", Color.orange);
		}
		
		Res.bitFont.drawString(getX() + 100, getY() + 16, Math.round(WaterQueenWaveInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 9, "Send out a large tidal wave that damages all", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 15, "enemies it passes through.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 26, "Water Blast", new Color(28, 154, 237));


		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(getX() + 32, getY() + 33,
							Res.oneDecimal(EntityInfo.getOverallPower(WaterQueenWaterBallInfo.DAMAGE,
									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
							Color.orange);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 33, WaterQueenWaterBallInfo.DAMAGE + "", Color.orange);
		}


		Res.bitFont.drawString(getX() + 63, getY() + 33f, "-" + WaterQueenWaterBallInfo.SLOW_AMOUNT + "%", Color.red);

		Res.bitFont.drawString(getX() + 100, getY() + 33f, Math.round(WaterQueenWaterBallInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 26, "Send out a water ball that damages and slows", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 32, "the first enemy hit.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 43, "Absorb", Color.green);

		Res.bitFont.drawString(getX() + 32, getY() + 50, "+" + WaterQueenAbsorbInfo.ABSORB_AMOUNT + "%", Color.green);

		Res.bitFont.drawString(getX() + 100, getY() + 50f, Math.round(WaterQueenAbsorbInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 111, getY() + 43, "Absorb all incoming damage healing yourself", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 49,
				"for " + WaterQueenAbsorbInfo.ABSORB_AMOUNT + "% of the damage taken.", Color.white);

		Res.bitFont.drawString(getX() + 24, getY() + 60, "Water Power", new Color(28, 154, 237));

		if (Game.getGame().isInGame()) {
			Res.bitFont
					.drawString(
							getX() + 32, getY() + 67, "+" + Math.round(Game.getGame().getPlayer().getBaseHealth()
									/ 2 / (WaterQueenBuffInfo.DESTROY_TIMER / 1000)) + "/sec",
							Color.green);
		} else {
			Res.bitFont.drawString(getX() + 32, getY() + 67, "+"
					+ Math.round(WaterQueenInfo.BASE_HEALTH / 2 / (WaterQueenBuffInfo.DESTROY_TIMER / 1000)) + "/sec",
					Color.green);
		}

		Res.bitFont.drawString(getX() + 100, getY() + 67f, Math.round(WaterQueenBuffInfo.COOLDOWN) + "", Color.red);

		Res.bitFont.drawString(getX() + 72, getY() + 67f, "+" + WaterQueenBuffInfo.MOVE_SPEED_BOOST_AMOUNT + "%",
				Color.green);

		Res.bitFont.drawString(getX() + 111, getY() + 60, "Gain bonus move speed and heal yourself for", Color.white);
		Res.bitFont.drawString(getX() + 111, getY() + 66, "half of your base health over time.", Color.white);
	}
}
