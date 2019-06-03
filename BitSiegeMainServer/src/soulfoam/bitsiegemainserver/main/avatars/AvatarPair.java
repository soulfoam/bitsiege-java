package soulfoam.bitsiegemainserver.main.avatars;

public class AvatarPair{

	private int id;
	private int slot;
	
	public AvatarPair(int id, int slot){
		this.id = id;
		this.slot = slot;
	}
	
	public int getID() {
		return id;
	}


	public int getSlot() {
		return slot;
	}
	
}