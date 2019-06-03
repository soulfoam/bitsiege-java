package soulfoam.arena.main.command.client;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;

public class SpawnAbilityCommand extends Command{
	
	private int ownerID;
	private int abilityID;
	private int gameID;
	private float x;
	private float y;
	private float dx;
	private float dy;
	private Ability ability;
	
	public SpawnAbilityCommand(int ownerID, int abilityID, int gameID, float x, float y, float dx, float dy) {
		super(Command.COMMAND_PACKET_ADD_ABILITY);
		this.ownerID = ownerID;
		this.abilityID = abilityID;
		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

	}
	
	public SpawnAbilityCommand(Ability ability) {
		super(Command.COMMAND_PACKET_ADD_ABILITY);
		this.ability = ability;
	}
	
	public boolean execute(int delta) {

		if (ability == null){
			Entity abilityOwner = Game.getGame().getPlayerObject(ownerID);
			
			if (abilityOwner == null){
				
				return true;
			}
			
			Ability newAbility = Game.getGame().getAbilityByID(abilityID, gameID, x, y, dx, dy, abilityOwner);
			Ability a = Game.getGame().getAbilityObject(gameID);
	
			if (!Game.getGame().getAbilities().contains(a)) {
				Game.getGame().addAbility(newAbility);
			} 
			else {
				if (a.getAbilityID() != abilityID) {
					a.removeThis();
					Game.getGame().addAbility(newAbility);
				}
			}
		}
		else{
			Game.getGame().addAbility(ability);
		}
		return true;
	}

	public void undo() {

	}
	
}