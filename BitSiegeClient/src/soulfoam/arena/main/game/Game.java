package soulfoam.arena.main.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.entities.Camera;
import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.entities.abilities.AbilityBook;
import soulfoam.arena.entities.gameshop.GameShop;
import soulfoam.arena.entities.objectives.Objective;
import soulfoam.arena.main.ClientEngine;
import soulfoam.arena.main.ClientFunctions;
import soulfoam.arena.main.ClientSettings;
import soulfoam.arena.main.InputHandler;
import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.SoundSystem;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.gfx.HUDDisplay;
import soulfoam.arena.main.gfx.ParticleSystem;
import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GameUtil;
import soulfoam.arena.net.client.GameClient;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arena.world.World;
import soulfoam.arena.world.editor.MapEditor;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.opcode.OPCode;

public class Game {

	private static Game gm = null;

	public static Game getGame() {
		if (gm == null) {
			gm = new Game();
		}

		return gm;
	}

	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	private ArrayList<Ability> abilitiesToRemove = new ArrayList<Ability>();
	
	private ArrayList<Entity> players = new ArrayList<Entity>();
	private ArrayList<Objective> objectives = new ArrayList<Objective>();
	
	private GameUtil util;

	private StateBasedGame s;

	private boolean inGame;
	private boolean loaded;

	///////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

	private World world;
	private HUDDisplay hudDisplay;
	private SoundSystem soundSystem;
	private GameShop gameShop;
	private MapEditor mapEditor;

	private ParticleSystem particleSystem;

	private boolean showShop;

	private GameClient client;
	private ClientFunctions clientFunctions;

	private Entity player;

	private ClientSettings clientSettings;

	private InputHandler input;
	private Camera cam;

	public void init(GameContainer gc, StateBasedGame s) throws SlickException {

		gc.setMouseCursor("crosshair.png", 16, 16);
		
		util = new GameUtil();

		world = new World();
		world.setMap(Res.MAP_RESOURCE.getMap(World.MAP_ID));

		gameShop = new GameShop();

		clientFunctions = new ClientFunctions();

		clientSettings = new ClientSettings();

		client = new GameClient(PregameLobbyManager.getLobby().getServerIP(), PregameLobbyManager.getLobby().getServerPort());
		client.start();
		
		client.sendReliableData(OPCode.OP_SPAWNREQUEST + LobbyManager.getManager().getUserAccount().getID());
		
		particleSystem = new ParticleSystem();

		cam = new Camera(0, 0);
		hudDisplay = new HUDDisplay(gc);
		input = new InputHandler(gc);
		input.clearAllInput();

		mapEditor = new MapEditor();

		inGame = true;

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {

		this.s = s;

		if (!loaded) {
			clientFunctions.getCommandHandler().update(delta);
			clientFunctions.checkForConnection(s, delta);
			return;
		}
		
		clientFunctions.update(gc, s, delta);
		
		for (Entity p : players) {
			p.update(delta);
			p.getUnderglow().update(delta);
		}

		checkSoundEffects();

		input.update(delta, gc);

		mapEditor.update(gc, delta);

		for (Ability a : abilities) {
			a.update(delta);
			a.handleInterpTimer(delta);
			if (a.needsInterp()) {
				a.interpMovement(delta);
			}
		}
		
		abilities.removeAll(abilitiesToRemove);
		abilitiesToRemove.clear();

		Objective[] tempListObjectives = new Objective[objectives.size()];
		objectives.toArray(tempListObjectives);

		for (Objective o : tempListObjectives) {
			o.update(delta);
			o.interpolateMovement(delta);
		}

		particleSystem.update(delta);

		hudDisplay.update(gc, s, delta);
		gameShop.update(gc, delta);
		world.update(delta);
		if (!soundSystem.currentBackgroundMusic.playing()) {
			soundSystem.chooseRandomBackgroundMusic();
		}

		clientSettings.update(delta);

		cam.update(delta);

	}

	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
		
		if (!loaded) {
			return;
		}

		g.translate(-cam.getX(), -cam.getY());

		if (hudDisplay.getScreenShake().isShaking) {
			g.translate(Res.roundForRendering(-hudDisplay.getScreenShake().getRumbleX()),
					Res.roundForRendering(-hudDisplay.getScreenShake().getRumbleY()));
		}
		
		world.render(g);
		clientSettings.render(g);

		if (player.isBlind() && !player.isDead()) {
			g.setColor(new Color(255, 255, 255, 245));
			g.fillRect(player.getX() - GameInfo.RES_WIDTH, player.getY() - GameInfo.RES_HEIGHT, 1000, 1000);
		}

		g.translate(cam.getX(), cam.getY());

		mapEditor.render(gc, g);

		renderOverlay(gc, g);
		
		if (SettingManager.CONFIG_SHOWFPS) {
			Res.bitFont.drawString(GameInfo.RES_WIDTH - 14, 1, "" + gc.getFPS(), Color.yellow);
		}
	}
	
