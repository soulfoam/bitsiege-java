package soulfoam.arenaserver.entities;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.abilities.misc.Knockback;
import soulfoam.arenaserver.entities.abilities.misc.StackableAbility;
import soulfoam.arenaserver.main.ServerValues;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.command.server.SpawnAbilityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.misc.StatKeeper;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.world.MapLayer;
import soulfoam.arenaserver.world.Tile;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.gameinfo.ShopInfo;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;


public abstract class Entity extends GameObject {

	protected StatKeeper statKeeper = new StatKeeper(this);

	protected ArrayList<StackableAbility> stacks = new ArrayList<StackableAbility>();
	protected HashSet<Entity> allyAssists = new HashSet<Entity>();
	protected HashSet<Entity> enemyAssists = new HashSet<Entity>();

	protected InetAddress ipAddress;
	protected int port;
	
	protected Random rand = new Random();
	
	protected int kills;
	protected int deaths;
	protected int captureScore;
	protected int assists;
	
	protected Timestamp pingRequestTime;
	protected int ping;
	protected ArrayList<Integer> pingHistory = new ArrayList<Integer>();

	protected float assistRemoveTime = 10 * 1000;
	protected float assistEnemyRemoveTimer = assistRemoveTime;
	protected float assistAllyRemoveTimer = assistRemoveTime;
	
	protected int accountID;
	protected int playerID;

	protected String username = "";
	protected String usernameColor = "";
	
	protected float connectionCheckTime = 7 * 1000;
	protected float connectionCheckTimer = connectionCheckTime;
	
	protected boolean fighting;
	protected boolean bot;
	protected boolean dead;
	protected boolean responding;
	
	protected float respawnTimer;

	protected int challengerType;
	
	protected Timestamp joinedTimeStamp;
	
	protected float oldX, oldY;
	protected int skinID;
	protected int underglowID;
	
	protected int mouseX, mouseY;

	protected float baseMoveSpeed;
	protected float moveSpeed;
	
	protected float powerBuffAmount;
	protected float attackSpeedBuffAmount;
	protected float moveSpeedBuffAmount;
	protected float healthBuffAmount;
	protected float cdrBuffAmount;
	
	protected float powerBuffTempAmount;
	
	protected float health;
	protected float shield;
	protected float baseShield;
	protected float shieldTimer;
	protected float baseHealth;

	protected byte currentAction;
	
	protected byte currentDirection; 
	protected byte currentVelocity = EntityInfo.DIR_NONE; 

	protected boolean isAutoAttacking;

	protected Ability abilityBasicAttack;
	protected Ability ability1;
	protected Ability ability2;
	protected Ability ability3;
	protected Ability ability4;
	
	protected float abilityBasicAttackCDTimer;
	protected float abilityBasicAttackCDTime;
	
	protected float ability1CDTimer;
	protected float ability1CDTime;
	
	protected float ability2CDTimer;
	protected float ability2CDTime;
	
	protected float ability3CDTimer;
	protected float ability3CDTime;
	
	protected float ability4CDTimer;
	protected float ability4CDTime;
	
	protected float stunTimer;
	protected boolean stunned;
	
	protected float blindTimer;
	protected boolean blind;
	
	protected float confusedTimer;
	protected boolean confused;
	
	protected float slowTimer;
	protected boolean slowed;
	protected float slowAmount;
	protected boolean addedSpeedBack;
	
	protected float totalDamage;
	
	protected boolean invisible;
	
	public static final int ABSORB_OFF = 0;
	public static final int ABSORB_HEAL = 1;
	public static final int ABSORB_NOHEAL = 2;
	protected int absorbDamageType = ABSORB_OFF;
	protected int absorbPercentage;
	
	protected boolean canBeDamaged = true;
	protected boolean canBeStunned = true;
	protected boolean canBeSlowed = true;
	protected boolean canBeBlinded = true;
	protected boolean canBeConfused = true;
	protected boolean canBeKnockedBack = true;
	
	protected Entity lastAttacker = this;
	
	protected byte team;

	protected int gold = 200;

	public abstract void update(int delta);
	public abstract void initAbilities();

