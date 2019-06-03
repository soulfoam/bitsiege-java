package soulfoam.arena.entities.abilities.waterqueen;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenAbsorbInfo;

public class WaterQueenAbsorb extends Ability {

	public WaterQueenAbsorb(int gameID, Entity player) {

		abilityID = AbilityInfo.WATERQUEENABSORB;

		this.gameID = gameID;

		this.player = player;

		coolDown = WaterQueenAbsorbInfo.COOLDOWN;
		destroyTimer = WaterQueenAbsorbInfo.DESTROY_TIMER;

		width = WaterQueenAbsorbInfo.WIDTH;
		height = WaterQueenAbsorbInfo.HEIGHT;
		hitBoxWidth = WaterQueenAbsorbInfo.HITBOX_WIDTH;
		hitBoxHeight = WaterQueenAbsorbInfo.HITBOX_HEIGHT;

		skillIcon = Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[2];
		soundEffect = Res.WATERQUEEN_RESOURCE.SFX_WATERQUEEN[3];

		x = player.getX() + 4;
		y = player.getY() + 8;
		animation = new Animation(player.getSkin().getWaterQueenSkin().getAbsorb(), animSpeed);

	}

	public void update(int delta) {

		if (loopSound) {
			if (!soundEffect.playing()) {
				Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			}
		}

		x = player.getX() + 4;
		y = player.getY() + 8;

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			soundEffect.stop();
			removeThis();
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 16);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 16);
	}

	public Shape getBounds() {
		float radius = 12;
		return new Circle(x, y, radius);
	}

	public void playSoundEffect() {
		loopSound = true;
	}

}
