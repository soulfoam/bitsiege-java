package soulfoam.arenaserver.main.game;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.challengers.bots.BotPathFinding;
import soulfoam.arenaserver.entities.objectives.CapturePoint;
import soulfoam.arenaserver.entities.objectives.HealthObjective;
import soulfoam.arenaserver.entities.objectives.Objective;
import soulfoam.arenaserver.entities.objectives.SmallCapturePoint;
import soulfoam.arenaserver.main.ServerFunctions;
import soulfoam.arenaserver.main.ServerSettings;
import soulfoam.arenaserver.main.SettingManager;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.main.util.GameUtil;
import soulfoam.arenaserver.net.NetworkManager;
import soulfoam.arenaserver.net.server.GameServer;
import soulfoam.arenaserver.net.server.IDPool;
import soulfoam.arenaserver.world.World;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;


public class Game{
	
	private static Game gm = null;

	public static Game getGame() {
		if (gm == null) {
			gm = new Game();
		}

		return gm;
	}

	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	private ArrayList<Ability> abilitiesToRemove = new ArrayList<Ability>();
	private ArrayList<Ability> abilitiesToRemoveBroadcast = new ArrayList<Ability>();
	
	private ArrayList<Entity> players = new ArrayList<Entity>();
	private ArrayList<Objective> objectives = new ArrayList<Objective>();

	private IDPool idPool;
	
	private GameUtil util;

	private Random random = new Random();
	
	private boolean shouldUpdate;

	private World world;

	private GameServer server;
	private ServerFunctions serverFunctions;
	private BotPathFinding botPathFinding;

	private ServerSettings serverSettings;

	private Res res;
	
	public void init(){
		this.idPool = new IDPool();
		
		util = new GameUtil();
		
		serverSettings = new ServerSettings();
		
		world = new World();
		world.setMap(Res.MAP_RESOURCE.getMap(NetworkManager.getNetworkManager().getMapID())); 
		
		serverFunctions = new ServerFunctions();
		
		server = new GameServer(SettingManager.SERVER_PORT);
		server.start();
		
		botPathFinding = new BotPathFinding();
		botPathFinding.start();
		
//		serverFunctions.setUpObjectives();
//		serverFunctions.setupBots();
	}

	
	public void update(GameContainer gc, StateBasedGame s, int delta){

		serverFunctions.update(delta);
		
		for (Entity p : players) {
			p.update(delta);
		}

		for (Ability a : abilities) {
			a.update(delta);
		}
		
		for (Objective o : objectives){
			o.update(delta);
		}
		
		abilities.removeAll(abilitiesToRemove);
		abilitiesToRemove.clear();

	}
	
	public void removeObjective(Objective o){
		idPool.addObjectiveIDToPool(o.getObjectiveGameID());
		objectives.remove(o);
	}
	
	public Objective getObjectiveObject(int ogid){
		for (Objective objective: objectives){
			if (objective.getObjectiveGameID() == ogid){
				return objective;
			}
		}
		return null;
	}
	
	public Ability getAbilityObject(int abilityGameID){

		for (Ability a: abilities){
			if (a.getGameID() == abilityGameID){
				return a;
			}
		}
		return null;
	}
	
	public int getAbilityIndex(int abilityGameID){
		int index = 0;
		for (Ability ability: abilities){
			if (ability.getGameID() == abilityGameID){
				break;
			}
			index++;
		}
		return index;
	}
	
	public void addAbility(Ability a){
		if (a != null){
			abilities.add(a);
		}
	}
	
	public void removeAbility(Ability a, boolean broadcast){
	
		idPool.addAbilityIDToPool(a.getGameID());
		
		if (broadcast){
			abilitiesToRemoveBroadcast.add(a);
		}
		
		abilitiesToRemove.add(a);
		
	}
	
	public void removeAllAbilities(){

		for (Ability a: abilities){
			a.removeThis(false);
		}
		
	}
	
	public Entity getPlayerObject(int playerID){

		for (Entity player: players){
			if (player.getPlayerID() == playerID){
				return player;
			}
		}
		return null;
	}
	
	public Entity getPlayerObject(String username){
		for (Entity player: players){
			if (player.getUsername().equalsIgnoreCase(username)){
				return player;
			}
		}
		return null;
	}
	
