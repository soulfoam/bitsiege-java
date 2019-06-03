package soulfoam.bitsiegemainserver.main.accounts;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import soulfoam.bitsiegemainserver.main.ClientThread;

public class ActiveAccountManager {

	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();

	public ArrayList<ClientThread> getClients() {
		return clients;
	}
	
	public ClientThread getClientFromUsername(String username){
		for (ClientThread ct : clients) {
			if (ct.getUserAccount().getUsername().equalsIgnoreCase(username)){
				return ct;
			}
		}

		return null;
	}
	
	public ClientThread getClientFromAccountID(int accountID){
		for (ClientThread ct : clients) {
			if (ct.getUserAccount().getID() == accountID){
				return ct;
			}
		}
		
		return null;
	}
	
	public boolean isLoggedInFromUsername(String username){
		
		ClientThread ct = null;
		ct = getClientFromUsername(username);
		
		if (ct != null){
			return ct.getUserAccount().isLoggedIn();
		}
		
		return false;
	}
	
	public boolean isLoggedInFromID(int accountID){
		
		ClientThread ct = null;
		ct = getClientFromAccountID(accountID);
		
		if (ct != null){
			return ct.getUserAccount().isLoggedIn();
		}
		
		return false;
	}

}
