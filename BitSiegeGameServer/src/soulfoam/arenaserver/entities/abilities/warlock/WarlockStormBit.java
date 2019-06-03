package soulfoam.arenaserver.entities.abilities.warlock;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockStormBitInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class WarlockStormBit extends Ability{
	
	public WarlockStormBit(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.WARLOCKSTORMBIT;
		
		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dy = 1;
		
		this.player = player;
		
		
		this.damage = WarlockStormBitInfo.DAMAGE;
		this.moveSpeed = WarlockStormBitInfo.MOVE_SPEED;
		this.destroyTimer = WarlockStormBitInfo.DESTROY_TIMER;
		
		this.width = WarlockStormBitInfo.WIDTH;
		this.height = WarlockStormBitInfo.HEIGHT;
		this.hitBoxWidth = WarlockStormBitInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WarlockStormBitInfo.HITBOX_HEIGHT;
		
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
		move(delta, false);
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
				if (!collision.getPlayersCycled().contains(p) && collision.getPlayersCycled().size() < playersToDamage){
					p.takeDamage(damage, player);
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