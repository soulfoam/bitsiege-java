package soulfoam.arena.entities.challengers;

import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.AbilityBook;
import soulfoam.arena.entities.challengers.animation.ClericAnimation;
import soulfoam.arena.entities.challengers.skins.Skin;
import soulfoam.arena.entities.challengers.tooltips.ClericToolTip;
import soulfoam.arena.entities.challengers.underglows.Underglow;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.ClericInfo;

public class Cleric extends Entity {

	public Cleric(float x, float y, int skinID, int underglowID) {

		challengerType = EntityInfo.CLERICCHALLENGER;
		this.x = x;
		this.y = y;
		ctt = new ClericToolTip(10, 52);
		skin = new Skin(this, skinID);
		underglow = new Underglow(this, underglowID);
		initAbilities();
		animation = new ClericAnimation(this);
		width = 32;
		height = 32;
		hitBoxWidth = 8;
		hitBoxHeight = 16;

		currentDirection = EntityInfo.DIR_DOWN;
		baseMoveSpeed = ClericInfo.BASE_MOVE_SPEED;
		moveSpeed = baseMoveSpeed;
		baseHealth = ClericInfo.BASE_HEALTH;
		baseHealthStart = baseHealth;
		health = baseHealth;

		selectedAbility = 1;

	}

	public void initAbilities() {
		abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.CLERICBASICATTACK, 0, 0, 0, 0, 0, this);
		abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();

		ability1 = AbilityBook.getAbilityByID(AbilityInfo.CLERICHEAL, 0, 0, 0, 0, 0, this);
		ability1CDTime = ability1.getCoolDown() * 1000;

		ability2 = AbilityBook.getAbilityByID(AbilityInfo.CLERICBLIND, 0, 0, 0, 0, 0, this);
		ability2CDTime = ability2.getCoolDown() * 1000;

		ability3 = AbilityBook.getAbilityByID(AbilityInfo.CLERICSWITCH, 0, 0, 0, 0, 0, this);
		ability3CDTime = ability3.getCoolDown() * 1000;

		ability4 = AbilityBook.getAbilityByID(AbilityInfo.CLERICTEAMHEAL, 0, 0, 0, 0, 0, this);
		ability4CDTime = ability4.getCoolDown() * 1000;
	}

	public void update(int delta) {
		handleTimers(delta);
	}

	public void render(Graphics g) {
		animation.render(g);
	}

}
