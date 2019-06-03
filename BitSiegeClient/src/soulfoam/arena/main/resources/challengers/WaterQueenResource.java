package soulfoam.arena.main.resources.challengers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.resources.Res;

public class WaterQueenResource {

	private SpriteSheet wave;
	private SpriteSheet waterball;
	private SpriteSheet absorb;
	private SpriteSheet autoattack;

	public Image[] WALK_UP = new Image[5];
	public Image[] WALK_LEFT = new Image[5];
	public Image[] WALK_DOWN = new Image[5];
	public Image[] WALK_RIGHT = new Image[5];
	public Image[] IDLE_UP = new Image[2];
	public Image[] IDLE_LEFT = new Image[2];
	public Image[] IDLE_DOWN = new Image[2];
	public Image[] IDLE_RIGHT = new Image[2];

	public Image[] WALK_UP_SWAMP = new Image[5];
	public Image[] WALK_LEFT_SWAMP = new Image[5];
	public Image[] WALK_DOWN_SWAMP = new Image[5];
	public Image[] WALK_RIGHT_SWAMP = new Image[5];
	public Image[] IDLE_UP_SWAMP = new Image[2];
	public Image[] IDLE_LEFT_SWAMP = new Image[2];
	public Image[] IDLE_DOWN_SWAMP = new Image[2];
	public Image[] IDLE_RIGHT_SWAMP = new Image[2];

	public Image[] WAVE = new Image[6];
	public Image[] WAVE_SWAMP = new Image[6];

	public Image[] AUTOATTACK = new Image[4];
	public Image[] AUTOATTACK_SWAMP = new Image[4];

	public Image[] WATERBALL = new Image[3];
	public Image[] WATERBALL_SWAMP = new Image[3];

	public Image[] ABSORB = new Image[8];
	public Image[] ABSORB_SWAMP = new Image[8];

	public Image[] WATERQUEEN_HOTBAR_ICONS = new Image[5];
	public Sound[] SFX_WATERQUEEN = new Sound[5];

	public Image[] PORTRAITS = new Image[2];

	public WaterQueenResource() {

		loadCharacter();
		loadAbilities();
		loadSounds();
		loadHotbarIcons();

		loadPortraits();
	}
	
	private void loadPortraits(){
		SpriteSheet portraits = new SpriteSheet("art/characters/waterqueen/portraits.png");
		PORTRAITS[0] = portraits.grabImage(0, 0, 16, 16);
		PORTRAITS[1] = portraits.grabImage(1, 0, 16, 16);
	}

	public void loadAbilities() {
		autoattack = new SpriteSheet("art/characters/waterqueen/autoattack.png");
		wave = new SpriteSheet("art/characters/waterqueen/wave.png");
		waterball = new SpriteSheet("art/characters/waterqueen/waterball.png");
		absorb = new SpriteSheet("art/characters/waterqueen/absorb.png");

		WAVE = wave.grabRow(0, 24, 24);
		WAVE_SWAMP = wave.grabRow(1, 24, 24);

		AUTOATTACK = autoattack.grabRow(0, 16, 16, 4);
		AUTOATTACK_SWAMP = autoattack.grabRow(1, 16, 16, 4);

		WATERBALL = waterball.grabRow(0, 24, 24, 3);
		WATERBALL_SWAMP = waterball.grabRow(1, 24, 24, 3);

		ABSORB = absorb.grabRow(0, 32, 32, 8);
		ABSORB_SWAMP = absorb.grabRow(1, 32, 32, 8);
	}

	public void loadHotbarIcons() {
		Res.loadImage("icons/waterqueenwave.png", WATERQUEEN_HOTBAR_ICONS, 0);
		Res.loadImage("icons/waterqueenball.png", WATERQUEEN_HOTBAR_ICONS, 1);
		Res.loadImage("icons/waterqueenabsorb.png", WATERQUEEN_HOTBAR_ICONS, 2);
		Res.loadImage("icons/waterqueenwaterwalk.png", WATERQUEEN_HOTBAR_ICONS, 3);
	}

	public void loadSounds() {
		Res.loadSound("sound/sfx/waterqueen/aa.ogg", SFX_WATERQUEEN, 0);
		Res.loadSound("sound/sfx/waterqueen/ability1.ogg", SFX_WATERQUEEN, 1);
		Res.loadSound("sound/sfx/waterqueen/ability2.ogg", SFX_WATERQUEEN, 2);
		Res.loadSound("sound/sfx/waterqueen/ability3.ogg", SFX_WATERQUEEN, 3);
		Res.loadSound("sound/sfx/waterqueen/ability4.ogg", SFX_WATERQUEEN, 4);
	}

