package soulfoam.arena.entities.abilities.cleric;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericHealInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class ClericHeal extends Ability {

	public ClericHeal(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.CLERICHEAL;
		renderLayer = Ability.RENDER_GROUND;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = ClericHealInfo.COOLDOWN;
		damage = ClericHealInfo.DAMAGE;
		destroyTime = ClericHealInfo.DESTROY_TIMER;
		destroyTimer = destroyTime;

		width = ClericHealInfo.WIDTH;
		height = ClericHealInfo.HEIGHT;
		hitBoxWidth = ClericHealInfo.HITBOX_WIDTH;
		hitBoxHeight = ClericHealInfo.HITBOX_HEIGHT;

		skillIcon = Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[0];
		soundEffect = Res.CLERIC_RESOURCE.SFX_CLERIC[2];
		soundEffectVolume = 0.8f;

		animSpeed = 70;
		animation = new Animation(Res.CLERIC_RESOURCE.AOE_HEAL, animSpeed);
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
		return Res.roundForRendering(x - 56);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 32);
	}

	public float getTotalDamage() {
		return EntityInfo.getOverallPower(damage, Game.getGame().getPlayer().getPowerBuffAmount()) * (destroyTimer / 1000);
	}

	public Shape getBounds() {
		hitBoxWidth = 53;
		hitBoxHeight = 26;
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		loopSound = true;
	}

	public float sortByY() {
		return 0;
	}
}
