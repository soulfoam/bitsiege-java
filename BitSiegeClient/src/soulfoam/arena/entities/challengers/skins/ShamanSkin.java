package soulfoam.arena.entities.challengers.skins;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class ShamanSkin {

	private Image[] walkUp = Res.SHAMAN_RESOURCE.WALK_UP;
	private Image[] walkLeft = Res.SHAMAN_RESOURCE.WALK_LEFT;
	private Image[] walkDown = Res.SHAMAN_RESOURCE.WALK_DOWN;
	private Image[] walkRight = Res.SHAMAN_RESOURCE.WALK_RIGHT;
	private Image[] idleUp = Res.SHAMAN_RESOURCE.IDLE_UP;
	private Image[] idleLeft = Res.SHAMAN_RESOURCE.IDLE_LEFT;
	private Image[] idleDown = Res.SHAMAN_RESOURCE.IDLE_DOWN;
	private Image[] idleRight = Res.SHAMAN_RESOURCE.IDLE_RIGHT;

	private Image[] autoAttack = Res.SHAMAN_RESOURCE.AUTO_ATTACK;

	private Image[] buffTotem = Res.SHAMAN_RESOURCE.BUFF_TOTEM;
	private Image[] debuffTotem = Res.SHAMAN_RESOURCE.DEBUFF_TOTEM;
	private Image[] shieldTotem = Res.SHAMAN_RESOURCE.SHIELD_TOTEM;
	private Image[] confuseTotem = Res.SHAMAN_RESOURCE.CONFUSE_TOTEM;
	
	private Image portrait = Res.SHAMAN_RESOURCE.PORTRAITS[0];
	
	public ShamanSkin(int skinID) {

		if (skinID == CosmeticLibrary.SHAMAN_SKIN_NORMAL) {
			walkUp = Res.SHAMAN_RESOURCE.WALK_UP;
			walkLeft = Res.SHAMAN_RESOURCE.WALK_LEFT;
			walkDown = Res.SHAMAN_RESOURCE.WALK_DOWN;
			walkRight = Res.SHAMAN_RESOURCE.WALK_RIGHT;
			idleUp = Res.SHAMAN_RESOURCE.IDLE_UP;
			idleLeft = Res.SHAMAN_RESOURCE.IDLE_LEFT;
			idleDown = Res.SHAMAN_RESOURCE.IDLE_DOWN;
			idleRight = Res.SHAMAN_RESOURCE.IDLE_RIGHT;

			autoAttack = Res.SHAMAN_RESOURCE.AUTO_ATTACK;

			buffTotem = Res.SHAMAN_RESOURCE.BUFF_TOTEM;
			debuffTotem = Res.SHAMAN_RESOURCE.DEBUFF_TOTEM;
			shieldTotem = Res.SHAMAN_RESOURCE.SHIELD_TOTEM;
			confuseTotem = Res.SHAMAN_RESOURCE.CONFUSE_TOTEM;
			
			portrait = Res.SHAMAN_RESOURCE.PORTRAITS[0];
		}

		if (skinID == CosmeticLibrary.SHAMAN_SKIN_VOID) {
			walkUp = Res.SHAMAN_RESOURCE.WALK_UP_VOID;
			walkLeft = Res.SHAMAN_RESOURCE.WALK_LEFT_VOID;
			walkDown = Res.SHAMAN_RESOURCE.WALK_DOWN_VOID;
			walkRight = Res.SHAMAN_RESOURCE.WALK_RIGHT_VOID;
			idleUp = Res.SHAMAN_RESOURCE.IDLE_UP_VOID;
			idleLeft = Res.SHAMAN_RESOURCE.IDLE_LEFT_VOID;
			idleDown = Res.SHAMAN_RESOURCE.IDLE_DOWN_VOID;
			idleRight = Res.SHAMAN_RESOURCE.IDLE_RIGHT_VOID;

			autoAttack = Res.SHAMAN_RESOURCE.AUTO_ATTACK_VOID;

			buffTotem = Res.SHAMAN_RESOURCE.BUFF_TOTEM_VOID;
			debuffTotem = Res.SHAMAN_RESOURCE.DEBUFF_TOTEM_VOID;
			shieldTotem = Res.SHAMAN_RESOURCE.SHIELD_TOTEM_VOID;
			confuseTotem = Res.SHAMAN_RESOURCE.CONFUSE_TOTEM_VOID;
			
			portrait = Res.SHAMAN_RESOURCE.PORTRAITS[1];
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

	public Image[] getAutoAttack() {
		return autoAttack;
	}

	public Image[] getBufftotem() {
		return buffTotem;
	}

	public Image[] getDebuffTotem() {
		return debuffTotem;
	}

	public Image[] getShieldTotem() {
		return shieldTotem;
	}

	public Image[] getConfuseTotem() {
		return confuseTotem;
	}

	public Image getPortrait() {
		return portrait;
	}


}
