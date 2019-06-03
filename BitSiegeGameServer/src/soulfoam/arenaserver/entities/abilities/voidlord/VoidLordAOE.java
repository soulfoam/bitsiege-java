package soulfoam.arenaserver.entities.abilities.voidlord;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordAOEInfo;


public class VoidLordAOE extends Ability{

	public boolean setInvis;

	public VoidLordAOE(int gameID, Entity player){

		this.abilityID = AbilityInfo.VOIDLORDAOE;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;

		this.player = player;
		

		this.coolDown = VoidLordAOEInfo.COOLDOWN;
		this.damage = VoidLordAOEInfo.DAMAGE;
		this.destroyTime = VoidLordAOEInfo.DESTROY_TIMER;
		this.destroyTimer = destroyTime;
		
		this.width = VoidLordAOEInfo.WIDTH;
		this.height = VoidLordAOEInfo.HEIGHT;
		this.hitBoxWidth = VoidLordAOEInfo.HITBOX_WIDTH;
		this.hitBoxHeight = VoidLordAOEInfo.HITBOX_HEIGHT;
		

		this.x = player.getX() + 4;
		this.y = player.getY() + 15;
		
		
	}

	
	public void update(int delta) {


		x = player.getX() + 4;
		y = player.getY() + 15;
		
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}
		
		if (!setInvis){

			player.setInvisible(false);
			
			setInvis = true;
			
		}
		
		handleCollision(delta);

	}
	
	public void handleCollision(int delta){
	
		for (Entity p: Game.getGame().getPlayers()){
			if (getBounds().intersects(p.getBoundsFeet()) || getBounds().contains(p.getBoundsFeet()))
			{
				if (!p.isDead() && p.getTeam() != player.getTeam()){
					p.takeDamage((damage / destroyTime) * delta, player);
				}
			}
		}
		
	}
	
	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}
	
}
