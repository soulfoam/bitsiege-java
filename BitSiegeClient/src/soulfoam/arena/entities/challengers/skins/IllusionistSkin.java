package soulfoam.arena.entities.challengers.skins;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class IllusionistSkin {

	private Image[] walkUp = Res.ILLUSIONIST_RESOURCE.WALK_UP;
	private Image[] walkLeft = Res.ILLUSIONIST_RESOURCE.WALK_LEFT;
	private Image[] walkDown = Res.ILLUSIONIST_RESOURCE.WALK_DOWN;
	private Image[] walkRight = Res.ILLUSIONIST_RESOURCE.WALK_RIGHT;
	private Image[] idleUp = Res.ILLUSIONIST_RESOURCE.IDLE_UP;
	private Image[] idleLeft = Res.ILLUSIONIST_RESOURCE.IDLE_LEFT;
	private Image[] idleDown = Res.ILLUSIONIST_RESOURCE.IDLE_DOWN;
	private Image[] idleRight = Res.ILLUSIONIST_RESOURCE.IDLE_RIGHT;
	
	private Image[] autoAttack = Res.ILLUSIONIST_RESOURCE.AUTOATTACK;
	private Image[] cloneSpawnUp = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_UP;
	private Image[] cloneSpawnLeft = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_LEFT;
	private Image[] cloneSpawnDown = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_DOWN;
	private Image[] cloneSpawnRight = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_RIGHT;
	private Image portrait = Res.ILLUSIONIST_RESOURCE.PORTRAITS[0];
	
	public IllusionistSkin(int skinID) {
		if (skinID == CosmeticLibrary.ILLUSIONIST_SKIN_NORMAL) {
			walkUp = Res.ILLUSIONIST_RESOURCE.WALK_UP;
			walkLeft = Res.ILLUSIONIST_RESOURCE.WALK_LEFT;
			walkDown = Res.ILLUSIONIST_RESOURCE.WALK_DOWN;
			walkRight = Res.ILLUSIONIST_RESOURCE.WALK_RIGHT;

			idleUp = Res.ILLUSIONIST_RESOURCE.IDLE_UP;
			idleLeft = Res.ILLUSIONIST_RESOURCE.IDLE_LEFT;
			idleDown = Res.ILLUSIONIST_RESOURCE.IDLE_DOWN;
			idleRight = Res.ILLUSIONIST_RESOURCE.IDLE_RIGHT;

			autoAttack = Res.ILLUSIONIST_RESOURCE.AUTOATTACK;

			cloneSpawnUp = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_UP;
			cloneSpawnLeft = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_LEFT;
			cloneSpawnDown = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_DOWN;
			cloneSpawnRight = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_RIGHT;
			
			portrait = Res.ILLUSIONIST_RESOURCE.PORTRAITS[0];
			
		}

		if (skinID == CosmeticLibrary.ILLUSIONIST_SKIN_PYRO) {
			walkUp = Res.ILLUSIONIST_RESOURCE.WALK_UP_PYRO;
			walkLeft = Res.ILLUSIONIST_RESOURCE.WALK_LEFT_PYRO;
			walkDown = Res.ILLUSIONIST_RESOURCE.WALK_DOWN_PYRO;
			walkRight = Res.ILLUSIONIST_RESOURCE.WALK_RIGHT_PYRO;

			idleUp = Res.ILLUSIONIST_RESOURCE.IDLE_UP_PYRO;
			idleLeft = Res.ILLUSIONIST_RESOURCE.IDLE_LEFT_PYRO;
			idleDown = Res.ILLUSIONIST_RESOURCE.IDLE_DOWN_PYRO;
			idleRight = Res.ILLUSIONIST_RESOURCE.IDLE_RIGHT_PYRO;

			autoAttack = Res.ILLUSIONIST_RESOURCE.AUTOATTACK_PYRO;

			cloneSpawnUp = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_UP_PYRO;
			cloneSpawnLeft = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_LEFT_PYRO;
			cloneSpawnDown = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_DOWN_PYRO;
			cloneSpawnRight = Res.ILLUSIONIST_RESOURCE.CLONESPAWN_RIGHT_PYRO;
			
			portrait = Res.ILLUSIONIST_RESOURCE.PORTRAITS[1];
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

	public Image[] getAutoAttack() {
		return autoAttack;
	}

	public Image[] getCloneSpawnUp() {
		return cloneSpawnUp;
	}

	public Image[] getCloneSpawnLeft() {
		return cloneSpawnLeft;
	}

	public Image[] getCloneSpawnDown() {
		return cloneSpawnDown;
	}

	public Image[] getCloneSpawnRight() {
		return cloneSpawnRight;
	}

}
