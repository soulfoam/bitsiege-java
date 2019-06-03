package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.GameObject;
import soulfoam.arena.main.resources.Res;

public class Particle extends GameObject {

	public float dx;
	public float dy;
	public int size;
	public int life;
	public Color color;

	public Particle(float x, float y, float dx, float dy, int size, int life, Color c) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		this.life = life;
		color = c;
	}

	public boolean update(int delta) {
		x += dx;
		y += dy;
		life--;
		if (life <= 0) {
			return true;
		}
		return false;
	}

	public void render(Graphics g) {

		g.setColor(color);
		g.fillRect(Res.roundForRendering(x), Res.roundForRendering(y), size, size);

	}

}