package soulfoam.arena.main.resources.challengers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.resources.Res;

public class IllusionistResource {

	private SpriteSheet autoattack;
	private SpriteSheet clonespawn;

	public Image[] WALK_UP;
	public Image[] WALK_LEFT;
	public Image[] WALK_DOWN;
	public Image[] WALK_RIGHT;
	public Image[] IDLE_UP;
	public Image[] IDLE_LEFT;
	public Image[] IDLE_DOWN;
	public Image[] IDLE_RIGHT;

	public Image[] WALK_UP_PYRO = new Image[5];
	public Image[] WALK_LEFT_PYRO = new Image[5];
	public Image[] WALK_DOWN_PYRO = new Image[5];
	public Image[] WALK_RIGHT_PYRO = new Image[5];
	public Image[] IDLE_UP_PYRO = new Image[2];
	public Image[] IDLE_LEFT_PYRO = new Image[2];
	public Image[] IDLE_DOWN_PYRO = new Image[2];
	public Image[] IDLE_RIGHT_PYRO = new Image[2];
	
	public Image[] AUTOATTACK = new Image[5];
	public Image[] AUTOATTACK_PYRO = new Image[5];

	public Image[] CLONESPAWN_UP = new Image[8];
	public Image[] CLONESPAWN_LEFT = new Image[8];
	public Image[] CLONESPAWN_DOWN = new Image[8];
	public Image[] CLONESPAWN_RIGHT = new Image[8];

	public Image[] CLONESPAWN_UP_PYRO = new Image[8];
	public Image[] CLONESPAWN_LEFT_PYRO = new Image[8];
	public Image[] CLONESPAWN_DOWN_PYRO = new Image[8];
	public Image[] CLONESPAWN_RIGHT_PYRO = new Image[8];

	public Image[] ILLUSIONIST_HOTBAR_ICONS = new Image[4];
	public Sound[] SFX_ILLUSIONIST = new Sound[5];

	public Image[] PORTRAITS = new Image[2];

	public IllusionistResource() {

		loadCharacter();
		loadAbilities();
		loadSounds();
		loadHotbarIcons();
		loadPortraits();
		
	}

	private void loadPortraits(){
		SpriteSheet portraits = new SpriteSheet("art/characters/illusionist/portraits.png");
		PORTRAITS[0] = portraits.grabImage(0, 0, 16, 16);
		PORTRAITS[1] = portraits.grabImage(1, 0, 16, 16);
	}
	
	public void loadAbilities() {
		autoattack = new SpriteSheet("art/characters/illusionist/autoattack.png");
		clonespawn = new SpriteSheet("art/characters/illusionist/clonespawn.png");

		AUTOATTACK = autoattack.grabRow(0, 16, 16, 5);
		AUTOATTACK_PYRO = autoattack.grabRow(1, 16, 16, 5);

		CLONESPAWN_UP = clonespawn.grabRow(0, 24, 24, 8);
		CLONESPAWN_LEFT = clonespawn.grabRow(1, 24, 24, 8);
		CLONESPAWN_DOWN = clonespawn.grabRow(2, 24, 24, 8);
		CLONESPAWN_RIGHT = clonespawn.grabRow(3, 24, 24, 8);

		CLONESPAWN_UP_PYRO = clonespawn.grabRow(4, 24, 24, 8);
		CLONESPAWN_LEFT_PYRO = clonespawn.grabRow(5, 24, 24, 8);
		CLONESPAWN_DOWN_PYRO = clonespawn.grabRow(6, 24, 24, 8);
		CLONESPAWN_RIGHT_PYRO = clonespawn.grabRow(7, 24, 24, 8);
	}

	public void loadHotbarIcons() {
		SpriteSheet hotbarIcons = new SpriteSheet("art/characters/illusionist/icons.png");
		ILLUSIONIST_HOTBAR_ICONS = hotbarIcons.grabRow(0, 16, 16);
	}

