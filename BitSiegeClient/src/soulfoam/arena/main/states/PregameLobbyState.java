package soulfoam.arena.main.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.PregameLobbyManager;

public class PregameLobbyState extends BasicGameState {

	public void init(GameContainer gc, StateBasedGame s) throws SlickException {
		PregameLobbyManager.getLobby().init(gc, s);
	}

	public void leave(GameContainer gc, StateBasedGame s) throws SlickException {
		PregameLobbyManager.getLobby().leave(gc, s);
	}

	public void enter(GameContainer gc, StateBasedGame s) throws SlickException {
		PregameLobbyManager.getLobby().enter(gc, s);
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
		PregameLobbyManager.getLobby().update(gc, s, delta);
	}

	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
		PregameLobbyManager.getLobby().render(gc, s, g);
	}
	
	public void renderOverlay(GameContainer container, Graphics g) throws SlickException {
		
	}

	public int getID() {
		return States.PREGAMELOBBY;
	}

}
