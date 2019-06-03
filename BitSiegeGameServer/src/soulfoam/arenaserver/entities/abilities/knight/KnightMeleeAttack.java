package soulfoam.arenaserver.entities.abilities.knight;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightMeleeAttackInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class KnightMeleeAttack extends Ability{

	public KnightMeleeAttack(int gameID, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.KNIGHTMELEEATTACK;
		this.tag = AbilityInfo.MELEE;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = KnightMeleeAttackInfo.COOLDOWN;
		this.damage = KnightMeleeAttackInfo.DAMAGE;
		this.destroyTimer = KnightMeleeAttackInfo.DESTROY_TIMER;
		
		this.width = KnightMeleeAttackInfo.WIDTH;
		this.height = KnightMeleeAttackInfo.HEIGHT;
		this.hitBoxWidth = KnightMeleeAttackInfo.HITBOX_WIDTH;
		this.hitBoxHeight = KnightMeleeAttackInfo.HITBOX_HEIGHT;
		

		this.x = (player.getX() - 4) + (dx*8);
		this.y = player.getY() + (dy*8);
		
		
	}

	public void update(int delta) {
		
		handleCollision();	
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0){
			removeThis(false);
		}
		

		x = (player.getX() - 4) + (dx*8);
		y = player.getY() + (dy*8);
		

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
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
}
