package soulfoam.arenaserver.main.command.server;

import java.net.InetAddress;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.abilities.AbilityBook;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;

import soulfoam.arenashared.main.opcode.OPCode;

public class SpawnAbilityCommand extends Command{
	
	private float x, y, dx, dy;
	private int abilityID, gameID;
	private int modeID;
	private Ability ability;
	private boolean broadcastAdd = true;
	
	public SpawnAbilityCommand(int playerID, int abilityID, int gameID, float x, float y){
		super(Command.COMMAND_SPAWN_ABILITY, playerID);
		this.abilityID = abilityID;
		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.modeID = 0;
	}
	
	public SpawnAbilityCommand(int playerID, int abilityID, int gameID, float x, float y, float dx, float dy){
		super(Command.COMMAND_SPAWN_ABILITY, playerID);
		this.abilityID = abilityID;
		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.modeID = 1;
	}
	
	public SpawnAbilityCommand(Ability ability){
		super(Command.COMMAND_SPAWN_ABILITY);
		this.ability = ability;
		this.modeID = 2;
	}
	
	public SpawnAbilityCommand(Ability ability, boolean broadcastAdd){
		super(Command.COMMAND_SPAWN_ABILITY);
		this.ability = ability;
		this.broadcastAdd = broadcastAdd;
		this.modeID = 2;
	}

	public boolean execute(int delta) {

		player = Game.getGame().getPlayerObject(playerID);

		if (player == null){
			return true;
		}
		
		if (modeID == 0){
			Ability a = AbilityBook.getAbilityByID(abilityID, gameID, x, y, player);
			addAbility(a);
		}

		if (modeID == 1){
			Ability a = AbilityBook.getAbilityByID(abilityID, gameID, x, y, dx, dy, player);
			addAbility(a);
		}
		
		if (modeID == 2){
			addAbility(ability);
		}
		
		return true;
	
	}

	private void addAbility(Ability a){
		Game.getGame().addAbility(a);
		if (broadcastAdd){
			Game.getGame().getServer().sendReliableDataToEveryone(OPCode.OP_ADDABILITY + playerID + "," + a.getAbilityID() + "," + a.getGameID() + "," 
					+ Res.roundForNetwork(a.getX()) + "," + Res.roundForNetwork(a.getY()) + "," + Res.roundForNetwork(a.getDx()) + "," + Res.roundForNetwork(a.getDy()));
		}
	}

	public void undo() {
		
	}


}
