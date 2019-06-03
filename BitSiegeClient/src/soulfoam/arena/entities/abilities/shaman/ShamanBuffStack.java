package soulfoam.arena.entities.abilities.shaman;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.misc.StackableAbility;
import soulfoam.arenashared.main.abilityinfo.AbilityStackInfo;

public class ShamanBuffStack extends StackableAbility {

	public float attackSpeedCDTime;

	public ShamanBuffStack(Entity stackOwner, float attackSpeedCDTime) {
		super(AbilityStackInfo.SHAMANBUFFSTACK, stackOwner);
		this.attackSpeedCDTime = attackSpeedCDTime;
	}

	public void applyStack() {
		stackOwner.setAbilityBasicAttackCDTime(stackOwner.getAbilityBasicAttackCDTime() + attackSpeedCDTime);
	}

	public void consumeStack() {
		needsRemoved = true;
		stackOwner.setAbilityBasicAttackCDTime(stackOwner.getAbilityBasicAttackCDTime() - attackSpeedCDTime);
	}
}
