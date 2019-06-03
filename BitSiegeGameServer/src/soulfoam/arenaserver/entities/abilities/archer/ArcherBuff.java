package soulfoam.arenaserver.entities.abilities.archer;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherBuffInfo;


public class ArcherBuff extends Ability{
	
	public float attackCDTimeBoost;
	public float moveSpeedBoost;
	
	private boolean addedBoost;

	public ArcherBuff(int gameID, Entity player){
		
		this.abilityID = AbilityInfo.ARCHERBUFF;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.coolDown = ArcherBuffInfo.COOLDOWN;
		this.destroyTimer = ArcherBuffInfo.DESTROY_TIMER;
		this.moveSpeedBoost = ArcherBuffInfo.MOVE_SPEED_BOOST_AMOUNT;
		this.attackCDTimeBoost = ArcherBuffInfo.ATTACK_SPEED_BOOST_AMOUNT;

		this.width = ArcherBuffInfo.WIDTH;
		this.height = ArcherBuffInfo.HEIGHT;
		this.hitBoxWidth = ArcherBuffInfo.HITBOX_WIDTH;
		this.hitBoxHeight = ArcherBuffInfo.HITBOX_HEIGHT;
		

		this.x = player.getX();
		this.y = player.getY() + 1;
		this.moveSpeedBoost = Res.percentOf(player.getBaseMoveSpeed(), ArcherBuffInfo.MOVE_SPEED_BOOST_AMOUNT);
		this.attackCDTimeBoost = Res.percentOf(player.getAbilityBasicAttackCDTime(), ArcherBuffInfo.ATTACK_SPEED_BOOST_AMOUNT);
		
		
	}
	
	public void update(int delta){
		

		this.x = player.getX();
		this.y = player.getY() + 1;
		
		
		if (!addedBoost){

			player.setMoveSpeed(player.getMoveSpeed() + moveSpeedBoost);
			player.setAbilityBasicAttackCDTime(player.getAbilityBasicAttackCDTime() + attackCDTimeBoost);
			
			addedBoost = true;
		}
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0)
		{	
			removeThis(true);
		}
		
	}
	
	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	
	public void removeThis(boolean broadcastRemove){

		player.setAbilityBasicAttackCDTime(player.getAbilityBasicAttackCDTime() - attackCDTimeBoost);
		player.setMoveSpeed(player.getMoveSpeed() - moveSpeedBoost);
		
		
		Game.getGame().removeAbility(this, false);

	}

}
