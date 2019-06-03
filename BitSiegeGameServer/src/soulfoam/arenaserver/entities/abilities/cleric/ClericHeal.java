package soulfoam.arenaserver.entities.abilities.cleric;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericHealInfo;


public class ClericHeal extends Ability{
	
	public ClericHeal(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.CLERICHEAL;
		
		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;
		

		this.coolDown = ClericHealInfo.COOLDOWN; 
		this.damage = ClericHealInfo.DAMAGE;
		this.destroyTime = ClericHealInfo.DESTROY_TIMER;
		this.destroyTimer = destroyTime;
		
		this.width = ClericHealInfo.WIDTH;
		this.height = ClericHealInfo.HEIGHT;
		this.hitBoxWidth = ClericHealInfo.HITBOX_WIDTH;
		this.hitBoxHeight = ClericHealInfo.HITBOX_HEIGHT;

	}

	public void update(int delta) {

		handleCollision(delta);	

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}

	}
	
	public void handleCollision(int delta){

		for (Entity p: Game.getGame().getPlayers()){
			if (getBounds().intersects(p.getBoundsFeet()) || getBounds().contains(p.getBoundsFeet()))
			{
				if (!p.isDead() && p.getTeam() == player.getTeam()){
					p.takeHeal(damage/destroyTime * delta, player);
				}
			}
		}
	}
	
	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}
	
}
