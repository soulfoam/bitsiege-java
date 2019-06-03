package soulfoam.arena.main.resources.challengers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.resources.Res;

public class WarlockResource {

	private SpriteSheet lifeDrain;
	private SpriteSheet lifeDrainBig;
	private SpriteSheet ult;
	private SpriteSheet teleport;
	private SpriteSheet tower;
	private SpriteSheet autoattack;
	private SpriteSheet towerattack;

	public Image[] WALK_UP = new Image[8];
	public Image[] WALK_LEFT = new Image[8];
	public Image[] WALK_DOWN = new Image[8];
	public Image[] WALK_RIGHT = new Image[8];
	public Image[] IDLE_UP = new Image[4];
	public Image[] IDLE_LEFT = new Image[4];
	public Image[] IDLE_DOWN = new Image[4];
	public Image[] IDLE_RIGHT = new Image[4];

	public Image[] WALK_UP_FROST = new Image[5];
	public Image[] WALK_LEFT_FROST = new Image[5];
	public Image[] WALK_DOWN_FROST = new Image[5];
	public Image[] WALK_RIGHT_FROST = new Image[5];
	public Image[] IDLE_UP_FROST = new Image[2];
	public Image[] IDLE_LEFT_FROST = new Image[2];
	public Image[] IDLE_DOWN_FROST = new Image[2];
	public Image[] IDLE_RIGHT_FROST = new Image[2];

	public Image[] TOWER = new Image[6];
	public Image[] TOWER_FROST = new Image[6];

	public Image[] AUTOATTACK = new Image[1];
	public Image[] AUTOATTACK_FROST = new Image[1];

	public Image[] TOWERATTACK = new Image[1];
	public Image[] TOWERATTACK_FROST = new Image[2];

	public Image[] LIFEDRAIN = new Image[6];
	public Image[] LIFEDRAIN_BIG = new Image[6];
	
	public Image[] TELEPORT_START = new Image[4];
	public Image[] TELEPORT_END = new Image[4];

	public Image[] STORMBIT = new Image[3];
	public Image[] STORMBIT_FROST = new Image[3];

	public Image[] WARLOCK_HOTBAR_ICONS = new Image[5];
	public Sound[] SFX_WARLOCK = new Sound[5];

	public Image[] PORTRAITS = new Image[2];

	public WarlockResource() {

		loadCharacter();
		loadAbilities();
		loadSounds();
		loadHotbarIcons();
		loadPortraits();
		
	}

	private void loadPortraits(){
		SpriteSheet portraits = new SpriteSheet("art/characters/warlock/portraits.png");
		PORTRAITS[0] = portraits.grabImage(0, 0, 16, 16);
		PORTRAITS[1] = portraits.grabImage(1, 0, 16, 16);
	}
	
	public void loadAbilities() {
		tower = new SpriteSheet("art/characters/warlock/tower.png");
		towerattack = new SpriteSheet("art/characters/warlock/towerattack.png");
		lifeDrain = new SpriteSheet("art/characters/warlock/lifedrain.png");
		lifeDrainBig = new SpriteSheet("art/characters/warlock/lifedrainbig.png");
		teleport = new SpriteSheet("art/characters/warlock/teleport.png");
		ult = new SpriteSheet("art/characters/warlock/ult.png");
		autoattack = new SpriteSheet("art/characters/warlock/autoattack.png");

		TOWER = tower.grabRow(0, 32, 24);
		TOWER_FROST = tower.grabRow(1, 32, 24);
		LIFEDRAIN = lifeDrain.grabRow(0, 48, 32);
		LIFEDRAIN_BIG = lifeDrainBig.grabRow(0, 96, 64);
		TELEPORT_START = teleport.grabRow(0, 56, 56, 4);
		TELEPORT_END = teleport.grabRow(1, 56, 56, 4);

		AUTOATTACK = autoattack.grabRow(0, 16, 16, 1);
		AUTOATTACK_FROST = autoattack.grabRow(1, 16, 16, 1);

		TOWERATTACK = towerattack.grabRow(0, 16, 16, 2);
		TOWERATTACK_FROST = towerattack.grabRow(1, 16, 16, 2);

		STORMBIT = ult.grabRow(0, 32, 24, 3);
		STORMBIT_FROST = ult.grabRow(1, 32, 24, 3);
	}

