package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherFireArrowInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherIceArrowInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class ArcherToolTip extends ChallengerToolTip {

	public ArcherToolTip(float x, float y) {
		setX(x);
		setY(y);

		si1 = Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[0];
		si2 = Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[1];
		si3 = Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[2];
		si4 = Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[3];
	}

	public void update(GameContainer gc) {

	}

	public void render(Graphics g) {

		setX(GameInfo.RES_WIDTH / 2 - Res.UI_RESOURCE.TOOLTIP.getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - Res.UI_RESOURCE.TOOLTIP.getHeight() / 2);
		
		String headerText = "";
		Color headerColor = Color.white;

		int coolDown = 0;
	
		int abilityID = 0;
		
		if (abilityID == 0){
			
			Res.UI_RESOURCE.TOOLTIP.draw(getX(), getY());

			headerText = "Ice Arrow";
			headerColor = new Color(0, 100, 255);
			
			coolDown = Math.round(ArcherIceArrowInfo.COOLDOWN);
			
			si1.draw(getX() + 3, getY() + 2);
			
			Res.drawNewLineText(getX() + 3, getY() + 36, "Changes auto attacks to ice \narrows that slow enemies.", Res.COLOR_RESOURCE.TOOL_TIP_DESCRIPTION);
	
			if (Game.getGame().isInGame()) {
				Res.bitFont.drawString(getX() + 3, getY() + 108, Res.oneDecimal(EntityInfo.getOverallPower(ArcherIceArrowInfo.DAMAGE, Game.getGame().getPlayer().getPowerBuffAmount())) + "", 
						Res.COLOR_RESOURCE.TOOL_TIP_DAMAGE);
			} 
			else {
				Res.bitFont.drawString(getX() + 3, getY() + 108, "AA DMG Increase: " + Math.round(ArcherIceArrowInfo.DAMAGE),  Res.COLOR_RESOURCE.TOOL_TIP_DAMAGE);
			}

			Res.bitFont.drawString(getX() + 3, getY() + 116, "Slow Amount: -" + ArcherIceArrowInfo.SLOW_AMOUNT + "%", Res.COLOR_RESOURCE.TOOL_TIP_SLOW);
			Res.bitFont.drawString(getX() + 3, getY() + 124, "Slow Duration: " + ArcherIceArrowInfo.SLOW_DURATION + " seconds", Res.COLOR_RESOURCE.TOOL_TIP_DURATION);
			Res.bitFont.drawString(getX() + 3, getY() + 132, "Arrow Duration: " + Res.getDisplayDuration(ArcherIceArrowInfo.DESTROY_TIMER), Res.COLOR_RESOURCE.TOOL_TIP_DURATION);
			Res.bitFont.drawString(getX() + 3, getY() + 140, "Arrow Speed: " + Res.getDisplayMoveSpeed(ArcherIceArrowInfo.MOVE_SPEED), Res.COLOR_RESOURCE.TOOL_TIP_DURATION);
		}
		
		if (abilityID == 1){

			headerText = "Fire Arrow";
			headerColor = new Color(200, 30, 0);
			coolDown = Math.round(ArcherFireArrowInfo.COOLDOWN);
			
			si2.draw(getX() + 3, getY() + 2);
			
			Res.drawNewLineText(getX() + 3, getY() + 18, "Changes auto attacks to \nfire arrows that travel\nlong distances and deal\nhigh damage.", Res.COLOR_RESOURCE.TOOL_TIP_DESCRIPTION);
			
			if (Game.getGame().isInGame()) {
				Res.bitFont.drawString(getX() + 32, getY() + 33, Res.oneDecimal(EntityInfo.getOverallPower(ArcherFireArrowInfo.DAMAGE, Game.getGame().getPlayer().getPowerBuffAmount())) + "",
								Color.orange);
			} 
			else {
				Res.bitFont.drawString(getX() + 3, getY() + 108, "Damage: " + ArcherFireArrowInfo.DAMAGE, Res.COLOR_RESOURCE.TOOL_TIP_DAMAGE);
			}
			
			Res.bitFont.drawString(getX() + 3, getY() + 116, "Arrow Duration: " + Res.getDisplayDuration(ArcherFireArrowInfo.DESTROY_TIMER), Res.COLOR_RESOURCE.TOOL_TIP_DURATION);
			Res.bitFont.drawString(getX() + 3, getY() + 124, "Arrow Speed: " + Res.getDisplayMoveSpeed(ArcherFireArrowInfo.MOVE_SPEED), Res.COLOR_RESOURCE.TOOL_TIP_DURATION);
		}

		Res.bitFont.drawString((getX() + 147) - Res.getTextCenter(coolDown + "") + 31 / 2, getY() + 7, coolDown + "", Res.COLOR_RESOURCE.TOOL_TIP_COOLDOWN);
		Res.bitFont.drawString((getX() + 23) - Res.getTextCenter(headerText) + 121 / 2, getY() + 7, headerText, headerColor);
//
//		Res.bitFont.drawString(getX() + 24, getY() + 43, "Explosive Arrow", new Color(255, 100, 0));
//
//
//		if (Game.getGame().isInGame()) {
//			Res.bitFont
//					.drawString(getX() + 32, getY() + 50,
//							Res.oneDecimal(EntityInfo.getOverallPower(ArcherExplosiveArrowInfo.DAMAGE,
//									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
//							Color.orange);
//		} else {
//			Res.bitFont.drawString(getX() + 32, getY() + 50, ArcherExplosiveArrowInfo.DAMAGE + "", Color.orange);
//		}
//
//		Res.ARCHER_RESOURCE.EXPLOSION[3].draw(getX() + 55, getY() + 48, 8, 8);
//		if (Game.getGame().isInGame()) {
//			Res.bitFont
//					.drawString(getX() + 64, getY() + 50,
//							Res.oneDecimal(EntityInfo.getOverallPower(ArcherExplosionInfo.DAMAGE,
//									Game.getGame().getPlayer().getPowerBuffAmount())) + "",
//							Color.orange);
//		} else {
//			Res.bitFont.drawString(getX() + 64, getY() + 50, ArcherExplosionInfo.DAMAGE + "", Color.orange);
//		}
//
//
//		Res.bitFont.drawString(getX() + 100, getY() + 50f, Math.round(ArcherExplosiveArrowSwitchInfo.COOLDOWN) + "",
//				Color.red);
//
//		Res.bitFont.drawString(getX() + 111, getY() + 43, "Changes auto attacks to explosive arrows that", Color.white);
//		Res.bitFont.drawString(getX() + 111, getY() + 49, "explode every fourth arrow dealing AOE damage.", Color.white);
//
//		Res.bitFont.drawString(getX() + 24, getY() + 60, "Extreme Precision", Color.yellow);
//
//
//
//		Res.bitFont.drawString(getX() + 32, getY() + 67,
//				"+" + Math.round(ArcherBuffInfo.ATTACK_SPEED_BOOST_AMOUNT) + "%", Color.green);
//
//
//		Res.bitFont.drawString(getX() + 64, getY() + 67, "+" + ArcherBuffInfo.MOVE_SPEED_BOOST_AMOUNT + "%",
//				Color.green);
//
//
//		Res.bitFont.drawString(getX() + 100, getY() + 67, Math.round(ArcherBuffInfo.COOLDOWN) + "", Color.red);
//		Res.bitFont.drawString(getX() + 111, getY() + 60, "Increases movement speed and increases auto", Color.white);
//		Res.bitFont.drawString(getX() + 111, getY() + 66, "attack speed.", Color.white);
	}
}
