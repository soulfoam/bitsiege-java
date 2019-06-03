package soulfoam.arenaserver.entities.abilities.shaman;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanConfuseTotemInfo;


public class ShamanConfuseTotem extends Ability{

	public ShamanConfuseTotem(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.SHAMANCONFUSETOTEM;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;
		this.x = x + 4;
		this.y = y + 9;

		this.player = player;
		
		
		this.coolDown = ShamanConfuseTotemInfo.COOLDOWN;
		this.destroyTimer = ShamanConfuseTotemInfo.DESTROY_TIMER;
		
		this.width = ShamanConfuseTotemInfo.WIDTH;
		this.height = ShamanConfuseTotemInfo.HEIGHT;
		this.hitBoxWidth = ShamanConfuseTotemInfo.HITBOX_WIDTH;
		this.hitBoxHeight = ShamanConfuseTotemInfo.HITBOX_HEIGHT;

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
				if (!p.isDead() && p.getTeam() != player.getTeam()){
					if (!collision.getPlayersHit().contains(p) && p.canBeConfused()){
						p.takeConfuse(ShamanConfuseTotemInfo.CONFUSE_TIME, player);
						collision.getPlayersHit().add(p);
					}
				}
			}
		}

		
	}
	
	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}