	public void loadHotbarIcons() {
		SpriteSheet hotbarIcons = new SpriteSheet("art/characters/warlock/icons.png");
		WARLOCK_HOTBAR_ICONS = hotbarIcons.grabRow(0, 16, 16);
	}

	public void loadSounds() {
		Res.loadSound("sound/sfx/warlock/aa.ogg", SFX_WARLOCK, 0);
		Res.loadSound("sound/sfx/warlock/ability1.ogg", SFX_WARLOCK, 1);
		Res.loadSound("sound/sfx/warlock/ability2.ogg", SFX_WARLOCK, 2);
		Res.loadSound("sound/sfx/warlock/ability3.ogg", SFX_WARLOCK, 3);
		Res.loadSound("sound/sfx/warlock/ability4.ogg", SFX_WARLOCK, 4);
	}

	public void loadCharacter() {
		SpriteSheet characterSheet = new SpriteSheet("art/characters/warlock/warlock.png");
		
		IDLE_UP = characterSheet.grabCharacter(0, 32, 32, 4);
		IDLE_LEFT = characterSheet.grabCharacter(1, 32, 32, 4);
		IDLE_DOWN = characterSheet.grabCharacter(2, 32, 32, 4);
		IDLE_RIGHT = characterSheet.grabCharacter(3, 32, 32, 4);
		
		WALK_UP = characterSheet.grabCharacter(4, 32, 32, 8);
		WALK_LEFT = characterSheet.grabCharacter(5, 32, 32, 8);
		WALK_DOWN = characterSheet.grabCharacter(6, 32, 32, 8);
		WALK_RIGHT = characterSheet.grabCharacter(7, 32, 32, 8);

		Image[] characterUpFrost = new Image[7];
		Image[] characterLeftFrost = new Image[7];
		Image[] characterDownFrost = new Image[7];
		Image[] characterRightFrost = new Image[7];
		characterUpFrost = characterSheet.grabCharacter(4, 24, 24, 7);
		characterLeftFrost = characterSheet.grabCharacter(5, 24, 24, 7);
		characterDownFrost = characterSheet.grabCharacter(6, 24, 24, 7);
		characterRightFrost = characterSheet.grabCharacter(7, 24, 24, 7);

		IDLE_UP_FROST[0] = characterUpFrost[0];
		IDLE_UP_FROST[1] = characterUpFrost[1];

		WALK_UP_FROST[0] = characterUpFrost[2];
		WALK_UP_FROST[1] = characterUpFrost[3];
		WALK_UP_FROST[2] = characterUpFrost[4];
		WALK_UP_FROST[3] = characterUpFrost[5];
		WALK_UP_FROST[4] = characterUpFrost[6];

		IDLE_LEFT_FROST[0] = characterLeftFrost[0];
		IDLE_LEFT_FROST[1] = characterLeftFrost[1];

		WALK_LEFT_FROST[0] = characterLeftFrost[2];
		WALK_LEFT_FROST[1] = characterLeftFrost[3];
		WALK_LEFT_FROST[2] = characterLeftFrost[4];
		WALK_LEFT_FROST[3] = characterLeftFrost[5];
		WALK_LEFT_FROST[4] = characterLeftFrost[6];

		IDLE_DOWN_FROST[0] = characterDownFrost[0];
		IDLE_DOWN_FROST[1] = characterDownFrost[1];

		WALK_DOWN_FROST[0] = characterDownFrost[2];
		WALK_DOWN_FROST[1] = characterDownFrost[3];
		WALK_DOWN_FROST[2] = characterDownFrost[4];
		WALK_DOWN_FROST[3] = characterDownFrost[5];
		WALK_DOWN_FROST[4] = characterDownFrost[6];

		IDLE_RIGHT_FROST[0] = characterRightFrost[0];
		IDLE_RIGHT_FROST[1] = characterRightFrost[1];

		WALK_RIGHT_FROST[0] = characterRightFrost[2];
		WALK_RIGHT_FROST[1] = characterRightFrost[3];
		WALK_RIGHT_FROST[2] = characterRightFrost[4];
		WALK_RIGHT_FROST[3] = characterRightFrost[5];
		WALK_RIGHT_FROST[4] = characterRightFrost[6];
		
	}

}
