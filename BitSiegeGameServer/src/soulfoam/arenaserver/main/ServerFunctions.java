package soulfoam.arenaserver.main;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.EntityBook;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.abilities.misc.Knockback;
import soulfoam.arenaserver.entities.abilities.misc.StackableAbility;
import soulfoam.arenaserver.entities.challengers.bots.Bot;
import soulfoam.arenaserver.entities.objectives.CapturePoint;
import soulfoam.arenaserver.entities.objectives.HealthObjective;
import soulfoam.arenaserver.entities.objectives.Objective;
import soulfoam.arenaserver.entities.objectives.SmallCapturePoint;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.command.server.RemoveEntityCommand;
import soulfoam.arenaserver.main.command.server.ServerCommandHandler;
import soulfoam.arenaserver.main.command.server.SpawnAbilityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.misc.DataObject;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.main.util.GameUtil;
import soulfoam.arenaserver.world.MapLayer;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.ShopInfo;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;
import soulfoam.arenashared.main.opcode.OPCode;

public class ServerFunctions {

	private int teamDPoints;
	private int teamAPoints;

	private int gameClock;
	private int gameClockTimer;

	private boolean gameOver;
	
	private float halfTimeTime = 5 * 1000;
	private float halfTimeTimer = halfTimeTime;
	
	private int lastRoundWinner = -1;
	private int lastRoundHow = -1;

	private float gameOverTimer = 3.5f * 1000;
	
	private float roundEndTime = 3 * 1000;
	private float roundEndTimer = roundEndTime;
	
	private float packetSendTimer = 0;
	private float packetSendTime = 40;

	private float miscPacketSendTimer = 0;
	private float miscPacketSendTime = 100;
	
	private float pingSendTime = 1 * 1000;
	private float pingSendTimer = 0;
	
	private boolean hasSetupBots;
	private boolean serverFull;
	
	private int packetCount;
	
	private Random random = new Random();
	
	private ServerCommandHandler commandHandler;
	
	public ServerFunctions(){
		
		this.commandHandler = new ServerCommandHandler();
	
		gameClock = Game.getGame().getServerSettings().getGameTime();
	}
	
	public void update(int delta){
		gameClock -= delta;
		commandHandler.update(delta);

		serverHandleBotCount();
		checkForConnection(delta);
		
		if (packetSendTimer > 0) {
			packetSendTimer-= delta;
		}
		
		if (packetSendTimer <= 0){
			handleSendPacketsServer();
			packetCount++;
			packetSendTimer = packetSendTime;
		}

		if (miscPacketSendTimer > 0){
			miscPacketSendTimer-= delta;
		}
		if (miscPacketSendTimer <= 0){					
			handleSendMiscPacketsServer();
			miscPacketSendTimer = miscPacketSendTime;
		}

		if (pingSendTimer > 0){
			pingSendTimer -= delta;
		}
		
		if (pingSendTimer <= 0){
			for (Entity p : Game.getGame().getPlayers()){
				if (!p.isBot()){
					Game.getGame().getServer().sendData(OPCode.OP_PINGINGAME, p.getIpAddress(), p.getPort());
					p.setPingRequestTime(new Timestamp(System.currentTimeMillis()));
				}
			}
			pingSendTimer = pingSendTime;
		}

		
	}
	
