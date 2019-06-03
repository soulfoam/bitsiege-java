package soulfoam.arenaserver.entities.abilities.cleric;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericBasicAttackHealInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class ClericBasicAttackHeal extends Ability{

	public ClericBasicAttackHeal(int gameID, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.CLERICBASICATTACKHEAL;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = ClericBasicAttackHealInfo.COOLDOWN;
		this.damage = ClericBasicAttackHealInfo.DAMAGE;
		this.moveSpeed = ClericBasicAttackHealInfo.MOVE_SPEED;
		this.destroyTimer = ClericBasicAttackHealInfo.DESTROY_TIMER;
		
		this.width = ClericBasicAttackHealInfo.WIDTH;
		this.height = ClericBasicAttackHealInfo.HEIGHT;
		this.hitBoxWidth = ClericBasicAttackHealInfo.HITBOX_WIDTH;
		this.hitBoxHeight = ClericBasicAttackHealInfo.HITBOX_HEIGHT;
		

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
			if (getBounds().intersects(p.getBounds()) || getBounds().contains(p.getBounds())){
				if (!collision.getPlayersHit().contains(p) && !p.isDead() && p.getTeam() == player.getTeam() && p.getPlayerID() != player.getPlayerID()){
					collision.getPlayersHit().add(p);
				}
			}
			
		}
		
		if (collision.getPlayersHit().size() > 0){
			for (Entity p: collision.getPlayersHit()){
				if (!collision.getPlayersCycled().contains(p)){
					p.takeHeal(damage, player);
					collision.getPlayersCycled().add(p);
				}
			}
		}

	}
	

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
