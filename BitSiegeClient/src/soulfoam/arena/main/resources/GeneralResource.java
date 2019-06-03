package soulfoam.arena.main.resources;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import soulfoam.arena.main.gfx.SpriteSheet;

public class GeneralResource {

	private SpriteSheet meleeSheet;
	private SpriteSheet shieldSheet;
	private SpriteSheet respawnSheet;

	public Image[] MELEE_WHITE = new Image[3];
	public Image[] MELEE_RED = new Image[3];
	public Image[] MELEE_PURPLE = new Image[3];

	public Image[] SHIELD_ANIMATION = new Image[1];

	public Image[] RESPAWN_ANIMATION = new Image[14];

	
	public GeneralResource() {
		meleeSheet = new SpriteSheet("art/general/melee.png");
		shieldSheet = new SpriteSheet("art/general/shield.png");
		respawnSheet = new SpriteSheet("art/general/respawn.png");

		MELEE_WHITE = meleeSheet.grabRow(0, 24, 24, 3);
		MELEE_RED = meleeSheet.grabRow(1, 24, 24, 3);
		MELEE_PURPLE = meleeSheet.grabRow(2, 24, 24, 3);

		SHIELD_ANIMATION = shieldSheet.grabRow(0, 32, 32, 1);

		RESPAWN_ANIMATION = respawnSheet.grabRow(0, 24, 24, 14);

	}
}
