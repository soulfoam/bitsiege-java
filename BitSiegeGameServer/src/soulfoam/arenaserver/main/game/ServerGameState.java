package soulfoam.arenaserver.main.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arenaserver.main.ServerFunctions;
import soulfoam.arenaserver.main.ServerSettings;
import soulfoam.arenaserver.main.ServerValues;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.main.util.GameUtil;
import soulfoam.arenaserver.net.NetworkManager;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;

import soulfoam.arenashared.main.opcode.OPCode;

public class ServerGameState extends BasicGameState{

    public float sendLobbyPacketTime = 150;
    public float sendLobbyPacketTimer = sendLobbyPacketTime;
    public float restartGameTimer = 2 * 1000;

	public int teamDWins;
	public int teamAWins;
	public int gamesPlayed;
	
	private boolean running;
    
	public void enter(GameContainer gc, StateBasedGame s) throws SlickException {
	
	}
	
	public void leave(GameContainer gc, StateBasedGame s) throws SlickException {

	}
	
	public void init(GameContainer gc, StateBasedGame s) throws SlickException {
		NetworkManager.getNetworkManager().init();
		Game.getGame().init();
	}

	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
	}
	
	public void renderOverlay(GameContainer container, Graphics g) throws SlickException {
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
		
		if (NetworkManager.getNetworkManager().isReadyToStartGame()){
			resetGame();
			running = true;
			NetworkManager.getNetworkManager().setReadyToStartGame(false);
			return;
		}
		
		if (!running){
			return;
		}
		
		Game.getGame().update(gc, s, delta);
		
		if (Game.getGame().getServerFunctions().isGameOver()){
			System.out.println("game was over");
			running = false;
			resetGame();
		}
	}

	private void resetGame(){
		Game.getGame().destroyGame();
		Game.getGame().init();
	}
	

	public int getID() {
		return 0;
	}

}
