package soulfoam.arenaserver.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.challengers.Illusionist;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistRemoteSpawnInfo;


public class IllusionistRemoteCloneSpawn extends Ability{

	private boolean addedOnce = false;
	
	public IllusionistRemoteCloneSpawn(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.ILLUSIONISTREMOTECLONESPAWN;
		
		this.gameID = gameID;
		this.x = x;
		this.y = y;
		
		this.player = player;
		

		this.coolDown = IllusionistRemoteSpawnInfo.COOLDOWN;

	}
	
	public void update(int delta) {

		Illusionist illusPlayer = (Illusionist) player;
		if (player != null){
			if (illusPlayer.passive.ultCharges > 0){
				if (!addedOnce){
					takeCharge(illusPlayer);
					Game.getGame().getServerFunctions().sendAbilityAdd(player.getPlayerID(), AbilityInfo.ILLUSIONISTULTCLONE, Game.getGame().getIdPool().getAvailableAbilityID(), x, y);
					addedOnce = true;
				}
			}
		}
		
		removeThis(false);

	}
	
	private void takeCharge(Illusionist illusPlayer){
		illusPlayer.passive.ultCharges--;
		if (illusPlayer.passive.ultCharges <= 0){
			illusPlayer.setAbility4CDTimer(999 * 1000);
		}
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
