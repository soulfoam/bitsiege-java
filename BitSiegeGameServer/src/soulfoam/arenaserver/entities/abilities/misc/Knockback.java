package soulfoam.arenaserver.entities.abilities.misc;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.challengers.bots.Bot;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class Knockback extends Ability{
	
	public float knockBackSpeed;

	public float packetSendTimer = 0;
	public float packetSendTime = 0.030f * 1000;
	
	private boolean stopOnWall;
	
	public Knockback(int gameID, float dx, float dy, float knockBackTime, float knockBackSpeed, boolean stopOnWall, Entity player){
		
		this.abilityID = AbilityInfo.GENERALKNOCKBACK;
		this.gameID = gameID;
		this.width = 0;
		this.height = 0;
		this.hitBoxWidth = width;
		this.hitBoxHeight = height;
		this.x = 0;
		this.y = 0;
		this.dx = dx;
		this.dy = dy;
		this.moveSpeed = 0;
		this.damage = 0;
		this.coolDown = 0;

		this.destroyTimer = knockBackTime * 1000;
		this.player = player;
		
		
		this.knockBackSpeed = knockBackSpeed;
		this.stopOnWall = stopOnWall;
	}
	
	public void init(){
		
	}
	
	public void update(int delta) {

			
		Game.getGame().getServerFunctions().getCommandHandler().addCommand(new MoveEntityCommand(player.getPlayerID(), dx, dy, knockBackSpeed, true, stopOnWall));
		
		if (player.isBot()){
			Bot b = (Bot) player;
			b.resetPath();
		}
		
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}
			
		
		
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}


}
