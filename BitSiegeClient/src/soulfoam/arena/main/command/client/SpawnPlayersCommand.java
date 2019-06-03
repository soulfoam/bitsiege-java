package soulfoam.arena.main.command.client;

import java.util.ArrayList;
import java.util.Iterator;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;

public class SpawnPlayersCommand extends Command {

	private ArrayList<Entity> players = new ArrayList<Entity>();

	public SpawnPlayersCommand(ArrayList<Entity> players) {
		super(Command.COMMAND_PACKET_ADD_ENTITY);
		this.players = players;
	}

	public boolean execute(int delta) {
		
		ArrayList<Integer> idList = new ArrayList<Integer>();
		
		for (Entity p : players){
			idList.add(p.getPlayerID());
		}
		
		for (Entity p : players){
			if (!idList.contains(p.getPlayerID())){
				Game.getGame().getClientFunctions().getCommandHandler().addCommand(new RemoveEntityCommand(p.getPlayerID()));
			}
		}
		
		for (Entity p : players){
			if (Game.getGame().getPlayerObject(p.getPlayerID()) == null) {
				Game.getGame().getPlayers().add(p);
			}
		}

		return true;
	}

	public void undo() {

	}

}
