package soulfoam.arenaserver.entities.challengers.bots;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenaserver.entities.abilities.archer.ArcherPassive;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.world.MapLayer;
import soulfoam.arenaserver.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.challengers.ArcherInfo;


public class ArcherBot extends Bot{

	public ArcherPassive passive;
	public float switchArrowTime = 8 * 1000;
	public float switchArrowTimer = switchArrowTime;
	
	public ArcherBot(float x, float y) {
		
		this.challengerType = Bot.ARCHERCLASSBOT;
		this.x = x;
		this.y = y;
		
		this.bot = true;
		this.skinID = CosmeticLibrary.ARCHER_SKIN_NORMAL;
		this.underglowID = -1;
		initAbilities();
		this.passive = new ArcherPassive(this);
		this.width = 32;
		this.height = 24;
		this.hitBoxWidth = 8;
		this.hitBoxHeight = 16;
		this.baseMoveSpeed = ArcherInfo.BASE_MOVE_SPEED;
		this.moveSpeed = baseMoveSpeed;
		this.baseHealth = ArcherInfo.BASE_HEALTH;
		this.health = baseHealth;
		this.setCurrentAction(EntityInfo.IDLE);
		this.currentDirection = EntityInfo.DIR_DOWN;

	}
	
	public void initAbilities(){
		this.abilityBasicAttack = AbilityBook.getAbilityByID(AbilityInfo.ARCHERICEARROW, 0, 0, 0, this);
		this.abilityBasicAttackCDTime = abilityBasicAttack.getCoolDown();
		
		this.ability1 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERICEARROWSWITCH, 0, 0, 0, this);
		this.ability1CDTime = ability1.getCoolDown() * 1000;
		
		this.ability2 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERFIREARROWSWITCH, 0, 0, 0, this);
		this.ability2CDTime = ability2.getCoolDown() * 1000;
		
		this.ability3 = AbilityBook.getAbilityByID(AbilityInfo.ARCHEREXPLODINGARROWSWITCH, 0, 0, 0, this);
		this.ability3CDTime = ability3.getCoolDown() * 1000;
		
		this.ability4 = AbilityBook.getAbilityByID(AbilityInfo.ARCHERBUFF, 0, 0, 0, this);
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
			
			if (switchArrowTimer > 0){
				switchArrowTimer -= delta;
			}
			if (switchArrowTimer <= 0){
				castAbility(Res.randInt(1, 3));
				switchArrowTimer = switchArrowTime;
			}

			lookAndFightTarget(delta);
			
			if (!fighting){
				if (team == Res.TEAM_D){
					pathDefenderPatrol();
				}
				if (team == Res.TEAM_A){
					pathAttackerPatrol();
				}
			}				

			checkPathQue();
			handlePathMovement(delta);

			if (fighting){
				isAutoAttacking = true;
				castAbility(4);
			}
			else{
				isAutoAttacking = false;
			}
		}
		else{
			resetSelf();
		}
	}
	
	public void lookAndFightTarget(int delta){
		
		if (targetPlayer == null){
			for (Entity p: Game.getGame().getPlayers()){
				if (p.getBounds().intersects(getRangeBounds()) && p.getTeam() != team 
						&& !p.isDead() && !p.isInvisible()
						&& !Game.getGame().getWorld().getTile(p.getX(), p.getY(), MapLayer.INFORMATION).isBlocked() && !Game.getGame().getWorld().getTile(p.getX(), p.getY() + Tile.TILE_SIZE, MapLayer.INFORMATION).isBlocked()
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
