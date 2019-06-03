package soulfoam.arenaserver.main;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;

public class ServerSettings {

	private int gameTime = (60 * 20) * 1000;
	
	private boolean spawnBots = false;


	public void handleCommand(String command) {
		if (command.trim().startsWith("/sb0")){
			Game.getGame().getServerFunctions().spawnBot(Res.TEAM_A, Res.randInt(200, 207), null);
		}
		if (command.trim().startsWith("/sb1")){
			Game.getGame().getServerFunctions().spawnBot(Res.TEAM_D, Res.randInt(200, 207), null);
		}
	}


	public boolean spawnBots() {
		return spawnBots;
	}


	public int getGameTime() {
		return gameTime;
	}

}
