package soulfoam.arena.entities.challengers.skins;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class WaterQueenSkin {

	private Image[] walkUp = Res.WATERQUEEN_RESOURCE.WALK_UP;
	private Image[] walkLeft = Res.WATERQUEEN_RESOURCE.WALK_LEFT;
	private Image[] walkDown = Res.WATERQUEEN_RESOURCE.WALK_DOWN;
	private Image[] walkRight = Res.WATERQUEEN_RESOURCE.WALK_RIGHT;
	private Image[] idleUp = Res.WATERQUEEN_RESOURCE.IDLE_UP;
	private Image[] idleLeft = Res.WATERQUEEN_RESOURCE.IDLE_LEFT;
	private Image[] idleDown = Res.WATERQUEEN_RESOURCE.IDLE_DOWN;
	private Image[] idleRight = Res.WATERQUEEN_RESOURCE.IDLE_RIGHT;

	private Image[] wave = Res.WATERQUEEN_RESOURCE.WAVE;

	private Image[] waterBall = Res.WATERQUEEN_RESOURCE.WATERBALL;
	private Image[] absorb = Res.WATERQUEEN_RESOURCE.ABSORB;

	private Image[] autoAttack = Res.WATERQUEEN_RESOURCE.AUTOATTACK;
	
	private Image portrait = Res.WATERQUEEN_RESOURCE.PORTRAITS[0];
	
	public WaterQueenSkin(int skinID) {

		if (skinID == CosmeticLibrary.WATERQUEEN_SKIN_NORMAL) {

			walkUp = Res.WATERQUEEN_RESOURCE.WALK_UP;
			walkLeft = Res.WATERQUEEN_RESOURCE.WALK_LEFT;
			walkDown = Res.WATERQUEEN_RESOURCE.WALK_DOWN;
			walkRight = Res.WATERQUEEN_RESOURCE.WALK_RIGHT;

			idleUp = Res.WATERQUEEN_RESOURCE.IDLE_UP;
			idleLeft = Res.WATERQUEEN_RESOURCE.IDLE_LEFT;
			idleDown = Res.WATERQUEEN_RESOURCE.IDLE_DOWN;
			idleRight = Res.WATERQUEEN_RESOURCE.IDLE_RIGHT;

			wave = Res.WATERQUEEN_RESOURCE.WAVE;

			waterBall = Res.WATERQUEEN_RESOURCE.WATERBALL;
			absorb = Res.WATERQUEEN_RESOURCE.ABSORB;

			autoAttack = Res.WATERQUEEN_RESOURCE.AUTOATTACK;
			
			portrait = Res.WATERQUEEN_RESOURCE.PORTRAITS[0];
			
		}

		if (skinID == CosmeticLibrary.WATERQUEEN_SKIN_SWAMP) {

			walkUp = Res.WATERQUEEN_RESOURCE.WALK_UP_SWAMP;
			walkLeft = Res.WATERQUEEN_RESOURCE.WALK_LEFT_SWAMP;
			walkDown = Res.WATERQUEEN_RESOURCE.WALK_DOWN_SWAMP;
			walkRight = Res.WATERQUEEN_RESOURCE.WALK_RIGHT_SWAMP;

			idleUp = Res.WATERQUEEN_RESOURCE.IDLE_UP_SWAMP;
			idleLeft = Res.WATERQUEEN_RESOURCE.IDLE_LEFT_SWAMP;
			idleDown = Res.WATERQUEEN_RESOURCE.IDLE_DOWN_SWAMP;
			idleRight = Res.WATERQUEEN_RESOURCE.IDLE_RIGHT_SWAMP;

			wave = Res.WATERQUEEN_RESOURCE.WAVE_SWAMP;

			waterBall = Res.WATERQUEEN_RESOURCE.WATERBALL_SWAMP;
			absorb = Res.WATERQUEEN_RESOURCE.ABSORB_SWAMP;

			autoAttack = Res.WATERQUEEN_RESOURCE.AUTOATTACK_SWAMP;
			
			portrait = Res.WATERQUEEN_RESOURCE.PORTRAITS[1];

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

	public Image[] getWave() {
		return wave;
	}

	public Image[] getWaterBall() {
		return waterBall;
	}

	public Image[] getAbsorb() {
		return absorb;
	}

	public Image[] getAutoAttack() {
		return autoAttack;
	}

	public Image getPortrait() {
		return portrait;
	}

}
