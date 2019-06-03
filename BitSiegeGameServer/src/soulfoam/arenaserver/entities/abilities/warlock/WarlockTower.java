package soulfoam.arenaserver.entities.abilities.warlock;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTowerInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class WarlockTower extends Ability{
	
	private float attackTime = WarlockTowerInfo.ATTACK_TIMER;
	private float attackTimer = attackTime;
	private boolean canAttack = true;

	public WarlockTower(int gameID, float x, float y, Entity player){

		this.abilityID = AbilityInfo.WARLOCKTOWER;
		
		this.gameID = gameID;
		this.x = x - 12;
		this.y = y - 8;

		this.player = player;
		
		
		this.coolDown = WarlockTowerInfo.COOLDOWN;
		this.destroyTimer = WarlockTowerInfo.DESTROY_TIMER;
		
		this.width = WarlockTowerInfo.WIDTH;
		this.height = WarlockTowerInfo.HEIGHT;
		this.hitBoxWidth = WarlockTowerInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WarlockTowerInfo.HITBOX_HEIGHT;
	}
	

	public void update(int delta) {
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0){
			removeThis(false);
		}
		
		if (attackTimer > 0) {
			attackTimer-= delta;
			canAttack = false;
		}
		
		if (attackTimer <= 0){
			canAttack = true;
		}

		handleAttackServer();
	}

	
	public void handleAttackServer(){
		
		if (canAttack){
	
	        float dx = player.getMouseX() - x - 12;
	        float dy = player.getMouseY() - y - 5;

	        float dirLength = (float) Math.sqrt(dx*dx + dy*dy);
	        dx = dx/dirLength;
	        dy = dy/dirLength;
	        
			Game.getGame().getServerFunctions().sendAbilityVelocityAdd(player.getPlayerID(), AbilityInfo.WARLOCKTOWERATTACK, Game.getGame().getIdPool().getAvailableAbilityID(), x + 11, y + 8, dx, dy);
			
			attackTimer = attackTime;
			
		}
		
	}

	public Shape getBounds() {
		
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);

	}	

}
