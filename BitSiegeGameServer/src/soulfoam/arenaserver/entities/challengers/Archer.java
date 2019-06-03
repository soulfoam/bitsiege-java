package soulfoam.arenaserver.entities.challengers;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenaserver.entities.abilities.archer.ArcherPassive;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.ArcherInfo;

public class Archer extends Entity{
	
	public ArcherPassive passive;

	public Archer(float x, float y, int skinID, int underglowID) {
		
		this.challengerType = EntityInfo.ARCHERCHALLENGER;
		this.x = x;
		this.y = y;
		
		this.skinID = skinID;
		this.underglowID = underglowID;
		initAbilities();
		this.passive = new ArcherPassive(this);
		this.width = 32;
		this.height = 24;
		this.hitBoxWidth = 8;
		this.hitBoxHeight = 16;
		this.baseMoveSpeed = ArcherInfo.BASE_MOVE_SPEED;
		this.moveSpeed = baseMoveSpeed;
		this.baseHealth = ArcherInfo.BASE_HEALTH;
		this.health = baseHealth;
	
	}
	
	public void initAbilities(){
		this.abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.ARCHERICEARROW, 0, 0, 0, this);
		this.abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();
		
		this.ability1 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERICEARROWSWITCH, 0, 0, 0, this);
		this.ability1CDTime = ability1.getCoolDown() * 1000;
		
		this.ability2 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERFIREARROWSWITCH, 0, 0, 0, this);
		this.ability2CDTime = ability2.getCoolDown() * 1000;
		
		this.ability3 = AbilityBook.getAbilityByID(AbilityInfo.ARCHEREXPLODINGARROWSWITCH, 0, 0, 0, this);
		this.ability3CDTime = ability3.getCoolDown() * 1000;
		
		this.ability4 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERBUFF, 0, 0, 0, this);
		this.ability4CDTime = ability4.getCoolDown() * 1000;
	}
	
	
	public void update(int delta){
		handleTimers(delta);
	}

}
