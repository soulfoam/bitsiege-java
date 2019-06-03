package soulfoam.arenaserver.entities.abilities.archer;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.misc.StackableAbility;
import soulfoam.arenashared.main.abilityinfo.AbilityStackInfo;

public class ArcherIceArrowStack extends StackableAbility {
		
	public ArcherIceArrowStack(Entity stackOwner){
		super(AbilityStackInfo.ARCHERICEARROWSTACK, stackOwner, stackOwner, 10);
	}
	
}
