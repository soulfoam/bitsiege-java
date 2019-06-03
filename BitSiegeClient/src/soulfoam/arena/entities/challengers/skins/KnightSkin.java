package soulfoam.arena.entities.challengers.skins;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class KnightSkin {

	private Image[] walkUp = Res.KNIGHT_RESOURCE.WALK_UP;
	private Image[] walkLeft = Res.KNIGHT_RESOURCE.WALK_LEFT;
	private Image[] walkDown = Res.KNIGHT_RESOURCE.WALK_DOWN;
	private Image[] walkRight = Res.KNIGHT_RESOURCE.WALK_RIGHT;
	private Image[] idleUp = Res.KNIGHT_RESOURCE.IDLE_UP;
	private Image[] idleLeft = Res.KNIGHT_RESOURCE.IDLE_LEFT;
	private Image[] idleDown = Res.KNIGHT_RESOURCE.IDLE_DOWN;
	private Image[] idleRight = Res.KNIGHT_RESOURCE.IDLE_RIGHT;

	private Image[] melee = Res.GENERAL_RESOURCE.MELEE_WHITE;

	private Image[] shieldThrow = Res.KNIGHT_RESOURCE.SHIELD_THROW;
	private Image[] spin = Res.KNIGHT_RESOURCE.SPIN;
	
	private Image portrait = Res.KNIGHT_RESOURCE.PORTRAITS[0];
	
	public KnightSkin(int skinID) {
		if (skinID == CosmeticLibrary.KNIGHT_SKIN_NORMAL) {
			walkUp = Res.KNIGHT_RESOURCE.WALK_UP;
			walkLeft = Res.KNIGHT_RESOURCE.WALK_LEFT;
			walkDown = Res.KNIGHT_RESOURCE.WALK_DOWN;
			walkRight = Res.KNIGHT_RESOURCE.WALK_RIGHT;

			idleUp = Res.KNIGHT_RESOURCE.IDLE_UP;
			idleLeft = Res.KNIGHT_RESOURCE.IDLE_LEFT;
			idleDown = Res.KNIGHT_RESOURCE.IDLE_DOWN;
			idleRight = Res.KNIGHT_RESOURCE.IDLE_RIGHT;

			melee = Res.GENERAL_RESOURCE.MELEE_WHITE;

			shieldThrow = Res.KNIGHT_RESOURCE.SHIELD_THROW;
			spin = Res.KNIGHT_RESOURCE.SPIN;
			
			portrait = Res.KNIGHT_RESOURCE.PORTRAITS[0];
			
		}

		if (skinID == CosmeticLibrary.KNIGHT_SKIN_BLOOD) {
			walkUp = Res.KNIGHT_RESOURCE.WALK_UP_BLOOD;
			walkLeft = Res.KNIGHT_RESOURCE.WALK_LEFT_BLOOD;
			walkDown = Res.KNIGHT_RESOURCE.WALK_DOWN_BLOOD;
			walkRight = Res.KNIGHT_RESOURCE.WALK_RIGHT_BLOOD;

			idleUp = Res.KNIGHT_RESOURCE.IDLE_UP_BLOOD;
			idleLeft = Res.KNIGHT_RESOURCE.IDLE_LEFT_BLOOD;
			idleDown = Res.KNIGHT_RESOURCE.IDLE_DOWN_BLOOD;
			idleRight = Res.KNIGHT_RESOURCE.IDLE_RIGHT_BLOOD;

			melee = Res.GENERAL_RESOURCE.MELEE_RED;

			shieldThrow = Res.KNIGHT_RESOURCE.SHIELD_THROW_BLOOD;
			spin = Res.KNIGHT_RESOURCE.SPIN_BLOOD;
			
			portrait = Res.KNIGHT_RESOURCE.PORTRAITS[1];
			
		}
	}

	public Image getPortrait() {
		return portrait;
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

	public Image[] getMelee() {
		return melee;
	}

	public Image[] getShieldThrow() {
		return shieldThrow;
	}

	public Image[] getSpin() {
		return spin;
	}

}