    public void handleSendPacketsServer(){

		String playerUpdateTeamD = OPCode.OP_PLAYERUPDATE;
		String playerUpdateTeamA = OPCode.OP_PLAYERUPDATE;
		String objectiveUpdateHealthOrb = OPCode.OP_OBJECTIVEUPDATEHEALTHORB;
		String objectiveUpdateHealthOrbPlayer = OPCode.OP_OBJECTIVEUPDATEHEALTHORBPLAYER;
		String abilityRemoveUpdate = OPCode.OP_REMOVEABILITY;
		
		for (Entity p : Game.getGame().getPlayers()){

			if (p.getChallengerType() != EntityInfo.SPECTATE_PLAYER){
				if (p.getTeam() == Res.TEAM_D) {
					playerUpdateTeamD += p.getPlayerID() + "," + Res.roundForNetwork(p.getX()) + "," + Res.roundForNetwork(p.getY()) + "," + p.getCurrentDirection() + "," + p.getCurrentAction() 
					+ "," + Res.roundForNetwork(p.getHealth()) + "," + GameUtil.boolToByte(p.isStunned()) + "," + GameUtil.boolToByte(p.isBlind()) + "," 
					+ GameUtil.boolToByte(p.isInvisible()) + "," + Res.roundForNetwork(p.getShield()) + "," + GameUtil.boolToByte(p.isConfused()) + "," + p.getCurrentVelocity() 
					+ "," + GameUtil.boolToByte(p.isDead()) + "]";
				}
				if (p.getTeam() == Res.TEAM_A){
					playerUpdateTeamA += p.getPlayerID() + "," + Res.roundForNetwork(p.getX()) + "," + Res.roundForNetwork(p.getY()) + "," + p.getCurrentDirection() + "," + p.getCurrentAction() 
					+ "," + Res.roundForNetwork(p.getHealth()) + "," + GameUtil.boolToByte(p.isStunned()) + "," + GameUtil.boolToByte(p.isBlind()) + "," 
					+ GameUtil.boolToByte(p.isInvisible()) + "," + Res.roundForNetwork(p.getShield()) + "," + GameUtil.boolToByte(p.isConfused()) + "," + p.getCurrentVelocity() 
					+ "," + GameUtil.boolToByte(p.isDead()) + "]";
				}
			}
			
		}
		
		for (Objective o : Game.getGame().getObjectives()){
			if (o.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE || o.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUPSMALL_OBJECTIVE){
				objectiveUpdateHealthOrb += o.getObjectiveGameID() + "," + Res.roundForNetwork(o.getX()) + "," + Res.roundForNetwork(o.getY()) 
				+ "," + o.getObjectiveTeam() + "," + GameUtil.boolToByte(o.isBeingHeld()) + "," + o.getObjectiveHolderID() + "," + GameUtil.boolToByte(o.isNeedsRemoved()) + "]";
			}
			if (o.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE){
				objectiveUpdateHealthOrbPlayer += o.getObjectiveGameID() + "," + Res.roundForNetwork(o.getX()) + "," + Res.roundForNetwork(o.getY()) 
				+ "," + GameUtil.boolToByte(o.isBeingHeld()) + "," + o.getObjectiveHolderID() + "," + GameUtil.boolToByte(o.isNeedsRemoved()) + "]";
			}
			if (o.isNeedsRemoved()){
				Game.getGame().removeObjective(o);
			}
		}
		

		for (Ability a : Game.getGame().getAbilitiesToRemoveBroadcast()){
			abilityRemoveUpdate += a.getGameID() + "]";
		}
		
		Game.getGame().getServer().sendDataToEveryone(playerUpdateTeamD);
		Game.getGame().getServer().sendDataToEveryone(playerUpdateTeamA);
		Game.getGame().getServer().sendDataToEveryone(objectiveUpdateHealthOrb);
		Game.getGame().getServer().sendDataToEveryone(objectiveUpdateHealthOrbPlayer);

		if (Game.getGame().getAbilitiesToRemoveBroadcast().size() > 0){
			Game.getGame().getServer().sendReliableDataToEveryone(abilityRemoveUpdate);
			Game.getGame().getAbilitiesToRemoveBroadcast().clear();
		}

		packetSendTimer = packetSendTime;
		
	}	
	
	public void handleSendMiscPacketsServer(){
		
		String gameInfoUpdate = OPCode.OP_GAMEINFOUPDATE;
		String playerMiscUpdateTeamD = OPCode.OP_PLAYERUPDATEMISC;
		String playerMiscUpdateTeamA = OPCode.OP_PLAYERUPDATEMISC;
		String capturePointUpdate = OPCode.OP_OBJECTIVEUPDATECAPTUREPOINT;
		
		gameInfoUpdate += teamDPoints + "," + teamAPoints + "," + gameClock;
		
		for (Entity p : Game.getGame().getPlayers()){

			if (p.getTeam() == Res.TEAM_D){
				playerMiscUpdateTeamD += p.getPlayerID() + "," + p.getBaseHealth() + "," + Res.roundForNetwork(p.getMoveSpeed()) + "," + p.getKills() + "," 
				+ p.getDeaths() + "," + p.getCaptureScore() + "," + p.getAssists() + "," + Res.roundForNetwork(p.getAttackSpeedBuffAmount()) 
				+ "," + Res.roundForNetwork(p.getPowerBuffAmount()) + "," + Res.roundForNetwork(p.getMoveSpeedBuffAmount()) 
				+ "," + p.getGold() + "," + p.getTeam() + "," + p.getBaseShield() + "," 
				+ Math.round(p.getAbility1CDTimer()) + "," + Math.round(p.getAbility2CDTimer()) + "," + Math.round(p.getAbility3CDTimer()) + "," + Math.round(p.getAbility4CDTimer()) 
				+ "," + p.getPing() + "," + Res.twoDecimal(p.getAbilityBasicAttackCDTime()) + "]";  
			}
			if (p.getTeam() == Res.TEAM_A){
				playerMiscUpdateTeamA += p.getPlayerID() + "," + p.getBaseHealth() + "," + Res.roundForNetwork(p.getMoveSpeed()) + "," + p.getKills() + "," 
				+ p.getDeaths() + "," + p.getCaptureScore() + "," + p.getAssists() + "," + Res.roundForNetwork(p.getAttackSpeedBuffAmount()) 
				+ "," + Res.roundForNetwork(p.getPowerBuffAmount()) + "," + Res.roundForNetwork(p.getMoveSpeedBuffAmount()) 
				+ "," + p.getGold() + "," + p.getTeam() + "," + p.getBaseShield() + "," 
				+ Math.round(p.getAbility1CDTimer()) + "," + Math.round(p.getAbility2CDTimer()) + "," + Math.round(p.getAbility3CDTimer()) + "," + Math.round(p.getAbility4CDTimer()) 
				+ "," + p.getPing() + "," + Res.twoDecimal(p.getAbilityBasicAttackCDTime()) + "]";  
			}
			
		}
		
		for (Objective o : Game.getGame().getObjectives()){
			if (o.getObjectiveTeam() == ObjectiveInfo.CAPTURE_POINT || o.getObjectiveTeam() == ObjectiveInfo.SMALL_CAPTURE_POINT){
				capturePointUpdate += o.getObjectiveGameID() + "," + o.getObjectiveTeam() + "," + Res.roundForNetwork(o.getX()) + "," + Res.roundForNetwork(o.getY()) 
				+ "," + o.getHealth() + "," + o.getBaseHealth() + "," + GameUtil.boolToByte(o.isBeingHeld()) + "]";
			}
		
		}
		Game.getGame().getServer().sendDataToEveryone(gameInfoUpdate);
		Game.getGame().getServer().sendDataToEveryone(playerMiscUpdateTeamD);
		Game.getGame().getServer().sendDataToEveryone(playerMiscUpdateTeamA);
		Game.getGame().getServer().sendDataToEveryone(capturePointUpdate);
		
	}
	
