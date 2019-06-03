package soulfoam.arena.entities.abilities.cleric;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericSwitchInfo;

public class ClericSwitch extends Ability {

	public ClericSwitch(int gameID, Entity player) {

		abilityID = AbilityInfo.CLERICSWITCH;
		this.gameID = gameID;
		this.player = player;
		coolDown = ClericSwitchInfo.COOLDOWN;

		skillIcon = Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[2];

		if (player.getAbilityBasicAttack().getAbilityID() == AbilityInfo.CLERICBASICATTACKHEAL) {
			soundEffect = Res.CLERIC_RESOURCE.SFX_CLERIC[5];
		} else {
			soundEffect = Res.CLERIC_RESOURCE.SFX_CLERIC[4];
		}

	}

	public void update(int delta) {

		if (player.getAbilityBasicAttack().getAbilityID() == AbilityInfo.CLERICBASICATTACKHEAL) {
			player.setAbilityBasicAttack(Game.getGame().getAbilityByID(AbilityInfo.CLERICBASICATTACK, 0, 0, 0, 0, 0, player));
		} else {
			player.setAbilityBasicAttack(Game.getGame().getAbilityByID(AbilityInfo.CLERICBASICATTACKHEAL, 0, 0, 0, 0, 0, player));
		}

		playSoundEffect();
		removeThis();

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 4);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 4);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce && player.isLocalPlayer()) {
			Game.getGame().getSoundSystem().playSound(soundEffect, 1);
			playedSoundOnce = true;
		}
	}

}
