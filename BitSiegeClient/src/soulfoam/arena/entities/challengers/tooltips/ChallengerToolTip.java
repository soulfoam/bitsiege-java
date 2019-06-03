package soulfoam.arena.entities.challengers.tooltips;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public abstract class ChallengerToolTip {

	private float x;
	private float y;

	protected Image si1;
	protected Image si2;
	protected Image si3;
	protected Image si4;
	
	public abstract void update(GameContainer gc);

	public abstract void render(Graphics g);

	public static ChallengerToolTip getChallengerToolTip(int challengerID) {
		float x = 0;
		float y = 0;

		if (challengerID == EntityInfo.KNIGHTCHALLENGER) {
			return new KnightToolTip(x, y);
		}
		if (challengerID == EntityInfo.WARLOCKCHALLENGER) {
			return new WarlockToolTip(x, y);
		}
		if (challengerID == EntityInfo.ARCHERCHALLENGER) {
			return new ArcherToolTip(x, y);
		}
		if (challengerID == EntityInfo.CLERICCHALLENGER) {
			return new ClericToolTip(x, y);
		}
		if (challengerID == EntityInfo.ILLUSIONISTCHALLENGER) {
			return new IllusionistToolTip(x, y);
		}
		if (challengerID == EntityInfo.VOIDLORDCHALLENGER) {
			return new VoidLordToolTip(x, y);
		}
		if (challengerID == EntityInfo.WATERQUEENCHALLENGER) {
			return new WaterQueenToolTip(x, y);
		}
		if (challengerID == EntityInfo.SHAMANCHALLENGER) {
			return new ShamanToolTip(x, y);
		}

		return null;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
