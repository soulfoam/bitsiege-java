package soulfoam.arenaserver.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistSpawnCloneInfo;


public class IllusionistSpawnClone extends Ability{
	
	public boolean addedOnce;
	
	public IllusionistSpawnClone(int gameID, Entity player){
		
		this.abilityID = AbilityInfo.ILLUSIONISTSPAWNCLONE;
		
		this.gameID = gameID;

		this.player = player;
		

		this.coolDown = IllusionistSpawnCloneInfo.COOLDOWN;

		this.width = IllusionistSpawnCloneInfo.WIDTH;
		this.height = IllusionistSpawnCloneInfo.HEIGHT;
		this.hitBoxWidth = IllusionistSpawnCloneInfo.HITBOX_WIDTH;
		this.hitBoxHeight = IllusionistSpawnCloneInfo.HITBOX_HEIGHT;
		

		this.x = player.getX();
		this.y = player.getY();
		

	}
	
	public void update(int delta) {
		
		if (!addedOnce){
			Game.getGame().getServerFunctions().sendAbilityAdd(player.getPlayerID(), AbilityInfo.ILLUSIONISTCLONE, Game.getGame().getIdPool().getAvailableAbilityID(), player.getX(), player.getY());
			addedOnce = true;
		}
		
		removeThis(false);
		
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
