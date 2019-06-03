package soulfoam.arenaserver.entities.objectives;

import java.util.ArrayList;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class CapturePoint extends Objective {
	
	private float tickTime = 1 * 1000;
	private float tickTimer = tickTime;

	private ArrayList<Entity> playersCapturing = new ArrayList<>();
	
	public CapturePoint(float x, float y){

		this.objectiveTeam = ObjectiveInfo.CAPTURE_POINT;

		this.x = x;
		this.y = y;
		
		width = 264;
		height = 144;
		hitBoxWidth = 124;
		hitBoxHeight = 64;
		
		this.baseHealthStart = 80;
		this.baseHealth = baseHealthStart;
		this.health = 0;
		
	}

	public void update(int delta){
		health = 0;


		handleCollision(delta);

		
		if (tickTimer <= 0){

			for (Entity p : playersCapturing){
				p.setPlayerCaptureScore(p.getCaptureScore() + 1);
			}
			tickTimer = tickTime;
		}
		
	}
	
	public void handleCollision(int delta){

		for (Entity p : Game.getGame().getPlayers()){
			if (getBounds().contains(p.getBoundsFeet())){
		    	if (!playersCapturing.contains(p)){
		    		playersCapturing.add(p);
		    	}
			}
			
			
			if (p.isDead()){
				if (playersCapturing.contains(p)){
					playersCapturing.remove(p);
				}
			}
		}
		

	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

	public float getTickTime() {
		return tickTime;
	}

	public void setTickTime(float tickTime) {
		this.tickTime = tickTime;
	}

	public float getTickTimer() {
		return tickTimer;
	}

	public void setTickTimer(float tickTimer) {
		this.tickTimer = tickTimer;
	}

	public ArrayList<Entity> getPlayersCapturing() {
		return playersCapturing;
	}

	public void setPlayersCapturing(ArrayList<Entity> playersCapturing) {
		this.playersCapturing = playersCapturing;
	}

	
}
