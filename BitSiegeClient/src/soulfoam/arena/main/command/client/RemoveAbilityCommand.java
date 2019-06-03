package soulfoam.arena.main.command.client;

import java.util.ArrayList;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;


public class RemoveAbilityCommand extends Command{

	private int gameID;
	
	public RemoveAbilityCommand(int gameID){
		super(Command.COMMAND_PACKET_REMOVE_ENTITY);
		this.gameID = gameID;
	}

	public boolean execute(int delta) {
		Ability a = Game.getGame().getAbilityObject(gameID);
		if (a != null){
			a.removeThis();
		}
		
		return true;

	}
	
	
	public void undo() {
		
	}
}