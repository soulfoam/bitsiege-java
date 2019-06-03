package soulfoam.arena.entities.abilities.warlock;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockLifeDrainInfo;

public class WarlockLifeDrain extends Ability {

	public WarlockLifeDrain(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.WARLOCKLIFEDRAIN;
		tag = AbilityInfo.AOE;
		renderLayer = Ability.RENDER_BACK;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = WarlockLifeDrainInfo.COOLDOWN;
		damage = WarlockLifeDrainInfo.DAMAGE;
		destroyTime = WarlockLifeDrainInfo.DESTROY_TIMER;
		destroyTimer = destroyTime;
		slowAmount = WarlockLifeDrainInfo.SLOW_AMOUNT;

		width = WarlockLifeDrainInfo.WIDTH;
		height = WarlockLifeDrainInfo.HEIGHT;
		hitBoxWidth = WarlockLifeDrainInfo.HITBOX_WIDTH;
		hitBoxHeight = WarlockLifeDrainInfo.HITBOX_HEIGHT;

		skillIcon = Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[1];
		soundEffect = Res.WARLOCK_RESOURCE.SFX_WARLOCK[2];
		soundEffectVolume = 0.8f;

		animation = new Animation(Res.WARLOCK_RESOURCE.LIFEDRAIN, animSpeed);
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
		return Res.roundForRendering(x - 30);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 20);
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		loopSound = true;
	}

}
