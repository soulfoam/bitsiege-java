package soulfoam.arenaserver.entities.abilities.archer;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.challengers.Archer;
import soulfoam.arenaserver.entities.challengers.bots.ArcherBot;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherExplosiveArrowInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class ArcherExplodingArrow extends Ability{

	public boolean addedToArrowCount;
	
	public ArcherExplodingArrow(int gameID, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.ARCHEREXPLODINGARROW;
		this.tag = AbilityInfo.PROJECTILE;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = ArcherExplosiveArrowInfo.COOLDOWN;
		this.damage = ArcherExplosiveArrowInfo.DAMAGE;
		this.moveSpeed = ArcherExplosiveArrowInfo.MOVE_SPEED;
		this.destroyTimer = ArcherExplosiveArrowInfo.DESTROY_TIMER;
		
		this.width = ArcherExplosiveArrowInfo.WIDTH;
		this.height = ArcherExplosiveArrowInfo.HEIGHT;
		

		this.hitBoxWidth = 8;
		this.hitBoxHeight = 8;
		this.x = player.getX() + 1;
		this.y = player.getY() + 6;
	

	}

	public void update(int delta){
		if (!addedToArrowCount){
			handleArrowCount();
		}
		handleMovement(delta);
		handleCollision();	
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0){
			removeThis(true);
		}
	}
	
	public void handleMovement(int delta){

		if (!move(delta)){
			handleExplosion();
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
					handleExplosion();
				}
				
			}
			removeThis(true);
		}
		
	}
	
	public void handleExplosion(){

		if (player.isBot()){
			ArcherBot b = (ArcherBot) player;
			if (b.passive.explosionArrowCount >= b.passive.EXPLOSION_COUNT){
				Game.getGame().getServerFunctions().sendAbilityAdd(player.getPlayerID(), AbilityInfo.ARCHEREXPLOSION, Game.getGame().getIdPool().getAvailableAbilityID(), x, y);
				b.passive.explosionArrowCount = 0;
			}
		}
		else{
			Archer p = (Archer) player;
			if (p.passive.explosionArrowCount >= p.passive.EXPLOSION_COUNT){
				Game.getGame().getServerFunctions().sendAbilityAdd(player.getPlayerID(), AbilityInfo.ARCHEREXPLOSION, Game.getGame().getIdPool().getAvailableAbilityID(), x, y);
				p.passive.explosionArrowCount = 0;
			}
		}
		
	}
	
	public void handleArrowCount(){

		if (player.isBot()){
			ArcherBot b = (ArcherBot) player;
			b.passive.explosionArrowCount++;
		}
		else{
			Archer p = (Archer) player;
			p.passive.explosionArrowCount++;
		}
		

		addedToArrowCount = true;
		
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
}
