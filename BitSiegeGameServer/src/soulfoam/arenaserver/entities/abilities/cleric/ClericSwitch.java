package soulfoam.arenaserver.entities.abilities.cleric;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericSwitchInfo;


public class ClericSwitch extends Ability{

	public ClericSwitch(int gameID, Entity player){
		
		this.abilityID = AbilityInfo.CLERICSWITCH;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.coolDown = ClericSwitchInfo.COOLDOWN;
	}
	
	public void update(int delta){
		

		if (player.getAbilityBasicAttack().getAbilityID() == AbilityInfo.CLERICBASICATTACKHEAL)
		{
			player.setAbilityBasicAttack(AbilityBook.getAbilityByID(AbilityInfo.CLERICBASICATTACK, 0, 0, 0, player));
		}
		else{
			player.setAbilityBasicAttack(AbilityBook.getAbilityByID(AbilityInfo.CLERICBASICATTACKHEAL, 0, 0, 0, player));
		}

		
	
	
		removeThis(false);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
