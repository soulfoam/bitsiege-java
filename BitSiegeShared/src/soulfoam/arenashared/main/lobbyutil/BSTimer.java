package soulfoam.arenashared.main.lobbyutil;

public class BSTimer {
	
	private long startTime;
	private long time;
	
	private long elapsedTime;

	public BSTimer(long time){
		reset();
		this.time = time;
	}
	
	public boolean update(){
		elapsedTime = System.currentTimeMillis() - startTime;

		if (elapsedTime >= time){
			return true;
		}

		return false;
	}

	public long getTimerDuration(){
		return time;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}
	
	public void reset(){
		this.startTime = System.currentTimeMillis();
	}

}
