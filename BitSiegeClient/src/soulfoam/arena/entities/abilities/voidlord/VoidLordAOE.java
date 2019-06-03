package soulfoam.arena.entities.abilities.voidlord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordAOEInfo;

public class VoidLordAOE extends Ability {

	public VoidLordAOE(int gameID, Entity player) {

		abilityID = AbilityInfo.VOIDLORDAOE;
		tag = AbilityInfo.AOE;
		renderLayer = Ability.RENDER_BACK;

		this.gameID = gameID;

		this.player = player;

		coolDown = VoidLordAOEInfo.COOLDOWN;
		damage = VoidLordAOEInfo.DAMAGE;
		destroyTime = VoidLordAOEInfo.DESTROY_TIMER;
		destroyTimer = destroyTime;

		width = VoidLordAOEInfo.WIDTH;
		height = VoidLordAOEInfo.HEIGHT;
		hitBoxWidth = VoidLordAOEInfo.HITBOX_WIDTH;
		hitBoxHeight = VoidLordAOEInfo.HITBOX_HEIGHT;

		skillIcon = Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[2];
		soundEffect = Res.VOIDLORD_RESOURCE.SFX_VOIDLORD[3];

		x = player.getX() + 4;
		y = player.getY() + 15;
		animation = new Animation(player.getSkin().getVoidLordSkin().getAoe(), animSpeed);

	}

	public void update(int delta) {

		if (loopSound) {
			if (!soundEffect.playing()) {
				Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			}
		}

		x = player.getX() + 4;
		y = player.getY() + 15;

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
