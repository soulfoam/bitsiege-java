package soulfoam.arena.entities.objectives;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class SmallCapturePoint extends Objective {

	private ArrayList<Entity> playersCapturing = new ArrayList<>();
	private ArrayList<Entity> playersDefending = new ArrayList<>();

	private Color red = new Color(219, 40, 49);
	private Color blue = new Color(40, 218, 219);

	public SmallCapturePoint(float x, float y) {

		this.objectiveTeam = ObjectiveInfo.SMALL_CAPTURE_POINT;

		this.x = x;
		this.y = y;
		width = 88;
		height = 44;
		hitBoxWidth = 41;
		hitBoxHeight = 22;

		baseHealthStart = 120;
		baseHealth = baseHealthStart;
		health = 0;

	}

	public void update(int delta) {
		handleCollision(delta);
	}

	public void render(Graphics g) {


		g.setColor(new Color(117, 92, 100));
		g.fillRect(x - 33, y - 12, 68, 20);

		float fillAmount = 68 * (health / baseHealth);
		
		g.setColor(red);
		g.fillRect(x - 33, y - 12, fillAmount, 20);
		

		Res.OBJECTIVE_RESOURCE.SMALL_CAPTURE_POINT[0].draw(Res.roundForRendering(x-42), Res.roundForRendering(y-24));
		
	}

	public void render(Graphics g, float x, float y) {
		Res.OBJECTIVE_RESOURCE.SMALL_CAPTURE_POINT[0].draw(Res.roundForRendering(x + 2), Res.roundForRendering(y - 3));
	}
	
	public void handleCollision(int delta){

		for (Entity p : Game.getGame().getPlayers()){
			if (getBounds().contains(p.getBoundsFeet()))
			{
				if (p.getTeam() == Res.TEAM_A){
			    	if (!playersCapturing.contains(p)){
			    		playersCapturing.add(p);
			    	}
				}
				else{
			    	if (!playersDefending.contains(p)){
			    		playersDefending.add(p);
			    	}
				}
			}
			else{
				if (p.getTeam() == Res.TEAM_A){
			    	if (playersCapturing.contains(p)){
			    		playersCapturing.remove(p);
			    	}
				}
				else{
			    	if (playersDefending.contains(p)){
			    		playersDefending.remove(p);
			    	}
				}
			}
			
			if (p.isDead()){
				if (playersCapturing.contains(p)){
					playersCapturing.remove(p);
				}
				if (playersDefending.contains(p)){
					playersDefending.remove(p);
				}
			}
		}
		

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
