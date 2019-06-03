package soulfoam.arena.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.entities.challengers.Illusionist;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistSwapPositionInfo;

public class IllusionistSwapPosition extends Ability {

	private IllusionistClone cloneToSwap;

	public IllusionistSwapPosition(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.ILLUSIONISTSWAPPOSITION;

		this.gameID = gameID;
		this.player = player;
		this.x = x;
		this.y = y;
		
		coolDown = IllusionistSwapPositionInfo.COOLDOWN;
		destroyTimer = 300;

		skillIcon = Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[2];
		soundEffect = Res.ILLUSIONIST_RESOURCE.SFX_ILLUSIONIST[3];

		changeDirectionOnCast = false;

	}

	public boolean canCast(float x, float y) {
		cloneToSwap = getLastPlacedClone();
		if (cloneToSwap == null) {
			return false;
		}
		return true;
	}

	public void update(int delta) {

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}
		
		if (cloneToSwap != null){
			cloneToSwap.setCurrentDirection(player.getCurrentDirection());
			cloneToSwap.setX(x);
			cloneToSwap.setY(y + 4);
		}
		else{
			cloneToSwap = getLastPlacedClone();
		}

	}

	private IllusionistClone getLastPlacedClone() {

		Illusionist p = (Illusionist) player;
		if (!p.passive.getCloneList().isEmpty()) {
			return p.passive.getCloneList().get(p.passive.getCloneList().size() - 1);
		}

		return null;
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce && player.isLocalPlayer()) {
			Game.getGame().getSoundSystem().playSound(soundEffect, 1);
			playedSoundOnce = true;
		}
	}

}
