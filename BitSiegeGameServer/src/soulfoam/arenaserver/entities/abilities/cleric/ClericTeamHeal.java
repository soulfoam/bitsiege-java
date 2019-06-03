package soulfoam.arenaserver.entities.abilities.cleric;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericTeamHealInfo;


public class ClericTeamHeal extends Ability{
	
	public ClericTeamHeal(int gameID, Entity player){
		
		this.abilityID = AbilityInfo.CLERICTEAMHEAL;
		
		this.gameID = gameID;

		this.player = player;
		

		this.coolDown = ClericTeamHealInfo.COOLDOWN;
		this.damage = ClericTeamHealInfo.DAMAGE;
		

	}
	
	public void update(int delta) {
		for (Entity p: Game.getGame().getPlayers()){

			if (!collision.getPlayersHit().contains(p) && !p.isDead() && p.getTeam() == player.getTeam()){
				p.takeHeal(damage, player);
				collision.getPlayersHit().add(p);
			}
			
		}
			
		
		removeThis(false);

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
