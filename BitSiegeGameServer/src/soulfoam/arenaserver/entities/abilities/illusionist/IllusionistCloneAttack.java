package soulfoam.arenaserver.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistCloneAttackInfo;


public class IllusionistCloneAttack extends Ability{
	
	private boolean setAttacking;

	public IllusionistCloneAttack(int gameID, float x, float y, Entity player){
		
		this.abilityID = AbilityInfo.ILLUSIONISTCLONEATTACK;
		this.gameID = gameID;
		this.player = player;
		
		
		this.x = x;
		this.y = y;
		
		this.coolDown = IllusionistCloneAttackInfo.COOLDOWN;
		this.destroyTimer = IllusionistCloneAttackInfo.DESTROY_TIMER;
		
	}

	public void update(int delta) {
		
		if (!setAttacking){

			for (Ability a: Game.getGame().getAbilities()){
				if (a.getPlayer().getPlayerID() == player.getPlayerID()){
					if (a.getAbilityID() == AbilityInfo.ILLUSIONISTCLONE){
						IllusionistClone ic = (IllusionistClone) a;
						
				        float dx = player.getMouseX() - ic.getX();
				        float dy = player.getMouseY() - ic.getY() + 4;

				        float dirLength = (float) Math.sqrt(dx*dx + dy*dy);
				        dx = dx/dirLength;
				        dy = dy/dirLength;
				        
						ic.attack(dx, dy);
					}
				}
			}
			setAttacking = true;
		}
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0){
			removeThis(false);
		}

	}


	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
