package soulfoam.arenaserver.entities.abilities.warlock;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockLifeDrainInfo;


public class WarlockLifeDrain extends Ability{

	public WarlockLifeDrain(int gameID, float x, float y, Entity player){

		this.abilityID = AbilityInfo.WARLOCKLIFEDRAIN;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;
		
		this.player = player;
		
		
		this.x = x + 4;
		this.y = y + 4;
		
		this.coolDown = WarlockLifeDrainInfo.COOLDOWN;
		this.damage = WarlockLifeDrainInfo.DAMAGE;
		this.destroyTime = WarlockLifeDrainInfo.DESTROY_TIMER;
		this.destroyTimer = destroyTime;
		this.slowAmount = 85;
		this.slowDuration = WarlockLifeDrainInfo.SLOW_DURATION;
		
		this.width = WarlockLifeDrainInfo.WIDTH;
		this.height = WarlockLifeDrainInfo.HEIGHT;
		this.hitBoxWidth = WarlockLifeDrainInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WarlockLifeDrainInfo.HITBOX_HEIGHT;
	

	}
	
	public void update(int delta) {

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0){
			removeThis(false);
		}
		
		handleCollision(delta);

	}
	
	public void handleCollision(int delta){
		for (Entity p: Game.getGame().getPlayers()){
			if (getBounds().contains(p.getBoundsFeet()) || getBounds().intersects(p.getBoundsFeet())){
		    	if (!p.isDead() && p.getTeam() != player.getTeam()){
					p.takeDamage((damage / destroyTime) * delta, player);
					p.takeSlow(slowDuration, Res.percentOf(p.getMoveSpeed(), WarlockLifeDrainInfo.SLOW_AMOUNT), player);
					if (p.canBeDamaged()){
						player.takeHeal((damage / destroyTime) * delta, player);
					}
		    	}
			}
		}
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}
