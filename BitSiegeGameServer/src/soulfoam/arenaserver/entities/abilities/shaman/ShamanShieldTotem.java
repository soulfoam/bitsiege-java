package soulfoam.arenaserver.entities.abilities.shaman;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanShieldTotemInfo;


public class ShamanShieldTotem extends Ability{
	
	public float shieldTime = ShamanShieldTotemInfo.SHIELD_TIMER;
	
	public ShamanShieldTotem(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.SHAMANSHIELDTOTEM;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;
		this.x = x + 4;
		this.y = y + 11;

		this.player = player;
		
		
		this.coolDown = ShamanShieldTotemInfo.COOLDOWN;
		this.destroyTimer = ShamanShieldTotemInfo.DESTROY_TIMER;
		
		this.width = ShamanShieldTotemInfo.WIDTH;
		this.height = ShamanShieldTotemInfo.HEIGHT;
		this.hitBoxWidth = ShamanShieldTotemInfo.HITBOX_WIDTH;
		this.hitBoxHeight = ShamanShieldTotemInfo.HITBOX_HEIGHT;
	}
	
	public void update(int delta) {
		
		handleCollision(delta);
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}

	}

	public void handleCollision(int delta){

		for (Entity p: Game.getGame().getPlayers()){
			if (getBounds().intersects(p.getBoundsFeet()) || getBounds().contains(p.getBoundsFeet()))
			{
				if (!collision.getPlayersHit().contains(p) && !p.isDead() && p.getTeam() == player.getTeam()){
					p.takeShield(shieldTime, Res.percentOf(p.getBaseHealth(), ShamanShieldTotemInfo.SHIELD_AMOUNT), player);
					collision.getPlayersHit().add(p);
				}
			}
		}
		
			
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}


}