	public void loadSounds() {
		Res.loadSound("sound/sfx/illusionist/aa.ogg", SFX_ILLUSIONIST, 0);
		Res.loadSound("sound/sfx/illusionist/ability1.ogg", SFX_ILLUSIONIST, 1);
		Res.loadSound("sound/sfx/illusionist/ability2.ogg", SFX_ILLUSIONIST, 2);
		Res.loadSound("sound/sfx/illusionist/ability3.ogg", SFX_ILLUSIONIST, 3);
		Res.loadSound("sound/sfx/illusionist/ability4.ogg", SFX_ILLUSIONIST, 4);
	}

	public void loadCharacter() {
		SpriteSheet characterSheet = new SpriteSheet("art/characters/illusionist/illusionist.png");
		
		IDLE_UP = characterSheet.grabCharacter(0, 32, 32, 4);
		IDLE_LEFT = characterSheet.grabCharacter(1, 32, 32, 4);
		IDLE_DOWN = characterSheet.grabCharacter(2, 32, 32, 4);
		IDLE_RIGHT = characterSheet.grabCharacter(3, 32, 32, 4);
		
		WALK_UP = characterSheet.grabCharacter(4, 32, 32, 8);
		WALK_LEFT = characterSheet.grabCharacter(5, 32, 32, 8);
		WALK_DOWN = characterSheet.grabCharacter(6, 32, 32, 8);
		WALK_RIGHT = characterSheet.grabCharacter(7, 32, 32, 8);

		Image[] characterUpPyro = new Image[7];
		Image[] characterLeftPyro = new Image[7];
		Image[] characterDownPyro = new Image[7];
		Image[] characterRightPyro = new Image[7];
		characterUpPyro = characterSheet.grabCharacter(4, 24, 24, 7);
		characterLeftPyro = characterSheet.grabCharacter(5, 24, 24, 7);
		characterDownPyro = characterSheet.grabCharacter(6, 24, 24, 7);
		characterRightPyro = characterSheet.grabCharacter(7, 24, 24, 7);

		IDLE_UP_PYRO[0] = characterUpPyro[0];
		IDLE_UP_PYRO[1] = characterUpPyro[1];

		WALK_UP_PYRO[0] = characterUpPyro[2];
		WALK_UP_PYRO[1] = characterUpPyro[3];
		WALK_UP_PYRO[2] = characterUpPyro[4];
		WALK_UP_PYRO[3] = characterUpPyro[5];
		WALK_UP_PYRO[4] = characterUpPyro[6];

		IDLE_LEFT_PYRO[0] = characterLeftPyro[0];
		IDLE_LEFT_PYRO[1] = characterLeftPyro[1];

		WALK_LEFT_PYRO[0] = characterLeftPyro[2];
		WALK_LEFT_PYRO[1] = characterLeftPyro[3];
		WALK_LEFT_PYRO[2] = characterLeftPyro[4];
		WALK_LEFT_PYRO[3] = characterLeftPyro[5];
		WALK_LEFT_PYRO[4] = characterLeftPyro[6];

		IDLE_DOWN_PYRO[0] = characterDownPyro[0];
		IDLE_DOWN_PYRO[1] = characterDownPyro[1];

		WALK_DOWN_PYRO[0] = characterDownPyro[2];
		WALK_DOWN_PYRO[1] = characterDownPyro[3];
		WALK_DOWN_PYRO[2] = characterDownPyro[4];
		WALK_DOWN_PYRO[3] = characterDownPyro[5];
		WALK_DOWN_PYRO[4] = characterDownPyro[6];

		IDLE_RIGHT_PYRO[0] = characterRightPyro[0];
		IDLE_RIGHT_PYRO[1] = characterRightPyro[1];

		WALK_RIGHT_PYRO[0] = characterRightPyro[2];
		WALK_RIGHT_PYRO[1] = characterRightPyro[3];
		WALK_RIGHT_PYRO[2] = characterRightPyro[4];
		WALK_RIGHT_PYRO[3] = characterRightPyro[5];
		WALK_RIGHT_PYRO[4] = characterRightPyro[6];

	}
}
