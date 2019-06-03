package soulfoam.arena.main.menu;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import soulfoam.arena.entities.LobbyEntity;
import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.menu.gamelobby.ChallengerSelectUI;
import soulfoam.arena.main.menu.gamelobby.LobbyChatUI;
import soulfoam.arena.main.menu.gamelobby.GameStartTimerUI;
import soulfoam.arena.main.menu.gamelobby.MapUI;
import soulfoam.arena.main.menu.gamelobby.SelectedChallengerUI;
import soulfoam.arena.main.menu.gamelobby.TeamSelectUI;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.states.States;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class PregameLobbyManager {

	private static PregameLobbyManager plm = null;

	public static PregameLobbyManager getLobby() {
		if (plm == null) {
			plm = new PregameLobbyManager();
		}

		return plm;
	}

	private TeamSelectUI tsui;
	private SelectedChallengerUI scui;
	private MapUI mapui;
	private LobbyChatUI chatui;
	private ChallengerSelectUI csui;
	private GameStartTimerUI gstu;

	private CopyOnWriteArrayList<LobbyEntity> teamAPlayers = new CopyOnWriteArrayList<LobbyEntity>();
	private CopyOnWriteArrayList<LobbyEntity> teamDPlayers = new CopyOnWriteArrayList<LobbyEntity>();

	private boolean changeToGameState;
	private String serverIP = "";
	private int serverPort = 0;

	public static int underglowPick = 0;

	public void init(GameContainer gc, StateBasedGame s) throws SlickException {
		tsui = new TeamSelectUI(299, 28);
		scui = new SelectedChallengerUI(200, 7);
		mapui = new MapUI(255, 118);
		chatui = new LobbyChatUI(21, 5, gc); 
		csui = new ChallengerSelectUI(16, 238);
		gstu = new GameStartTimerUI(301, 11);
	}

	public void leave(GameContainer gc, StateBasedGame s) throws SlickException {

		chatui.getChatList().clear();
		chatui.getChatTextField().setText("");

		teamAPlayers.clear();
		teamDPlayers.clear();

		csui.enter();
		scui.enter();

	}

	public void enter(GameContainer gc, StateBasedGame s) throws SlickException {

		SoundStore.get().setMusicOn(!SettingManager.MUTE_MENU_MUSIC);
		SoundStore.get().setSoundsOn(!SettingManager.MUTE_MENU_MUSIC);

		underglowPick = 0;

		tsui.enter();
		scui.enter();
		mapui.enter();
		chatui.enter();
		csui.enter();
		gstu.enter();

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {

		gstu.update(delta, gc, s);
		tsui.update(delta, gc, s);
		scui.update(delta, gc, s);
		csui.update(delta, gc, s);
		//mapui.update(delta, gc, s);
		chatui.update(delta, gc, s);

		Res.listenForScreenShot(gc);

		if (changeToGameState) {
			s.enterState(States.GAME, new FadeOutTransition(Color.black, 250), new FadeInTransition(Color.black, 250));
			changeToGameState = false;
		}

	}

	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {

		Res.BS_BACKGROUND.draw(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);
		
		Res.UI_RESOURCE.LOBBY_UI.draw(0, 0);
		tsui.render(g, gc);
		chatui.render(g, gc);
		csui.render(g, gc);
//		mapui.render(g, gc);
		scui.render(g, gc);
		gstu.render(g, gc);

	}

	public void removeEntity(int accountID) {
		LobbyEntity entity = getLobbyEntity(accountID);

		if (entity.getPlayerTeam() == Res.TEAM_A) {
			teamAPlayers.remove(entity);
		}
		if (entity.getPlayerTeam() == Res.TEAM_D) {
			teamDPlayers.remove(entity);
		}
	}

	public LobbyEntity getLobbyEntity(int accountID) {
		for (LobbyEntity le : teamAPlayers) {
			if (le.getID() == accountID) {
				return le;
			}
		}

		for (LobbyEntity le : teamDPlayers) {
			if (le.getID() == accountID) {
				return le;
			}
		}

		return null;
	}

	public int getID() {
		return States.PREGAMELOBBY;
	}

	public TeamSelectUI getTeamSelectUI() {
		return tsui;
	}

	public SelectedChallengerUI getSelectedChallengerUI() {
		return scui;
	}

	public MapUI getMapUI() {
		return mapui;
	}

	public LobbyChatUI getChatUI() {
		return chatui;
	}

	public ChallengerSelectUI getChallengerSelectUI() {
		return csui;
	}

	public GameStartTimerUI getGameStartTimerUI() {
		return gstu;
	}

	public CopyOnWriteArrayList<LobbyEntity> getTeamAPlayers() {
		return teamAPlayers;
	}

	public CopyOnWriteArrayList<LobbyEntity> getTeamDPlayers() {
		return teamDPlayers;
	}

	public boolean changeToGameState() {
		return changeToGameState;
	}

	public void setChangeToGameState(boolean changeToGameState) {
		this.changeToGameState = changeToGameState;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

}
