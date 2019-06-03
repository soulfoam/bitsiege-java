package soulfoam.arenaserver.entities.abilities.waterqueen;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenAbsorbInfo;


public class WaterQueenAbsorb extends Ability{

	public WaterQueenAbsorb(int gameID, Entity player){

		this.abilityID = AbilityInfo.WATERQUEENABSORB;

		this.gameID = gameID;
		this.player = player;
		

		this.coolDown = WaterQueenAbsorbInfo.COOLDOWN;
		this.destroyTimer = WaterQueenAbsorbInfo.DESTROY_TIMER;
		
		this.width = WaterQueenAbsorbInfo.WIDTH;
		this.height = WaterQueenAbsorbInfo.HEIGHT;
		this.hitBoxWidth = WaterQueenAbsorbInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WaterQueenAbsorbInfo.HITBOX_HEIGHT;
		

		x = player.getX() + 4;
		y = player.getY() + 8;
		
	}
	
	public void update(int delta) {
		

		player.setCanBeDamaged(false);
		player.setAbsorbDamageType(Entity.ABSORB_HEAL);
		player.setAbsorbPercentage(WaterQueenAbsorbInfo.ABSORB_AMOUNT);
		


		x = player.getX() + 4;
		y = player.getY() + 8;
		
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}

	}

	public Shape getBounds() {
		float radius = 12;
		return new Circle(x, y, radius);
	}

	public void removeThis(boolean broadcastRemove){

		player.setCanBeDamaged(true);
		player.setAbsorbDamageType(Entity.ABSORB_OFF);
		player.setAbsorbPercentage(0);
		
		
		Game.getGame().removeAbility(this, false);

	}

}
