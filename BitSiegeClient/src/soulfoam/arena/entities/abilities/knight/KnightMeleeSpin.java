package soulfoam.arena.entities.abilities.knight;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightMeleeSpinInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class KnightMeleeSpin extends Ability {

	public KnightMeleeSpin(int gameID, Entity player) {

		abilityID = AbilityInfo.KNIGHTMELEESPIN;
		tag = AbilityInfo.AOE;

		this.gameID = gameID;

		this.player = player;

		coolDown = KnightMeleeSpinInfo.COOLDOWN;
		damage = KnightMeleeSpinInfo.DAMAGE;
		destroyTime = KnightMeleeSpinInfo.DESTROY_TIMER;
		destroyTimer = destroyTime;

		width = KnightMeleeSpinInfo.WIDTH;
		height = KnightMeleeSpinInfo.HEIGHT;
		hitBoxWidth = KnightMeleeSpinInfo.HITBOX_WIDTH;
		hitBoxHeight = KnightMeleeSpinInfo.HITBOX_HEIGHT;

		skillIcon = Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[1];
		soundEffect = Res.KNIGHT_RESOURCE.SFX_KNIGHT[2];

		x = player.getX() + 4;
		y = player.getY() + 8;
		animation = new Animation(player.getSkin().getKnightSkin().getSpin(), animSpeed);

	}

	public void update(int delta) {

		if (loopSound) {
			if (!soundEffect.playing()) {
				Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			}
		}

		x = player.getX() + 4;
		y = player.getY() + 8;
		player.setCurrentAction(EntityInfo.SPINNING);

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 20);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 8);
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		loopSound = true;
	}

	public void removeThis() {
		player.setCurrentAction(EntityInfo.IDLE);

		Game.getGame().removeAbility(this);
	}
}
