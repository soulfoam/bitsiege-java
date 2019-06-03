package soulfoam.arenaserver.entities.abilities.archer;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenaserver.entities.challengers.Archer;
import soulfoam.arenaserver.entities.challengers.bots.ArcherBot;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherExplosiveArrowInfo;


public class ArcherExplodingArrowSwitch extends Ability{

	public ArcherExplodingArrowSwitch(int gameID, Entity player){
		
		this.abilityID = AbilityInfo.ARCHEREXPLODINGARROWSWITCH;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.coolDown = ArcherExplosiveArrowInfo.COOLDOWN;
		
	}
	
	public void update(int delta) {


		player.setAbilityBasicAttack(AbilityBook.getAbilityByID(AbilityInfo.ARCHEREXPLODINGARROW, 0, 0, 0, player));
		if (player.isBot()){
			ArcherBot p = (ArcherBot) player;
			p.passive.explosionArrowCount = 0;
		}
		else{
			Archer p = (Archer) player;
			p.passive.explosionArrowCount = 0;
		}
		
		
		removeThis(false);
	}


	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