	public void checkForConnection(int delta){
		
		for (Entity p : Game.getGame().getPlayers()) {

			if (p.getConnectionCheckTimer() > 0){
				p.setConnectionCheckTimer(p.getConnectionCheckTimer() - delta);
			}
			if (p.getConnectionCheckTimer() <= 0){

				if (!p.isBot()){
					if (p.isResponding()){
						p.setResponding(false);
					}
					else{
						replacePlayerWithBot(p);
						serverRemovePlayer(p);
					}
				}
				p.setConnectionCheckTimer(p.getConnectionCheckTime());
			}
			
		}
			
	}

	public void serverGivePermHealth(float amount, Entity serverPlayerBuffed){
		serverPlayerBuffed.setBaseHealth(serverPlayerBuffed.getBaseHealth() + amount);
		serverPlayerBuffed.setHealthBuffAmount(serverPlayerBuffed.getHealthBuffAmount() + amount);
	}
	
	public void serverGivePermMoveSpeed(float amount, Entity serverPlayerBuffed){
		serverPlayerBuffed.setBaseMoveSpeed(serverPlayerBuffed.getBaseMoveSpeed() + amount);
		serverPlayerBuffed.setMoveSpeed(serverPlayerBuffed.getMoveSpeed() + amount);
		serverPlayerBuffed.setMoveSpeedBuffAmount(serverPlayerBuffed.getMoveSpeedBuffAmount() + amount);
	}
	
	public void serverGivePermPower(float amount, Entity serverPlayerBuffed){
		serverPlayerBuffed.setPowerBuffAmount(serverPlayerBuffed.getPowerBuffAmount() + amount);
	}
	
	public void serverGivePermAttackSpeed(float amount, Entity serverPlayerBuffed){
		serverPlayerBuffed.setAttackSpeedBuffAmount(serverPlayerBuffed.getAttackSpeedBuffAmount() + amount);
	}
	
	public void serverSendNotification(int messageType, Entity p1, Entity p2){
		if (messageType == Res.MESSAGE_PLAYERKILLEDPLAYER){
			String notificationPacket = OPCode.OP_NOTIFICATIONADD;
			notificationPacket += Game.getGame().getIdPool().getAvailableNotificationID() + "," + Res.MESSAGE_PLAYERKILLEDPLAYER + "," + p1.getPlayerID() + "," + p2.getPlayerID();
			Game.getGame().getServer().sendReliableDataToEveryone(notificationPacket);
		}
	}
	
	public void serverSpawnCapturePoint(float x, float y, byte objectiveTeam){
		Objective capturePoint = null;
		if (objectiveTeam == ObjectiveInfo.CAPTURE_POINT){
			capturePoint = new CapturePoint(x, y);
		}
		if (objectiveTeam == ObjectiveInfo.SMALL_CAPTURE_POINT){
			capturePoint = new SmallCapturePoint(x, y);
		}
		capturePoint.setObjectiveGameID(Game.getGame().getIdPool().getAvailableObjectiveID());
		Game.getGame().getObjectives().add(capturePoint);
	}
	
	public void serverSpawnHealthOrb(float x, float y, byte orbType){
		Objective healthPickup = new HealthObjective(x, y, orbType);
		healthPickup.setObjectiveGameID(Game.getGame().getIdPool().getAvailableObjectiveID());
		Game.getGame().getObjectives().add(healthPickup);
	}
	
	public void serverRemovePlayer(Entity player){
		commandHandler.addCommand(new RemoveEntityCommand(player.getPlayerID()));
	}
	