	public void loadCharacter() {
		SpriteSheet characterSheet = new SpriteSheet("art/characters/waterqueen/waterqueen.png");
		Image[] characterUp = new Image[7];
		Image[] characterLeft = new Image[7];
		Image[] characterDown = new Image[7];
		Image[] characterRight = new Image[7];
		characterUp = characterSheet.grabCharacter(0, 24, 24, 7);
		characterLeft = characterSheet.grabCharacter(1, 24, 24, 7);
		characterDown = characterSheet.grabCharacter(2, 24, 24, 7);
		characterRight = characterSheet.grabCharacter(3, 24, 24, 7);

		IDLE_UP[0] = characterUp[0];
		IDLE_UP[1] = characterUp[1];

		WALK_UP[0] = characterUp[2];
		WALK_UP[1] = characterUp[3];
		WALK_UP[2] = characterUp[4];
		WALK_UP[3] = characterUp[5];
		WALK_UP[4] = characterUp[6];

		IDLE_LEFT[0] = characterLeft[0];
		IDLE_LEFT[1] = characterLeft[1];

		WALK_LEFT[0] = characterLeft[2];
		WALK_LEFT[1] = characterLeft[3];
		WALK_LEFT[2] = characterLeft[4];
		WALK_LEFT[3] = characterLeft[5];
		WALK_LEFT[4] = characterLeft[6];

		IDLE_DOWN[0] = characterDown[0];
		IDLE_DOWN[1] = characterDown[1];

		WALK_DOWN[0] = characterDown[2];
		WALK_DOWN[1] = characterDown[3];
		WALK_DOWN[2] = characterDown[4];
		WALK_DOWN[3] = characterDown[5];
		WALK_DOWN[4] = characterDown[6];

		IDLE_RIGHT[0] = characterRight[0];
		IDLE_RIGHT[1] = characterRight[1];

		WALK_RIGHT[0] = characterRight[2];
		WALK_RIGHT[1] = characterRight[3];
		WALK_RIGHT[2] = characterRight[4];
		WALK_RIGHT[3] = characterRight[5];
		WALK_RIGHT[4] = characterRight[6];

		Image[] characterUpSwamp = new Image[7];
		Image[] characterLeftSwamp = new Image[7];
		Image[] characterDownSwamp = new Image[7];
		Image[] characterRightSwamp = new Image[7];
		characterUpSwamp = characterSheet.grabCharacter(4, 24, 24, 7);
		characterLeftSwamp = characterSheet.grabCharacter(5, 24, 24, 7);
		characterDownSwamp = characterSheet.grabCharacter(6, 24, 24, 7);
		characterRightSwamp = characterSheet.grabCharacter(7, 24, 24, 7);

		IDLE_UP_SWAMP[0] = characterUpSwamp[0];
		IDLE_UP_SWAMP[1] = characterUpSwamp[1];

		WALK_UP_SWAMP[0] = characterUpSwamp[2];
		WALK_UP_SWAMP[1] = characterUpSwamp[3];
		WALK_UP_SWAMP[2] = characterUpSwamp[4];
		WALK_UP_SWAMP[3] = characterUpSwamp[5];
		WALK_UP_SWAMP[4] = characterUpSwamp[6];

		IDLE_LEFT_SWAMP[0] = characterLeftSwamp[0];
		IDLE_LEFT_SWAMP[1] = characterLeftSwamp[1];

		WALK_LEFT_SWAMP[0] = characterLeftSwamp[2];
		WALK_LEFT_SWAMP[1] = characterLeftSwamp[3];
		WALK_LEFT_SWAMP[2] = characterLeftSwamp[4];
		WALK_LEFT_SWAMP[3] = characterLeftSwamp[5];
		WALK_LEFT_SWAMP[4] = characterLeftSwamp[6];

		IDLE_DOWN_SWAMP[0] = characterDownSwamp[0];
		IDLE_DOWN_SWAMP[1] = characterDownSwamp[1];

		WALK_DOWN_SWAMP[0] = characterDownSwamp[2];
		WALK_DOWN_SWAMP[1] = characterDownSwamp[3];
		WALK_DOWN_SWAMP[2] = characterDownSwamp[4];
		WALK_DOWN_SWAMP[3] = characterDownSwamp[5];
		WALK_DOWN_SWAMP[4] = characterDownSwamp[6];

		IDLE_RIGHT_SWAMP[0] = characterRightSwamp[0];
		IDLE_RIGHT_SWAMP[1] = characterRightSwamp[1];

		WALK_RIGHT_SWAMP[0] = characterRightSwamp[2];
		WALK_RIGHT_SWAMP[1] = characterRightSwamp[3];
		WALK_RIGHT_SWAMP[2] = characterRightSwamp[4];
		WALK_RIGHT_SWAMP[3] = characterRightSwamp[5];
		WALK_RIGHT_SWAMP[4] = characterRightSwamp[6];

	}

}
