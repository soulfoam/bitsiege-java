package soulfoam.arena.main.states;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.ClientEngine;
import soulfoam.arena.main.SoundSystem;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class GameState extends BasicGameState {

	public void init(GameContainer gc, StateBasedGame s) throws SlickException {

	}

	public void leave(GameContainer gc, StateBasedGame s) throws SlickException {
		Game.getGame().destroyGame();
	}

	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
		Game.getGame().render(gc, s, g);
	}
	
	public void renderOverlay(GameContainer gc, Graphics g) throws SlickException {

	}
	
	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {

		Game.getGame().update(gc, s, delta);
		Res.listenForScreenShot(gc);
	}

	public void enter(GameContainer gc, StateBasedGame s) throws SlickException {

		Game.getGame().init(gc, s);
		Game.getGame().setSoundSystem(new SoundSystem());
		Game.getGame().getSoundSystem().setSoundSettings();
		Game.getGame().getSoundSystem().chooseRandomBackgroundMusic();
	}

	public int getID() {
		return States.GAME;
	}

	public void mouseWheelMoved(int m) {

		Game.getGame().getInput().moveMouseWheel(m);

		if (Game.getGame().getMapEditor() != null) {
			Game.getGame().getMapEditor().mouseWheelMoved(m);
		}

	}

	public void keyReleased(int key, char c) {

		if (Game.getGame().getMapEditor() != null) {
			Game.getGame().getMapEditor().keyReleased(key, c);
		}

		if (Game.getGame().getHUDDisplay().getEscapeHUD().getKeybindUI().isRendering()) {
			if (Game.getGame().getHUDDisplay().getEscapeHUD().getKeybindUI().setID != 4
					&& Game.getGame().getHUDDisplay().getEscapeHUD().getKeybindUI().setID != 5) {
				Game.getGame().getHUDDisplay().getEscapeHUD().getKeybindUI().setKeybind(key);
			} else {
				Game.getGame().getHUDDisplay().getEscapeHUD().getKeybindUI().setMode = false;
			}
		}

	}

	public void mousePressed(int button, int x, int y) {
		if (Game.getGame().getMapEditor() != null) {
			Game.getGame().getMapEditor().mousePressed(button, x, y);
		}
	}


}
