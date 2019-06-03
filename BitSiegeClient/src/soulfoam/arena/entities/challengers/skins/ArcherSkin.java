package soulfoam.arena.entities.challengers.skins;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class ArcherSkin {

	private Image[] walkUp = Res.ARCHER_RESOURCE.WALK_UP;
	private Image[] walkLeft = Res.ARCHER_RESOURCE.WALK_LEFT;
	private Image[] walkDown = Res.ARCHER_RESOURCE.WALK_DOWN;
	private Image[] walkRight = Res.ARCHER_RESOURCE.WALK_RIGHT;
	private Image[] idleUp = Res.ARCHER_RESOURCE.IDLE_UP;
	private Image[] idleLeft = Res.ARCHER_RESOURCE.IDLE_LEFT;
	private Image[] idleDown = Res.ARCHER_RESOURCE.IDLE_DOWN;
	private Image[] idleRight = Res.ARCHER_RESOURCE.IDLE_RIGHT;

	private Image portrait = Res.ARCHER_RESOURCE.PORTRAITS[0];
	
	public ArcherSkin(int skinID) {
		if (skinID == CosmeticLibrary.ARCHER_SKIN_NORMAL) {
			walkUp = Res.ARCHER_RESOURCE.WALK_UP;
			walkLeft = Res.ARCHER_RESOURCE.WALK_LEFT;
			walkDown = Res.ARCHER_RESOURCE.WALK_DOWN;
			walkRight = Res.ARCHER_RESOURCE.WALK_RIGHT;

			idleUp = Res.ARCHER_RESOURCE.IDLE_UP;
			idleLeft = Res.ARCHER_RESOURCE.IDLE_LEFT;
			idleDown = Res.ARCHER_RESOURCE.IDLE_DOWN;
			idleRight = Res.ARCHER_RESOURCE.IDLE_RIGHT;
			
			portrait = Res.ARCHER_RESOURCE.PORTRAITS[0];
		}

		if (skinID == CosmeticLibrary.ARCHER_SKIN_EXPLOSIVE) {
			walkUp = Res.ARCHER_RESOURCE.WALK_UP_EXPLOSIVE;
			walkLeft = Res.ARCHER_RESOURCE.WALK_LEFT_EXPLOSIVE;
			walkDown = Res.ARCHER_RESOURCE.WALK_DOWN_EXPLOSIVE;
			walkRight = Res.ARCHER_RESOURCE.WALK_RIGHT_EXPLOSIVE;

			idleUp = Res.ARCHER_RESOURCE.IDLE_UP_EXPLOSIVE;
			idleLeft = Res.ARCHER_RESOURCE.IDLE_LEFT_EXPLOSIVE;
			idleDown = Res.ARCHER_RESOURCE.IDLE_DOWN_EXPLOSIVE;
			idleRight = Res.ARCHER_RESOURCE.IDLE_RIGHT_EXPLOSIVE;
			
			portrait = Res.ARCHER_RESOURCE.PORTRAITS[1];
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

	public Image getPortrait() {
		return portrait;
	}


}
