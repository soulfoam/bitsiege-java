package soulfoam.arenaserver.main.misc;

import soulfoam.arenaserver.entities.Entity;

public class StatKeeper {
	
	public float TOTAL_DAMAGE_DEALT;
	public float TOTAL_HEALING_DEALT;
	public float TOTAL_DAMAGE_TAKEN;
	public float TOTAL_HEALING_TAKEN;

	public int TIME_PLAYED;
	
	//only used for stat loading from site
	public int KILL_COUNT;
	public int DEATH_COUNT;
	public int ASSIST_COUNT;
	public int CAPTURE_SCORE_COUNT;
	public int WIN_COUNT;
	public int LOSE_COUNT;
	
	private int playedTimer;
	
	public Entity thePlayer;
	
	public int classType;
	
	public StatKeeper(Entity player){
		thePlayer = player;
	}
	
	public StatKeeper(int classType){
		this.classType = classType;
	}
	
	public void update(int delta){
		playedTimer += delta;
		if (playedTimer >= 1000){
			TIME_PLAYED++;
			playedTimer = 0;
		}
	}

}
