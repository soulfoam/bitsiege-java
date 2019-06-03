package soulfoam.arena.entities.challengers;

import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.AbilityBook;
import soulfoam.arena.entities.challengers.animation.ShamanAnimation;
import soulfoam.arena.entities.challengers.skins.Skin;
import soulfoam.arena.entities.challengers.tooltips.ShamanToolTip;
import soulfoam.arena.entities.challengers.underglows.Underglow;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.ShamanInfo;

public class Shaman extends Entity {

	public Shaman(float x, float y, int skinID, int underglowID) {

		challengerType = EntityInfo.SHAMANCHALLENGER;
		this.x = x;
		this.y = y;

		ctt = new ShamanToolTip(10, 52);
		skin = new Skin(this, skinID);
		underglow = new Underglow(this, underglowID);
		initAbilities();
		animation = new ShamanAnimation(this);
		width = 32;
		height = 32;
		hitBoxWidth = 8;
		hitBoxHeight = 16;

		currentDirection = EntityInfo.DIR_DOWN;
		baseMoveSpeed = ShamanInfo.BASE_MOVE_SPEED;
		moveSpeed = baseMoveSpeed;
		baseHealth = ShamanInfo.BASE_HEALTH;
		baseHealthStart = baseHealth;
		health = baseHealth;

		selectedAbility = 1;

	}

	public void initAbilities() {
		abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.SHAMANBASICATTACK, 0, 0, 0, 0, 0, this);
		abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();

		ability1 = AbilityBook.getAbilityByID(AbilityInfo.SHAMANBUFFTOTEM, 0, 0, 0, 0, 0, this);
		ability1CDTime = ability1.getCoolDown() * 1000;

		ability2 = AbilityBook.getAbilityByID(AbilityInfo.SHAMANSHIELDTOTEM, 0, 0, 0, 0, 0, this);
		ability2CDTime = ability2.getCoolDown() * 1000;

		ability3 = AbilityBook.getAbilityByID(AbilityInfo.SHAMANDEBUFFTOTEM, 0, 0, 0, 0, 0, this);
		ability3CDTime = ability3.getCoolDown() * 1000;

		ability4 = AbilityBook.getAbilityByID(AbilityInfo.SHAMANCONFUSETOTEM, 0, 0, 0, 0, 0, this);
		ability4CDTime = ability4.getCoolDown() * 1000;
	}

	public void update(int delta) {
		handleTimers(delta);
	}

	public void render(Graphics g) {
		animation.render(g);
	}

}
