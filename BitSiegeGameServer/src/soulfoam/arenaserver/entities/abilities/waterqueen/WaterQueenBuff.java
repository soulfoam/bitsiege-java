package soulfoam.arenaserver.entities.abilities.waterqueen;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenBuffInfo;


public class WaterQueenBuff extends Ability{
	
	public float moveSpeedBoost;

	private boolean addedBoost;
	
	
	public WaterQueenBuff(int gameID, Entity player){

		this.abilityID = AbilityInfo.WATERQUEENWATERBUFF;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.coolDown = WaterQueenBuffInfo.COOLDOWN;
		this.destroyTime = WaterQueenBuffInfo.DESTROY_TIMER;
		this.destroyTimer = destroyTime;
		
		this.width = WaterQueenBuffInfo.WIDTH;
		this.height = WaterQueenBuffInfo.HEIGHT;
		this.hitBoxWidth = WaterQueenBuffInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WaterQueenBuffInfo.HITBOX_HEIGHT;

		this.x = player.getX();
		this.y = player.getY() + 1;
		this.moveSpeedBoost = Res.percentOf(player.getMoveSpeed(), WaterQueenBuffInfo.MOVE_SPEED_BOOST_AMOUNT);
		this.damage = player.getBaseHealth()/2;
		

	}
	
	public void update(int delta) {
		
		if (!addedBoost){
			player.setMoveSpeed(player.getMoveSpeed() + moveSpeedBoost);
			addedBoost = true;
		}
		
		x = player.getX();
		y = player.getY() + 1;
		
		player.takeHeal((damage / destroyTime) *delta, player);
		
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
	public void removeThis(boolean broadcastRemove){
		
		player.setMoveSpeed(player.getMoveSpeed() - moveSpeedBoost);
		
		Game.getGame().removeAbility(this, false);

	}

}
