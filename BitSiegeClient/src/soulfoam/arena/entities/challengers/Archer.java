package soulfoam.arena.entities.challengers;

import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.AbilityBook;
import soulfoam.arena.entities.abilities.archer.ArcherPassive;
import soulfoam.arena.entities.challengers.animation.ArcherAnimation;
import soulfoam.arena.entities.challengers.skins.Skin;
import soulfoam.arena.entities.challengers.tooltips.ArcherToolTip;
import soulfoam.arena.entities.challengers.underglows.Underglow;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.ArcherInfo;

public class Archer extends Entity {

	public ArcherPassive passive;

	public Archer(float x, float y, int skinID, int underglowID) {

		challengerType = EntityInfo.ARCHERCHALLENGER;
		this.x = x;
		this.y = y;
		ctt = new ArcherToolTip(10, 52);
		skin = new Skin(this, skinID);
		underglow = new Underglow(this, underglowID);
		initAbilities();
		animation = new ArcherAnimation(this);
		passive = new ArcherPassive(this);
		width = 32;
		height = 32;
		hitBoxWidth = 8;
		hitBoxHeight = 16;

		currentDirection = EntityInfo.DIR_DOWN;
		baseMoveSpeed = ArcherInfo.BASE_MOVE_SPEED;
		moveSpeed = baseMoveSpeed;
		baseHealth = ArcherInfo.BASE_HEALTH;
		baseHealthStart = baseHealth;
		health = baseHealth;

		selectedAbility = 1;

	}

	public void initAbilities() {
		abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.ARCHERICEARROW, 0, 0, 0, 0, 0, this);
		abilityBasicAttackCDTime = ArcherInfo.BASE_ATTACK_SPEED;
		
		ability1 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERICEARROWSWITCH, 0, 0, 0, 0, 0, this);
		ability1CDTime = ability1.getCoolDown() * 1000;

		ability2 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERFIREARROWSWITCH, 0, 0, 0, 0, 0, this);
		ability2CDTime = ability2.getCoolDown() * 1000;

		ability3 = AbilityBook.getAbilityByID(AbilityInfo.ARCHEREXPLODINGARROWSWITCH, 0, 0, 0, 0, 0, this);
		ability3CDTime = ability3.getCoolDown() * 1000;

		ability4 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERBUFF, 0, 0, 0, 0, 0, this);
		ability4CDTime = ability4.getCoolDown() * 1000;
	}

	public void update(int delta) {
		handleTimers(delta);
	}

	public void render(Graphics g) {
		animation.render(g);
	}

	public float getRenderX() {
		return Res.roundForRendering(x - 8);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 4);
	}
	
	public float getAttackRenderBodyX() {
		return Res.roundForRendering(x - 8);
	}

	public float getAttackRenderBodyY() {
		return Res.roundForRendering(y - 4);
	}
	
	public float getAttackRenderLegsX() {
		return Res.roundForRendering(x - 8);
	}

	public float getAttackRenderLegsY() {
		return Res.roundForRendering(y - 4);
	}
	
}