	public Entity getPlayerObject(InetAddress ipAddress, int port){
		for (Entity player: players){
			if (ipAddress != null && player.getIpAddress() != null && player.getIpAddress().getHostAddress().equalsIgnoreCase(ipAddress.getHostAddress()) && player.getPort() == port){
				return player;
			}
		}
		return null;
	}
	
	public int getPlayerIndex(int playerID){
		int index = 0;

		for (Entity player: players){
			if (player != null && player.getPlayerID() == playerID){
				break;
			}
			index++;
		}
		return index;
	}
	
	public int getObjectiveIndex(int ogid){
		int index = 0;

		for (Objective objective: objectives){
			if (objective.getObjectiveGameID() == ogid){
				break;
			}
			index++;
		}
		return index;
	}
	
	public ArrayList<CapturePoint> getCapturePoints(){

		ArrayList<CapturePoint> capturePoints = new ArrayList<CapturePoint>();
		
		for (Objective objective: objectives){
			if (objective.getObjectiveTeam() == ObjectiveInfo.CAPTURE_POINT){
				capturePoints.add((CapturePoint)objective);
			}
		}
		

		return capturePoints;
	}
	
	public ArrayList<SmallCapturePoint> getSmallCapturePoints(){

		ArrayList<SmallCapturePoint> capturePoints = new ArrayList<SmallCapturePoint>();
		
		for (Objective objective: objectives){
			if (objective.getObjectiveTeam() == ObjectiveInfo.SMALL_CAPTURE_POINT){
				capturePoints.add((SmallCapturePoint)objective);
			}
		}
		

		return capturePoints;
	}
	
	public ArrayList<HealthObjective> getHealthOrbs(){

		Objective[] tempList = new Objective[objectives.size()];
		objectives.toArray(tempList);
		
		ArrayList<HealthObjective> healthorbs = new ArrayList<HealthObjective>();
		
		for (Objective objective: tempList){
			if (objective != null){
				if (objective.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE 
					|| objective.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE || objective.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUPSMALL_OBJECTIVE){
					healthorbs.add((HealthObjective)objective);
				}
			}
		}
		

		return healthorbs;
	}
	
	public void destroyGame(){

		server.getSocket().close();
		server.setSocket(null);
		server.setRunning(false);
		server = null;
		
		abilities.clear();
		abilitiesToRemove.clear();
		abilitiesToRemoveBroadcast.clear();
		players.clear();
		objectives.clear();


		botPathFinding.setRunning(false);
		botPathFinding = null;
		
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public ArrayList<Ability> getAbilitiesToRemove() {
		return abilitiesToRemove;
	}

	public ArrayList<Entity> getPlayers() {
		return players;
	}

	public ArrayList<Objective> getObjectives() {
		return objectives;
	}

	public IDPool getIdPool() {
		return idPool;
	}

	public GameUtil getUtil() {
		return util;
	}

	public Random getRandom() {
		return random;
	}

	public boolean isShouldUpdate() {
		return shouldUpdate;
	}

	public World getWorld() {
		return world;
	}

	public GameServer getServer() {
		return server;
	}

	public ServerFunctions getServerFunctions() {
		return serverFunctions;
	}

	public BotPathFinding getBotPathFinding() {
		return botPathFinding;
	}

	public ServerSettings getServerSettings() {
		return serverSettings;
	}

	public Res getRes() {
		return res;
	}

	public void setIdPool(IDPool idPool) {
		this.idPool = idPool;
	}

	public void setUtil(GameUtil util) {
		this.util = util;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public void setShouldUpdate(boolean shouldUpdate) {
		this.shouldUpdate = shouldUpdate;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void setServer(GameServer server) {
		this.server = server;
	}

	public void setServerFunctions(ServerFunctions serverFunctions) {
		this.serverFunctions = serverFunctions;
	}

	public void setBotPathFinding(BotPathFinding botPathFinding) {
		this.botPathFinding = botPathFinding;
	}

	public void setServerSettings(ServerSettings serverSettings) {
		this.serverSettings = serverSettings;
	}

	public ArrayList<Ability> getAbilitiesToRemoveBroadcast() {
		return abilitiesToRemoveBroadcast;
	}
}
