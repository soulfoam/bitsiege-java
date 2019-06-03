package soulfoam.arena.main.command.client;

import org.newdawn.slick.Color;

import soulfoam.arena.entities.EntityBook;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.net.lobby.LobbyManager;

public class SpawnLocalPlayerCommand extends Command {

	private int id;
	private float x, y;
	private byte team;
	private int classPick;
	private int skinPick;
	private int underglowPick;
	private int r;
	private int g;
	private int b;

	public SpawnLocalPlayerCommand(int id, float x, float y, byte team, int classPick, int skinPick, int underglowPick, int r, int g, int b) {
		super(Command.COMMAND_PACKET_MOVE_ENTITY);

		this.id = id;
		this.x = x;
		this.y = y;
		this.team = team;
		this.classPick = classPick;
		this.skinPick = skinPick;
		this.underglowPick = underglowPick;	
		this.r = r;
		this.g = g;
		this.b = b;

	}

	public boolean execute(int delta) {

		if (!Game.getGame().getClientFunctions().gotRespawn()) {
			Game.getGame().setPlayer(EntityBook.getChallengerByID(classPick, x, y, skinPick, underglowPick));

			Game.getGame().getPlayer().setPlayerID(id);
			Game.getGame().getPlayer().setTeam(team);

			Game.getGame().getPlayer().setUsername(LobbyManager.getManager().getUserAccount().getUsername());
			Game.getGame().getPlayer().setLocalPlayer(true);
			Game.getGame().getPlayer().setRemoteX(x);
			Game.getGame().getPlayer().setRemoteY(y);
			Game.getGame().getPlayer().setUsernameColor(new Color(r, g, b));
			Game.getGame().getPlayers().add(Game.getGame().getPlayer());

			Game.getGame().getClientFunctions().setGotRespawn(true);
			
			Game.getGame().getCam().setTarget(Game.getGame().getPlayer());
			
			Game.getGame().setLoaded(true);
		}

		return true;

	}

	public void undo() {

	}

}
