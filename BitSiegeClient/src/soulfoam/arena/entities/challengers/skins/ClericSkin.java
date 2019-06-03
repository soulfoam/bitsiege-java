package soulfoam.arena.entities.challengers.skins;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class ClericSkin {

	private Image[] walkUp = Res.CLERIC_RESOURCE.WALK_UP;
	private Image[] walkLeft = Res.CLERIC_RESOURCE.WALK_LEFT;
	private Image[] walkDown = Res.CLERIC_RESOURCE.WALK_DOWN;
	private Image[] walkRight = Res.CLERIC_RESOURCE.WALK_RIGHT;

	private Image[] idleUp = Res.CLERIC_RESOURCE.IDLE_UP;
	private Image[] idleLeft = Res.CLERIC_RESOURCE.IDLE_LEFT;
	private Image[] idleDown = Res.CLERIC_RESOURCE.IDLE_DOWN;
	private Image[] idleRight = Res.CLERIC_RESOURCE.IDLE_RIGHT;

	private Image[] blind = Res.CLERIC_RESOURCE.BLIND;
	
	private Image portrait = Res.CLERIC_RESOURCE.PORTRAITS[0];
	
	public ClericSkin(int skinID) {

		if (skinID == CosmeticLibrary.CLERIC_SKIN_NORMAL) {
			walkUp = Res.CLERIC_RESOURCE.WALK_UP;
			walkLeft = Res.CLERIC_RESOURCE.WALK_LEFT;
			walkDown = Res.CLERIC_RESOURCE.WALK_DOWN;
			walkRight = Res.CLERIC_RESOURCE.WALK_RIGHT;

			idleUp = Res.CLERIC_RESOURCE.IDLE_UP;
			idleLeft = Res.CLERIC_RESOURCE.IDLE_LEFT;
			idleDown = Res.CLERIC_RESOURCE.IDLE_DOWN;
			idleRight = Res.CLERIC_RESOURCE.IDLE_RIGHT;

			blind = Res.CLERIC_RESOURCE.BLIND;

			portrait = Res.CLERIC_RESOURCE.PORTRAITS[0];
		}

		if (skinID == CosmeticLibrary.CLERIC_SKIN_AMETHYST) {
			walkUp = Res.CLERIC_RESOURCE.WALK_UP_AMETHYST;
			walkLeft = Res.CLERIC_RESOURCE.WALK_LEFT_AMETHYST;
			walkDown = Res.CLERIC_RESOURCE.WALK_DOWN_AMETHYST;
			walkRight = Res.CLERIC_RESOURCE.WALK_RIGHT_AMETHYST;

			idleUp = Res.CLERIC_RESOURCE.IDLE_UP_AMETHYST;
			idleLeft = Res.CLERIC_RESOURCE.IDLE_LEFT_AMETHYST;
			idleDown = Res.CLERIC_RESOURCE.IDLE_DOWN_AMETHYST;
			idleRight = Res.CLERIC_RESOURCE.IDLE_RIGHT_AMETHYST;

			blind = Res.CLERIC_RESOURCE.BLIND_AMETHYST;
			
			portrait = Res.CLERIC_RESOURCE.PORTRAITS[1];
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

	public Image[] getBlind() {
		return blind;
	}

}
