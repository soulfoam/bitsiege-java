package soulfoam.arena.main.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.ClientEngine;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class MenuState extends BasicGameState {

	public void init(GameContainer gc, StateBasedGame s) throws SlickException {
		MainMenuManager.getMainMenu().init(gc, s);
	}

	public void enter(GameContainer gc, StateBasedGame s) throws SlickException {
		//ClientEngine.game.setGameResolution(GameInfo.MENU_RES_WIDTH, GameInfo.MENU_RES_HEIGHT);
		MainMenuManager.getMainMenu().enter(gc, s);
	}

	public void leave(GameContainer gc, StateBasedGame s) throws SlickException {
		MainMenuManager.getMainMenu().leave(gc, s);
	}

	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
		MainMenuManager.getMainMenu().render(gc, s, g);
	}

	public void renderOverlay(GameContainer container, Graphics g) throws SlickException {
	
	}
	
	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {

		MainMenuManager.getMainMenu().update(gc, s, delta);

		Res.listenForScreenShot(gc);
	}

	public int getID() {
		return States.MENU;
	}

	public void keyReleased(int key, char c) {
		MainMenuManager.getMainMenu().keyReleased(key, c);

	}

	public void mousePressed(int button, int x, int y) {
		MainMenuManager.getMainMenu().mousePressed(button, x, y);
	}

	public void mouseWheelMoved(int m) {
		MainMenuManager.getMainMenu().mouseWheelMoved(m);
	}
}
