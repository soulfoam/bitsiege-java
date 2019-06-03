package soulfoam.arenaserver.entities.abilities.voidlord;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordMeleeAttackInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class VoidLordMeleeAttack extends Ability{
	
	public boolean setInvis;

	public VoidLordMeleeAttack(int gameID, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.VOIDLORDMELEEATTACK;
		this.tag = AbilityInfo.MELEE;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = VoidLordMeleeAttackInfo.COOLDOWN;
		this.damage = VoidLordMeleeAttackInfo.DAMAGE;
		this.destroyTimer = VoidLordMeleeAttackInfo.DESTROY_TIMER;
		
		this.width = VoidLordMeleeAttackInfo.WIDTH;
		this.height = VoidLordMeleeAttackInfo.HEIGHT;
		this.hitBoxWidth = VoidLordMeleeAttackInfo.HITBOX_WIDTH;
		this.hitBoxHeight = VoidLordMeleeAttackInfo.HITBOX_HEIGHT;


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
		
		
		if (!setInvis){
			player.setInvisible(false);
			setInvis = true;
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
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
}
