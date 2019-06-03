package soulfoam.arenaserver.entities.abilities.warlock;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTeleportFXInfo;


public class WarlockTeleportFX extends Ability{

	public WarlockTeleportFX(int gameID, float x, float y, Entity player){

		this.abilityID = AbilityInfo.WARLOCKTELEPORTFX;
		
		this.gameID = gameID;

		this.player = player;
		
		
		this.destroyTimer = WarlockTeleportFXInfo.DESTROY_TIMER;
		
		this.width = WarlockTeleportFXInfo.WIDTH;
		this.height = WarlockTeleportFXInfo.HEIGHT;
		this.hitBoxWidth = WarlockTeleportFXInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WarlockTeleportFXInfo.HITBOX_HEIGHT;


		this.x = player.getX() - 24;
		this.y = player.getY() - 30;
		

	}

	public void update(int delta){
		removeThis(false);
	}


	public Shape getBounds() {
		
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);

	}

}
