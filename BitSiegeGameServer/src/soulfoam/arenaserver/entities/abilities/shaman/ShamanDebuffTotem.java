package soulfoam.arenaserver.entities.abilities.shaman;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanDebuffTotemInfo;


public class ShamanDebuffTotem extends Ability{

	public ShamanDebuffTotem(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.SHAMANDEBUFFTOTEM;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;
		this.x = x + 4;
		this.y = y + 9;

		this.player = player;
		
		
		this.coolDown = ShamanDebuffTotemInfo.COOLDOWN;
		this.destroyTimer = ShamanDebuffTotemInfo.DESTROY_TIMER;
		this.slowDuration = ShamanDebuffTotemInfo.SLOW_DURATION;
		this.stunDuration = ShamanDebuffTotemInfo.STUN_DURATION;
		
		this.width = ShamanDebuffTotemInfo.WIDTH;
		this.height = ShamanDebuffTotemInfo.HEIGHT;
		this.hitBoxWidth = ShamanDebuffTotemInfo.HITBOX_WIDTH;
		this.hitBoxHeight = ShamanDebuffTotemInfo.HITBOX_HEIGHT;
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
					p.takeSlow(slowDuration, Res.percentOf(p.getMoveSpeed(), ShamanDebuffTotemInfo.SLOW_AMOUNT), player);
					if (getBounds().intersects(player.getBoundsFeet()) || getBounds().contains(player.getBoundsFeet())){
						if (!collision.getPlayersHit().contains(p)){
							p.takeStun(stunDuration, player);
							collision.getPlayersHit().add(p);
						}
					}
				}
			}
		}

		
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}