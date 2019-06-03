package soulfoam.arena.main.menu.gamelobby;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.resources.Res;

public class GameStartTimerUI {

	private float x, y, width = 156;

	private float timer;

	public GameStartTimerUI(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void enter() {

	}

	public void update(int delta, GameContainer gc, StateBasedGame s) {
		timer -= delta;
	}

	public void render(Graphics g, GameContainer gc) {

		Color timerColor = Color.green;

		if (getTimerInSeconds() <= 20 && getTimerInSeconds() > 5) {
			timerColor = Color.yellow;
		}

		if (getTimerInSeconds() <= 5) {
			timerColor = Color.red;
		}

		if (timer <= 0) {
			timer = 0;
		}

		Res.bitFont.drawString(x - Res.getTextCenter("Game Starts in: " + getTimerInSeconds()) + (width  / 2), y, "Game Starts in: " + getTimerInSeconds(), timerColor);

	}

	public int getTimerInSeconds() {
		return (int) timer / 1000;
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}

}
