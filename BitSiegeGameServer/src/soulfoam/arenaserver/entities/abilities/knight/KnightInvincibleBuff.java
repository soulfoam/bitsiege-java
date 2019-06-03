package soulfoam.arenaserver.entities.abilities.knight;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightInvincibleBuffInfo;


public class KnightInvincibleBuff extends Ability{
	
	public KnightInvincibleBuff(int gameID, Entity player){
		
		this.abilityID = AbilityInfo.KNIGHTINVINCIBLEBUFF;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.coolDown = KnightInvincibleBuffInfo.COOLDOWN;
		this.destroyTimer = KnightInvincibleBuffInfo.DESTROY_TIMER;
		
		this.width = KnightInvincibleBuffInfo.WIDTH;
		this.height = KnightInvincibleBuffInfo.HEIGHT;
		this.hitBoxWidth = KnightInvincibleBuffInfo.HITBOX_WIDTH;
		this.hitBoxHeight = KnightInvincibleBuffInfo.HITBOX_HEIGHT;
		

		this.x = player.getX() - 4;
		this.y = player.getY() - 7;
		
		
	}

	public void update(int delta) {
		

		player.setCanBeDamaged(false);
		player.setCanBeSlowed(false);
		player.setCanBeStunned(false);
		player.setCanBeBlinded(false);
		player.setCanBeConfused(false);
		player.setCanBeKnockedBack(false);
		player.getStacks().clear();

		x = player.getX() - 4;
		y = player.getY() - 7;
		
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}

		for (Ability a: Game.getGame().getAbilities()){

			if (a.getPlayer().getTeam() != player.getTeam() ){
				if (a.getTag() == AbilityInfo.PROJECTILE || a.getTag() == AbilityInfo.DASHSTOPONHIT){
					if (getBounds().intersects(a.getBounds()) || getBounds().contains(a.getBounds())){
						a.removeThis(true);
					}
				}
			}
			
		}

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
	public void removeThis(boolean broadcastRemove){

		player.setCanBeDamaged(true);
		player.setCanBeSlowed(true);
		player.setCanBeStunned(true);
		player.setCanBeBlinded(true);
		player.setCanBeConfused(true);
		player.setCanBeKnockedBack(true);
		
		
		Game.getGame().removeAbility(this, false);

	}

}
