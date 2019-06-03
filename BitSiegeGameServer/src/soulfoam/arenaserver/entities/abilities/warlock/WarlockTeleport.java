package soulfoam.arenaserver.entities.abilities.warlock;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.challengers.bots.Bot;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTeleportInfo;


public class WarlockTeleport extends Ability{
	
	private boolean canTeleport;

	private boolean sentFX;
	
	public WarlockTeleport(int gameID, float x, float y, Entity player){

		this.abilityID = AbilityInfo.WARLOCKTELEPORT;
		
		this.gameID = gameID;
		this.x = x;
		this.y = y + 4;
		this.player = player;
		
		
		this.coolDown = WarlockTeleportInfo.COOLDOWN; 
		this.destroyTimer = WarlockTeleportInfo.DESTROY_TIMER;
		
		this.width = WarlockTeleportInfo.WIDTH;
		this.height = WarlockTeleportInfo.HEIGHT;
		this.hitBoxWidth = WarlockTeleportInfo.HITBOX_WIDTH;
		this.hitBoxHeight = WarlockTeleportInfo.HITBOX_HEIGHT;
	
	}

	public void update(int delta){
		
		handleTimers(delta);
		if (player.isBot()){
			Bot b = (Bot) player;
			b.resetSelf();
		}
			
		
		removeThis(false);
	}
	
	public void handleTimers(int delta){
		
		if (!getTile().isBlocked() && !getTile().getTileBelow().isBlocked()){
			canTeleport = true;
		}
		else{
			canTeleport = false;
		}
		
		if (canTeleport){
			if (!sentFX){
				Game.getGame().getServerFunctions().sendAbilityAdd(player.getPlayerID(), AbilityInfo.WARLOCKTELEPORTFX, Game.getGame().getIdPool().getAvailableAbilityID(), player.getX(), player.getY());
				sentFX = true;
			}
			
			MoveEntityCommand smec = new MoveEntityCommand(player.getPlayerID(), getTile().getX(), getTile().getY());
			Game.getGame().getServerFunctions().getCommandHandler().addCommand(smec);

		}
		else{
			player.setAbility3CDTimer(0);
		}
		
	}

	public Shape getBounds() {
		
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);

	}

}
