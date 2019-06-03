package soulfoam.arena.entities.abilities.warlock;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockStormBitInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockStormInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class WarlockStorm extends Ability {

	public WarlockStorm(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.WARLOCKSTORM;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = WarlockStormInfo.COOLDOWN;
		destroyTimer = WarlockStormInfo.DESTROY_TIMER;

		skillIcon = Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[3];
		soundEffect = Res.WARLOCK_RESOURCE.SFX_WARLOCK[4];

	}

	public void update(int delta) {

		if (loopSound) {
			if (!soundEffect.playing()) {
				Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			}
		}

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			soundEffect.stop();
			removeThis();
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 20);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 1);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public float getTotalDamage() {
		float dmg = WarlockStormInfo.COLUMN_COUNT * WarlockStormInfo.ROW_COUNT * WarlockStormBitInfo.DAMAGE;
		return EntityInfo.getOverallPower(dmg, Game.getGame().getPlayer().getPowerBuffAmount());
	}

	public void playSoundEffect() {
		loopSound = true;
	}

}
