package soulfoam.arena.main.resources.challengers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.resources.Res;

public class ShamanResource {

	private SpriteSheet totems;
	private SpriteSheet autoattack;
	private SpriteSheet ult;
	private SpriteSheet bufftotemradius;
	private SpriteSheet shieldtotemradius;
	private SpriteSheet debufftotemradius;
	private SpriteSheet confusetotemradius;

	public Image[] WALK_UP = new Image[8];
	public Image[] WALK_LEFT = new Image[8];
	public Image[] WALK_DOWN = new Image[8];
	public Image[] WALK_RIGHT = new Image[8];
	public Image[] IDLE_UP = new Image[4];
	public Image[] IDLE_LEFT = new Image[4];
	public Image[] IDLE_DOWN = new Image[4];
	public Image[] IDLE_RIGHT = new Image[4];

	public Image[] WALK_UP_VOID = new Image[5];
	public Image[] WALK_LEFT_VOID = new Image[5];
	public Image[] WALK_DOWN_VOID = new Image[5];
	public Image[] WALK_RIGHT_VOID = new Image[5];
	public Image[] IDLE_UP_VOID = new Image[2];
	public Image[] IDLE_LEFT_VOID = new Image[2];
	public Image[] IDLE_DOWN_VOID = new Image[2];
	public Image[] IDLE_RIGHT_VOID = new Image[2];

	public Image[] AUTO_ATTACK = new Image[2];
	public Image[] AUTO_ATTACK_VOID = new Image[2];

	public Image[] BUFF_TOTEM = new Image[1];
	public Image[] DEBUFF_TOTEM = new Image[1];
	public Image[] SHIELD_TOTEM = new Image[1];
	public Image[] CONFUSE_TOTEM = new Image[1];

	public Image[] BUFF_TOTEM_VOID = new Image[1];
	public Image[] DEBUFF_TOTEM_VOID = new Image[1];
	public Image[] SHIELD_TOTEM_VOID = new Image[1];
	public Image[] CONFUSE_TOTEM_VOID = new Image[1];

	public Image[] BUFF_TOTEM_RADIUS_ALLY = new Image[8];
	public Image[] BUFF_TOTEM_RADIUS_ENEMY = new Image[8];

	public Image[] SHIELD_TOTEM_RADIUS_ALLY = new Image[8];
	public Image[] SHIELD_TOTEM_RADIUS_ENEMY = new Image[8];

	public Image[] DEBUFF_TOTEM_RADIUS_ALLY = new Image[8];
	public Image[] DEBUFF_TOTEM_RADIUS_ENEMY = new Image[8];

	public Image[] MEGA_TOTEM_RADIUS_ALLY = new Image[8];
	public Image[] MEGA_TOTEM_RADIUS_ENEMY = new Image[8];

	public Image[] SHAMAN_HOTBAR_ICONS = new Image[5];
	public Sound[] SFX_SHAMAN = new Sound[3];

	public Image[] PORTRAITS = new Image[2];

	public ShamanResource() {

		loadCharacter();
		loadAbilities();
		loadSounds();
		loadHotbarIcons();
		loadPortraits();
		
	}

	private void loadPortraits(){
		SpriteSheet portraits = new SpriteSheet("art/characters/shaman/portraits.png");
		PORTRAITS[0] = portraits.grabImage(0, 0, 16, 16);
		PORTRAITS[1] = portraits.grabImage(1, 0, 16, 16);
	}
	
	public void loadAbilities() {
		autoattack = new SpriteSheet("art/characters/shaman/autoattack.png");
		totems = new SpriteSheet("art/characters/shaman/totems.png");
		ult = new SpriteSheet("art/characters/shaman/ult.png");

		AUTO_ATTACK = autoattack.grabRow(0, 16, 16, 2);
		AUTO_ATTACK_VOID = autoattack.grabRow(1, 16, 16, 2);

		BUFF_TOTEM[0] = totems.grabImage(0, 0, 16, 16);
		DEBUFF_TOTEM[0] = totems.grabImage(1, 0, 16, 16);
		SHIELD_TOTEM[0] = totems.grabImage(2, 0, 16, 16);
		CONFUSE_TOTEM[0] = ult.grabImage(0, 0, 16, 24);

		BUFF_TOTEM_VOID[0] = totems.grabImage(0, 1, 16, 16);
		DEBUFF_TOTEM_VOID[0] = totems.grabImage(1, 1, 16, 16);
		SHIELD_TOTEM_VOID[0] = totems.grabImage(2, 1, 16, 16);
		CONFUSE_TOTEM_VOID[0] = ult.grabImage(0, 1, 16, 24);

		bufftotemradius = new SpriteSheet("art/characters/shaman/bufftotemradius.png");
		debufftotemradius = new SpriteSheet("art/characters/shaman/debufftotemradius.png");
		shieldtotemradius = new SpriteSheet("art/characters/shaman/shieldtotemradius.png");
		confusetotemradius = new SpriteSheet("art/characters/shaman/confusetotemradius.png");

		BUFF_TOTEM_RADIUS_ALLY = bufftotemradius.grabRow(0, 176, 96, 8);
		BUFF_TOTEM_RADIUS_ENEMY = bufftotemradius.grabRow(1, 176, 96, 8);

		SHIELD_TOTEM_RADIUS_ALLY = shieldtotemradius.grabRow(0, 176, 96, 8);
		SHIELD_TOTEM_RADIUS_ENEMY = shieldtotemradius.grabRow(1, 176, 96, 8);

		DEBUFF_TOTEM_RADIUS_ALLY = debufftotemradius.grabRow(1, 176, 96, 8);
		DEBUFF_TOTEM_RADIUS_ENEMY = debufftotemradius.grabRow(0, 176, 96, 8);

		MEGA_TOTEM_RADIUS_ALLY = confusetotemradius.grabRow(1, 176, 96, 8);
		MEGA_TOTEM_RADIUS_ENEMY = confusetotemradius.grabRow(0, 176, 96, 8);
	}

