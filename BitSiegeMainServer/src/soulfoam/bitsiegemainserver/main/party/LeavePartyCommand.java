package soulfoam.bitsiegemainserver.main.party;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;

public class LeavePartyCommand extends Command{
	
	private ClientThread clientThread;
	private int requestedLeaveID;
	private int result = -1;
	
	public LeavePartyCommand(ClientThread clientThread, int requestedLeaveID){
		this.clientThread = clientThread;
		this.requestedLeaveID = requestedLeaveID;
	}
	
	public boolean execute() {
		
		if (clientThread.getUserAccount().getID() == requestedLeaveID){

			if (clientThread.getParty().getPartyMembers().size() == 1){
				return false;
			}
			
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new RemoveUserFromPartyCommand(clientThread));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			clientThread.getOutput().println(LobbyOPCode.OP_PARTYCHANGE);
			clientThread.getOutput().println("L" + "," + LobbyReturnCode.PARTY_LEAVE_SUCCESS);
			
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new CreatePartyCommand(clientThread));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			result = LobbyReturnCode.PARTY_LEAVE_SUCCESS;
		}
		
		if (clientThread.getUserAccount().getID() != requestedLeaveID){

			if (clientThread.getParty().getPartyLeader().getUserAccount().getID() == clientThread.getUserAccount().getID()){
				ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(requestedLeaveID);
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new RemoveUserFromPartyCommand(ct));
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (ct != null){
					ct.getOutput().println(LobbyOPCode.OP_PARTYCHANGE);
					ct.getOutput().println("L" + "," + LobbyReturnCode.PARTY_KICKED);
				}

				if (clientThread != null){
					clientThread.getOutput().println(LobbyOPCode.OP_PARTYCHANGE);
					clientThread.getOutput().println("K" + "," + LobbyReturnCode.PARTY_KICK_SUCCESS);
				}
				
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new CreatePartyCommand(ct));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				result = LobbyReturnCode.PARTY_KICK_SUCCESS;
			}
			else{
				result = LobbyReturnCode.PARTY_KICK_NOPERMISSION;
			}
			
		}
		
		result = LobbyReturnCode.PARTY_LEAVE_SUCCESS;
		
		return true;
	}

	public void undo() {
		
	}


}
