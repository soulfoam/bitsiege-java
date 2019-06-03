package soulfoam.arenaserver.entities.abilities.archer;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.AbilityStackInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherIceArrowInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class ArcherIceArrow extends Ability{
	
	public ArcherIceArrow(int gameID, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.ARCHERICEARROW;
		this.tag = AbilityInfo.PROJECTILE;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = ArcherIceArrowInfo.COOLDOWN;
		this.damage = ArcherIceArrowInfo.DAMAGE;
		this.moveSpeed = ArcherIceArrowInfo.MOVE_SPEED;
		this.destroyTimer = ArcherIceArrowInfo.DESTROY_TIMER;
		this.slowDuration = ArcherIceArrowInfo.SLOW_DURATION;
		this.slowAmount = ArcherIceArrowInfo.SLOW_AMOUNT;
		
		this.width = ArcherIceArrowInfo.WIDTH;
		this.height = ArcherIceArrowInfo.HEIGHT;
		

		this.hitBoxWidth = 8;
		this.hitBoxHeight = 8;
		this.x = player.getX() + 5;
		this.y = player.getY() + 9;
		
	}

	public void update(int delta) {
		
		handleMovement(delta);
		handleCollision();	
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}

	}
	
	public void handleMovement(int delta){
		
		if (!move(delta)){
			removeThis(false);
		}
		
	}

	public void handleCollision(){

		for (Entity p: Game.getGame().getPlayers()){
			if (getBounds().intersects(p.getBounds()) || getBounds().contains(p.getBounds())){
				if (!collision.getPlayersHit().contains(p) && !p.isDead() && p.getTeam() != player.getTeam()){
					collision.getPlayersHit().add(p);
				}
			}
		}

		if (collision.getPlayersHit().size() > 0){
			for (Entity p: collision.getPlayersHit()){
				if (!collision.getPlayersCycled().contains(p) && collision.getPlayersCycled().size() < playersToDamage){
					p.takeDamage(damage, player);
					p.takeSlow(slowDuration, Res.percentOf(p.getMoveSpeed(), (int)slowAmount), player);
					collision.getPlayersCycled().add(p);
				}
			}
			removeThis(true);
		}
			
	}
	

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
}
