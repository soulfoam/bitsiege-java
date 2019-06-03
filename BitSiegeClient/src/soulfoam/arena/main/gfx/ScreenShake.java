package soulfoam.arena.main.gfx;

import java.util.Random;

import org.newdawn.slick.Graphics;

import soulfoam.arena.main.game.Game;


public class ScreenShake {

	private Random random = new Random();
	private float rumbleX;
	private float rumbleY;
	private float rumbleTime;
	private float currentRumbleTime;
	private float rumblePower;
	private float currentRumblePower;

	public boolean isShaking;

	private boolean didStunShake;

	// methods
	public void update(int delta) {

		if (currentRumbleTime <= rumbleTime) {
			currentRumblePower = rumblePower * ((rumbleTime - currentRumbleTime) / rumbleTime);

			setRumbleX((random.nextFloat() - 0.5f) * 2 * currentRumblePower);
			setRumbleY((random.nextFloat() - 0.5f) * 2 * currentRumblePower);

			currentRumbleTime += delta;
		}
		if (Game.getGame().getPlayer().isStunned()) {
			if (!didStunShake) {
				didStunShake = true;
				shakeScreen(3, 500);
			}
		} else {
			didStunShake = false;
		}
	}

	public void applyTransform(Graphics g) {
		if (currentRumbleTime <= rumbleTime) {
			isShaking = true;
		} else {
			isShaking = false;
		}
	}

	public void shakeScreen(float power, int time) {
		rumblePower = power;
		rumbleTime = time;
		currentRumbleTime = 0;
	}

	public float getRumbleX() {
		return rumbleX;
	}

	public void setRumbleX(float rumbleX) {
		this.rumbleX = rumbleX;
	}

	public float getRumbleY() {
		return rumbleY;
	}

	public void setRumbleY(float rumbleY) {
		this.rumbleY = rumbleY;
	}

}
