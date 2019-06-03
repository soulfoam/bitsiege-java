package soulfoam.arena.main.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class LeaderboardState extends BasicGameState {

	public void init(GameContainer gc, StateBasedGame s) throws SlickException {

	}

	public void enter(GameContainer gc, StateBasedGame s) throws SlickException {
		// Engine.app = new AppGameContainer(new ScalableGame(new Engine("Bit
		// Siege"), 1000, 1500, false));
		// Engine.app.setDisplayMode(768, 432, false);
	}

	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {

		// Res.bitFont.drawString(20, 100, "Sup dude how long");
	}
	
	public void renderOverlay(GameContainer container, Graphics g) throws SlickException {
		
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {

	}

	public int getID() {
		return States.LEADERBOARD;
	}

}