	public void renderOverlay(GameContainer gc, Graphics g){
		
		if (!loaded) {
			return;
		}

		if (hudDisplay.showHUD()) {
			hudDisplay.render(g);
//			hudDisplay.renderScreenShake(g);
//			hudDisplay.renderTeamHUD(g);
//			hudDisplay.renderMap(g);
//			hudDisplay.renderNotificationLog(g);
//			hudDisplay.renderHotbar(g);
//			hudDisplay.renderStatHUD(g, gc);
//			hudDisplay.renderGoldHUD(g);
//			hudDisplay.renderScoreHUD(g, gc);
//			hudDisplay.renderTimerHUD(g, gc);
			
			hudDisplay.renderDeadScreen(g);
			if (showShop) {
				gameShop.render(g, gc);
			}

			hudDisplay.renderChat(gc, g);

			if (input.isToolTipsPressed()) {
				player.getChallengerToolTip().render(g);
			}

			if (input.isScoreboardPressed()) {
				hudDisplay.renderScoreboardHUD(g, gc);
			}

		}

		hudDisplay.showAfterGameScreen(g);

		if (input.isEscapePressed()) {
			hudDisplay.renderEscapeMenu(gc, g);
		}
	}

	public Entity getPlayerObject(int playerID) {

		for (Entity player : players) {
			if (player.getPlayerID() == playerID) {
				return player;
			}
		}
		return null;
	}

	public int getPlayerIndex(int playerID) {
		int index = 0;

		for (Entity player : players) {
			if (player.getPlayerID() == playerID) {
				break;
			}
			index++;
		}
		return index;
	}

	public Objective getObjectiveObject(int ogid) {
		Objective[] tempList = new Objective[objectives.size()];
		objectives.toArray(tempList);
		for (Objective objective : tempList) {
			if (objective != null && objective.getObjectiveGameID() == ogid) {
				return objective;
			}
		}
		return null;
	}

	public int getObjectiveIndex(int ogid) {
		int index = 0;
		Objective[] tempList = new Objective[objectives.size()];
		objectives.toArray(tempList);
		for (Objective objective : tempList) {
			if (objective != null && objective.getObjectiveGameID() == ogid) {
				break;
			}
			index++;
		}
		return index;
	}

	public Ability getAbilityObject(int abilityGameID) {
		for (Ability a : abilities) {
			if (a.getGameID() == abilityGameID) {
				return a;
			}
		}
		return null;
	}

	public int getAbilityIndex(int abilityGameID) {
		int index = 0;
		for (Ability ability : abilities) {
			if (ability != null && ability.getGameID() == abilityGameID) {
				break;
			}
			index++;
		}
		return index;
	}

	public Ability getAbilityByID(int abilityID, int gameID, float x, float y, float dx, float dy, Entity player) {
		Ability a = AbilityBook.getAbilityByID(abilityID, gameID, x, y, dx, dy, player);
		if (a != null) {
			a.initForGame();
		}
		return a;
	}

	public void addAbility(Ability a) {
		if (a != null) {
			abilities.add(a);
		}
	}

	public void removeAbility(Ability a) {

		if (a.loopSound()) {
			a.getSoundEffect().stop();
		}
		
		abilitiesToRemove.add(a);

	}

	public void removeEntityAbilities(Entity player) {
		for (Ability a : abilities) {

			if (a.getPlayer().getPlayerID() == player.getPlayerID()) {
				a.removeThis();
			}
			
		}
	}

	public void removeAllAbilities() {
		for (Ability a : abilities) {
			a.removeThis();
		}
	}

	public void checkSoundEffects() {
		for (Ability a : abilities) {
			if (cam.getCurrentPlayer() != null) {
				if (a.getBounds().intersects(cam.getCurrentPlayer().getViewPort())
						|| cam.getCurrentPlayer().getViewPort().contains(a.getBounds())) {
					a.playSoundEffect();
				} else {
					if (a.getSoundEffect() != null && a.loopSound()) {
						a.getSoundEffect().stop();
						a.setLoopSound(false);
					}
				}
			}
		}

	}

	public void destroyGame() {

		removeAllAbilities();
		showShop = false;
		inGame = false;
		loaded = false;

		players.clear();
		objectives.clear();
		abilities.clear();
		abilitiesToRemove.clear();
		
		client.setRunning(false);
		client.getSocket().close();
		client.setSocket(null);
		client = null;

	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public ArrayList<Entity> getPlayers() {
		return players;
	}

	public HUDDisplay getHUDDisplay() {
		return hudDisplay;
	}

	public ClientFunctions getClientFunctions() {
		return clientFunctions;
	}

	public StateBasedGame getStateBasedGame() {
		return s;
	}

	public Entity getPlayer() {
		return player;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	public GameUtil getUtil() {
		return util;
	}

	public void setUtil(GameUtil util) {
		this.util = util;
	}

	public Camera getCam() {
		return cam;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public ArrayList<Objective> getObjectives() {
		return objectives;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public SoundSystem getSoundSystem() {
		return soundSystem;
	}

	public ClientSettings getClientSettings() {
		return clientSettings;
	}

	public GameClient getClient() {
		return client;
	}

	public InputHandler getInput() {
		return input;
	}

	public void setInput(InputHandler input) {
		this.input = input;
	}

	public MapEditor getMapEditor() {
		return mapEditor;
	}

	public boolean showShop() {
		return showShop;
	}

	public void setShowShop(boolean showShop) {
		this.showShop = showShop;
	}

	public void setSoundSystem(SoundSystem soundSystem) {
		this.soundSystem = soundSystem;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public GameShop getGameShop() {
		return gameShop;
	}

}
