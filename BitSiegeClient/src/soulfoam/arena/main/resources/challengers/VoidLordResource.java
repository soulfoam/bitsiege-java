package soulfoam.arena.main.resources.challengers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.resources.Res;

public class VoidLordResource {

	private SpriteSheet aoe;
	private SpriteSheet pull;
	private SpriteSheet ult;

	public Image[] WALK_UP = new Image[4];
	public Image[] WALK_LEFT = new Image[4];
	public Image[] WALK_DOWN = new Image[4];
	public Image[] WALK_RIGHT = new Image[4];

	public Image[] IDLE_UP = new Image[4];
	public Image[] IDLE_LEFT = new Image[4];
	public Image[] IDLE_DOWN = new Image[4];
	public Image[] IDLE_RIGHT = new Image[4];

	public Image[] ULT = new Image[5];
	public Image[] AOE = new Image[4];

	public Image[] PULL = new Image[3];

	public Image[] WALK_UP_BLOOD = new Image[4];
	public Image[] WALK_LEFT_BLOOD = new Image[4];
	public Image[] WALK_DOWN_BLOOD = new Image[4];
	public Image[] WALK_RIGHT_BLOOD = new Image[4];

	public Image[] IDLE_UP_BLOOD = new Image[4];
	public Image[] IDLE_LEFT_BLOOD = new Image[4];
	public Image[] IDLE_DOWN_BLOOD = new Image[4];
	public Image[] IDLE_RIGHT_BLOOD = new Image[4];

	public Image[] ULT_BLOOD = new Image[5];
	public Image[] AOE_BLOOD = new Image[4];

	public Image[] PULL_BLOOD = new Image[3];

	public Image[] VOIDLORD_HOTBAR_ICONS = new Image[5];
	public Sound[] SFX_VOIDLORD = new Sound[5];

	public Image[] PORTRAITS = new Image[2];

	public VoidLordResource() {

		loadCharacter();
		loadAbilities();
		loadSounds();
		loadHotbarIcons();
		loadPortraits();
	}

	private void loadAbilities() {
		aoe = new SpriteSheet("art/characters/voidlord/aoe.png");
		AOE = aoe.grabRow(0, 48, 32, 4);
		AOE_BLOOD = aoe.grabRow(1, 48, 32, 4);

		ult = new SpriteSheet("art/characters/voidlord/ult.png");
		ULT = ult.grabRow(0, 72, 40, 5);
		ULT_BLOOD = ult.grabRow(1, 72, 40, 5);

		pull = new SpriteSheet("art/characters/voidlord/pull.png");
		PULL = pull.grabRow(0, 24, 24, 3);
		PULL_BLOOD = pull.grabRow(1, 24, 24, 3);
	}

	private void loadHotbarIcons() {
		SpriteSheet hotbarIcons = new SpriteSheet("art/characters/voidlord/icons.png");
		VOIDLORD_HOTBAR_ICONS = hotbarIcons.grabRow(0, 16, 16);
	}
	
	private void loadPortraits(){
		SpriteSheet portraits = new SpriteSheet("art/characters/voidlord/portraits.png");
		PORTRAITS[0] = portraits.grabImage(0, 0, 16, 16);
		PORTRAITS[1] = portraits.grabImage(1, 0, 16, 16);
	}

	private void loadSounds() {
		Res.loadSound("sound/sfx/voidlord/aa.ogg", SFX_VOIDLORD, 0);
		Res.loadSound("sound/sfx/voidlord/ability1.ogg", SFX_VOIDLORD, 1);
		Res.loadSound("sound/sfx/voidlord/ability2.ogg", SFX_VOIDLORD, 2);
		Res.loadSound("sound/sfx/voidlord/ability3.ogg", SFX_VOIDLORD, 3);
		Res.loadSound("sound/sfx/voidlord/ability4.ogg", SFX_VOIDLORD, 4);
	}

	private void loadCharacter() {
		SpriteSheet characterSheet = new SpriteSheet("art/characters/voidlord/voidlord.png");

		IDLE_UP = characterSheet.grabCharacter(0, 32, 32, 4); 
		IDLE_LEFT = characterSheet.grabCharacter(1, 32, 32, 4); 
		IDLE_DOWN = characterSheet.grabCharacter(2, 32, 32, 4); 
		IDLE_RIGHT = characterSheet.grabCharacter(3, 32, 32, 4); 
		
		WALK_UP = characterSheet.grabCharacter(4, 32, 32, 4); 
		WALK_LEFT = characterSheet.grabCharacter(5, 32, 32, 4); 
		WALK_DOWN = characterSheet.grabCharacter(6, 32, 32, 4); 
		WALK_RIGHT = characterSheet.grabCharacter(7, 32, 32, 4); 

		IDLE_UP_BLOOD = characterSheet.grabCharacter(8, 32, 32, 4); 
		IDLE_LEFT_BLOOD = characterSheet.grabCharacter(9, 32, 32, 4); 
		IDLE_DOWN_BLOOD = characterSheet.grabCharacter(10, 32, 32, 4); 
		IDLE_RIGHT_BLOOD = characterSheet.grabCharacter(11, 32, 32, 4); 
		
		WALK_UP_BLOOD = characterSheet.grabCharacter(12, 32, 32, 4); 
		WALK_LEFT_BLOOD = characterSheet.grabCharacter(13, 32, 32, 4); 
		WALK_DOWN_BLOOD = characterSheet.grabCharacter(14, 32, 32, 4); 
		WALK_RIGHT_BLOOD = characterSheet.grabCharacter(15, 32, 32, 4); 

	}

}
