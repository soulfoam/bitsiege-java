package soulfoam.arena.entities.challengers.skins;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class VoidLordSkin {

	private Image[] walkUp = Res.VOIDLORD_RESOURCE.WALK_UP;
	private Image[] walkLeft = Res.VOIDLORD_RESOURCE.WALK_LEFT;
	private Image[] walkDown = Res.VOIDLORD_RESOURCE.WALK_DOWN;
	private Image[] walkRight = Res.VOIDLORD_RESOURCE.WALK_RIGHT;
	private Image[] idleUp = Res.VOIDLORD_RESOURCE.IDLE_UP;
	private Image[] idleLeft = Res.VOIDLORD_RESOURCE.IDLE_LEFT;
	private Image[] idleDown = Res.VOIDLORD_RESOURCE.IDLE_DOWN;
	private Image[] idleRight = Res.VOIDLORD_RESOURCE.IDLE_RIGHT;

	private Image[] pull = Res.VOIDLORD_RESOURCE.PULL;

	private Image[] melee = Res.GENERAL_RESOURCE.MELEE_PURPLE;

	private Image[] aoe = Res.VOIDLORD_RESOURCE.AOE;
	private Image[] ult = Res.VOIDLORD_RESOURCE.ULT;

	private Image portrait = Res.VOIDLORD_RESOURCE.PORTRAITS[0];
	
	public VoidLordSkin(int skinID) {

		if (skinID == CosmeticLibrary.VOIDLORD_SKIN_NORMAL) {
			walkUp = Res.VOIDLORD_RESOURCE.WALK_UP;
			walkLeft = Res.VOIDLORD_RESOURCE.WALK_LEFT;
			walkDown = Res.VOIDLORD_RESOURCE.WALK_DOWN;
			walkRight = Res.VOIDLORD_RESOURCE.WALK_RIGHT;
			idleUp = Res.VOIDLORD_RESOURCE.IDLE_UP;
			idleLeft = Res.VOIDLORD_RESOURCE.IDLE_LEFT;
			idleDown = Res.VOIDLORD_RESOURCE.IDLE_DOWN;
			idleRight = Res.VOIDLORD_RESOURCE.IDLE_RIGHT;

			pull = Res.VOIDLORD_RESOURCE.PULL;

			aoe = Res.VOIDLORD_RESOURCE.AOE;
			ult = Res.VOIDLORD_RESOURCE.ULT;

			melee = Res.GENERAL_RESOURCE.MELEE_PURPLE;
			
			portrait = Res.VOIDLORD_RESOURCE.PORTRAITS[0];

		}
		if (skinID == CosmeticLibrary.VOIDLORD_SKIN_BLOOD) {
			walkUp = Res.VOIDLORD_RESOURCE.WALK_UP_BLOOD;
			walkLeft = Res.VOIDLORD_RESOURCE.WALK_LEFT_BLOOD;
			walkDown = Res.VOIDLORD_RESOURCE.WALK_DOWN_BLOOD;
			walkRight = Res.VOIDLORD_RESOURCE.WALK_RIGHT_BLOOD;
			idleUp = Res.VOIDLORD_RESOURCE.IDLE_UP_BLOOD;
			idleLeft = Res.VOIDLORD_RESOURCE.IDLE_LEFT_BLOOD;
			idleDown = Res.VOIDLORD_RESOURCE.IDLE_DOWN_BLOOD;
			idleRight = Res.VOIDLORD_RESOURCE.IDLE_RIGHT_BLOOD;

			pull = Res.VOIDLORD_RESOURCE.PULL_BLOOD;

			aoe = Res.VOIDLORD_RESOURCE.AOE_BLOOD;
			ult = Res.VOIDLORD_RESOURCE.ULT_BLOOD;

			melee = Res.GENERAL_RESOURCE.MELEE_RED;
			
			portrait = Res.VOIDLORD_RESOURCE.PORTRAITS[1];
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

	public Image[] getPull() {
		return pull;
	}

	public Image[] getMelee() {
		return melee;
	}

	public Image[] getAoe() {
		return aoe;
	}

	public Image[] getUlt() {
		return ult;
	}

	public Image getPortrait() {
		return portrait;
	}


}