	public void handleTimers(int delta){
		
		if (abilityBasicAttackCDTimer > 0) {
			abilityBasicAttackCDTimer-= delta;
		}

		if (ability1CDTimer > 0) {
			ability1CDTimer-= delta;
		}
		
		if (ability2CDTimer > 0) {
			ability2CDTimer-= delta;
		}
		
		if (ability3CDTimer > 0) {
			ability3CDTimer-= delta;
		}
		
		if (ability4CDTimer > 0) {
			ability4CDTimer-= delta;
		}
		
		if (abilityBasicAttackCDTimer <= 0) {
			abilityBasicAttackCDTimer = 0;
		}

		if (ability1CDTimer <= 0) {
			ability1CDTimer = 0;
		}

		if (ability2CDTimer <= 0) {
			ability2CDTimer = 0;
		}
		
		if (ability3CDTimer <= 0) {
			ability3CDTimer = 0;
		}
		
		if (ability4CDTimer <= 0) {
			ability4CDTimer = 0;
		}
		
		if (stunTimer > 0) {
			stunTimer-= delta;
			stunned = true;
		}

		if (stunTimer <= 0) {
			if (stunned){
				setCurrentAction(EntityInfo.IDLE);
				currentVelocity = EntityInfo.DIR_NONE;
			}
			stunned = false;
		}
		
		if (blindTimer > 0) {
			blindTimer-= delta;
			blind = true;
		}

		if (blindTimer <= 0) {
			blind = false;
		}
		
		if (slowTimer > 0) {
			slowTimer-= delta;
			slowed = true;
		}

		if (slowTimer <= 0) {
			slowed = false;
			if (!addedSpeedBack){
				moveSpeed += slowAmount;
				addedSpeedBack = true;
			}
		}
		
		if (assistEnemyRemoveTimer > 0) {
			assistEnemyRemoveTimer-= delta;
		}

		if (assistAllyRemoveTimer > 0) {
			assistAllyRemoveTimer-= delta;
		}
		
		if (assistEnemyRemoveTimer <= 0) {
			enemyAssists.clear();
			assistEnemyRemoveTimer = assistRemoveTime;
		}
		
		if (assistAllyRemoveTimer <= 0) {
			allyAssists.clear();
			assistAllyRemoveTimer = assistRemoveTime;
		}
		
		if (moveSpeed >= EntityInfo.MOVESPEED_CAP){
			moveSpeed = EntityInfo.MOVESPEED_CAP;
		}

		if (abilityBasicAttack != null && isAutoAttacking && abilityBasicAttackCDTimer <= 0 && !confused && !stunned && !blind && !dead){
			Game.getGame().getServerFunctions().sendAbilityAdd(playerID, abilityBasicAttack.getAbilityID(), Game.getGame().getIdPool().getAvailableAbilityID(), mouseX, mouseY);
			abilityBasicAttackCDTimer = EntityInfo.getAttackSpeedTimer(EntityInfo.getAttackSpeed(abilityBasicAttackCDTime, attackSpeedBuffAmount));
		}
		
		if (shieldTimer > 0){
			shieldTimer -= delta;
		}
		if (shieldTimer <= 0){
			shield = 0;
			baseShield = 0;
		}
		if (confusedTimer > 0){
			confusedTimer -= delta;
		}
		if (confusedTimer <= 0){
			confused = false;
		}
		
		MoveEntityCommand smec = new MoveEntityCommand(playerID);
		Game.getGame().getServerFunctions().getCommandHandler().addCommand(smec);

		for (StackableAbility s: stacks){

			s.update(delta);
			if (s.needsRemoved){
				if (s.consumeOnRemove){
					s.consumeStack();
				}
				stacks.remove(s);
			}
			
		}
		

	}

