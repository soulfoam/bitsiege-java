package soulfoam.bitsiegemainserver.main.misc;

import java.util.ArrayList;

public class IDPool {
	
	private ArrayList<Integer> partyGroupIDPool = new ArrayList<Integer>();
	
	public IDPool(){
		initPools();
	}
	
	public void initPools(){
		initPartyPool();
	}
	
	public void initPartyPool(){
		partyGroupIDPool.clear();
		for (int i = 0; i < 1000; i++){
			partyGroupIDPool.add(i);
		}
	}
	
	public int getAvailablePartyID(){
		if (partyGroupIDPool.isEmpty()){
			initPartyPool();
		}
		return partyGroupIDPool.remove(0);
	}
	
	public void addPartyIDToPool(int id){
		partyGroupIDPool.add(id);
	}
}
