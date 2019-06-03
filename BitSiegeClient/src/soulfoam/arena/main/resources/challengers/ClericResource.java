package soulfoam.arena.main.resources.challengers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.resources.Res;

public class ClericResource {

	private SpriteSheet aoeheal;
	private SpriteSheet autoattacks;
	private SpriteSheet blind;
	private SpriteSheet ultheal;

	public Image[] WALK_UP = new Image[5];
	public Image[] WALK_LEFT = new Image[5];
	public Image[] WALK_DOWN = new Image[5];
	public Image[] WALK_RIGHT = new Image[5];
	public Image[] IDLE_UP = new Image[2];
	public Image[] IDLE_LEFT = new Image[2];
	public Image[] IDLE_DOWN = new Image[2];
	public Image[] IDLE_RIGHT = new Image[2];

	public Image[] WALK_UP_AMETHYST = new Image[5];
	public Image[] WALK_LEFT_AMETHYST = new Image[5];
	public Image[] WALK_DOWN_AMETHYST = new Image[5];
	public Image[] WALK_RIGHT_AMETHYST = new Image[5];
	public Image[] IDLE_UP_AMETHYST = new Image[2];
	public Image[] IDLE_LEFT_AMETHYST = new Image[2];
	public Image[] IDLE_DOWN_AMETHYST = new Image[2];
	public Image[] IDLE_RIGHT_AMETHYST = new Image[2];

	public Image[] AOE_HEAL = new Image[8];

	public Image[] AUTOATTACK_HEAL = new Image[6];
	public Image[] AUTOATTACK_DAMAGE = new Image[6];

	public Image[] BLIND = new Image[6];
	public Image[] BLIND_AMETHYST = new Image[6];

	public Image[] ULT_HEAL = new Image[10];

	public Image[] CLERIC_HOTBAR_ICONS = new Image[5];
	public Sound[] SFX_CLERIC = new Sound[7];

	public Image[] PORTRAITS = new Image[2];

	public ClericResource() {

		loadCharacter();
		loadAbilities();
		loadSounds();
		loadHotbarIcons();
		loadPortraits();
		
	}

	private void loadPortraits(){
		SpriteSheet portraits = new SpriteSheet("art/characters/cleric/portraits.png");
		PORTRAITS[0] = portraits.grabImage(0, 0, 16, 16);
		PORTRAITS[1] = portraits.grabImage(1, 0, 16, 16);
	}
	
	public void loadAbilities() {
		aoeheal = new SpriteSheet("art/characters/cleric/aoeheal.png");
		autoattacks = new SpriteSheet("art/characters/cleric/autoattacks.png");
		blind = new SpriteSheet("art/characters/cleric/blind.png");
		ultheal = new SpriteSheet("art/characters/cleric/ultheal.png");

		AOE_HEAL = aoeheal.grabRow(0, 112, 64, 8);

		AUTOATTACK_HEAL = autoattacks.grabRow(0, 16, 16);
		AUTOATTACK_DAMAGE = autoattacks.grabRow(1, 16, 16);

		BLIND = blind.grabRow(0, 16, 16);
		BLIND_AMETHYST = blind.grabRow(1, 16, 16);

		ULT_HEAL = ultheal.grabRow(0, 16, 32, 10);
	}

	public void loadHotbarIcons() {
		Res.loadImage("icons/clerichealicon.png", CLERIC_HOTBAR_ICONS, 0);
		Res.loadImage("icons/clericblindicon.png", CLERIC_HOTBAR_ICONS, 1);
		Res.loadImage("icons/clericswitchicon.png", CLERIC_HOTBAR_ICONS, 2);
		Res.loadImage("icons/clericrevivehealicon.png", CLERIC_HOTBAR_ICONS, 3);
	}

	public void loadSounds() {
		Res.loadSound("sound/sfx/cleric/aadamage.ogg", SFX_CLERIC, 0);
		Res.loadSound("sound/sfx/cleric/aaheal.ogg", SFX_CLERIC, 1);
		Res.loadSound("sound/sfx/cleric/ability1.ogg", SFX_CLERIC, 2);
		Res.loadSound("sound/sfx/cleric/ability2.ogg", SFX_CLERIC, 3);
		Res.loadSound("sound/sfx/cleric/ability3switchheal.ogg", SFX_CLERIC, 4);
		Res.loadSound("sound/sfx/cleric/ability3switchdamage.ogg", SFX_CLERIC, 5);
		Res.loadSound("sound/sfx/cleric/ability4.ogg", SFX_CLERIC, 6);
	}

	public void loadCharacter() {
		SpriteSheet characterSheet = new SpriteSheet("art/characters/cleric/cleric.png");
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

		Image[] characterUpAmethyst = new Image[7];
		Image[] characterLeftAmethyst = new Image[7];
		Image[] characterDownAmethyst = new Image[7];
		Image[] characterRightAmethyst = new Image[7];
		characterUpAmethyst = characterSheet.grabCharacter(4, 24, 24, 7);
		characterLeftAmethyst = characterSheet.grabCharacter(5, 24, 24, 7);
		characterDownAmethyst = characterSheet.grabCharacter(6, 24, 24, 7);
		characterRightAmethyst = characterSheet.grabCharacter(7, 24, 24, 7);

		IDLE_UP_AMETHYST[0] = characterUpAmethyst[0];
		IDLE_UP_AMETHYST[1] = characterUpAmethyst[1];

		WALK_UP_AMETHYST[0] = characterUpAmethyst[2];
		WALK_UP_AMETHYST[1] = characterUpAmethyst[3];
		WALK_UP_AMETHYST[2] = characterUpAmethyst[4];
		WALK_UP_AMETHYST[3] = characterUpAmethyst[5];
		WALK_UP_AMETHYST[4] = characterUpAmethyst[6];

		IDLE_LEFT_AMETHYST[0] = characterLeftAmethyst[0];
		IDLE_LEFT_AMETHYST[1] = characterLeftAmethyst[1];

		WALK_LEFT_AMETHYST[0] = characterLeftAmethyst[2];
		WALK_LEFT_AMETHYST[1] = characterLeftAmethyst[3];
		WALK_LEFT_AMETHYST[2] = characterLeftAmethyst[4];
		WALK_LEFT_AMETHYST[3] = characterLeftAmethyst[5];
		WALK_LEFT_AMETHYST[4] = characterLeftAmethyst[6];

		IDLE_DOWN_AMETHYST[0] = characterDownAmethyst[0];
		IDLE_DOWN_AMETHYST[1] = characterDownAmethyst[1];

		WALK_DOWN_AMETHYST[0] = characterDownAmethyst[2];
		WALK_DOWN_AMETHYST[1] = characterDownAmethyst[3];
		WALK_DOWN_AMETHYST[2] = characterDownAmethyst[4];
		WALK_DOWN_AMETHYST[3] = characterDownAmethyst[5];
		WALK_DOWN_AMETHYST[4] = characterDownAmethyst[6];

		IDLE_RIGHT_AMETHYST[0] = characterRightAmethyst[0];
		IDLE_RIGHT_AMETHYST[1] = characterRightAmethyst[1];

		WALK_RIGHT_AMETHYST[0] = characterRightAmethyst[2];
		WALK_RIGHT_AMETHYST[1] = characterRightAmethyst[3];
		WALK_RIGHT_AMETHYST[2] = characterRightAmethyst[4];
		WALK_RIGHT_AMETHYST[3] = characterRightAmethyst[5];
		WALK_RIGHT_AMETHYST[4] = characterRightAmethyst[6];

	}
}
