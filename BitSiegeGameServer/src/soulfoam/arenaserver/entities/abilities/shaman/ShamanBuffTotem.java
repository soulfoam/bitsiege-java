package soulfoam.arenaserver.entities.abilities.shaman;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.AbilityStackInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanBuffTotemInfo;


public class ShamanBuffTotem extends Ability{
	
	public float damageModifier;

	public ShamanBuffTotem(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.SHAMANBUFFTOTEM;
		this.tag = AbilityInfo.AOE;
		
		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;
		

		this.coolDown = ShamanBuffTotemInfo.COOLDOWN;
		this.destroyTimer = ShamanBuffTotemInfo.DESTROY_TIMER;
		
		this.width = ShamanBuffTotemInfo.WIDTH;
		this.height = ShamanBuffTotemInfo.HEIGHT;
		this.hitBoxWidth = ShamanBuffTotemInfo.HITBOX_WIDTH;
		this.hitBoxHeight = ShamanBuffTotemInfo.HITBOX_HEIGHT;
		

		this.damageModifier = Res.percentOf(player.getBaseHealth(), ShamanBuffTotemInfo.DAMAGE_BUFF_AMOUNT);
		

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
			if (getBounds().contains(p.getBoundsFeet())){
		    	if (!p.isDead() && p.getTeam() == player.getTeam()){
					if (!collision.getPlayersHit().contains(p) && !collision.getPlayersCycled().contains(p)){
						collision.getPlayersHit().add(p);
					}
		    	}
	  		}
		}

		if (!collision.getPlayersHit().isEmpty()){
			for (Entity p : collision.getPlayersHit()){
				if (p.getStack(AbilityStackInfo.SHAMANBUFFSTACK) == null){
					if (p.getPlayerID() == player.getPlayerID()){
						ShamanBuffStack s = new ShamanBuffStack(p, player, Res.percentOf(p.getAbilityBasicAttackCDTime(), ShamanBuffTotemInfo.ATTACK_SPEED_BUFF_AMOUNT) * 2, damageModifier * 2);
						p.takeStack(s);
					}
					else{
						ShamanBuffStack s = new ShamanBuffStack(p, player, Res.percentOf(p.getAbilityBasicAttackCDTime(), ShamanBuffTotemInfo.ATTACK_SPEED_BUFF_AMOUNT), damageModifier);
						p.takeStack(s);
					}
					if (p != player){
						p.getAllyAssists().add(player);
					}
					p.setAssistAllyRemoveTimer(p.getAssistRemoveTime());
					collision.getPlayersCycled().add(p);
				}
			}
		}
		
		if (!collision.getPlayersCycled().isEmpty()){
			for (Entity p : collision.getPlayersCycled()){
				if (!getBounds().contains(p.getBoundsFeet())){
					p.consumeStack(AbilityStackInfo.SHAMANBUFFSTACK);
				}
			}
		}
		
		collision.getPlayersHit().clear();
		collision.getPlayersCycled().clear();
		
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}
	
	public void removeThis(boolean broadcastRemove){
	
		if (!collision.getPlayersCycled().isEmpty()){
			for (Entity p : collision.getPlayersCycled()){
				p.consumeStack(AbilityStackInfo.SHAMANBUFFSTACK);
			}
		}
		
		Game.getGame().removeAbility(this, false);
	}
}
