package soulfoam.arenaserver.entities.abilities.knight;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightShieldThrowInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class KnightShieldThrow extends Ability{

	public KnightShieldThrow(int gameID, float dx, float dy, Entity player){

		this.abilityID = AbilityInfo.KNIGHTSHIELDTHROW;
		this.tag = AbilityInfo.PROJECTILE;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = KnightShieldThrowInfo.COOLDOWN;
		this.damage = KnightShieldThrowInfo.DAMAGE;
		this.moveSpeed = KnightShieldThrowInfo.MOVE_SPEED;
		this.destroyTimer = KnightShieldThrowInfo.DESTROY_TIMER;
		this.stunDuration = KnightShieldThrowInfo.STUN_DURATION;
		
		this.width = KnightShieldThrowInfo.WIDTH;
		this.height = KnightShieldThrowInfo.HEIGHT;
		this.hitBoxWidth = KnightShieldThrowInfo.HITBOX_WIDTH;
		this.hitBoxHeight = KnightShieldThrowInfo.HITBOX_HEIGHT;


		this.x = player.getX();
		this.y = player.getY() + 4;
		

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
					p.takeStun(stunDuration, player);
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