	public void serverBroadcastPlayers(){
		String addPlayersPacket = OPCode.OP_ADDPLAYERS;
		
		for (Entity p: Game.getGame().getPlayers()){
			addPlayersPacket += p.getPlayerID() + "," + Res.roundForNetwork(p.getX()) + "," + Res.roundForNetwork(p.getY()) + "," + p.getCurrentDirection() + ","  
			+ p.getChallengerType() + "," + p.getTeam() + "," + p.getUsername() + "," + p.getSkinID() + "," + p.getUnderglowID() + "," + p.getUsernameColor() + "]";
			
		}
		
		for (Entity p: Game.getGame().getPlayers()){
			if (!p.isBot()){
				Game.getGame().getServer().sendReliableData(addPlayersPacket, p.getIpAddress(), p.getPort());
			}
		}
	}

	public void serverBuyItem(Entity player, int itemID){

		int itemPrice = 0;
		if (itemID == 0) itemPrice = ShopInfo.HP_PRICE;
		if (itemID == 1) itemPrice = ShopInfo.POWER_PRICE;
		if (itemID == 2) itemPrice = ShopInfo.ATTACKSPEED_PRICE;
		if (itemID == 3) itemPrice = ShopInfo.MOVESPEED_PRICE;
		if (player.getGold() >= itemPrice){
			player.setGold(player.getGold() - itemPrice);
			if (itemID == 0){
				serverGivePermHealth(ShopInfo.HP_ADD_VALUE, player);
				player.takeHeal(ShopInfo.HP_ADD_VALUE);
			}
			if (itemID == 1){
				serverGivePermPower(ShopInfo.POWER_ADD_VALUE, player);
			}
			if (itemID == 2 && !player.isMaxAttackSpeed()){
				serverGivePermAttackSpeed(ShopInfo.ATTACKSPEED_ADD_VALUE, player);
			}
			if (itemID == 3 && !player.isMaxMoveSpeed()){
				serverGivePermMoveSpeed(ShopInfo.MOVESPEED_ADD_VALUE, player);
			}
		}
			
		

	}
	
