package soulfoam.arenaserver.entities.challengers.bots;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.ClericInfo;


public class ClericBot extends Bot{
	
	public Entity targetAllyPlayer;

	
	public ClericBot(float x, float y) {
		
		this.challengerType = Bot.CLERICCLASSBOT;
		this.x = x;
		this.y = y;
		
		this.bot = true;
		this.skinID = CosmeticLibrary.CLERIC_SKIN_NORMAL;
		this.underglowID = -1;
		initAbilities();
		this.width = 32;
		this.height = 24;
		this.hitBoxWidth = 8;
		this.hitBoxHeight = 16;
		this.baseMoveSpeed = ClericInfo.BASE_MOVE_SPEED;
		this.moveSpeed = baseMoveSpeed;
		this.baseHealth = ClericInfo.BASE_HEALTH;
		this.health = baseHealth;
		this.setCurrentAction(EntityInfo.IDLE);
		this.currentDirection = EntityInfo.DIR_DOWN;

	}
	
	public void initAbilities(){
		this.abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.CLERICBASICATTACK, 0, 0, 0, this);
		this.abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();
		
		this.ability1 = AbilityBook.getAbilityByID(AbilityInfo.CLERICHEAL, 0, 0, 0, this);
		this.ability1CDTime = ability1.getCoolDown() * 1000;
		
		this.ability2 = AbilityBook.getAbilityByID(AbilityInfo.CLERICBLIND, 0, 0, 0, this);
		this.ability2CDTime = ability2.getCoolDown() * 1000;
		
		this.ability3 = AbilityBook.getAbilityByID(AbilityInfo.CLERICSWITCH, 0, 0, 0, this);
		this.ability3CDTime = ability3.getCoolDown() * 1000;
		
