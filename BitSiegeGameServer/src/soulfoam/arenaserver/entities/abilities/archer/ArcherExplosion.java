package soulfoam.arenaserver.entities.abilities.archer;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherExplosionInfo;


public class ArcherExplosion extends Ability{

	public ArcherExplosion(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.ARCHEREXPLOSION;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;
		
		
		this.damage = ArcherExplosionInfo.DAMAGE;
		this.destroyTimer = ArcherExplosionInfo.DESTROY_TIMER;

		this.width = ArcherExplosionInfo.WIDTH;
		this.height = ArcherExplosionInfo.HEIGHT;
		this.hitBoxWidth = ArcherExplosionInfo.HITBOX_WIDTH;
		this.hitBoxHeight = ArcherExplosionInfo.HITBOX_HEIGHT;
	}

	public void update(int delta) {
		
		handleCollision();	
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(true);
		}
	}


	public void handleCollision(){
		
		for (Entity p: Game.getGame().getPlayers()){

			if (getBounds().intersects(p.getBounds()) || getBounds().contains(p.getBounds()))
			{
				if (!collision.getPlayersHit().contains(p) && !p.isDead() && p.getTeam() != player.getTeam()){
					collision.getPlayersHit().add(p);
				}
			}
			
		}
		
		if (collision.getPlayersHit().size() > 0){
			for (Entity p: collision.getPlayersHit()){
				if (!collision.getPlayersCycled().contains(p)){
					p.takeDamage(damage, player);
					collision.getPlayersCycled().add(p);
				}
			}
		}
			
		
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}
}