	public void serverBuyRandomItemForBot(Entity bot){
		if (bot.getChallengerType() == Bot.KNIGHTCLASSBOT){
			int random = Res.randInt(0, 3);
			if (random == 0){
				serverBuyItem(bot, 0);
			}
			if (random == 1){
				serverBuyItem(bot, 1);
			}
			if (random == 2){
				serverBuyItem(bot, 2);
			}
			if (random == 3){
				serverBuyItem(bot, 3);
			}
		}
		
		if (bot.getChallengerType() == Bot.WARLOCKCLASSBOT){
			int random = Res.randInt(0, 3);
			if (random == 0){
				serverBuyItem(bot, 0);
			}
			if (random == 1 || random == 2){
				serverBuyItem(bot, 1);
			}
			if (random == 3){
				serverBuyItem(bot, 3);
			}
		}
		
		if (bot.getChallengerType() == Bot.ARCHERCLASSBOT){
			int random = Res.randInt(0, 4);
			if (random == 0){
				serverBuyItem(bot, 0);
			}
			if (random == 1 || random == 2){
				serverBuyItem(bot, 2);
			}
			if (random == 3){
				serverBuyItem(bot, 1);
			}
			if (random == 4){
				serverBuyItem(bot, 3);
			}
		}
		
		if (bot.getChallengerType() == Bot.CLERICCLASSBOT){
			int random = Res.randInt(0, 3);
			if (random == 0){
				serverBuyItem(bot, 0);
			}
			if (random == 1 || random == 2){
				serverBuyItem(bot, 1);
			}
			if (random == 3){
				serverBuyItem(bot, 3);
			}
		}
		
		if (bot.getChallengerType() == Bot.ILLUSIONISTCLASSBOT){
			int random = Res.randInt(0, 3);
			if (random == 0 || random == 1){
				serverBuyItem(bot, 1);
			}
			if (random == 2){
				serverBuyItem(bot, 0);
			}
			if (random == 3){
				serverBuyItem(bot, 3);
			}
		}
		
		if (bot.getChallengerType() == Bot.VOIDLORDCLASSBOT){
			int random = Res.randInt(0, 3);
			if (random == 0){
				serverBuyItem(bot, 0);
			}
			if (random == 1){
				serverBuyItem(bot, 1);
			}
			if (random == 2){
				serverBuyItem(bot, 2);
			}
			if (random == 3){
				serverBuyItem(bot, 3);
			}
		}
		
		if (bot.getChallengerType() == Bot.WATERQUEENCLASSBOT){
			int random = Res.randInt(0, 3);
			if (random == 0 || random == 1){
				serverBuyItem(bot, 1);
			}
			if (random == 2){
				serverBuyItem(bot, 0);
			}
			if (random == 3){
				serverBuyItem(bot, 3);
			}
		}
		
		if (bot.getChallengerType() == Bot.SHAMANCLASSBOT){
			int random = Res.randInt(0, 1);
			if (random == 0){
				serverBuyItem(bot, 2);
			}
			if (random == 1){
				serverBuyItem(bot, 0);
			}
		}
	}
	
	
	public void serverHandleBotCount(){

		if (hasSetupBots){

			ArrayList<Entity> teamDList = new ArrayList<Entity>();
			ArrayList<Entity> teamAList = new ArrayList<Entity>();

			
			for (Entity p : Game.getGame().getPlayers()){

				if (p.getTeam() == Res.TEAM_D){
					teamDList.add(p);
				}
				else if (p.getTeam() == Res.TEAM_A){
					teamAList.add(p);
				}
				
			}
				

			if (teamDList.size() > 4){
				int botCount = 0;
				//System.out.println("HAD TO MANY PLAYERS ON TEAM 0 : " + teamDList.size());
				for (Entity b : teamDList){
					if (b.isBot()){
						serverRemovePlayer(b);
						botCount++;
						return;
					}
				}
				if (botCount == 0){
					Entity latest = Game.getGame().getUtil().getLastJoinedServerPlayer(Res.TEAM_D);
					if (latest != null){
						serverRemovePlayer(latest);
					}
				}
			}
			
			if (teamAList.size() > 4){

				int botCount = 0;
				//System.out.println("HAD TO MANY PLAYERS ON TEAM 1 : " + teamAList.size());
				for (Entity b : teamAList){
					if (b.isBot()){
						serverRemovePlayer(b);
						botCount++;
						return;
					}
					if (botCount == 0){
						Entity latest = Game.getGame().getUtil().getLastJoinedServerPlayer(Res.TEAM_A);
						if (latest != null){
							serverRemovePlayer(latest);
						}
					}
				}
			}
			
			
			if (teamDList.size() < 4){
	
				//System.out.println("NOT ENOUGH BOTS ON TEAM 0 : " + teamDList.size());
				spawnBot(Res.TEAM_D, Game.getGame().getUtil().getBotNotOnTeam(Res.TEAM_D), null);
			}

			if (teamAList.size() < 4){
				//System.out.println("NOT ENOUGH BOTS ON TEAM 1 : " + teamAList.size());
				spawnBot(Res.TEAM_A, Game.getGame().getUtil().getBotNotOnTeam(Res.TEAM_A), null);
			}
			
			if (teamDList.size() == 4){

				ArrayList<Integer> players = new ArrayList<Integer>();
				ArrayList<Integer> bots = new ArrayList<Integer>();
				ArrayList<Integer> playersToBots = new ArrayList<Integer>();
				
				ArrayList<Entity> duplicatePlayers = new ArrayList<Entity>();
				
				for (Entity p : teamDList){
					if (p.isBot()){
						bots.add(p.getChallengerType());
					}
					else{
						players.add(p.getChallengerType());
					}
				}
				
				///Player to Player check
				for (Entity p : teamDList){
					for (Integer i : GameUtil.findDuplicateInteger(players)){
						if (p.getChallengerType() == i){
							duplicatePlayers.add(p);
						}
					}
					
				}
				for (Entity p : duplicatePlayers){
					if (p == Game.getGame().getUtil().getLatestJoined(duplicatePlayers)){
						System.out.println("There were duplicate players! Removed: " + p.getUsername());
						serverRemovePlayer(p);
					}
				}
				
				duplicatePlayers.clear();

				
				///Player to bot check
				for (Integer n : players){
					playersToBots.add(Game.getGame().getUtil().getBotIDFromPlayerID(n));
				}
				for (Integer n : bots){
					playersToBots.add(n);
				}
				
				for (Entity p : teamDList){
					for (Integer i : GameUtil.findDuplicateInteger(playersToBots)){
						if (p.getChallengerType() == i){
							duplicatePlayers.add(p);
						}
					}
					
				}
				for (Entity p : duplicatePlayers){
					if (p == Game.getGame().getUtil().getLatestJoined(duplicatePlayers)){
						serverRemovePlayer(p);
					}
				}

				duplicatePlayers.clear();
			}
			
			if (teamAList.size() == 4){

				ArrayList<Integer> players = new ArrayList<Integer>();
				ArrayList<Integer> bots = new ArrayList<Integer>();
				ArrayList<Integer> playersToBots = new ArrayList<Integer>();
				
				ArrayList<Entity> duplicatePlayers = new ArrayList<Entity>();
				
				for (Entity p : teamAList){
					if (p.isBot()){
						bots.add(p.getChallengerType());
					}
					else{
						players.add(p.getChallengerType());
					}
				}
				
				///Player to Player check
				for (Entity p : teamAList){
					for (Integer i : GameUtil.findDuplicateInteger(players)){
						if (p.getChallengerType() == i){
							duplicatePlayers.add(p);
						}
					}
					
				}
				for (Entity p : duplicatePlayers){
					if (p == Game.getGame().getUtil().getLatestJoined(duplicatePlayers)){
						System.out.println("There were duplicate players! Removed: " + p.getUsername());
						serverRemovePlayer(p);
					}
				}
				
				duplicatePlayers.clear();

				
				///Player to bot check
				for (Integer n : players){
					playersToBots.add(Game.getGame().getUtil().getBotIDFromPlayerID(n));
				}
				for (Integer n : bots){
					playersToBots.add(n);
				}
				
				for (Entity p : teamAList){
					for (Integer i : GameUtil.findDuplicateInteger(playersToBots)){
						if (p.getChallengerType() == i){
							duplicatePlayers.add(p);
						}
					}
					
				}
				for (Entity p : duplicatePlayers){
					if (p == Game.getGame().getUtil().getLatestJoined(duplicatePlayers)){
						serverRemovePlayer(p);
					}
				}

				duplicatePlayers.clear();
			}
		}
	}
	
