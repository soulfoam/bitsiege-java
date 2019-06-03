package soulfoam.arenaserver.entities.abilities.voidlord;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.challengers.bots.Bot;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.game.Game;


public class VoidLordPullAction extends Ability{
	
	
	public float packetSendTimer = 0;
	public float packetSendTime = 0.030f * 1000;

	public Entity targetPlayer;


	public VoidLordPullAction(int gameID, Entity player, Entity targetPlayer){

		this.gameID = gameID;

		this.player = player;
		this.targetPlayer = targetPlayer;
		


	}

	public void update(int delta){
		handleTimers(delta);
		
		if (player.isBot()){
			Bot b = (Bot) player;
			b.resetSelf();
		}
	}
	
	public void handleTimers(int delta){
	
		if (targetPlayer.isDead()){
			removeThis(false);
			return;
		}
		
		if (!targetPlayer.getTile().isBlocked() && !targetPlayer.getTile().getTileBelow().isBlocked()){
			MoveEntityCommand smec = new MoveEntityCommand(player.getPlayerID(), targetPlayer.getTile().getX(), targetPlayer.getTile().getY());
			Game.getGame().getServerFunctions().getCommandHandler().addCommand(smec);
		}
		if (player.getTile().getX() == targetPlayer.getTile().getX() && player.getTile().getY() == targetPlayer.getTile().getY()){
			removeThis(false);
		}
		

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}


}
