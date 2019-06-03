package soulfoam.arena.entities.challengers.skins;

import soulfoam.arena.entities.Entity;

public class Skin {

	private int skinID;
	private Entity player;

	private ArcherSkin archerSkin;
	private ClericSkin clericSkin;
	private IllusionistSkin illusionistSkin;
	private KnightSkin knightSkin;
	private ShamanSkin shamanSkin;
	private VoidLordSkin voidLordSkin;
	private WarlockSkin warlockSkin;
	private WaterQueenSkin waterQueenSkin;

	public Skin(Entity player, int skinID) {
		this.player = player;
		this.skinID = skinID;

		archerSkin = new ArcherSkin(skinID);
		clericSkin = new ClericSkin(skinID);
		illusionistSkin = new IllusionistSkin(skinID);
		knightSkin = new KnightSkin(skinID);
		shamanSkin = new ShamanSkin(skinID);
		voidLordSkin = new VoidLordSkin(skinID);
		warlockSkin = new WarlockSkin(skinID);
		waterQueenSkin = new WaterQueenSkin(skinID);
	}

	public int getSkinID() {
		return skinID;
	}

	public Entity getPlayer() {
		return player;
	}

	public ArcherSkin getArcherSkin() {
		return archerSkin;
	}

	public ClericSkin getClericSkin() {
		return clericSkin;
	}

	public IllusionistSkin getIllusionistSkin() {
		return illusionistSkin;
	}

	public KnightSkin getKnightSkin() {
		return knightSkin;
	}

	public ShamanSkin getShamanSkin() {
		return shamanSkin;
	}

	public VoidLordSkin getVoidLordSkin() {
		return voidLordSkin;
	}

	public WarlockSkin getWarlockSkin() {
		return warlockSkin;
	}

	public WaterQueenSkin getWaterQueenSkin() {
		return waterQueenSkin;
	}

}