	public boolean move(float xm, float ym, int delta) {

		if (xm == 0 && ym == 0){
			return false;
		}
		
		float distance = (float) Math.sqrt(xm*xm+ym*ym);

		float multiplierX = Math.abs(xm) / distance;
		float multiplierY = Math.abs(ym) / distance;
		
		float velocityX = xm * multiplierX * delta;
		float velocityY = ym * multiplierY * delta;
		
		boolean canMoveX = true;
		boolean canMoveY = true;
		
		for (float xa = x + velocityX; xa < x + velocityX + hitBoxWidth; xa++) {
			for (float ya = y + 0; ya < y + 0 + hitBoxHeight; ya++) {

				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					canMoveX = false;
				}

				if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocked()) {
					canMoveX = false;
				}
			}
		}
		
		//if (canMoveX){
			x += velocityX;
		//}
		
		for (float xa = x + 0; xa < x + 0 + hitBoxWidth; xa++) {
			for (float ya = y + velocityY; ya < y + velocityY + hitBoxHeight; ya++) {
				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					canMoveY = false;
				}

				if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocked()) {
					canMoveY = false;
				}
			}
		}
		
		//if (canMoveY){
			y += velocityY;
		//}
		
		//if (!canMoveX && !canMoveY){
		//	return false;
		//}

		return true;
	}
	
	public boolean moveVelocity(float dx, float dy, float moveSpeed, int delta) {
		
		float velocityX = dx * moveSpeed * delta;
		float velocityY = dy * moveSpeed * delta;
		
		boolean canMoveX = true;
		boolean canMoveY = true;
		
		for (float xa = x + velocityX; xa < x + velocityX + hitBoxWidth; xa++) {
			for (float ya = y + 0; ya < y + 0 + hitBoxHeight; ya++) {

				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					canMoveX = false;
				}

				if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocked()) {
					canMoveX = false;
				}
			}
		}
		
		if (canMoveX){
			x += velocityX;
		}
		
		for (float xa = x + 0; xa < x + 0 + hitBoxWidth; xa++) {
			for (float ya = y + velocityY; ya < y + velocityY + hitBoxHeight; ya++) {
				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					canMoveY = false;
				}

				if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocked()) {
					canMoveY = false;
				}
			}
		}
		
		if (canMoveY){
			y += velocityY;
		}
		
		if (!canMoveX && !canMoveY){
			return false;
		}
		
		return true;
	}
	
	public boolean moveVelocityStopOnWall(float dx, float dy, float moveSpeed, int delta) {
		
		float velocityX = dx * moveSpeed * delta;
		float velocityY = dy * moveSpeed * delta;
		
		for (float xa = x + velocityX; xa < x + velocityX + hitBoxWidth; xa++) {
			for (float ya = y + 0; ya < y + 0 + hitBoxHeight; ya++) {

				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					return false;
				}

				if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocked()) {
					return false;
				}
			}
		}

		for (float xa = x + 0; xa < x + 0 + hitBoxWidth; xa++) {
			for (float ya = y + velocityY; ya < y + velocityY + hitBoxHeight; ya++) {
				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					return false;
				}

				if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocked()) {
					return false;
				}
			}
		}

		x += velocityX;
		y += velocityY;
		
		return true;
	}
	
	public void takeDamage(float damage, Entity attacker) {
		float totalDamage = EntityInfo.getOverallPower(damage, attacker.getPowerBuffAmount());
		
		if (team != attacker.getTeam()){
			if (absorbDamageType != Entity.ABSORB_OFF){
				if (absorbDamageType == Entity.ABSORB_HEAL){
					takeHeal(Res.percentOf(totalDamage, getAbsorbPercentage()), this);
				}
				if (absorbDamageType == Entity.ABSORB_NOHEAL){
					totalDamage -= Res.percentOf(totalDamage, getAbsorbPercentage());
				}
			}
		}
		
		if (canBeDamaged){
			if (!dead){
				
				if (team != attacker.getTeam()){

					setShield(shield - totalDamage);
					if (shield <= 0){
						setHealth(health - Math.abs(shield));
					}
					statKeeper.TOTAL_DAMAGE_TAKEN += totalDamage;
					attacker.getStatKeeper().TOTAL_DAMAGE_DEALT += totalDamage;
					enemyAssists.add(attacker);
					assistEnemyRemoveTimer = assistRemoveTime;
					invisible = false;
					lastAttacker = attacker;
				}

				if (health <= 0){
					dead = true;
					deaths++;
					setGold(gold - ShopInfo.DEATH_PENALTY);
					lastAttacker = attacker;
					
					Game.getGame().getServerFunctions().removeEntityAbilities(this);
					

					attacker.setPlayerKills(attacker.getKills() + 1);
					attacker.setGold(attacker.getGold() + ShopInfo.KILL_REWARD);
					
					for (Entity p : getEnemyAssists()){
						if (p.getPlayerID() != attacker.getPlayerID()){
							p.setPlayerAssists(p.getAssists() + 1);
							p.setGold(p.getGold() + ShopInfo.ASSIST_REWARD);
						}
					}
					
					for (Entity p : attacker.getAllyAssists()){
						if (p.getPlayerID() != attacker.getPlayerID() && !getEnemyAssists().contains(p)){
							p.setPlayerAssists(p.getAssists() + 1);
							p.setGold(p.getGold() + ShopInfo.ASSIST_REWARD);
						}
					}
					
					enemyAssists.clear();
					allyAssists.clear();
					
					Game.getGame().getServerFunctions().serverSpawnHealthOrb(getX() + 8, getY() + 4, ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE);
					Game.getGame().getServerFunctions().serverSendNotification(Res.MESSAGE_PLAYERKILLEDPLAYER, attacker, this);
				}
			}
			
		}
	}
	
	public void takeStack(StackableAbility stack) {
		stacks.add(stack);
		stack.applyStack();
	}
	
	public void takeHeal(float amount, Entity caster) {
		if (team == caster.getTeam()){
			float totalHeal = EntityInfo.getOverallPower(amount, caster.getPowerBuffAmount());
			takeHeal(totalHeal);
			caster.getStatKeeper().TOTAL_HEALING_DEALT += totalHeal;
			
			if (caster != this){
				allyAssists.add(caster);
				assistAllyRemoveTimer = assistRemoveTime;
			}
		}
	
	}
	
	public void takeHeal(float amount) {
		setHealth(getHealth() + amount);
		statKeeper.TOTAL_HEALING_TAKEN += amount;
	}
	
	public void takeKnockback(float dx, float dy, float knockBackTime, float knockBackSpeed, boolean stopOnWall, Entity attacker){
		if (canBeKnockedBack && team != attacker.getTeam()){
			invisible = false;
			enemyAssists.add(attacker);
			assistEnemyRemoveTimer = assistRemoveTime;
			Game.getGame().getServerFunctions().getCommandHandler().addCommand(
					new SpawnAbilityCommand(new Knockback(Game.getGame().getIdPool().getAvailableAbilityID(), dx, dy, knockBackTime, knockBackSpeed, stopOnWall, this)));
		}
	}
	
	public void takeStun(float stunDuration, Entity attacker){
		if (team != attacker.getTeam()){
			if (canBeStunned){
				if (!stunned){
					stunTimer = stunDuration * 1000;
					stunned = true;
					invisible = false;
					enemyAssists.add(attacker);
					assistEnemyRemoveTimer = assistRemoveTime;
				}
			}
		}
	}
	
	public void takeBlind(float blindDuration, Entity attacker){
		if (team != attacker.getTeam()) {
			if (canBeBlinded){
				if (!blind){
					blindTimer = blindDuration * 1000;
					blind = true;
					invisible = false;
					enemyAssists.add(attacker);
					assistEnemyRemoveTimer = assistRemoveTime;
				}
			}
		}
	}
	
	public void takeSlow(float slowDuration, float amount, Entity attacker){
		if (team != attacker.getTeam()) {
			if (canBeSlowed){
				if (!slowed){
					slowTimer = slowDuration * 1000;
					slowed = true;
					slowAmount = amount;
					moveSpeed -= amount;
					addedSpeedBack = false;
					enemyAssists.add(attacker);
					assistEnemyRemoveTimer = assistRemoveTime;
					
				}
			}
		}
	}
	
	public void takeShield(float shieldDuration, float shieldAmount, Entity shieldGiver){
		if (team == shieldGiver.getTeam()){
			shield += shieldAmount;
			baseShield = shield;
			shieldTimer = shieldDuration * 1000;
			
			if (shieldGiver != this){
				allyAssists.add(shieldGiver);
			}
			
			assistAllyRemoveTimer = assistRemoveTime;
			
		
		}
	}
	
	public void takeConfuse(float confuseDuration, Entity attacker){
		if (team != attacker.getTeam()){
			if (canBeConfused){
				if (!confused){
					confusedTimer = confuseDuration * 1000;
					confused = true;
					invisible = false;
					enemyAssists.add(attacker);
					assistEnemyRemoveTimer = assistRemoveTime;
				}
			}
		}
	}
	
	public Shape getBounds(){
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
	public Shape getBoundsCenterBody(){
		return new Rectangle(x , y + hitBoxHeight / 4, hitBoxWidth, hitBoxHeight / 2);
	}
	
	public Shape getBoundsLowerBody(){
		return new Rectangle(x, y + hitBoxHeight - (hitBoxHeight / 2), hitBoxWidth, hitBoxHeight / 2);
	}
	
	public Shape getBoundsFeet(){
		return new Rectangle(x, y + hitBoxHeight - 1, hitBoxWidth, 1);
	}
	
	public Tile getTile(){
		Tile t = new Tile(0, 0, -1, 1);
		t = Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION);
		return t;
	}

	public StackableAbility getStack(int stackID){

		for (StackableAbility s: stacks){
			if (s.stackID == stackID){
				return s;
			}
		}
		
		return null;
	}

	public int getStackCount(int stackID){
		int count = 0;
		for (StackableAbility s: stacks){
			if (s.stackID == stackID){
				count++;
			}
		}
		
		return count;
	}
	
	public void consumeStack(int stackID){
		for (StackableAbility s: stacks){
			if (s.stackID == stackID){
				s.consumeStack();
				if (s.removeOnConsume){
					stacks.remove(s);
				}
			}
		}
	}
	
	public void consumeStack(int stackID, int stacksToConsume){

		int count = 0;
		for (StackableAbility s: stacks){
			if (s.stackID == stackID){
				if (count >= stacksToConsume){
					break;
				}
				count++;
				s.consumeStack();
				if (s.removeOnConsume){
					stacks.remove(s);
				}
			}
		}
	}
	
	public void setAutoAttack(boolean on){
		if (on){
			isAutoAttacking = true;
		}
		else{
			isAutoAttacking = false;
		}
	}
	
	public void setCurrentAction(byte action){
		currentAction = action;
	}
	
	public void addPingToHistory(int ping){
		if (pingHistory.size() > ServerValues.PING_HISTORY_SIZE){
			pingHistory.clear();
		}

		pingHistory.add(ping);
	}

	public int getPing(){
		int totalPing = 0;
		int returnPing = 0;
		for (int i : pingHistory){
			totalPing += i;
		}

		if (pingHistory.size() > 0){
			returnPing = totalPing / pingHistory.size();
		}
		else{
			returnPing = totalPing;
		}
		
		if (returnPing <= 0){
			return 1;
		}
		else{
			return returnPing;
		}
	}

	public int goldFromStats(){
		int totalGold = 0;
		
		totalGold += (healthBuffAmount / ShopInfo.HP_ADD_VALUE) * ShopInfo.HP_PRICE;
		totalGold += (powerBuffAmount / ShopInfo.POWER_ADD_VALUE) * ShopInfo.POWER_PRICE;
		totalGold += (attackSpeedBuffAmount / ShopInfo.ATTACKSPEED_ADD_VALUE) * ShopInfo.ATTACKSPEED_PRICE;
		totalGold += (moveSpeedBuffAmount / ShopInfo.MOVESPEED_ADD_VALUE) * ShopInfo.MOVESPEED_PRICE;
		return totalGold;
	}

	public boolean isMaxAttackSpeed(){
		if (EntityInfo.getAttackSpeed(abilityBasicAttackCDTime, attackSpeedBuffAmount) >= EntityInfo.ATTACKSPEED_CAP){
			return true;
		}

		return false;
	}
	
	public boolean isMaxMoveSpeed(){
		if (moveSpeed >= EntityInfo.MOVESPEED_CAP){
			return true;
		}
		
		return false;
	}
	
	public float getPowerBuffAmount(){
		return powerBuffAmount + powerBuffTempAmount;
	}
	
	public void castAbilityAction(byte slotID, float x, float y){

		Ability a = null;

		if (Math.abs(x - this.x) <= GameInfo.RES_WIDTH && Math.abs(y - this.y) <= GameInfo.RES_HEIGHT){
			if (!dead && !stunned && !blind && !confused){
				if (slotID == Res.SLOT_1 && ability1CDTimer <= 0){
					ability1CDTimer = ability1CDTime;
					a = ability1;
				}
				else if (slotID == Res.SLOT_2 && ability2CDTimer <= 0){
					ability2CDTimer = ability2CDTime;
					a = ability2;
				}
				else if (slotID == Res.SLOT_3 && ability3CDTimer <= 0){
					ability3CDTimer = ability3CDTime;
					a = ability3;
				}
				else if (slotID == Res.SLOT_4 && ability4CDTimer <= 0){
					ability4CDTimer = ability4CDTime;
					a = ability4;
				}
			}
	
			if (a != null){
				Game.getGame().getServerFunctions().sendAbilityAdd(playerID, a.getAbilityID(), Game.getGame().getIdPool().getAvailableAbilityID(), x, y);
			}
		
		}	
		
	}
	public StatKeeper getStatKeeper() {
		return statKeeper;
	}
	public ArrayList<StackableAbility> getStacks() {
		return stacks;
	}
	public HashSet<Entity> getAllyAssists() {
		return allyAssists;
	}
	public HashSet<Entity> getEnemyAssists() {
		return enemyAssists;
	}
	public InetAddress getIpAddress() {
		return ipAddress;
	}
	public int getPort() {
		return port;
	}

	public int getKills() {
		return kills;
	}
	public int getDeaths() {
		return deaths;
	}
	public int getCaptureScore() {
		return captureScore;
	}
	public int getAssists() {
		return assists;
	}
	public Timestamp getPingRequestTime() {
		return pingRequestTime;
	}
	public ArrayList<Integer> getPingHistory() {
		return pingHistory;
	}
	public float getAssistRemoveTime() {
		return assistRemoveTime;
	}
	public float getAssistEnemyRemoveTimer() {
		return assistEnemyRemoveTimer;
	}
	public float getAssistAllyRemoveTimer() {
		return assistAllyRemoveTimer;
	}
	public int getAccountID() {
		return accountID;
	}
	public int getPlayerID() {
		return playerID;
	}
	public String getUsername() {
		return username;
	}
	public float getConnectionCheckTime() {
		return connectionCheckTime;
	}
	public float getConnectionCheckTimer() {
		return connectionCheckTimer;
	}
	public boolean isFighting() {
		return fighting;
	}
	public boolean isBot() {
		return bot;
	}
	public boolean isDead() {
		return dead;
	}
	public boolean isResponding() {
		return responding;
	}
	public float getRespawnTimer() {
		return respawnTimer;
	}
	public int getChallengerType() {
		return challengerType;
	}
	public Timestamp getJoinedTimeStamp() {
		return joinedTimeStamp;
	}
	public float getOldX() {
		return oldX;
	}
	public float getOldY() {
		return oldY;
	}
	public int getSkinID() {
		return skinID;
	}
	public int getUnderglowID() {
		return underglowID;
	}
	public int getMouseX() {
		return mouseX;
	}
	public int getMouseY() {
		return mouseY;
	}
	public float getBaseMoveSpeed() {
		return baseMoveSpeed;
	}
	public float getMoveSpeed() {
		return moveSpeed;
	}
	public float getAttackSpeedBuffAmount() {
		return attackSpeedBuffAmount;
	}
	public float getMoveSpeedBuffAmount() {
		return moveSpeedBuffAmount;
	}
	public float getHealthBuffAmount() {
		return healthBuffAmount;
	}
	public float getCdrBuffAmount() {
		return cdrBuffAmount;
	}
	public float getPowerBuffTempAmount() {
		return powerBuffTempAmount;
	}
	public float getHealth() {
		return health;
	}
	public float getShield() {
		return shield;
	}
	public float getBaseShield() {
		return baseShield;
	}
	public float getShieldTimer() {
		return shieldTimer;
	}
	public float getBaseHealth() {
		return baseHealth;
	}
	public byte getCurrentAction() {
		return currentAction;
	}
	public byte getCurrentDirection() {
		return currentDirection;
	}
	public byte getCurrentVelocity() {
		return currentVelocity;
	}
	public boolean isAutoAttacking() {
		return isAutoAttacking;
	}

	public Ability getAbilityBasicAttack() {
		return abilityBasicAttack;
	}
	public Ability getAbility1() {
		return ability1;
	}
	public Ability getAbility2() {
		return ability2;
	}
	public Ability getAbility3() {
		return ability3;
	}
	public Ability getAbility4() {
		return ability4;
	}
	public float getAbilityBasicAttackCDTimer() {
		return abilityBasicAttackCDTimer;
	}
	public float getAbilityBasicAttackCDTime() {
		return abilityBasicAttackCDTime;
	}
	public float getAbility1CDTimer() {
		return ability1CDTimer;
	}
	public float getAbility1CDTime() {
		return ability1CDTime;
	}
	public float getAbility2CDTimer() {
		return ability2CDTimer;
	}
	public float getAbility2CDTime() {
		return ability2CDTime;
	}
	public float getAbility3CDTimer() {
		return ability3CDTimer;
	}
	public float getAbility3CDTime() {
		return ability3CDTime;
	}
	public float getAbility4CDTimer() {
		return ability4CDTimer;
	}
	public float getAbility4CDTime() {
		return ability4CDTime;
	}
	public float getStunTimer() {
		return stunTimer;
	}
	public boolean isStunned() {
		return stunned;
	}
	public float getBlindTimer() {
		return blindTimer;
	}
	public boolean isBlind() {
		return blind;
	}
	public float getConfusedTimer() {
		return confusedTimer;
	}
	public boolean isConfused() {
		return confused;
	}
	public float getSlowTimer() {
		return slowTimer;
	}
	public boolean isSlowed() {
		return slowed;
	}
	public float getSlowAmount() {
		return slowAmount;
	}
	public boolean isAddedSpeedBack() {
		return addedSpeedBack;
	}
	public float getTotalDamage() {
		return totalDamage;
	}
	public boolean isInvisible() {
		return invisible;
	}
	public int getAbsorbDamageType() {
		return absorbDamageType;
	}
	public int getAbsorbPercentage() {
		return absorbPercentage;
	}
	public boolean canBeDamaged() {
		return canBeDamaged;
	}
	public boolean canBeStunned() {
		return canBeStunned;
	}
	public boolean canBeSlowed() {
		return canBeSlowed;
	}
	public boolean canBeBlinded() {
		return canBeBlinded;
	}
	public boolean canBeConfused() {
		return canBeConfused;
	}
	public boolean canBeKnockedBack() {
		return canBeKnockedBack;
	}
	public Entity getLastAttacker() {
		return lastAttacker;
	}
	public byte getTeam() {
		return team;
	}
	public int getGold() {
		return gold;
	}
	public void setStacks(ArrayList<StackableAbility> stacks) {
		this.stacks = stacks;
	}
	public void setAllyAssists(HashSet<Entity> allyAssists) {
		this.allyAssists = allyAssists;
	}
	public void setEnemyAssists(HashSet<Entity> enemyAssists) {
		this.enemyAssists = enemyAssists;
	}
	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setRand(Random rand) {
		this.rand = rand;
	}
	public void setPlayerKills(int playerKills) {
		this.kills = playerKills;
	}
	public void setPlayerDeaths(int playerDeaths) {
		this.deaths = playerDeaths;
	}
	public void setPlayerCaptureScore(int playerCaptureScore) {
		this.captureScore = playerCaptureScore;
	}
	public void setPlayerAssists(int playerAssists) {
		this.assists = playerAssists;
	}
	public void setPingRequestTime(Timestamp pingRequestTime) {
		this.pingRequestTime = pingRequestTime;
	}
	public void setPing(int ping) {
		this.ping = ping;
	}
	public void setPingHistory(ArrayList<Integer> pingHistory) {
		this.pingHistory = pingHistory;
	}
	public void setAssistRemoveTime(float assistRemoveTime) {
		this.assistRemoveTime = assistRemoveTime;
	}
	public void setAssistEnemyRemoveTimer(float assistEnemyRemoveTimer) {
		this.assistEnemyRemoveTimer = assistEnemyRemoveTimer;
	}
	public void setAssistAllyRemoveTimer(float assistAllyRemoveTimer) {
		this.assistAllyRemoveTimer = assistAllyRemoveTimer;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setConnectionCheckTime(float connectionCheckTime) {
		this.connectionCheckTime = connectionCheckTime;
	}
	public void setConnectionCheckTimer(float connectionCheckTimer) {
		this.connectionCheckTimer = connectionCheckTimer;
	}
	public void setFighting(boolean fighting) {
		this.fighting = fighting;
	}
	public void setBot(boolean bot) {
		this.bot = bot;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public void setResponding(boolean responding) {
		this.responding = responding;
	}
	public void setRespawnTimer(float respawnTimer) {
		this.respawnTimer = respawnTimer;
	}
	public void setChallengerType(int challengerType) {
		this.challengerType = challengerType;
	}
	public void setJoinedTimeStamp(Timestamp joinedTimeStamp) {
		this.joinedTimeStamp = joinedTimeStamp;
	}
	public void setOldX(float oldX) {
		this.oldX = oldX;
	}
	public void setOldY(float oldY) {
		this.oldY = oldY;
	}
	public void setSkinID(int skinID) {
		this.skinID = skinID;
	}
	public void setUnderglowID(int underglowID) {
		this.underglowID = underglowID;
	}
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	public void setBaseMoveSpeed(float baseMoveSpeed) {
		this.baseMoveSpeed = baseMoveSpeed;
	}
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	public void setPowerBuffAmount(float powerBuffAmount) {
		this.powerBuffAmount = powerBuffAmount;
	}
	public void setAttackSpeedBuffAmount(float attackSpeedBuffAmount) {
		this.attackSpeedBuffAmount = attackSpeedBuffAmount;
	}
	public void setMoveSpeedBuffAmount(float moveSpeedBuffAmount) {
		this.moveSpeedBuffAmount = moveSpeedBuffAmount;
	}
	public void setHealthBuffAmount(float healthBuffAmount) {
		this.healthBuffAmount = healthBuffAmount;
	}
	public void setCdrBuffAmount(float cdrBuffAmount) {
		this.cdrBuffAmount = cdrBuffAmount;
	}
	public void setPowerBuffTempAmount(float powerBuffTempAmount) {
		this.powerBuffTempAmount = powerBuffTempAmount;
	}
	public void setHealth(float health) {
		this.health = health;
		if (health <= 0) {
			this.health = 0;
		}
		if (health >= baseHealth) {
			this.health = baseHealth;
		}
	}
	public void setShield(float shield) {
		this.shield = shield;
		if (shield <= 0) {
			this.shield = 0;
		}
	}
	public void setBaseShield(float baseShield) {
		this.baseShield = baseShield;
	}
	public void setShieldTimer(float shieldTimer) {
		this.shieldTimer = shieldTimer;
	}
	public void setBaseHealth(float baseHealth) {
		this.baseHealth = baseHealth;
	}
	public void setCurrentDirection(byte currentDirection) {
		this.currentDirection = currentDirection;
	}
	public void setCurrentVelocity(byte currentVelocity) {
		this.currentVelocity = currentVelocity;
	}
	public void setAutoAttacking(boolean isAutoAttacking) {
		this.isAutoAttacking = isAutoAttacking;
	}

	public void setAbilityBasicAttack(Ability abilityBasicAttack) {
		this.abilityBasicAttack = abilityBasicAttack;
	}
	public void setAbility1(Ability ability1) {
		this.ability1 = ability1;
	}
	public void setAbility2(Ability ability2) {
		this.ability2 = ability2;
	}
	public void setAbility3(Ability ability3) {
		this.ability3 = ability3;
	}
	public void setAbility4(Ability ability4) {
		this.ability4 = ability4;
	}
	public void setAbilityBasicAttackCDTimer(float abilityBasicAttackCDTimer) {
		this.abilityBasicAttackCDTimer = abilityBasicAttackCDTimer;
	}
	public void setAbilityBasicAttackCDTime(float abilityBasicAttackCDTime) {
		this.abilityBasicAttackCDTime = abilityBasicAttackCDTime;
	}
	public void setAbility1CDTimer(float ability1cdTimer) {
		ability1CDTimer = ability1cdTimer;
	}
	public void setAbility1CDTime(float ability1cdTime) {
		ability1CDTime = ability1cdTime;
	}
	public void setAbility2CDTimer(float ability2cdTimer) {
		ability2CDTimer = ability2cdTimer;
	}
	public void setAbility2CDTime(float ability2cdTime) {
		ability2CDTime = ability2cdTime;
	}
	public void setAbility3CDTimer(float ability3cdTimer) {
		ability3CDTimer = ability3cdTimer;
	}
	public void setAbility3CDTime(float ability3cdTime) {
		ability3CDTime = ability3cdTime;
	}
	public void setAbility4CDTimer(float ability4cdTimer) {
		ability4CDTimer = ability4cdTimer;
	}
	public void setAbility4CDTime(float ability4cdTime) {
		ability4CDTime = ability4cdTime;
	}
	public void setStunTimer(float stunTimer) {
		this.stunTimer = stunTimer;
	}
	public void setStunned(boolean stunned) {
		this.stunned = stunned;
	}
	public void setBlindTimer(float blindTimer) {
		this.blindTimer = blindTimer;
	}
	public void setBlind(boolean blind) {
		this.blind = blind;
	}
	public void setConfusedTimer(float confusedTimer) {
		this.confusedTimer = confusedTimer;
	}
	public void setConfused(boolean confused) {
		this.confused = confused;
	}
	public void setSlowTimer(float slowTimer) {
		this.slowTimer = slowTimer;
	}
	public void setSlowed(boolean slowed) {
		this.slowed = slowed;
	}
	public void setSlowAmount(float slowAmount) {
		this.slowAmount = slowAmount;
	}
	public void setAddedSpeedBack(boolean addedSpeedBack) {
		this.addedSpeedBack = addedSpeedBack;
	}
	public void setTotalDamage(float totalDamage) {
		this.totalDamage = totalDamage;
	}
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}
	public void setAbsorbDamageType(int absorbDamageType) {
		this.absorbDamageType = absorbDamageType;
	}
	public void setAbsorbPercentage(int absorbPercentage) {
		this.absorbPercentage = absorbPercentage;
	}
	public void setCanBeDamaged(boolean canBeDamaged) {
		this.canBeDamaged = canBeDamaged;
	}
	public void setCanBeStunned(boolean canBeStunned) {
		this.canBeStunned = canBeStunned;
	}
	public void setCanBeSlowed(boolean canBeSlowed) {
		this.canBeSlowed = canBeSlowed;
	}
	public void setCanBeBlinded(boolean canBeBlinded) {
		this.canBeBlinded = canBeBlinded;
	}
	public void setCanBeConfused(boolean canBeConfused) {
		this.canBeConfused = canBeConfused;
	}
	public void setCanBeKnockedBack(boolean canBeKnockedBack) {
		this.canBeKnockedBack = canBeKnockedBack;
	}
	public void setLastAttacker(Entity lastAttacker) {
		this.lastAttacker = lastAttacker;
	}
	public void setPlayerTeam(byte playerTeam) {
		this.team = playerTeam;
	}
	public void setGold(int gold) {
		this.gold = gold;
		if (gold <= 0) {
			this.gold = 0;
		}
	}
	public String getUsernameColor() {
		return usernameColor;
	}
	public void setUsernameColor(String usernameColor) {
		this.usernameColor = usernameColor;
	}
	
}

