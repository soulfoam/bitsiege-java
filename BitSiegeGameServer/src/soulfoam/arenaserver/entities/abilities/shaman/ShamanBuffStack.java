package soulfoam.arenaserver.entities.abilities.shaman;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.misc.StackableAbility;
import soulfoam.arenashared.main.abilityinfo.AbilityStackInfo;

public class ShamanBuffStack extends StackableAbility{
	
	public float attackSpeedCDTime;
	public float damageBuffAmount;
	
	public ShamanBuffStack(Entity stackOwner, Entity stackGiver, float attackSpeedCDTime, float damageBuffAmount){
		super(AbilityStackInfo.SHAMANBUFFSTACK, stackOwner, stackGiver);
		this.attackSpeedCDTime = attackSpeedCDTime;
		this.damageBuffAmount = damageBuffAmount;
		this.removeOnConsume = true;
	}
	
	public void applyStack(){
		if (stackOwner != null){
			stackOwner.setAbilityBasicAttackCDTime(stackOwner.getAbilityBasicAttackCDTime() + attackSpeedCDTime);
			stackOwner.setPowerBuffTempAmount(stackOwner.getPowerBuffTempAmount() + damageBuffAmount);
		}
	}
	
	public void consumeStack(){
		if (stackOwner != null){
			stackOwner.setAbilityBasicAttackCDTime(stackOwner.getAbilityBasicAttackCDTime() - attackSpeedCDTime);
			stackOwner.setPowerBuffTempAmount(stackOwner.getPowerBuffTempAmount() - damageBuffAmount);
		}
	}

}
