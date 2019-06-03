package soulfoam.arenaserver.entities.challengers;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.KnightInfo;


public class Knight extends Entity{

	public Knight(float x, float y, int skinID, int underglowID) {

		this.challengerType = EntityInfo.KNIGHTCHALLENGER;
		this.x = x;
		this.y = y;
		
		this.skinID = skinID;
		this.underglowID = underglowID;
		initAbilities();
		this.width = 32;
		this.height = 24;
		this.hitBoxWidth = 8;
		this.hitBoxHeight = 16;
		this.baseMoveSpeed = KnightInfo.BASE_MOVE_SPEED;
		this.moveSpeed = baseMoveSpeed;
		this.baseHealth = KnightInfo.BASE_HEALTH;
		this.health = baseHealth;


	}
	
	public void initAbilities(){
		this.abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTMELEEATTACK, 0, 0, 0, this);
		this.abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();
		
		this.ability1 = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTSHIELDTHROW, 0, 0, 0, this);
		this.ability1CDTime = ability1.getCoolDown() * 1000;
		
		this.ability2 = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTMELEESPIN, 0, 0, 0, this);
		this.ability2CDTime = ability2.getCoolDown() * 1000;
		
		this.ability3 = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTDASH, 0, 0, 0, this);
		this.ability3CDTime = ability3.getCoolDown() * 1000;
		
		this.ability4 = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTINVINCIBLEBUFF, 0, 0, 0, this);
		this.ability4CDTime = ability4.getCoolDown() * 1000;
	}
	
	
	public void update(int delta){
		handleTimers(delta);
	}
	
}
