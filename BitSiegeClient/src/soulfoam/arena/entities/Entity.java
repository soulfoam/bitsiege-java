package soulfoam.arena.entities;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.entities.abilities.misc.StackableAbility;
import soulfoam.arena.entities.challengers.animation.AnimationHandler;
import soulfoam.arena.entities.challengers.skins.Skin;
import soulfoam.arena.entities.challengers.tooltips.ChallengerToolTip;
import soulfoam.arena.entities.challengers.underglows.Underglow;
import soulfoam.arena.main.ClientValues;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public abstract class Entity extends GameObject {

	public static final int PLAYER_STUN_PARTICLE_SIZE = 1;
	public static final int PLAYER_STUN_PARTICLE_LIFE = 7;

	protected Skin skin;
	protected Underglow underglow;

	protected ArrayList<StackableAbility> stacks = new ArrayList<StackableAbility>();
	protected HashSet<Entity> allyAssists = new HashSet<Entity>();
	protected HashSet<Entity> enemyAssists = new HashSet<Entity>();
	
	protected Random rand = new Random();

	protected int kills;
	protected int deaths;
	protected int points;
	protected int assists;

	protected int level;
	
	protected byte team;

	protected Timestamp pingRequestTime;
	protected int ping;

	protected int playerID;
	protected String username = "";
	protected Color usernameColor = Color.white;

	protected float connectionCheckTime = 7 * 1000;
	protected float connectionCheckTimer = connectionCheckTime;

	protected boolean bot;
	protected boolean dead = true;

	protected float respawnTimer;

	protected int selectedAbility;

	protected int challengerType;

	protected float oldX, oldY;
	protected float remoteX, remoteY;
	protected float serverUpdatedX, serverUpdatedY;

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
	protected float baseHealthStart;

	protected byte currentAction;

	protected byte currentDirection;
	protected byte currentVelocity;

	protected boolean isLocalPlayer;
	
	protected AnimationHandler animation;

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

	protected boolean stunned;
	protected boolean blind;

	protected boolean confused;
	protected boolean slowed;
	protected boolean invisible;
	protected float totalDamage;


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

	protected ChallengerToolTip ctt;

	protected int gold = 200;

	public abstract void update(int delta);

	public abstract void initAbilities();

	public void handleTimers(int delta) {

		animation.update(delta);

		if (moveSpeed < baseMoveSpeed) {
			slowed = true;
		} else {
			slowed = false;
		}

		if (dead) {
			Game.getGame().removeEntityAbilities(this);
		}

		if (!isLocalPlayer) {
			interpMovement(delta);
		} else {
			smoothPrediction(delta);
		}

	}

	public void interpMovement(int delta) {

		if (!dead) {

			float interpAmount = ClientValues.PLAYER_INTERP_AMOUNT;
			if (bot) {
				interpAmount = 0.015f;
			}
			float threshold = ClientValues.PLAYER_INTERP_THRESHOLD;
			float differenceX = remoteX - x;
			float differenceY = remoteY - y;

			if (differenceX > threshold || differenceX < -threshold) {
				x = remoteX;
			} else {
				x += differenceX * interpAmount * delta;
			}

			if (differenceY > threshold || differenceY < -threshold) {
				y = remoteY;
			} else {
				y += differenceY * interpAmount * delta;
			}

		} else {
			x = remoteX;
			y = remoteY;
		}

	}

	public void smoothPrediction(int delta) {

		if (!dead) {

			float interpAmount = ClientValues.LOCAL_PLAYER_INTERP_AMOUNT;
			float threshold = ClientValues.LOCAL_PLAYER_PREDICTION_THRESHOLD;

			float differenceX = remoteX - x;
			float differenceY = remoteY - y;

			if (differenceX > threshold || differenceX < -threshold) {
				x = remoteX;
			} else {
				x += differenceX * interpAmount * delta;
			}

			if (differenceY > threshold || differenceY < -threshold) {
				y = remoteY;
			} else {
				y += differenceY * interpAmount * delta;
			}

		} else {
			x = remoteX;
			y = remoteY;
		}

	}

	public StackableAbility getStack(int stackID) {

		StackableAbility[] tempList = new StackableAbility[stacks.size()];
		stacks.toArray(tempList);
		for (StackableAbility s : tempList) {
			if (s != null && s.getStackID() == stackID) {
				return s;
			}
		}

		return null;
	}

	public int getStackCount(int stackID) {
		int count = 0;
		StackableAbility[] tempList = new StackableAbility[stacks.size()];
		stacks.toArray(tempList);
		for (StackableAbility s : tempList) {
			if (s != null && s.getStackID() == stackID) {
				count++;
			}
		}

		return count;
	}

	public void consumeStack(int stackID) {
		StackableAbility[] tempList = new StackableAbility[stacks.size()];
		stacks.toArray(tempList);
		for (StackableAbility s : tempList) {
			if (s != null && s.getStackID() == stackID) {
				s.consumeStack();
			}
		}
	}

	public void consumeStack(int stackID, int stacksToConsume) {
		StackableAbility[] tempList = new StackableAbility[stacks.size()];
		stacks.toArray(tempList);
		int count = 0;
		for (StackableAbility s : tempList) {
			if (s != null && s.getStackID() == stackID) {
				if (count >= stacksToConsume) {
					break;
				}
				count++;
				s.consumeStack();
			}
		}
	}

	public Rectangle getViewPort() {
		int width = GameInfo.RES_WIDTH;
		int height = GameInfo.RES_HEIGHT;
		float vx = x - GameInfo.RES_WIDTH / 2;
		float vy = y - GameInfo.RES_HEIGHT / 2;

		if (Game.getGame().getCam().getX() <= 0) {
			vx = 0;
		}
		if (Game.getGame().getCam().getX() >= Tile.TILE_SIZE * Game.getGame().getWorld().getMap().getMapWidth()
				- GameInfo.RES_WIDTH) {
			vx = Tile.TILE_SIZE * Game.getGame().getWorld().getMap().getMapWidth() - GameInfo.RES_WIDTH;
		}
		if (Game.getGame().getCam().getY() <= 0) {
			vy = 0;
		}
		if (Game.getGame().getCam().getY() >= Tile.TILE_SIZE * Game.getGame().getWorld().getMap().getMapHeight()
				- GameInfo.RES_HEIGHT) {
			vy = Tile.TILE_SIZE * Game.getGame().getWorld().getMap().getMapHeight() - GameInfo.RES_HEIGHT;
		}

		return new Rectangle(vx, vy, width, height);
	}

	public Rectangle getView() {
		return new Rectangle(x - GameInfo.RES_WIDTH / 2, y - GameInfo.RES_HEIGHT / 2, GameInfo.RES_WIDTH,
				GameInfo.RES_HEIGHT);
	}

	public void takeDamageClient(Entity player, float damage) {

		if (!dead && player != null && team != player.team && canBeDamaged) {
			Game.getGame().getParticleSystem().addBloodParticle((int) damage, x + 3, y + 4, Color.red);
		}

		if (!dead && player == null) {
			Game.getGame().getParticleSystem().addBloodParticle((int) damage, x + 3, y + 4, Color.red);
		}
	}

	public void takeHealClient(Entity player, float healAmount) {
		if (!dead && player != null && team == player.team) {
			Game.getGame().getParticleSystem().addHealParticle((int) healAmount, x + 3, y + 4, Color.green);
		}
		if (!dead && player == null) {
			Game.getGame().getParticleSystem().addHealParticle((int) healAmount, x + 3, y + 4, Color.green);
		}
	}

	public float getRenderX() {
		return Res.roundForRendering(x - 8);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 4);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
	public Shape getBoundsCenterBody(){
		return new Rectangle(x , y + hitBoxHeight / 4, hitBoxWidth, hitBoxHeight / 2);
	}

	public Shape getBoundsLowerBody() {
		return new Rectangle(x, y + hitBoxHeight - hitBoxHeight / 2, hitBoxWidth, hitBoxHeight / 2);
	}

	public Shape getBoundsFeet() {
		return new Rectangle(x, y + hitBoxHeight - 1, hitBoxWidth, 1);
	}

	public void renderPlayerHealthBar(Graphics g) {
		if (Game.getGame().getClientSettings().isShowHealthBars()) {
			boolean render = true;

			if (invisible || dead) {
				render = false;
			}

			if (invisible) {
				if (Game.getGame().getPlayer().team == team) {
					render = true;
				}
			}
			
			Image plate = Res.UI_RESOURCE.PLATE_ALLY;
			Color opac = new Color(255, 255, 255, 255);
			
			if (Game.getGame().getPlayer().getTeam() != team) {
				plate = Res.UI_RESOURCE.PLATE_ENEMY;
			}
			if (Game.getGame().getPlayer() == this) {
				plate = Res.UI_RESOURCE.PLATE_PLAYER;
			}

			float healthBarSize = 14;
			float healthBarWidth = healthBarSize * (health / baseHealth);
			float shieldBarWidth = healthBarSize * (shield / baseShield);

			if (render) {
				plate.draw(Res.roundForRendering(x - 6), Res.roundForRendering(y - 12), opac);
				Res.UI_RESOURCE.PLATE_HEALTH_BAR.draw(Res.roundForRendering(x + 3), Res.roundForRendering(y - 9), healthBarWidth, 3, opac);
				Res.bitFont.drawString(Res.roundForRendering(x - 5), Res.roundForRendering(y - 10), level + "");
				
				if (shield > 0) {

				}
			}

		}
	}

	public void renderPlayerName(Graphics g) {
//		if (invisible && team == Game.getGame().getPlayer().team || !invisible) {
//			if (Game.getGame().getClientSettings().isShowPlayerNames() && !dead) {
//				Res.bitFont.drawString(Res.roundForRendering(getX()) - Res.getTextCenter(username) + (width/2), Res.roundForRendering(y - 12), username, healthColor);
//			}
//		}
	}

	public void renderShield(Graphics g) {
		if (shield > 0 && !invisible) {
			Res.GENERAL_RESOURCE.SHIELD_ANIMATION[0].draw(Res.roundForRendering(x - 12), Res.roundForRendering(y - 8));
		}
	}

	public Tile getTile() {
		Tile t = new Tile(0, 0, -1, 1);
		t = Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION);

		return t;
	}

	public float sortByY() {
		return y + hitBoxHeight;
	}

	public float getPowerBuffAmount() {
		return powerBuffAmount + powerBuffTempAmount;
	}

	public Skin getSkin() {
		if (skin == null) {
			skin = new Skin(this, 0);
		}
		return skin;
	}

	public Underglow getUnderglow() {
		if (underglow == null) {
			underglow = new Underglow(this, CosmeticLibrary.UNDERGLOW_NONE);
		}
		return underglow;
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

	public Random getRand() {
		return rand;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getPoints() {
		return points;
	}

	public int getAssists() {
		return assists;
	}

	public Timestamp getPingRequestTime() {
		return pingRequestTime;
	}

	public int getPing() {
		return ping;
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

	public boolean isBot() {
		return bot;
	}

	public boolean isDead() {
		return dead;
	}

	public float getRespawnTimer() {
		return respawnTimer;
	}

	public int getSelectedAbility() {
		return selectedAbility;
	}

	public int getChallengerType() {
		return challengerType;
	}

	public float getOldX() {
		return oldX;
	}

	public float getOldY() {
		return oldY;
	}

	public float getRemoteX() {
		return remoteX;
	}

	public float getRemoteY() {
		return remoteY;
	}

	public float getServerUpdatedX() {
		return serverUpdatedX;
	}

	public float getServerUpdatedY() {
		return serverUpdatedY;
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

	public float getBaseHealthStart() {
		return baseHealthStart;
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

	public boolean isLocalPlayer() {
		return isLocalPlayer;
	}

	public AnimationHandler getAnimation() {
		return animation;
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

	public boolean isStunned() {
		return stunned;
	}

	public boolean isBlind() {
		return blind;
	}

	public boolean isConfused() {
		return confused;
	}

	public boolean isSlowed() {
		return slowed;
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

	public ChallengerToolTip getChallengerToolTip() {
		return ctt;
	}

	public int getGold() {
		return gold;
	}

	public void setAbilityBasicAttack(Ability abilityBasicAttack) {
		this.abilityBasicAttack = abilityBasicAttack;
	}

	public void setCurrentAction(byte currentAction) {
		this.currentAction = currentAction;
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

	public void setAbilityBasicAttackCDTimer(float abilityBasicAttackCDTimer) {
		this.abilityBasicAttackCDTimer = abilityBasicAttackCDTimer;
	}

	public void setAbilityBasicAttackCDTime(float abilityBasicAttackCDTime) {
		this.abilityBasicAttackCDTime = abilityBasicAttackCDTime;
	}

	public void setPowerBuffTempAmount(float powerBuffTempAmount) {
		this.powerBuffTempAmount = powerBuffTempAmount;
	}

	public void setBot(boolean bot) {
		this.bot = bot;
	}

	public void setCurrentDirection(byte currentDirection) {
		this.currentDirection = currentDirection;
	}

	public void setCurrentVelocity(byte currentVelocity) {
		this.currentVelocity = currentVelocity;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTeam(byte team) {
		this.team = team;
	}

	public void setSelectedAbility(int selectedAbility) {
		this.selectedAbility = selectedAbility;
	}

	public void setRemoteX(float remoteX) {
		this.remoteX = remoteX;
	}

	public void setRemoteY(float remoteY) {
		this.remoteY = remoteY;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setLocalPlayer(boolean isLocalPlayer) {
		this.isLocalPlayer = isLocalPlayer;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setShield(float shield) {
		this.shield = shield;
	}

	public void setStunned(boolean isStunned) {
		stunned = isStunned;
	}

	public void setBlind(boolean isBlind) {
		blind = isBlind;
	}

	public void setConfused(boolean isConfused) {
		confused = isConfused;
	}

	public void setSlowed(boolean isSlowed) {
		slowed = isSlowed;
	}

	public void setInvisible(boolean isInvisible) {
		invisible = isInvisible;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public void setPing(int ping) {
		this.ping = ping;
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

	public void setBaseShield(float baseShield) {
		this.baseShield = baseShield;
	}

	public void setBaseHealth(float baseHealth) {
		this.baseHealth = baseHealth;
	}

	public void setAbility1CDTimer(float ability1cdTimer) {
		ability1CDTimer = ability1cdTimer;
	}

	public void setAbility2CDTimer(float ability2cdTimer) {
		ability2CDTimer = ability2cdTimer;
	}

	public void setAbility3CDTimer(float ability3cdTimer) {
		ability3CDTimer = ability3cdTimer;
	}

	public void setAbility4CDTimer(float ability4cdTimer) {
		ability4CDTimer = ability4cdTimer;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public Color getUsernameColor() {
		return usernameColor;
	}

	public void setUsernameColor(Color usernameColor) {
		this.usernameColor = usernameColor;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