		this.ability4 = AbilityBook.getAbilityByID(AbilityInfo.CLERICTEAMHEAL, 0, 0, 0, this);
		this.ability4CDTime = ability4.getCoolDown() * 1000;
	}
	
	public void update(int delta){
		
		handleTimers(delta);
		
		
		if (!stunned){
			handleLogic(delta);
		}

		Game.getGame().getServerFunctions().serverBuyRandomItemForBot(this);
		

		if (modifierTimer > 0) {
			modifierTimer-= delta;
		}
		
		if (modifierTimer <= 0)
		{
			modifier = Res.randInt(-30, 30);
			modifierTimer = modifierTime;
		}
	}


	public void handleLogic(int delta){
		
		if (!dead){

			lookAndFightTarget(delta);
			
			if (!fighting){
				if (team == Res.TEAM_D){
					pathDefenderPatrol();
					
				}
				if (team == Res.TEAM_A){
					pathAttackerPatrol();
				}
			}				
			else{
				if (pathType != BotPathFinding.PATH_TARGETPLAYER && pathType != BotPathFinding.PATH_TARGETOBJECTIVE){
					inProgress = false;
					currentTile = null;
					pathTile = null;
					
					if (currentPath != null){
						currentPath.clear();
					}
					
					pathType = BotPathFinding.PATH_NONE;
				}
			
			}
			
			checkPathQue();
			handlePathMovement(delta);
			
			if (fighting){
				isAutoAttacking = true;
				if (targetAllyPlayer == null && health != baseHealth){
					castAbility(1, x, y);
				}
				if (targetPlayer != null){
					castAbility(2);
				}
			}
			else{
				isAutoAttacking = false;
			}
			
			if (targetAllyPlayer != null && targetAllyPlayer.getHealth() != targetAllyPlayer.getBaseHealth()){
				castAbility(1, targetAllyPlayer.getX(), targetAllyPlayer.getY());
			}
			
			for (Entity player: Game.getGame().getPlayers()){
				if (player != null){
					if (player.getTeam() == team && player.getHealth() <= Res.percentOf(player.getBaseHealth(), 25)){
						castAbility(4, x, y);
					}
				}
			}

		}
		else{
			
			currentTile = null;
			
			if (currentPath != null){
				currentPath.clear();
			}

			targetPlayer = null;
			fighting = false;
			
			pathType = BotPathFinding.PATH_NONE;
			
		}
	}
	
	public void lookAndFightTarget(int delta){
		
		if (targetAllyPlayer == null){
			
			for (Entity p: Game.getGame().getPlayers()){
				if (p.getBounds().intersects(getRangeBounds()) && p.getTeam() == team 
						&& !p.isDead() && p.getChallengerType() != EntityInfo.SPECTATE_PLAYER){
					
					targetAllyPlayer = p;
				}
				
			}

		}
		
		if (targetAllyPlayer != null){
			
			if (!Game.getGame().getPlayers().contains(targetAllyPlayer) || !targetAllyPlayer.getBounds().intersects(getRangeBounds()) || targetAllyPlayer.isDead()){
				targetAllyPlayer = null;
			}
		}

		if (targetPlayer == null){

			for (Entity p: Game.getGame().getPlayers()){

				if (p.getBounds().intersects(getRangeBounds()) && p.getTeam() != team 
						&& !p.isDead() && !p.isInvisible()
						&& !Game.getGame().getWorld().getTile(p.getX(), p.getY()).isBlocked() && !Game.getGame().getWorld().getTile(p.getX(), p.getY() + Tile.TILE_SIZE).isBlocked()
						&& p.getChallengerType() != EntityInfo.SPECTATE_PLAYER){
					
					targetPlayer = p;
				}
				
			}

			fighting = false;
		}
		
		if (targetPlayer != null){
			
			fighting = true;
			
			if (!Game.getGame().getPlayers().contains(targetPlayer)){
				//System.out.println("THIS IS TARGET PLAYER BUT ISNT HERE:" +  targetPlayer);
				pathType = BotPathFinding.PATH_NONE;
				fighting = false;
				targetPlayer = null;
				
				return;
			}
				
			if (!targetPlayer.getBounds().intersects(getRangeBounds()) || targetPlayer.isInvisible()){
				pathType = BotPathFinding.PATH_NONE;
				fighting = false;
				targetPlayer = null;
				return;
			}
			
			if (targetPlayer.isDead()){
				resetPath();
				fighting = false;
				targetPlayer = null;
				return;
			}
			
			if (!currentPath.isEmpty()){
				return;
			}
				
			fighting = true;

			setCurrentAction(EntityInfo.WALKING);
			

			float dx = x - targetPlayer.getX() + targetXModifier;
			float dy = y - targetPlayer.getY() + targetYModifier;
			float distance = (float) Math.sqrt(dx*dx + dy*dy);

			float multiplier = moveSpeed / distance;

			float velocityX = dx * multiplier;
			float velocityY = dy * multiplier;
			
			this.currentVelocity = getVelocityDirection(-velocityX, -velocityY);
			
			MoveEntityCommand smec = new MoveEntityCommand(playerID);
			Game.getGame().getServerFunctions().getCommandHandler().addCommand(smec);
			
			if (Game.getGame().getWorld().getTile(targetPlayer.getX(), targetPlayer.getY()).isBlocked() && Game.getGame().getWorld().getTile(targetPlayer.getX(), targetPlayer.getY() + Tile.TILE_SIZE).isBlocked()){
				resetPath();
				fighting = false;
				targetPlayer = null;
				return;
			}
			
			if (isLineOfSightBlocked() && !inProgress){
				resetPath();
				pathToEnemy(targetPlayer);
			}
			
			if (targetPlayer.getX() > x){
				currentDirection = EntityInfo.DIR_RIGHT;
				targetXModifier = Res.randInt(25, 80);
				targetYModifier = -modifier;
			}
			
			if (targetPlayer.getX() < x){
				currentDirection = EntityInfo.DIR_LEFT;
				targetXModifier = -Res.randInt(25, 80);
				targetYModifier = modifier;
			}
			
			if (targetPlayer.getY() > y + 25){
				currentDirection = EntityInfo.DIR_DOWN;
				targetYModifier = Res.randInt(25, 80);
				targetXModifier = modifier;
			}
			
			if (targetPlayer.getY() < y - 25){
				currentDirection = EntityInfo.DIR_UP;
				targetYModifier = -Res.randInt(25, 80);
				targetXModifier = -modifier;
			}
				
		}
		
	}
	
}
