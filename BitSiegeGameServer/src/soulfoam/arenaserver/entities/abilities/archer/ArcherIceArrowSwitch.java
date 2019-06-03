package soulfoam.arenaserver.entities.abilities.archer;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherFireArrowInfo;


public class ArcherIceArrowSwitch extends Ability{
	
	public ArcherIceArrowSwitch(int gameID, Entity player){
		
		this.abilityID = AbilityInfo.ARCHERICEARROWSWITCH;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.coolDown = ArcherFireArrowInfo.COOLDOWN;

	}

	public void update(int delta) {


		player.setAbilityBasicAttack(AbilityBook.getAbilityByID(AbilityInfo.ARCHERICEARROW, 0, 0, 0, player));
		
		
		removeThis(false);
	}


	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
}
