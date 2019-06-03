package soulfoam.arena.entities.challengers;

import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.AbilityBook;
import soulfoam.arena.entities.challengers.animation.WaterQueenAnimation;
import soulfoam.arena.entities.challengers.skins.Skin;
import soulfoam.arena.entities.challengers.tooltips.WaterQueenToolTip;
import soulfoam.arena.entities.challengers.underglows.Underglow;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.WaterQueenInfo;

public class WaterQueen extends Entity {

	public WaterQueen(float x, float y, int skinID, int underglowID) {

		challengerType = EntityInfo.WATERQUEENCHALLENGER;
		this.x = x;
		this.y = y;

		ctt = new WaterQueenToolTip(10, 52);
		skin = new Skin(this, skinID);
		underglow = new Underglow(this, underglowID);
		initAbilities();
		animation = new WaterQueenAnimation(this);
		width = 32;
		height = 32;
		hitBoxWidth = 8;
		hitBoxHeight = 16;

		currentDirection = EntityInfo.DIR_DOWN;
		baseMoveSpeed = WaterQueenInfo.BASE_MOVE_SPEED;
		moveSpeed = baseMoveSpeed;
		baseHealth = WaterQueenInfo.BASE_HEALTH;
		baseHealthStart = baseHealth;
		health = baseHealth;

		selectedAbility = 1;

	}

	public void initAbilities() {
		abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.WATERQUEENBASICATTACK, 0, 0, 0, 0, 0, this);
		abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();

		ability1 = AbilityBook.getAbilityByID(AbilityInfo.WATERQUEENWAVE, 0, 0, 0, 0, 0, this);
		ability1CDTime = ability1.getCoolDown() * 1000;

		ability2 = AbilityBook.getAbilityByID(AbilityInfo.WATERQUEENWATERBALL, 0, 0, 0, 0, 0, this);
		ability2CDTime = ability2.getCoolDown() * 1000;

		ability3 = AbilityBook.getAbilityByID(AbilityInfo.WATERQUEENABSORB, 0, 0, 0, 0, 0, this);
		ability3CDTime = ability3.getCoolDown() * 1000;

		ability4 = AbilityBook.getAbilityByID(AbilityInfo.WATERQUEENWATERBUFF, 0, 0, 0, 0, 0, this);
		ability4CDTime = ability4.getCoolDown() * 1000;
	}

	public void update(int delta) {
		handleTimers(delta);
	}

	public void render(Graphics g) {
		animation.render(g);
	}

}
