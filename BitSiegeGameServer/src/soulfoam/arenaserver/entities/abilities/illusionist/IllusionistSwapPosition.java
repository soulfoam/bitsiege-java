package soulfoam.arenaserver.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.challengers.Illusionist;
import soulfoam.arenaserver.entities.challengers.bots.Bot;
import soulfoam.arenaserver.entities.challengers.bots.IllusionistBot;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistSwapPositionInfo;


public class IllusionistSwapPosition extends Ability{
	
	private boolean addedOnce = false;
	
	public IllusionistClone cloneToSwap;
	
	public float oldX, oldY;
	public byte oldCurrentDirection;
	
	public IllusionistSwapPosition(int gameID, Entity player){
		
		this.abilityID = AbilityInfo.ILLUSIONISTSWAPPOSITION;
		
		this.gameID = gameID;

		this.player = player;
		
		

		this.coolDown = IllusionistSwapPositionInfo.COOLDOWN;

		this.changeDirectionOnCast = false;
		

		this.x = player.getX();
		this.y = player.getY();
		

	}

	public void update(int delta) {
		
		if (!addedOnce){
			cloneToSwap = getLastPlacedClone();
			if (cloneToSwap != null){
				swapPositions();
				if (player.isBot()){
					Bot b = (Bot) player;
					b.resetSelf();	
				}
			}
			else{
				player.setAbility3CDTimer(player.getAbility3CDTime());
			}
			addedOnce = true;
		}
		
		removeThis(false);

	}
	
	private IllusionistClone getLastPlacedClone(){
		if (player != null){
			if (player.isBot()){
				IllusionistBot b = (IllusionistBot) player;
				if (!b.passive.cloneList.isEmpty()){
					return b.passive.cloneList.get(b.passive.cloneList.size()-1);
				}
			}
			else{
				Illusionist p = (Illusionist) player;
				if (!p.passive.cloneList.isEmpty()){
					return p.passive.cloneList.get(p.passive.cloneList.size()-1);
				}
			}
		}

		return null;
	}
	
	public void swapPositions(){
		oldX = cloneToSwap.getX();
		oldY = cloneToSwap.getY() - 4;
		
		if (!Game.getGame().getWorld().getTile(oldX, oldY).isBlocked() && !Game.getGame().getWorld().getTile(oldX, oldY + Tile.TILE_SIZE).isBlocked()){
			MoveEntityCommand smec = new MoveEntityCommand(player.getPlayerID(), oldX, oldY);
			Game.getGame().getServerFunctions().getCommandHandler().addCommand(smec);
		}

		cloneToSwap.setX(player.getX());
		cloneToSwap.setY(player.getY() + 4);
		
		cloneToSwap.setSwapTimer(1000);
	}
	

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	
}
