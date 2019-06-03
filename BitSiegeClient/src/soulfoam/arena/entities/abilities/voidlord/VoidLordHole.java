package soulfoam.arena.entities.abilities.voidlord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordHoleInfo;

public class VoidLordHole extends Ability {

	public VoidLordHole(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.VOIDLORDHOLE;
		tag = AbilityInfo.AOE;
		renderLayer = Ability.RENDER_GROUND;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = VoidLordHoleInfo.COOLDOWN;
		damage = VoidLordHoleInfo.DAMAGE;
		destroyTimer = VoidLordHoleInfo.DESTROY_TIMER;
		stunDuration = VoidLordHoleInfo.STUN_DURATION;

		width = VoidLordHoleInfo.WIDTH;
		height = VoidLordHoleInfo.HEIGHT;
		hitBoxWidth = VoidLordHoleInfo.HITBOX_WIDTH;
		hitBoxHeight = VoidLordHoleInfo.HITBOX_HEIGHT;

		skillIcon = Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[3];
		soundEffect = Res.VOIDLORD_RESOURCE.SFX_VOIDLORD[4];

		animation = new Animation(player.getSkin().getVoidLordSkin().getUlt(), 100);

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
		return Res.roundForRendering(x - 36);
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
