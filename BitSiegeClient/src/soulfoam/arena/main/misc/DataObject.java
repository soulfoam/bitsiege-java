package soulfoam.arena.main.misc;

public class DataObject {

	public float x, y;
	public int id;
	public byte team;

	public DataObject(float x, float y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public DataObject(float x, float y, byte team) {
		this.x = x;
		this.y = y;
		this.team = team;
	}

	public DataObject(float x, float y, int id, byte team) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.team = team;
	}
}
