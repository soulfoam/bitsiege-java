package soulfoam.arenaserver.entities.challengers;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenaserver.entities.abilities.illusionist.IllusionistPassive;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.IllusionistInfo;

public class Illusionist extends Entity{
	
	public IllusionistPassive passive;
	
	public Illusionist(float x, float y, int skinID, int underglowID) {
		
		this.challengerType = EntityInfo.ILLUSIONISTCHALLENGER;
		this.x = x;
		this.y = y;
		
		this.skinID = skinID;
		this.underglowID = underglowID;
		initAbilities();
		this.passive = new IllusionistPassive(this);
		this.width = 32;
		this.height = 24;
		this.hitBoxWidth = 8;
		this.hitBoxHeight = 16;
		this.baseMoveSpeed = IllusionistInfo.BASE_MOVE_SPEED;
		this.moveSpeed = baseMoveSpeed;
		this.baseHealth = IllusionistInfo.BASE_HEALTH;
		this.health = baseHealth;

	}
	
	public void initAbilities(){
		this.abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.ILLUSIONISTBASICATTACK, 0, 0, 0, this);
		this.abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();
		
		this.ability1 = AbilityBook.getAbilityByID(AbilityInfo.ILLUSIONISTSPAWNCLONE, 0, 0, 0, this);
		this.ability1CDTime = ability1.getCoolDown() * 1000;
		
		this.ability2 = AbilityBook.getAbilityByID(AbilityInfo.ILLUSIONISTCLONEATTACK, 0, 0, 0, this);
		this.ability2CDTime = ability2.getCoolDown() * 1000;
		
		this.ability3 = AbilityBook.getAbilityByID(AbilityInfo.ILLUSIONISTSWAPPOSITION, 0, 0, 0, this);
		this.ability3CDTime = ability3.getCoolDown() * 1000;
		
		this.ability4 = AbilityBook.getAbilityByID(AbilityInfo.ILLUSIONISTREMOTECLONESPAWN, 0, 0, 0, this);
		this.ability4CDTime = ability4.getCoolDown() * 1000;
	}
	

	public void update(int delta){
		handleTimers(delta);
		passive.update(delta);
	}
	
}
