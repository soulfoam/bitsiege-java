package soulfoam.arenaserver.entities.abilities.voidlord;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordHoleInfo;


public class VoidLordHole extends Ability{
	
	public boolean setInvis;
	
	public VoidLordHole(int gameID, Entity player){

		this.abilityID = AbilityInfo.VOIDLORDHOLE;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;

		this.player = player;
		

		this.coolDown = VoidLordHoleInfo.COOLDOWN; 
		this.damage = VoidLordHoleInfo.DAMAGE;
		this.destroyTimer = VoidLordHoleInfo.DESTROY_TIMER;
		this.stunDuration = VoidLordHoleInfo.STUN_DURATION;
		
		this.width = VoidLordHoleInfo.WIDTH;
		this.height = VoidLordHoleInfo.HEIGHT;
		this.hitBoxWidth = VoidLordHoleInfo.HITBOX_WIDTH;
		this.hitBoxHeight = VoidLordHoleInfo.HITBOX_HEIGHT;
		

		this.x = player.getX() + 4;
		this.y = player.getY() + 15;
		
		
	}
	
	public void update(int delta) {

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
		
		handleCollision();

	}
	
	public void handleCollision(){

		for (Entity p: Game.getGame().getPlayers()){
			if (getBounds().intersects(p.getBoundsFeet()) || getBounds().contains(p.getBoundsFeet()))
			{
				if (!p.isDead() && p.getTeam() != player.getTeam() && !collision.getPlayersHit().contains(p)){
					p.takeDamage(damage, player);
					p.takeStun(stunDuration, player);
					if (p.canBeStunned()){
						collision.getPlayersHit().add(p);
					}
				}
			}
		}
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}
