package soulfoam.arena.main.resources.cosmetic;

import org.newdawn.slick.Image;

import soulfoam.arena.main.gfx.SpriteSheet;

public class CosmeticResource {

	public Image[] WHITE_UNDERGLOW = new Image[1];
	public Image[] ORANGE_UNDERGLOW = new Image[1];
	public Image[] BLUE_UNDERGLOW = new Image[1];
	public Image[] PURPLE_UNDERGLOW = new Image[1];
	public Image[] GREEN_UNDERGLOW = new Image[1];
	public Image[] RED_UNDERGLOW = new Image[1];
	public Image[] RAINBOW_UNDERGLOW = new Image[1];
	public Image[] YELLOW_UNDERGLOW = new Image[1];

	public Image[] BLOODSPIN_UNDERGLOW = new Image[4];

	public CosmeticResource() {
		loadUnderglows();
	}

	public void loadUnderglows() {
		SpriteSheet underglows = new SpriteSheet("art/general/underglows.png");
		SpriteSheet animatedUnderglows = new SpriteSheet("art/general/animatedunderglows.png");

		WHITE_UNDERGLOW = underglows.grabRow(0, 16, 16, 1);
		ORANGE_UNDERGLOW = underglows.grabRow(1, 16, 16, 1);
		BLUE_UNDERGLOW = underglows.grabRow(2, 16, 16, 1);
		PURPLE_UNDERGLOW = underglows.grabRow(3, 16, 16, 1);
		GREEN_UNDERGLOW = underglows.grabRow(4, 16, 16, 1);
		RED_UNDERGLOW = underglows.grabRow(5, 16, 16, 1);
		RAINBOW_UNDERGLOW = underglows.grabRow(6, 16, 16, 1);
		YELLOW_UNDERGLOW = underglows.grabRow(7, 16, 16, 1);

		BLOODSPIN_UNDERGLOW = animatedUnderglows.grabRow(0, 16, 16, 4);
	}
}
