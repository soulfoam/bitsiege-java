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
import soulfoam.arenashared.main.entityinfo.challengers.KnightInfo;


public class KnightBot extends Bot{
	
	public boolean sideToSide;

	public float useUltTime = 0.25f * 1000;
	public float useUltTimer = useUltTime;
	public boolean useUlt;
	
	public KnightBot(float x, float y) {
		
		this.challengerType = Bot.KNIGHTCLASSBOT;
		this.x = x;
		this.y = y;
		
		this.bot = true;
		this.skinID = CosmeticLibrary.KNIGHT_SKIN_NORMAL;
		this.underglowID = -1;
		initAbilities();
		this.width = 32;
		this.height = 24;
		this.hitBoxWidth = 8;
		this.hitBoxHeight = 16;
		this.baseMoveSpeed = KnightInfo.BASE_MOVE_SPEED;
		this.moveSpeed = baseMoveSpeed;
		this.baseHealth = KnightInfo.BASE_HEALTH;
		this.health = baseHealth;
		this.setCurrentAction(EntityInfo.IDLE);
		this.currentDirection = EntityInfo.DIR_DOWN;


	}
	
	public void initAbilities(){
		this.abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTMELEEATTACK, 0, 0, 0, this);
		this.abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();
		
		this.ability1 = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTSHIELDTHROW, 0, 0, 0, this);
		this.ability1CDTime = ability1.getCoolDown() * 1000;
		
		this.ability2 = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTMELEESPIN, 0, 0, 0, this);
		this.ability2CDTime = ability2.getCoolDown() * 1000;
		
		this.ability3 = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTDASH, 0, 0, 0, this);
		this.ability3CDTime = ability3.getCoolDown() * 1000;
		
		this.ability4 = AbilityBook.getAbilityByID(AbilityInfo.KNIGHTINVINCIBLEBUFF, 0, 0, 0, this);
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
			modifier = Res.randInt(-15, 15);
			modifierTimer = modifierTime;
			sideToSide = rand.nextBoolean();
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
				if (useUlt){
					if (useUltTimer > 0){
						useUltTimer -= delta;
					}
					
					if (useUltTimer <= 0){
						castAbility(4);
						useUlt = false;
					}
				}
				isAutoAttacking = true;
				castAbility(2);
				castAbility(3);
				if (targetPlayer != null){
					castAbility(1);
				}
				if (health <= (baseHealth - 25)) {
					castUlt();
				}
			}
			else{
				isAutoAttacking = false;
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
			
			if (targetPlayer.getY() > y){
				if (!sideToSide){
					currentDirection = EntityInfo.DIR_DOWN;
					targetYModifier = 14;
					targetXModifier = modifier;
				}
				
			}
			
			
			if (targetPlayer.getY() < y){

				if (!sideToSide){
					currentDirection = EntityInfo.DIR_UP;
					targetYModifier = -14;
					targetXModifier = -modifier;
				}
				
			}

			
			if (targetPlayer.getX() > x){
				if (sideToSide){
					currentDirection = EntityInfo.DIR_RIGHT;
					targetXModifier = 14;
					targetYModifier = -modifier;
				}
			}
			
			if (targetPlayer.getX() < x){
				if (sideToSide){
					currentDirection = EntityInfo.DIR_LEFT;
					targetXModifier = -14;
					targetYModifier = modifier;
				}
			}
				
		}
		
	}
	
	public void castUlt(){
		useUlt = true;
		useUltTimer = useUltTime;
	}
	
}
