package soulfoam.arenaserver.entities.challengers.bots;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.objectives.Objective;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.world.MapLayer;
import soulfoam.arenaserver.world.Tile;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public abstract class Bot extends Entity{
	
	public static final int KNIGHTCLASSBOT = 200;
	public static final int WARLOCKCLASSBOT = 201;
	public static final int ARCHERCLASSBOT = 202;
	public static final int CLERICCLASSBOT = 203;	
	public static final int ILLUSIONISTCLASSBOT = 204;	
	public static final int VOIDLORDCLASSBOT = 205;	
	public static final int WATERQUEENCLASSBOT = 206;	
	public static final int SHAMANCLASSBOT = 207;	
	

	public static final int[] BOTLIST = {KNIGHTCLASSBOT, WARLOCKCLASSBOT, ARCHERCLASSBOT, CLERICCLASSBOT, ILLUSIONISTCLASSBOT, VOIDLORDCLASSBOT, WATERQUEENCLASSBOT, SHAMANCLASSBOT };
	
	protected ArrayList<Tile> currentPath = new ArrayList<Tile>();
	
	protected float targetXModifier;
	protected float targetYModifier;
	protected int modifier = Res.randInt(-30, 30);
	protected float modifierTime = 0.2f * 1000;
	protected float modifierTimer = modifierTime;	

	protected Tile currentTile;

	protected byte pathType;

	protected Entity targetPlayer;
	protected Tile pathTile;
	
	protected boolean foundPath;
	protected boolean inProgress; 

	protected float oldX, oldY;
	
	protected boolean isLineOfSightBlocked(){
		for (Tile t : Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION).lineOfSight(currentDirection)){
			if (t.isBlocked()){
				return true;
			}
		}
		return false;
	}
	
	public void resetSelf(){
		resetPath();
		targetPlayer = null;
		fighting = false;
		isAutoAttacking = false;
	}
	
	public void resetPath(){
		currentPath.clear();
		foundPath = false;
		inProgress = false;
		pathTile = null;
		currentTile = null;
		pathType = BotPathFinding.PATH_NONE;
	}
	
	public void checkPathQue(){
		PathFindRequest[] pfrTempList = new PathFindRequest[Game.getGame().getBotPathFinding().getPathRequests().size()];
		Game.getGame().getBotPathFinding().getPathRequests().toArray(pfrTempList);

		for (PathFindRequest pfr : pfrTempList){
			if (pfr != null && pfr.theBot != null && pfr.theBot.getPlayerID() == playerID && pfr.solvedPath){
				currentPath = pfr.finalPath;
				pfr.needsRemoved = true;
			}
		}
	}

	public void handlePathMovement(int delta){
		
		if (confused){
			resetPath();
			return;
		}

		if (Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION).getX() != 0) oldX = x;
		if (Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION).getY() != 0) oldY = y;

		if (Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION).getX() == 0 || Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION).getY() == 0){
			
			currentTile = null;
			
			if (currentPath != null){
				currentPath.clear();
			}
			
	
			pathType = BotPathFinding.PATH_NONE;
			
			x = oldX;
			y = oldY;
			
			
			//System.out.println("I WAS TRAPPPPED! Reset from team " + playerTeam + " and I'm: " + username + " but now I stayed: " + x + ":" + y);
			
		}
		
