package soulfoam.arenaserver.entities.abilities.waterqueen;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenWaterBallInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class WaterQueenWaterBall extends Ability{

	public WaterQueenWaterBall(int gameID, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.WATERQUEENWATERBALL;
		this.tag = AbilityInfo.PROJECTILE;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = WaterQueenWaterBallInfo.COOLDOWN;
		this.damage = WaterQueenWaterBallInfo.DAMAGE;
		this.moveSpeed = WaterQueenWaterBallInfo.MOVE_SPEED;
		this.destroyTimer = WaterQueenWaterBallInfo.DESTROY_TIMER;
		this.slowDuration = WaterQueenWaterBallInfo.SLOW_DURATION;
		this.slowAmount = WaterQueenWaterBallInfo.SLOW_AMOUNT;
		
		this.width = WaterQueenWaterBallInfo.WIDTH;
		this.height = WaterQueenWaterBallInfo.WIDTH;
		this.hitBoxWidth = WaterQueenWaterBallInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WaterQueenWaterBallInfo.HITBOX_HEIGHT;

		this.x = player.getX() - 4;
		this.y = player.getY();
		
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
