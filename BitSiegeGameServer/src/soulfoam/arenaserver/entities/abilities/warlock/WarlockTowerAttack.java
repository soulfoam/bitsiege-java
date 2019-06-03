package soulfoam.arenaserver.entities.abilities.warlock;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTowerAttackInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class WarlockTowerAttack extends Ability{
	
	public WarlockTowerAttack(int gameID, float x, float y, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.WARLOCKTOWERATTACK;
		
		this.gameID = gameID;
		this.tag = AbilityInfo.PROJECTILE;

		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		
		this.player = player;
		
		
		this.damage = WarlockTowerAttackInfo.DAMAGE;
		this.moveSpeed = WarlockTowerAttackInfo.MOVE_SPEED;
		this.destroyTimer = WarlockTowerAttackInfo.DESTROY_TIMER;
		
		this.width = WarlockTowerAttackInfo.WIDTH;
		this.height = WarlockTowerAttackInfo.HEIGHT;
		this.hitBoxWidth = WarlockTowerAttackInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WarlockTowerAttackInfo.HITBOX_HEIGHT;
		
		this.hitBoxWidth = 8;
		this.hitBoxHeight = 8;
		
	}
	
	public void update(int delta) {

		handleMovement(delta);
		handleCollision();	
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0){
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
