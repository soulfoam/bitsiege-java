package soulfoam.arena.main.resources.challengers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.resources.Res;

public class KnightResource {

	private SpriteSheet shieldThrow;
	private SpriteSheet spin;

	public Image[] WALK_UP = new Image[8];
	public Image[] WALK_LEFT = new Image[8];
	public Image[] WALK_DOWN = new Image[8];
	public Image[] WALK_RIGHT = new Image[8];
	public Image[] IDLE_UP = new Image[4];
	public Image[] IDLE_LEFT = new Image[4];
	public Image[] IDLE_DOWN = new Image[4];
	public Image[] IDLE_RIGHT = new Image[4];

	public Image[] WALK_UP_BLOOD = new Image[8];
	public Image[] WALK_LEFT_BLOOD = new Image[8];
	public Image[] WALK_DOWN_BLOOD = new Image[8];
	public Image[] WALK_RIGHT_BLOOD = new Image[8];
	public Image[] IDLE_UP_BLOOD = new Image[4];
	public Image[] IDLE_LEFT_BLOOD = new Image[4];
	public Image[] IDLE_DOWN_BLOOD = new Image[4];
	public Image[] IDLE_RIGHT_BLOOD = new Image[4];

	public Image[] SHIELD_THROW = new Image[4];
	public Image[] SHIELD_THROW_BLOOD = new Image[4];

	public Image[] SPIN = new Image[4];
	public Image[] SPIN_BLOOD = new Image[4];

	public Sound[] SFX_KNIGHT = new Sound[5];
	public Image[] KNIGHT_HOTBAR_ICONS = new Image[4];

	public Image[] PORTRAITS = new Image[2];
	
	public KnightResource() {

		loadCharacter();
		loadAbilities();
		loadSounds();
		loadHotbarIcons();
		loadPortraits();
		
	}
	
	private void loadPortraits(){
		SpriteSheet portraits = new SpriteSheet("art/characters/knight/portraits.png");
		PORTRAITS[0] = portraits.grabImage(0, 0, 16, 16);
		PORTRAITS[1] = portraits.grabImage(1, 0, 16, 16);
	}

	public void loadAbilities() {
		shieldThrow = new SpriteSheet("art/characters/knight/shieldthrow.png");

		SHIELD_THROW = shieldThrow.grabRow(0, 16, 16, 4);
		SHIELD_THROW_BLOOD = shieldThrow.grabRow(1, 16, 16, 4);

		spin = new SpriteSheet("art/characters/knight/spin.png");

		SPIN = spin.grabRow(0, 40, 16, 4);
		SPIN_BLOOD = spin.grabRow(1, 40, 16, 4);
	}

	public void loadHotbarIcons() {
		SpriteSheet hotbarIcons = new SpriteSheet("art/characters/knight/icons.png");
		KNIGHT_HOTBAR_ICONS = hotbarIcons.grabRow(0, 16, 16);
	}

	public void loadSounds() {
		Res.loadSound("sound/sfx/knight/aa.ogg", SFX_KNIGHT, 0);
		Res.loadSound("sound/sfx/knight/ability1.ogg", SFX_KNIGHT, 1);
		Res.loadSound("sound/sfx/knight/ability2.ogg", SFX_KNIGHT, 2);
		Res.loadSound("sound/sfx/knight/ability3.ogg", SFX_KNIGHT, 3);
		Res.loadSound("sound/sfx/knight/ability4.ogg", SFX_KNIGHT, 4);
	}

	public void loadCharacter() {
		SpriteSheet characterSheet = new SpriteSheet("art/characters/knight/knight.png");

		IDLE_UP = characterSheet.grabCharacter(0, 32, 32, 4);
		IDLE_LEFT = characterSheet.grabCharacter(1, 32, 32, 4);
		IDLE_DOWN = characterSheet.grabCharacter(2, 32, 32, 4);
		IDLE_RIGHT = characterSheet.grabCharacter(3, 32, 32, 4);
		
		WALK_UP = characterSheet.grabCharacter(4, 32, 32, 8);
		WALK_LEFT = characterSheet.grabCharacter(5, 32, 32, 8);
		WALK_DOWN = characterSheet.grabCharacter(6, 32, 32, 8);
		WALK_RIGHT = characterSheet.grabCharacter(7, 32, 32, 8);
		

		IDLE_UP_BLOOD = characterSheet.grabCharacter(0, 32, 32, 4);
		IDLE_LEFT_BLOOD = characterSheet.grabCharacter(1, 32, 32, 4);
		IDLE_DOWN_BLOOD = characterSheet.grabCharacter(2, 32, 32, 4);
		IDLE_RIGHT_BLOOD = characterSheet.grabCharacter(3, 32, 32, 4);
		
		WALK_UP_BLOOD = characterSheet.grabCharacter(4, 32, 32, 8);
		WALK_LEFT_BLOOD = characterSheet.grabCharacter(5, 32, 32, 8);
		WALK_DOWN_BLOOD = characterSheet.grabCharacter(6, 32, 32, 8);
		WALK_RIGHT_BLOOD = characterSheet.grabCharacter(7, 32, 32, 8);






	}
}