//		if (getTile().isBlocked && getTile().getTileBelow().isBlocked){
//			if (Game.getGame().world.getTile(x, y).isBlocked && Game.getGame().world.getTile(x, y).getTileBelow().isBlocked){
//				x = Game.getGame().util.chooseRandomSpawn(playerTeam, true);
//				y = Game.getGame().util.chooseRandomSpawn(playerTeam, false);
//			}
//			if (!Game.getGame().world.getTile(x, y).isBlocked && !Game.getGame().world.getTile(x, y).getTileBelow().isBlocked){
//				x = oldX;
//				y = oldY;
//			}
//
//		}
		
		if (currentPath != null){
			if (!currentPath.isEmpty()){
				inProgress = true;
				foundPath = false;
				
				setCurrentAction(EntityInfo.WALKING);
				
				Tile t = currentPath.get(0);

				float dx = t.getX() - x;
				float dy = t.getY() - y;
				float distance = (float) Math.sqrt(dx*dx + dy*dy);
	
				float multiplier = moveSpeed / distance;
	
				float velocityX = dx * multiplier;
				float velocityY = dy * multiplier;
				
				x += velocityX * delta;
				y += velocityY * delta;

				if (getTile() == t || getTile().getTileBelow() == t){
					currentTile = currentPath.remove(0);
				}
					
				if (!fighting){
					if (dx < 0 && Math.abs(dx) > Math.abs(dy)){
						currentDirection = EntityInfo.DIR_LEFT;
					}
					if (dx > 0 && Math.abs(dx) > Math.abs(dy)){
						currentDirection = EntityInfo.DIR_RIGHT;
					}
					if (dy > 0 && Math.abs(dy) > Math.abs(dx)){
						currentDirection = EntityInfo.DIR_DOWN;
					}
					if (dy < 0 && Math.abs(dy) > Math.abs(dx)){
						currentDirection = EntityInfo.DIR_UP;
					}
				}
				else{
					if (targetPlayer != null){
						float px = -(x - targetPlayer.getX()); 
						float py = -(y - targetPlayer.getY()); 
						
						if (px < 0 && Math.abs(px) > Math.abs(py)){
							currentDirection = EntityInfo.DIR_LEFT;
						}
						if (px > 0 && Math.abs(px) > Math.abs(py)){
							currentDirection = EntityInfo.DIR_RIGHT;
						}
						if (py > 0 && Math.abs(py) > Math.abs(px)){
							currentDirection = EntityInfo.DIR_DOWN;
						}
						if (py < 0 && Math.abs(py) > Math.abs(px)){
							currentDirection = EntityInfo.DIR_UP;
						}
					}
				}
				
			}
			else{
				
				inProgress = false;
				
				if (pathType == BotPathFinding.PATH_DEFENDERPATROL){
					if (Game.getGame().getWorld().getMap().getTILE_DEFENDER_PATROL().contains(Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION))){
						foundPath = true;
						pathType = BotPathFinding.PATH_NONE;
					}
				}
				
				if (pathType == BotPathFinding.PATH_ATTACKERPATROL){
					if (Game.getGame().getWorld().getMap().getTILE_ATTACKER_PATROL().contains(Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION))){
						foundPath = true;
						setCurrentAction(EntityInfo.IDLE);
					}
				}
				
				if (pathType == BotPathFinding.PATH_TARGETPLAYER || pathType == BotPathFinding.PATH_TARGETOBJECTIVE){
					pathType = BotPathFinding.PATH_NONE;
				}
			}	
		}
		
		
	}
	
	public void setPath(Tile pathTile){

		if (!inProgress && !pathTile.isBlocked()){

			currentTile = null;
			
			if (currentPath != null){
				currentPath.clear();
			}


			Game.getGame().getBotPathFinding().requestNewPath(getTile(), pathTile, this);

			//System.out.println("Path from: " + Game.getGame().world.getTile(x, y) + " to " + pathTile + " | " + "Current Path Type: " + returnPathName() + " from team " + playerTeam + " | Path: " + currentPath);
		}
	}
	
	public void pathToEnemy(Entity p){
		if (pathType != BotPathFinding.PATH_TARGETPLAYER){
			
			pathType = BotPathFinding.PATH_TARGETPLAYER;
			
			pathTile = Game.getGame().getWorld().getTile(p.getX(), p.getY());
			
			for (Tile t : pathTile.getNeighbors()){
				if (t != null){
					if (Game.getGame().getWorld().getTile(x, y).getX() == t.getX() && Game.getGame().getWorld().getTile(x, y).getY() == t.getY()){
						return;
					}
				}
			}

			setPath(pathTile);
		}
		else{
			if (!inProgress && !foundPath){
				if (team == Res.TEAM_A){
					pathAttackerPatrol();
				}
				else{
					pathDefenderPatrol();
				}
			}
		}
	}
	
	public void pathDefenderPatrol(){
		if (pathType != BotPathFinding.PATH_DEFENDERPATROL){
			
			pathType = BotPathFinding.PATH_DEFENDERPATROL;
			
			pathTile = Game.getGame().getWorld().getMap().getTILE_DEFENDER_PATROL().get(rand.nextInt(Game.getGame().getWorld().getMap().getTILE_DEFENDER_PATROL().size()));

			setPath(pathTile);
		}
		else{
			if (!inProgress && !foundPath){
				pathType = BotPathFinding.PATH_DEFENDERPATROL;
				
				pathTile = Game.getGame().getWorld().getMap().getTILE_DEFENDER_PATROL().get(rand.nextInt(Game.getGame().getWorld().getMap().getTILE_DEFENDER_PATROL().size()));

				setPath(pathTile);
			}
		}
	}
	
	public void pathAttackerPatrol(){
		
		if (pathType != BotPathFinding.PATH_ATTACKERPATROL){
			
			pathType = BotPathFinding.PATH_ATTACKERPATROL;
			
			pathTile = Game.getGame().getWorld().getMap().getTILE_ATTACKER_PATROL().get(rand.nextInt(Game.getGame().getWorld().getMap().getTILE_ATTACKER_PATROL().size()));
			
			setPath(pathTile);
		}
		else{
			if (!inProgress && !foundPath){
				pathType = BotPathFinding.PATH_ATTACKERPATROL;
				
				pathTile = Game.getGame().getWorld().getMap().getTILE_ATTACKER_PATROL().get(rand.nextInt(Game.getGame().getWorld().getMap().getTILE_ATTACKER_PATROL().size()));
				
				setPath(pathTile);
			}
		}
	}
	
	public void pathToObjective(Objective o){

		if (pathType != BotPathFinding.PATH_TARGETOBJECTIVE){

			pathType = BotPathFinding.PATH_TARGETOBJECTIVE;
			
			setPath(Game.getGame().getWorld().getTile(o.getX(), o.getY()));
		}
		else{
			if (!inProgress && !foundPath){
				pathType = BotPathFinding.PATH_NONE;
			}
		}
	}
	
	public Rectangle getRangeBounds(){
		return new Rectangle(x - GameInfo.RES_WIDTH / 2, y - GameInfo.RES_HEIGHT / 2, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);
	}
	
	public byte getVelocityDirection(float velocityX, float velocityY){
		if (velocityX != 0 && velocityY == 0){
			if (velocityX > 0){
				return EntityInfo.DIR_RIGHT;
			}
			if (velocityX < 0){
				return EntityInfo.DIR_LEFT;
			}
		}
		if (velocityX == 0 && velocityY != 0){
			if (velocityY > 0){
				return EntityInfo.DIR_DOWN;
			}
			if (velocityY < 0){
				return EntityInfo.DIR_UP;
			}
		}
		if (velocityX != 0 && velocityY != 0){
			if (velocityX < 0 && velocityY < 0){
				return EntityInfo.DIR_UP_LEFT;
			}
			if (velocityX > 0 && velocityY < 0){
				return EntityInfo.DIR_UP_RIGHT;
			}
			if (velocityX < 0 && velocityY > 0){
				return EntityInfo.DIR_DOWN_LEFT;
			}
			if (velocityX > 0 && velocityY > 0){
				return EntityInfo.DIR_DOWN_RIGHT;
			}
		}
		
		return -1;
	}
	
	public void castAbility(int selectedAbility, float ax, float ay){
		
		if (!blind && !confused){
			if (selectedAbility == 1) {
				castAbilityAction(Res.SLOT_1, ax, ay);
			}
			
			if (selectedAbility == 2) {
				castAbilityAction(Res.SLOT_2, ax, ay);
			}
			
			if (selectedAbility == 3) {
				castAbilityAction(Res.SLOT_3, ax, ay);
			}
			
			
			if (selectedAbility == 4) {
				castAbilityAction(Res.SLOT_4, ax, ay);
			}
		}
	}
	
	public void castAbility(int selectedAbility){
		
		if (!blind && !confused){
			if (selectedAbility == 1) {
				castAbilityAction(Res.SLOT_1, x, y);
			}
			
			if (selectedAbility == 2) {
				castAbilityAction(Res.SLOT_2, x, y);
			}
			
			if (selectedAbility == 3) {
				castAbilityAction(Res.SLOT_3, x, y);
			}
			
			
			if (selectedAbility == 4) {
				castAbilityAction(Res.SLOT_4, x, y);
			}
		}
	}
	
}
