package soulfoam.arena.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistCloneAttackInfo;

public class IllusionistCloneAttack extends Ability {

	private boolean setAttacking;

	public IllusionistCloneAttack(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.ILLUSIONISTCLONEATTACK;

		this.gameID = gameID;
		this.player = player;
		this.x = x;
		this.y = y;

		coolDown = IllusionistCloneAttackInfo.COOLDOWN;
		destroyTimer = IllusionistCloneAttackInfo.DESTROY_TIMER;

		skillIcon = Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[1];
		soundEffect = Res.ILLUSIONIST_RESOURCE.SFX_ILLUSIONIST[2];

	}

	public void update(int delta) {
		
		if (!setAttacking) {

			for (Ability a : Game.getGame().getAbilities()) {
				if (a.getPlayer().getPlayerID() == player.getPlayerID()) {
					if (a.getAbilityID() == AbilityInfo.ILLUSIONISTCLONE) {
						IllusionistClone ic = (IllusionistClone) a;
						
				        float dx = x - ic.getX();
				        float dy = y - ic.getY() + 4;

				        float dirLength = (float) Math.sqrt(dx*dx + dy*dy);
				        dx = dx/dirLength;
				        dy = dy/dirLength;
				        
						ic.attack(dx, dy);
					}
				}
			}
			setAttacking = true;
		}
		
		if (setAttacking) {
			removeThis();
		}

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
