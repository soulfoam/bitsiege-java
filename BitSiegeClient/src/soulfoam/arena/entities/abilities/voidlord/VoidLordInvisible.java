package soulfoam.arena.entities.abilities.voidlord;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordInvisibleInfo;

public class VoidLordInvisible extends Ability {

	public VoidLordInvisible(int gameID, Entity player) {

		abilityID = AbilityInfo.VOIDLORDINVISIBLE;

		this.gameID = gameID;

		this.player = player;

		coolDown = VoidLordInvisibleInfo.COOLDOWN;
		destroyTimer = VoidLordInvisibleInfo.DESTROY_TIMER;

		skillIcon = Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[0];
		soundEffect = Res.VOIDLORD_RESOURCE.SFX_VOIDLORD[1];

	}

	public void update(int delta) {

		if (playedSoundOnce && !soundEffect.playing()) {
			removeThis();
		}

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce && player != null && player.isLocalPlayer()) {
			Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			playedSoundOnce = true;
		}
	}

}