	public void setupBots(){
		if (Game.getGame().getServerSettings().spawnBots()){
			ArrayList<Integer> TEAM_DBots = new ArrayList<Integer>();
			ArrayList<Integer> TEAM_ABots = new ArrayList<Integer>();
			
			int TEAM_DSize = 4 - Game.getGame().getUtil().getTeamSize(Res.TEAM_D);
			int TEAM_ASize = 4 - Game.getGame().getUtil().getTeamSize(Res.TEAM_A);
			
			TEAM_DBots = Game.getGame().getUtil().getRandomTeam(TEAM_DSize, Res.TEAM_D);
			TEAM_ABots = Game.getGame().getUtil().getRandomTeam(TEAM_ASize, Res.TEAM_A); 

			for (int botType : TEAM_DBots){
				spawnBot(Res.TEAM_D, botType, null);
			}
			for (int botType : TEAM_ABots){
				spawnBot(Res.TEAM_A, botType, null);
			}

			hasSetupBots = true;
		}
	}

	public void spawnBot(byte botTeam, int botChallenger, Entity replacedPlayer){
		
		Entity bot;
		String botName = Game.getGame().getUtil().returnBotName(botChallenger);
		
		if (botTeam == Res.TEAM_D){
			bot = EntityBook.getChallengerByID(botChallenger, Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_D, true), Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_D, false), 0, -1);
		}
		else{
			bot = EntityBook.getChallengerByID(botChallenger, Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_A, true), Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_A, false), 0, -1);
		}
		
		bot.setPlayerID(Game.getGame().getIdPool().getAvailablePlayerID());
		bot.setPlayerTeam(botTeam);
		bot.setUsername(botName);
		bot.setIpAddress(null);
		bot.setPort(-1);
		
		if (replacedPlayer != null){
			bot.setGold(replacedPlayer.goldFromStats() + replacedPlayer.getGold());
			bot.setDead(replacedPlayer.isDead());
		}
		
		Game.getGame().getPlayers().add(bot);
		
		bot.setJoinedTimeStamp(new Timestamp(System.currentTimeMillis()));
		
	}
	
	public void replacePlayerWithBot(Entity player){
	//	spawnBot(player.playerTeam, Game.getGame().getUtil().getBotNotOnTeam(player.playerTeam), player);

	}
	
	public void replaceBotWithPlayer(Entity player){
		if (!player.isBot() && player.getChallengerType() != EntityInfo.SPECTATE_PLAYER){
			
			Entity botToRemove = null;
			
			ArrayList<Entity> teamBots = new ArrayList<Entity>();
			
			for (Entity b : Game.getGame().getPlayers()){
				if (b.isBot() && b.getTeam() == player.getTeam()){
					teamBots.add(b);
				}
			}
			
		
			for (int i = 0; i < teamBots.size(); i++) {
				if (teamBots.get(i).getChallengerType() == Game.getGame().getUtil().getBotIDFromPlayerID(player.getChallengerType())){
					botToRemove = teamBots.get(i);
				}
				if (i == teamBots.size() - 1 && botToRemove == null){
					botToRemove = teamBots.get(i);
				}	
			}
			
			if (botToRemove != null){

				player.setGold(botToRemove.goldFromStats() + botToRemove.getGold());
				
				player.setDead(botToRemove.isDead());
				
				serverRemovePlayer(botToRemove);

			}
		}			
		
	}
	
	public void resetPlayers(){
		for (Entity p: Game.getGame().getPlayers()){
			p.setDead(false);
			p.setHealth(p.getBaseHealth());
			p.setShield(0);
			p.setBaseShield(0);
			p.setMoveSpeed(p.getBaseMoveSpeed());
			p.setCurrentDirection(EntityInfo.DIR_DOWN);
			p.setSlowed(false);
			p.setBlind(false);
			p.setStunned(false);
			p.setInvisible(false);
			p.setConfused(false);
			p.setAbsorbDamageType(Entity.ABSORB_OFF);
			p.setAbsorbPercentage(0);
			p.setConfusedTimer(0);
			p.setBlindTimer(0);
			p.setSlowTimer(0);
			p.setSlowAmount(0);
			p.setStunTimer(0);
			p.setAbilityBasicAttackCDTimer(EntityInfo.getAttackSpeedTimer(EntityInfo.getAttackSpeed(p.getAbilityBasicAttackCDTime(), p.getAttackSpeedBuffAmount())));
			p.setAbility1CDTimer(0);
			p.setAbility2CDTimer(0);
			p.setAbility3CDTimer(0);
			p.setAbility4CDTimer(0);
			p.setCanBeDamaged(true);
			p.setCanBeStunned(true);
			p.setCanBeSlowed(true);
			p.setCanBeBlinded(true);
			p.setCanBeConfused(true);
			p.setCanBeKnockedBack(true);
			p.setAutoAttacking(false);
			p.getStacks().clear();

			if (!p.isBot()){
				if (p.getTeam() == Res.TEAM_D){
					if (!Game.getGame().getWorld().getMap().getTEAMD_SPAWN().contains(Game.getGame().getWorld().getTile(p.getX(), p.getY()))){
						MoveEntityCommand smec = new MoveEntityCommand(p.getPlayerID(), Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_D, true), Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_D, false));
						commandHandler.addCommand(smec);
					}
				}
				if (p.getTeam() == Res.TEAM_A){
					if (!Game.getGame().getWorld().getMap().getTEAMA_SPAWN().contains(Game.getGame().getWorld().getTile(p.getX(), p.getY()))){
						MoveEntityCommand smec = new MoveEntityCommand(p.getPlayerID(), Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_A, true), Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_A, false));
						commandHandler.addCommand(smec);
					}
				}
			}
			
		}
		
	}

	public void setUpObjectives(){

		Game.getGame().getObjectives().clear();
		for (DataObject data : Game.getGame().getWorld().getMap().getOBJECTIVES_TO_SPAWN()){
			if (data.team == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE){
				serverSpawnHealthOrb(data.x, data.y, ObjectiveInfo.HEALTHPICKUP_OBJECTIVE);
			}
			if(data.team == ObjectiveInfo.CAPTURE_POINT || data.team == ObjectiveInfo.SMALL_CAPTURE_POINT){
				serverSpawnCapturePoint(data.x, data.y, data.team);
			}
		}
		
	}
	
	public void manageBots(){
		
		for (Entity p: Game.getGame().getPlayers()){
			if (p.isBot()){
//					Bot b = (Bot) p;
//					if (b.targetPlayer != null){
//						System.out.println(b.username + " IS BROKE AND FUCKIN TARGETING " + b.targetPlayer.username);
//					}

				if (p.getTeam() == Res.TEAM_D){
					if (!Game.getGame().getWorld().getMap().getTEAMD_SPAWN().contains(Game.getGame().getWorld().getTile(p.getX(), p.getY(), MapLayer.INFORMATION))){
						p.setX(Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_D, true));
						p.setY(Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_D, false));
						p.setOldX(p.getX());
						p.setOldY(p.getY());
					}
				}
				if (p.getTeam() == Res.TEAM_A){
					if (!Game.getGame().getWorld().getMap().getTEAMA_SPAWN().contains(Game.getGame().getWorld().getTile(p.getX(), p.getY(), MapLayer.INFORMATION))){
						p.setX(Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_A, true));
						p.setY(Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_A, false));
						p.setOldX(p.getX());
						p.setOldY(p.getY());
					}
				}
				p.setCurrentAction(EntityInfo.IDLE);
				p.setCurrentDirection(EntityInfo.DIR_DOWN);
				Bot bot = (Bot) p;
				bot.resetSelf();
				
			}
			
		}
	
	}
	
	public void resetObjectives(){
		resetHealthOrbs();
		resetCapturePoints();
	}
	
	public void resetCapturePoints(){
		for (CapturePoint co : Game.getGame().getCapturePoints()){
			co.setHealth(0);
			co.getPlayersCapturing().clear();
			co.setBeingHeld(false);
		}
		
		for (SmallCapturePoint co : Game.getGame().getSmallCapturePoints()){
			co.setHealth(0);
			co.getPlayersCapturing().clear();
			co.setBeingHeld(false);
		}
	}
	
	public void resetHealthOrbs(){

		for (HealthObjective ho : Game.getGame().getHealthOrbs()){
			ho.setBeingHeld(false);
			ho.setObjectiveHolderID(-1);
			ho.respawnTimer = ho.respawnTime;
			if (ho.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE){
				ho.setNeedsRemoved(true);
			}
		}
		
	}
	
	public void resetStatsForHalfTime(){

		for (Entity e : Game.getGame().getPlayers()){
			e.setBaseHealth(e.getBaseHealth() - e.getHealthBuffAmount());
			e.setBaseMoveSpeed(e.getBaseMoveSpeed() - e.getMoveSpeedBuffAmount());
			e.setHealthBuffAmount(0);
			e.setAttackSpeedBuffAmount(0);
			e.setMoveSpeedBuffAmount(0);
			e.setPowerBuffAmount(0);

			e.setGold(200);
		}
		
		
	}
	
	public void sendAbilityMovementUpdate(Ability a, boolean interp){
		if (a != null){
			Game.getGame().getServer().sendDataToEveryone(OPCode.OP_ABILITYPOSITIONUPDATE + a.getGameID() + "," + a.getAbilityID() + "," + Res.roundForNetwork(a.getX()) 
			+ "," + Res.roundForNetwork(a.getY()) + "," + Res.roundForNetwork(a.getDx()) + "," + Res.roundForNetwork(a.getDy()) + "," + interp);
		}
	}
	
	public void sendAbilityVelocityAdd(int playerID, int abilityID, int gameID, float x, float y, float dx, float dy){
		commandHandler.addCommand(new SpawnAbilityCommand(playerID, abilityID, gameID, x, y, dx, dy));
		
	}
	
	public void sendAbilityAdd(int playerID, int abilityID, int gameID, float x, float y){
		commandHandler.addCommand(new SpawnAbilityCommand(playerID, abilityID, gameID, x, y));
	}
	
	public void removeEntityAbilities(Entity player){
		for (Ability a : Game.getGame().getAbilities()) {
			if (a.getPlayer().getPlayerID() == player.getPlayerID()){
				a.removeThis(false);
			}
		}
	}

	public int getTeamDPoints() {
		return teamDPoints;
	}

	public int getTeamAPoints() {
		return teamAPoints;
	}

	public int getGameClock() {
		return gameClock;
	}

	public int getGameClockTimer() {
		return gameClockTimer;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public float getHalfTimeTime() {
		return halfTimeTime;
	}

	public float getHalfTimeTimer() {
		return halfTimeTimer;
	}

	public int getLastRoundWinner() {
		return lastRoundWinner;
	}

	public int getLastRoundHow() {
		return lastRoundHow;
	}

	public float getGameOverTimer() {
		return gameOverTimer;
	}

	public float getRoundEndTime() {
		return roundEndTime;
	}

	public float getRoundEndTimer() {
		return roundEndTimer;
	}

	public float getPacketSendTimer() {
		return packetSendTimer;
	}

	public float getPacketSendTime() {
		return packetSendTime;
	}

	public float getMiscPacketSendTimer() {
		return miscPacketSendTimer;
	}

	public float getMiscPacketSendTime() {
		return miscPacketSendTime;
	}

	public float getPingSendTime() {
		return pingSendTime;
	}

	public float getPingSendTimer() {
		return pingSendTimer;
	}

	public boolean isHasSetupBots() {
		return hasSetupBots;
	}

	public boolean isServerFull() {
		return serverFull;
	}

	public int getPacketCount() {
		return packetCount;
	}

	public Random getRandom() {
		return random;
	}

	public ServerCommandHandler getCommandHandler() {
		return commandHandler;
	}

	public void setTeamDPoints(int teamDPoints) {
		this.teamDPoints = teamDPoints;
	}

	public void setTeamAPoints(int teamAPoints) {
		this.teamAPoints = teamAPoints;
	}

	public void setGameClock(int gameClock) {
		this.gameClock = gameClock;
	}

	public void setGameClockTimer(int gameClockTimer) {
		this.gameClockTimer = gameClockTimer;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setHalfTimeTime(float halfTimeTime) {
		this.halfTimeTime = halfTimeTime;
	}

	public void setHalfTimeTimer(float halfTimeTimer) {
		this.halfTimeTimer = halfTimeTimer;
	}

	public void setLastRoundWinner(int lastRoundWinner) {
		this.lastRoundWinner = lastRoundWinner;
	}

	public void setLastRoundHow(int lastRoundHow) {
		this.lastRoundHow = lastRoundHow;
	}

	public void setGameOverTimer(float gameOverTimer) {
		this.gameOverTimer = gameOverTimer;
	}

	public void setRoundEndTime(float roundEndTime) {
		this.roundEndTime = roundEndTime;
	}

	public void setRoundEndTimer(float roundEndTimer) {
		this.roundEndTimer = roundEndTimer;
	}

	public void setPacketSendTimer(float packetSendTimer) {
		this.packetSendTimer = packetSendTimer;
	}

	public void setPacketSendTime(float packetSendTime) {
		this.packetSendTime = packetSendTime;
	}

	public void setMiscPacketSendTimer(float miscPacketSendTimer) {
		this.miscPacketSendTimer = miscPacketSendTimer;
	}

	public void setMiscPacketSendTime(float miscPacketSendTime) {
		this.miscPacketSendTime = miscPacketSendTime;
	}

	public void setPingSendTime(float pingSendTime) {
		this.pingSendTime = pingSendTime;
	}

	public void setPingSendTimer(float pingSendTimer) {
		this.pingSendTimer = pingSendTimer;
	}

	public void setHasSetupBots(boolean hasSetupBots) {
		this.hasSetupBots = hasSetupBots;
	}

	public void setServerFull(boolean serverFull) {
		this.serverFull = serverFull;
	}

	public void setPacketCount(int packetCount) {
		this.packetCount = packetCount;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public void setCommandHandler(ServerCommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
	
}
