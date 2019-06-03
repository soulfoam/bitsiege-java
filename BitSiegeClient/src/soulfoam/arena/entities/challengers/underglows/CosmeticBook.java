package soulfoam.arena.entities.challengers.underglows;

import org.newdawn.slick.Animation;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;

public class CosmeticBook {

	public static Animation getUnderglowAnimation(int underglowID) {
		int animSpeed = 100;

		if (underglowID == CosmeticLibrary.UNDERGLOW_ORANGE) {
			return new Animation(Res.COSMETIC_RESOURCE.ORANGE_UNDERGLOW, animSpeed);
		}
		if (underglowID == CosmeticLibrary.UNDERGLOW_BLUE) {
			return new Animation(Res.COSMETIC_RESOURCE.BLUE_UNDERGLOW, animSpeed);
		}
		if (underglowID == CosmeticLibrary.UNDERGLOW_WHITE) {
			return new Animation(Res.COSMETIC_RESOURCE.WHITE_UNDERGLOW, animSpeed);
		}
		if (underglowID == CosmeticLibrary.UNDERGLOW_PURPLE) {
			return new Animation(Res.COSMETIC_RESOURCE.PURPLE_UNDERGLOW, animSpeed);
		}
		if (underglowID == CosmeticLibrary.UNDERGLOW_GREEN) {
			return new Animation(Res.COSMETIC_RESOURCE.GREEN_UNDERGLOW, animSpeed);
		}
		if (underglowID == CosmeticLibrary.UNDERGLOW_RED) {
			return new Animation(Res.COSMETIC_RESOURCE.RED_UNDERGLOW, animSpeed);
		}
		if (underglowID == CosmeticLibrary.UNDERGLOW_RAINBOW) {
			return new Animation(Res.COSMETIC_RESOURCE.RAINBOW_UNDERGLOW, animSpeed);
		}
		if (underglowID == CosmeticLibrary.UNDERGLOW_YELLOW) {
			return new Animation(Res.COSMETIC_RESOURCE.YELLOW_UNDERGLOW, animSpeed);
		}
		if (underglowID == CosmeticLibrary.UNDERGLOW_BLOOD_SPIN) {
			return new Animation(Res.COSMETIC_RESOURCE.BLOODSPIN_UNDERGLOW, animSpeed);
		}

		return null;
	}

}