	public void loadHotbarIcons() {
		Res.loadImage("icons/shamanbufftotem.png", SHAMAN_HOTBAR_ICONS, 0);
		Res.loadImage("icons/shamanshieldtotem.png", SHAMAN_HOTBAR_ICONS, 1);
		Res.loadImage("icons/shamandebufftotem.png", SHAMAN_HOTBAR_ICONS, 2);
		Res.loadImage("icons/shamanmegatotem.png", SHAMAN_HOTBAR_ICONS, 3);
	}

	public void loadSounds() {
		Res.loadSound("sound/sfx/shaman/aa.ogg", SFX_SHAMAN, 0);
		Res.loadSound("sound/sfx/shaman/ability.ogg", SFX_SHAMAN, 1);
		Res.loadSound("sound/sfx/shaman/ability4.ogg", SFX_SHAMAN, 2);
	}

	public void loadCharacter() {
		
		SpriteSheet characterSheet = new SpriteSheet("art/characters/shaman/shaman.png");
		
		IDLE_UP = characterSheet.grabCharacter(0, 32, 32, 4);
		IDLE_LEFT = characterSheet.grabCharacter(1, 32, 32, 4);
		IDLE_DOWN = characterSheet.grabCharacter(2, 32, 32, 4);
		IDLE_RIGHT = characterSheet.grabCharacter(3, 32, 32, 4);
		
		WALK_UP = characterSheet.grabCharacter(4, 32, 32, 8);
		WALK_LEFT = characterSheet.grabCharacter(5, 32, 32, 8);
		WALK_DOWN = characterSheet.grabCharacter(6, 32, 32, 8);
		WALK_RIGHT = characterSheet.grabCharacter(7, 32, 32, 8);


		Image[] characterUpVoid = new Image[7];
		Image[] characterLeftVoid = new Image[7];
		Image[] characterDownVoid = new Image[7];
		Image[] characterRightVoid = new Image[7];
		characterUpVoid = characterSheet.grabCharacter(4, 24, 24, 7);
		characterLeftVoid = characterSheet.grabCharacter(5, 24, 24, 7);
		characterDownVoid = characterSheet.grabCharacter(6, 24, 24, 7);
		characterRightVoid = characterSheet.grabCharacter(7, 24, 24, 7);

		IDLE_UP_VOID[0] = characterUpVoid[0];
		IDLE_UP_VOID[1] = characterUpVoid[1];

		WALK_UP_VOID[0] = characterUpVoid[2];
		WALK_UP_VOID[1] = characterUpVoid[3];
		WALK_UP_VOID[2] = characterUpVoid[4];
		WALK_UP_VOID[3] = characterUpVoid[5];
		WALK_UP_VOID[4] = characterUpVoid[6];

		IDLE_LEFT_VOID[0] = characterLeftVoid[0];
		IDLE_LEFT_VOID[1] = characterLeftVoid[1];

		WALK_LEFT_VOID[0] = characterLeftVoid[2];
		WALK_LEFT_VOID[1] = characterLeftVoid[3];
		WALK_LEFT_VOID[2] = characterLeftVoid[4];
		WALK_LEFT_VOID[3] = characterLeftVoid[5];
		WALK_LEFT_VOID[4] = characterLeftVoid[6];

		IDLE_DOWN_VOID[0] = characterDownVoid[0];
		IDLE_DOWN_VOID[1] = characterDownVoid[1];

		WALK_DOWN_VOID[0] = characterDownVoid[2];
		WALK_DOWN_VOID[1] = characterDownVoid[3];
		WALK_DOWN_VOID[2] = characterDownVoid[4];
		WALK_DOWN_VOID[3] = characterDownVoid[5];
		WALK_DOWN_VOID[4] = characterDownVoid[6];

		IDLE_RIGHT_VOID[0] = characterRightVoid[0];
		IDLE_RIGHT_VOID[1] = characterRightVoid[1];

		WALK_RIGHT_VOID[0] = characterRightVoid[2];
		WALK_RIGHT_VOID[1] = characterRightVoid[3];
		WALK_RIGHT_VOID[2] = characterRightVoid[4];
		WALK_RIGHT_VOID[3] = characterRightVoid[5];
		WALK_RIGHT_VOID[4] = characterRightVoid[6];


	}

}
