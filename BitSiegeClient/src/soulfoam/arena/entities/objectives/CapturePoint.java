package soulfoam.arena.entities.objectives;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class CapturePoint extends Objective {

	private ArrayList<Entity> playersCapturing = new ArrayList<>();
	private ArrayList<Entity> playersDefending = new ArrayList<>();

	private Color red = new Color(219, 40, 49);
	private Color blue = new Color(40, 218, 219);

	public CapturePoint(float x, float y) {

		this.objectiveTeam = ObjectiveInfo.CAPTURE_POINT;

		this.x = x;
		this.y = y;
		
		width = 264;
		height = 144;
		hitBoxWidth = 124;
		hitBoxHeight = 64;

		baseHealthStart = 120;
		baseHealth = baseHealthStart;
		health = 0;

	}

	public void update(int delta) {
		health = 0;
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(x - 72, y - 34, 140, 80);
		
		g.setColor(blue);
		g.fillRect(x - 72, y - 34, 74, 80);
		
		g.setColor(red);
		g.fillRect(x, y - 34, 70, 80);

		Res.OBJECTIVE_RESOURCE.CAPTURE_POINT[0].draw(Res.roundForRendering(x - 132), Res.roundForRendering(y - 72), 264, 144);

	}

	public void render(Graphics g, float x, float y) {
		Res.OBJECTIVE_RESOURCE.CAPTURE_POINT[0].draw(Res.roundForRendering(x + 4), Res.roundForRendering(y));
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

	public Shape getBounds(float x, float y) {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

	public int getCapPoints() {
		int add = playersCapturing.size() - playersDefending.size();

		if (add <= 0) {
			add = 0;
		}

		return add;
	}

	public void removeObjectiveClient() {

	}

}
