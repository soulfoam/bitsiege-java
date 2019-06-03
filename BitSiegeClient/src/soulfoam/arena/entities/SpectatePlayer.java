package soulfoam.arena.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.abilities.AbilityBook;
import soulfoam.arena.entities.challengers.animation.ArcherAnimation;
import soulfoam.arena.entities.challengers.skins.Skin;
import soulfoam.arena.entities.challengers.underglows.Underglow;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class SpectatePlayer extends Entity {

	public SpectatePlayer(float x, float y, int skinID, int underglowID) {

		challengerType = EntityInfo.SPECTATE_PLAYER;
		skin = new Skin(this, CosmeticLibrary.ARCHER_SKIN_NORMAL);
		underglow = new Underglow(this, CosmeticLibrary.UNDERGLOW_NONE);
		animation = new ArcherAnimation(this);
		kills = 0;
		deaths = 0;
		this.x = x;
		this.y = y;

		width = 8;
		height = 16;
		baseMoveSpeed = 1.15f;
		moveSpeed = baseMoveSpeed;

		currentDirection = EntityInfo.DIR_DOWN;
		health = 300;
		baseHealth = 300;
		baseHealthStart = baseHealth;

		selectedAbility = 1;

		canBeBlinded = false;
		canBeConfused = false;
		canBeKnockedBack = false;
		canBeSlowed = false;
		canBeStunned = false;
		canBeDamaged = false;

	}

	public void initAbilities() {
		abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.ARCHERICEARROW, 0, 0, 0, 0, 0, this);
		abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown() * 1000;

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

	public void handleInput(GameContainer gc, int delta) {
		// if (isLocalPlayer){
		// if (GameHolder.getGame().input.leftPressed) {
		// currentDirection = EntityInfo.DIR_LEFT;
		// move(-moveSpeed, 0, delta);
		// setCurrentAction(EntityInfo.WALKING);
		// }
		//
		//
		// if (GameHolder.getGame().input.rightPressed) {
		// currentDirection = EntityInfo.DIR_RIGHT;
		// move(moveSpeed, 0, delta);
		// setCurrentAction(EntityInfo.WALKING);
		// }
		//
		// if (GameHolder.getGame().input.upPressed) {
		// currentDirection = EntityInfo.DIR_UP;
		// move(0, -moveSpeed, delta);
		// setCurrentAction(EntityInfo.WALKING);
		// }
		//
		// if (GameHolder.getGame().input.downPressed) {
		// currentDirection = EntityInfo.DIR_DOWN;
		// move(0, moveSpeed, delta);
		// setCurrentAction(EntityInfo.WALKING);
		// }
		// }
	}

	public void castAbility(int selectedAbility) {

	}

	public Rectangle getBounds() {
		return new Rectangle(0, 0, 0, 0);
	}

	public void render(Graphics g) {

	}

	public void takeDamage(float damage, Entity player) {
	}

	public void takeHeal(double amount, Entity player) {
	}

	public void takeKnockback(int hitDirection, int knockBackAmount, double knockBackSpeed, Entity attacker) {

	}

	public void takeStun(double stunDuration, Entity attacker) {
	}

	public void takeBlind(double blindDuration, Entity attacker) {
	}

	public void takeSlow(double slowDuration, double slowAmount, Entity attacker) {
	}

}
