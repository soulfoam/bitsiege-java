package soulfoam.arena.entities.challengers.skins;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class WarlockSkin {

	private Image[] walkUp = Res.WARLOCK_RESOURCE.WALK_UP;
	private Image[] walkLeft = Res.WARLOCK_RESOURCE.WALK_LEFT;
	private Image[] walkDown = Res.WARLOCK_RESOURCE.WALK_DOWN;
	private Image[] walkRight = Res.WARLOCK_RESOURCE.WALK_RIGHT;

	private Image[] idleUp = Res.WARLOCK_RESOURCE.IDLE_UP;
	private Image[] idleLeft = Res.WARLOCK_RESOURCE.IDLE_LEFT;
	private Image[] idleDown = Res.WARLOCK_RESOURCE.IDLE_DOWN;
	private Image[] idleRight = Res.WARLOCK_RESOURCE.IDLE_RIGHT;

	private Image[] tower = Res.WARLOCK_RESOURCE.TOWER;

	private Image[] towerAttack = Res.WARLOCK_RESOURCE.TOWERATTACK;
	private Image[] autoAttack = Res.WARLOCK_RESOURCE.AUTOATTACK;

	private Image[] stormBit = Res.WARLOCK_RESOURCE.STORMBIT;
	
	private Image portrait = Res.WARLOCK_RESOURCE.PORTRAITS[0];
	
	public WarlockSkin(int skinID) {

		if (skinID == CosmeticLibrary.WARLOCK_SKIN_NORMAL) {
			walkUp = Res.WARLOCK_RESOURCE.WALK_UP;
			walkLeft = Res.WARLOCK_RESOURCE.WALK_LEFT;
			walkDown = Res.WARLOCK_RESOURCE.WALK_DOWN;
			walkRight = Res.WARLOCK_RESOURCE.WALK_RIGHT;

			idleUp = Res.WARLOCK_RESOURCE.IDLE_UP;
			idleLeft = Res.WARLOCK_RESOURCE.IDLE_LEFT;
			idleDown = Res.WARLOCK_RESOURCE.IDLE_DOWN;
			idleRight = Res.WARLOCK_RESOURCE.IDLE_RIGHT;

			tower = Res.WARLOCK_RESOURCE.TOWER;
			towerAttack = Res.WARLOCK_RESOURCE.TOWERATTACK;
			autoAttack = Res.WARLOCK_RESOURCE.AUTOATTACK;

			stormBit = Res.WARLOCK_RESOURCE.STORMBIT;
			
			portrait = Res.WARLOCK_RESOURCE.PORTRAITS[0];
			
		}

		if (skinID == CosmeticLibrary.WARLOCK_SKIN_FROST) {
			walkUp = Res.WARLOCK_RESOURCE.WALK_UP_FROST;
			walkLeft = Res.WARLOCK_RESOURCE.WALK_LEFT_FROST;
			walkDown = Res.WARLOCK_RESOURCE.WALK_DOWN_FROST;
			walkRight = Res.WARLOCK_RESOURCE.WALK_RIGHT_FROST;

			idleUp = Res.WARLOCK_RESOURCE.IDLE_UP_FROST;
			idleLeft = Res.WARLOCK_RESOURCE.IDLE_LEFT_FROST;
			idleDown = Res.WARLOCK_RESOURCE.IDLE_DOWN_FROST;
			idleRight = Res.WARLOCK_RESOURCE.IDLE_RIGHT_FROST;

			tower = Res.WARLOCK_RESOURCE.TOWER_FROST;
			towerAttack = Res.WARLOCK_RESOURCE.TOWERATTACK_FROST;
			autoAttack = Res.WARLOCK_RESOURCE.AUTOATTACK_FROST;

			stormBit = Res.WARLOCK_RESOURCE.STORMBIT_FROST;
			
			portrait = Res.WARLOCK_RESOURCE.PORTRAITS[1];
		}
	}

	public Image[] getWalkUp() {
		return walkUp;
	}

	public Image[] getWalkLeft() {
		return walkLeft;
	}

	public Image[] getWalkDown() {
		return walkDown;
	}

	public Image[] getWalkRight() {
		return walkRight;
	}

	public Image[] getIdleUp() {
		return idleUp;
	}

	public Image[] getIdleLeft() {
		return idleLeft;
	}

	public Image[] getIdleDown() {
		return idleDown;
	}

	public Image[] getIdleRight() {
		return idleRight;
	}

	public Image[] getTower() {
		return tower;
	}

	public Image[] getTowerAttack() {
		return towerAttack;
	}

	public Image[] getAutoAttack() {
		return autoAttack;
	}

	public Image[] getStormBit() {
		return stormBit;
	}

	public Image getPortrait() {
		return portrait;
	}

}
