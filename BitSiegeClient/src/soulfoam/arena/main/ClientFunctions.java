package soulfoam.arena.main;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.command.client.ClientCommandHandler;
import soulfoam.arena.main.command.client.local.ClientLocalInputCommand;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.states.States;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.opcode.OPCode;

public class ClientFunctions {

	private boolean gotRespawn;

	private long clientPacketBytes;
	private boolean serverResponding;

	private int teamDPoints;
	private int teamAPoints;

	private int gameClock;

	private byte gameOutCome;

	private float gameOverTimer = 5 * 1000;

	private float packetSendTimer = 0;
	private float packetSendTime = 50;

	private float syncPacketTimer = 0;
	private float syncPacketTime = 2 * 1000;

	private float connectionCheckTime = 7 * 1000;
	private float connectionCheckTimer = connectionCheckTime;

	private int packetCount;

	private ClientCommandHandler commandHandler;

	public ClientFunctions() {
		commandHandler = new ClientCommandHandler();
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
		commandHandler.update(delta);

		checkForConnection(s, delta);

		if (packetSendTimer > 0) {
			packetSendTimer -= delta;
		}

		if (packetSendTimer <= 0) {
			handleSendPacketsClient();
			packetCount++;
		}

		if (syncPacketTimer > 0) {
			syncPacketTimer -= delta;
		}

		if (syncPacketTimer <= 0) {
			syncGame();
			packetCount++;
		}

	}

	public void handleSendPacketsClient() {
		Game.getGame().getInput().createInputUpdate();

		packetSendTimer = packetSendTime;
	}

	public void checkForConnection(StateBasedGame s, int delta) {

		if (connectionCheckTimer > 0) {
			connectionCheckTimer -= delta;
		}

		if (connectionCheckTimer <= 0) {
			if (serverResponding) {
				serverResponding = false;
			} else {
				leaveCurrentGame(s);
			}
			connectionCheckTimer = connectionCheckTime;
		}

	}

	public void syncGame() {
//
//		GameHolder.getGame().getClient().sendData(OPCode.OP_REQUESTPLAYERS + GameHolder.getGame().getPlayer().getPlayerID() + "," + GameHolder.getGame().getPlayers().size());
//		GameHolder.getGame().getClient().sendData(OPCode.OP_GETALLABILITIES + GameHolder.getGame().getAbilities().size());
//		GameHolder.getGame().getClient().sendData(OPCode.OP_GETALLOBJECTIVES);

		syncPacketTimer = syncPacketTime;

	}

	public void leaveCurrentGame(StateBasedGame s) {

		leaveServer();

		s.enterState(States.MENU, new FadeOutTransition(Color.black, 250), new FadeInTransition(Color.black, 250));

	}

	public void leaveServer() {

		Game.getGame().getClient().sendDataBlast(OPCode.OP_REMOVEPLAYER, 4);

	}

	public boolean gotRespawn() {
		return gotRespawn;
	}

	public long getClientPacketBytes() {
		return clientPacketBytes;
	}

	public boolean isServerIsResponding() {
		return serverResponding;
	}

	public int getTeamDPoints() {
		return teamDPoints;
	}

	public int getTeamAPoints() {
		return teamAPoints;
	}

	public int getGameClock() {
		return gameClock;
	}

	public byte getGameOutCome() {
		return gameOutCome;
	}

	public float getGameOverTimer() {
		return gameOverTimer;
	}

	public float getPacketSendTimer() {
		return packetSendTimer;
	}

	public float getPacketSendTime() {
		return packetSendTime;
	}

	public float getSyncPacketTimer() {
		return syncPacketTimer;
	}

	public float getSyncPacketTime() {
		return syncPacketTime;
	}

	public float getConnectionCheckTime() {
		return connectionCheckTime;
	}

	public float getConnectionCheckTimer() {
		return connectionCheckTimer;
	}

	public int getPacketCount() {
		return packetCount;
	}

	public ClientCommandHandler getCommandHandler() {
		return commandHandler;
	}

	public void setGotRespawn(boolean gotRespawn) {
		this.gotRespawn = gotRespawn;
	}

	public void setClientPacketBytes(long clientPacketBytes) {
		this.clientPacketBytes = clientPacketBytes;
	}

	public void setServerResponding(boolean serverResponding) {
		this.serverResponding = serverResponding;
	}

	public void setTeamDPoints(int teamDPoints) {
		this.teamDPoints = teamDPoints;
	}

	public void setTeamAPoints(int teamAPoints) {
		this.teamAPoints = teamAPoints;
	}

	public void setGameClock(int gameClock) {
		this.gameClock = gameClock;
	}

	public void setGameOutCome(byte gameOutCome) {
		this.gameOutCome = gameOutCome;
	}

	public void setGameOverTimer(float gameOverTimer) {
		this.gameOverTimer = gameOverTimer;
	}

	public void setPacketSendTimer(float packetSendTimer) {
		this.packetSendTimer = packetSendTimer;
	}

	public void setPacketSendTime(float packetSendTime) {
		this.packetSendTime = packetSendTime;
	}

	public void setSyncPacketTimer(float syncPacketTimer) {
		this.syncPacketTimer = syncPacketTimer;
	}

	public void setSyncPacketTime(float syncPacketTime) {
		this.syncPacketTime = syncPacketTime;
	}

	public void setPacketCount(int packetCount) {
		this.packetCount = packetCount;
	}

	public void setCommandHandler(ClientCommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

}
