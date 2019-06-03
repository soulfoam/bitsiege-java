package soulfoam.arena.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistRemoteSpawnInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class IllusionistRemoteCloneSpawn extends Ability {

	public IllusionistRemoteCloneSpawn(int gameID, Entity player) {

		abilityID = AbilityInfo.ILLUSIONISTREMOTECLONESPAWN;
		this.gameID = gameID;

		this.player = player;

		destroyTimer = 300;
		coolDown = IllusionistRemoteSpawnInfo.COOLDOWN;

		skillIcon = Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[3];
		soundEffect = Res.ILLUSIONIST_RESOURCE.SFX_ILLUSIONIST[4];

	}
	
	public boolean canCast(float x, float y){
		if (!Game.getGame().getWorld().getTile(x, y).isBlocked() && !Game.getGame().getWorld().getTile(x, y).isBlocked()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void update(int delta) {

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce && player != null && player.isLocalPlayer()) {
			Game.getGame().getSoundSystem().playSound(soundEffect, 1);
			playedSoundOnce = true;
		}
	}
}
