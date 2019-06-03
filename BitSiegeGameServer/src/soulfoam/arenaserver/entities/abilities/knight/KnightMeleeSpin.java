package soulfoam.arenaserver.entities.abilities.knight;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightMeleeSpinInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class KnightMeleeSpin extends Ability{
	
	public KnightMeleeSpin(int gameID, Entity player){

		this.abilityID = AbilityInfo.KNIGHTMELEESPIN;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.coolDown = KnightMeleeSpinInfo.COOLDOWN;
		this.damage = KnightMeleeSpinInfo.DAMAGE;
		this.destroyTime = KnightMeleeSpinInfo.DESTROY_TIMER;
		this.destroyTimer = destroyTime;
		
		this.width = KnightMeleeSpinInfo.WIDTH;
		this.height = KnightMeleeSpinInfo.HEIGHT;
		this.hitBoxWidth = KnightMeleeSpinInfo.HITBOX_WIDTH;
		this.hitBoxHeight = KnightMeleeSpinInfo.HITBOX_HEIGHT;
		

		this.x = player.getX() + 4;
		this.y = player.getY() + 8;
		
		
	}

	public void update(int delta) {
		
		player.setCurrentAction(EntityInfo.SPINNING);
		
		handleCollisions(delta);

		x = player.getX() + 4;
		y = player.getY() + 8;
		
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}

	}
	
	public void handleCollisions(int delta){

		for (Entity p: Game.getGame().getPlayers()){
			if (getBounds().intersects(p.getBounds()) || getBounds().contains(p.getBounds()))
			{
				if (!p.isDead() && p.getTeam() != player.getTeam()){
					p.takeDamage((damage / destroyTime) * delta, player);
				}
			}
		}
		
	}
	
	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}
