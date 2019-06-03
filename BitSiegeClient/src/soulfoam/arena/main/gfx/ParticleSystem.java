package soulfoam.arena.main.gfx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

public class ParticleSystem {

	public List<Particle> particles = Collections.synchronizedList(new ArrayList<Particle>());

	public void update(int delta) {
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i) != null) {
				if (particles.get(i).update(delta)) {
					particles.remove(i);
				}
			}
		}
	}

	public void addBloodParticle(int damage, float x, float y, Color color) {
		if (damage <= 0) {
			damage = 1;
		}
		for (int i = 0; i < damage; i++) {
			addParticle(x, y, color, 1);
		}
	}

	public void addHealParticle(int healAmount, float x, float y, Color color) {
		for (int i = 0; i < healAmount; i++) {
			addParticle(x, y, color, 1);
		}
	}

	public void addStunParticles(float x, float y, int size, int life) {
		// for (int i = 0; i < 18; i++) {
		// addParticle(x + 4, y, Color.yellow, size, life);
		// }
	}

	public void addSlowParticles(float x, float y, int size, int life) {
		for (int i = 0; i < 10; i++) {
			addParticle(x + 3.5f, y + 14, new Color(51, 182, 255), size, life);
		}
	}

	public void addDeathParticles(Entity player) {
		// if (player != null && player.animation != null &&
		// player.animation.getStandStillAnimation() != null &&
		// player.animation.getStandStillAnimation().currentImg != null){
		// Image i = player.animation.getStandStillAnimation().currentImg;
		// float w = i.getWidth();
		// float h = i.getHeight();
		// for (int yy = 0; yy < h; yy++){
		// for (int xx = 0; xx < w; xx++){
		// Color pixelColor = i.getColor(xx, yy);
		// addParticle(player.x - 4, player.y - 8, pixelColor, 2.0f);
		// }
		// }
		// }
	}

	public void addTotemParticle(float x, float y, int totemID) {

		float dx = 0, dy = 0;

		dy = -0.25f;

		int life = 50;

		if (totemID == 0) {
			particles.add(new Particle(x, y, dx, dy, 1, life, new Color(212, 168, 48)));
			particles.add(new Particle(x + 5, y, dx, dy, 1, life, new Color(55, 141, 179)));
		} else if (totemID == 1) {
			particles.add(new Particle(x, y, dx, dy, 1, life, new Color(151, 151, 151)));
			particles.add(new Particle(x + 5, y, dx, dy, 1, life, new Color(151, 151, 151)));
		}

		else if (totemID == 2) {
			particles.add(new Particle(x, y, dx, dy, 1, life, new Color(192, 67, 47)));
			particles.add(new Particle(x + 5, y, dx, dy, 1, life, new Color(192, 67, 47)));
		} else if (totemID == 3) {
			particles.add(new Particle(x, y, dx, dy, 1, life, new Color(147, 51, 167)));
			particles.add(new Particle(x + 5, y, dx, dy, 1, life, new Color(147, 51, 167)));
		}
	}

	public void addParticle(float x, float y, Color color, int size) {
		Random random = new Random();
		boolean randBoolean = random.nextBoolean();

		float dx = 0, dy = 0;

		if (randBoolean) { // bottom right or top right
			dx = (int) (Math.random() * 4);
			dy = (int) (Math.random() * 4);
			if (random.nextBoolean()) {
				dx = (int) (Math.random() * -4);
				dy = (int) (Math.random() * -4);
			}
		} else {
			dx = (int) (Math.random() * -4);
			dy = (int) (Math.random() * 4);
			if (random.nextBoolean()) {
				dx = (int) (Math.random() * 4);
				dy = (int) (Math.random() * -4);
			}
		}

		int life = 30;

		particles.add(new Particle(x, y, dx, dy, size, life, color));

	}

	public void addParticle(float x, float y, Color color, float velocity) {
		float dx = 0;
		float dy = -1f;

		int life = 70;
		int size = 1;

		int randX = Res.randInt(-10, 10);
		int randY = Res.randInt(-10, 10);

		particles.add(new Particle(x + randX, y + randY, dx, dy, size, life, color));

	}

	public void addParticle(float x, float y, Color color, int size, int life) {
		Random random = new Random();
		boolean randBoolean = random.nextBoolean();

		float dx = 0, dy = 0;

		if (randBoolean) { // bottom right or top right
			dx = (int) (Math.random() * 4);
			dy = (int) (Math.random() * 4);
			if (random.nextBoolean()) {
				dx = (int) (Math.random() * -4);
				dy = (int) (Math.random() * -4);
			}
		} else {
			dx = (int) (Math.random() * -4);
			dy = (int) (Math.random() * 4);
			if (random.nextBoolean()) {
				dx = (int) (Math.random() * 4);
				dy = (int) (Math.random() * -4);
			}
		}

		particles.add(new Particle(x, y, dx, dy, size, life, color));

	}
}
