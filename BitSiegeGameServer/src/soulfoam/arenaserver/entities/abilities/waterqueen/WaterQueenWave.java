package soulfoam.arenaserver.entities.abilities.waterqueen;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenWaveInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class WaterQueenWave extends Ability{

	public WaterQueenWave(int gameID, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.WATERQUEENWAVE;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = WaterQueenWaveInfo.COOLDOWN;
		this.damage = WaterQueenWaveInfo.DAMAGE;
		this.moveSpeed = WaterQueenWaveInfo.MOVE_SPEED;
		this.destroyTimer = WaterQueenWaveInfo.DESTROY_TIMER;
		
		this.width = WaterQueenWaveInfo.WIDTH;
		this.height = WaterQueenWaveInfo.HEIGHT;
		this.hitBoxWidth = WaterQueenWaveInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WaterQueenWaveInfo.HITBOX_HEIGHT;
		

		this.x = player.getX() - 4;
		this.y = player.getY();
		
		
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
			if (getBounds().intersects(p.getBoundsFeet()) || getBounds().contains(p.getBoundsFeet()))
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
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}


}
