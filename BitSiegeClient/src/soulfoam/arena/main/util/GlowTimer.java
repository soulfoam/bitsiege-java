package soulfoam.arena.main.util;

public class GlowTimer {

	private boolean addGlowTime = true;
	private float timer;
	private float min;
	private float max;
	private float speed;

	public GlowTimer(float min, float max, float speed) {
		this.min = min;
		this.max = max;
		this.speed = speed / 25;
	}

	public void update(int delta) {
		if (addGlowTime) {
			timer += speed * delta;
			if (timer >= max) {
				addGlowTime = false;
			}
		} else {
			timer -= speed * delta;
			if (timer <= min) {
				addGlowTime = true;
			}
		}
	}

	public int getTimer() {
		return (int) timer;
	}
}
