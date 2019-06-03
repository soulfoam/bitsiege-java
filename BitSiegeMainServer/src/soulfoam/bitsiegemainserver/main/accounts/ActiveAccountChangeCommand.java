package soulfoam.bitsiegemainserver.main.accounts;

import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;

public class ActiveAccountChangeCommand extends Command{

	public static final byte ADD = 0;
	public static final byte REMOVE = 1;
	
	private ClientThread clientThread;
	private byte action;
	
	public ActiveAccountChangeCommand(ClientThread clientThread, byte action){
		this.clientThread = clientThread;
		this.action = action;
	
	}
	

	public boolean execute() {
		
		if (action == ADD){
			NetworkManager.getManager().getActiveAccountManager().getClients().add(clientThread);
		}
		
		if (action == REMOVE){
			NetworkManager.getManager().getActiveAccountManager().getClients().remove(clientThread);
		}

		
		return true;
	
	}


	public void undo() {
		
	}


}
