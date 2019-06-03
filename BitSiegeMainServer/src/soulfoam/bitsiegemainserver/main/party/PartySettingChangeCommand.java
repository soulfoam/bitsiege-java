package soulfoam.bitsiegemainserver.main.party;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.notify.NotifyUsersOfPartyCommand;

public class PartySettingChangeCommand extends Command{
	
	private final static byte NEW_LEADER = 0;
	private final static byte ANYONE_CAN_INVITE = 1;
	
	private byte action;
	private ClientThread clientThread;
	private int requestedLeaderID;
	private boolean anyoneCanInvite;
	private int result = -1;
	
	public PartySettingChangeCommand(ClientThread clientThread, int requestedLeaderID){
		this.clientThread = clientThread;
		this.requestedLeaderID = requestedLeaderID;
		this.action = NEW_LEADER;
	}
	
	public PartySettingChangeCommand(ClientThread clientThread, boolean can){
		this.clientThread = clientThread;
		this.anyoneCanInvite = can;
		this.action = ANYONE_CAN_INVITE;
	}
	
	public boolean execute() {
		
		if (action == NEW_LEADER){
			if (clientThread.getParty().getPartyLeader().getUserAccount().getID() == clientThread.getUserAccount().getID()){
				clientThread.getParty().setNewPartyLeader(requestedLeaderID);
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyUsersOfPartyCommand(clientThread.getParty()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				result = LobbyReturnCode.PARTY_MAKELEADER_SUCCESS;
				sendResult();
				return true;
			}
			
			result = LobbyReturnCode.PARTY_MAKELEADER_SERVERERROR;
			sendResult();
		}
		
		if (action == ANYONE_CAN_INVITE){
			if (clientThread.getParty().getPartyLeader().getUserAccount().getID()  == clientThread.getUserAccount().getID()){
				clientThread.getParty().setAnyoneInvite(anyoneCanInvite);
			}
		}
		
		return true;
	
	}

	private void sendResult(){
		clientThread.getOutput().println(LobbyOPCode.OP_PARTYCHANGE);
		clientThread.getOutput().println("M" + "," + result);
	}

	public void undo() {
		
	}


}
