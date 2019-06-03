package soulfoam.arenaserver.entities.abilities.warlock;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockStormInfo;


public class WarlockStorm extends Ability{
	
	private float elapsedTimer = 0;
	private float destroyTime;
	
	private int stormBitCount = WarlockStormInfo.COLUMN_COUNT;
	
	private boolean canAttack = true;

	public WarlockStorm(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.WARLOCKSTORM;
		
		this.gameID = gameID;
		this.x = x - 58;
		this.y = y - 50;

		this.player = player;
		

		this.coolDown = WarlockStormInfo.COOLDOWN;
		this.destroyTimer = WarlockStormInfo.DESTROY_TIMER;

	}
	
	public void update(int delta){
		
		if (elapsedTimer > 0){
			elapsedTimer-= delta;
			canAttack = false;
		}
		
		if (elapsedTimer <= 0){
			canAttack = true;
			elapsedTimer = 500;
		}
		if (canAttack){
			handleAttackServer();
		}
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}
		
	}

	public void handleAttackServer(){
		
		for (int i = 0; i < stormBitCount; i++){
			Game.getGame().getServerFunctions().sendAbilityAdd(player.getPlayerID(), AbilityInfo.WARLOCKSTORMBIT, Game.getGame().getIdPool().getAvailableAbilityID(), (x + 24 * i), y);
		}

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
