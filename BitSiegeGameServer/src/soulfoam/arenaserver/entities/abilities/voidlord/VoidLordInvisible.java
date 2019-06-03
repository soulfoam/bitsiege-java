package soulfoam.arenaserver.entities.abilities.voidlord;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordInvisibleInfo;


public class VoidLordInvisible extends Ability{
	
	public boolean setInvis;

	public VoidLordInvisible(int gameID, Entity player){

		this.abilityID = AbilityInfo.VOIDLORDINVISIBLE;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.coolDown = VoidLordInvisibleInfo.COOLDOWN;
		this.destroyTimer = VoidLordInvisibleInfo.DESTROY_TIMER;

		
	
		this.x = player.getX();
		this.y = player.getY();
		
		
	}

	public void update(int delta){
		if (!setInvis){
			player.setInvisible(true);
			setInvis = true;
		}
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0){
			player.setInvisible(false);
			removeThis(false);
		}
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}


}
