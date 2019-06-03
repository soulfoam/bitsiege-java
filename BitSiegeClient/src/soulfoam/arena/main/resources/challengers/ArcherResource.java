package soulfoam.arena.main.resources.challengers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.Tile;

public class ArcherResource {

	private SpriteSheet arrows;
	private SpriteSheet explosion;

	public Image[] ICE_ARROW;
	public Image[] FIRE_ARROW;
	public Image[] EXPLOSIVE_ARROW;

	public Image[] EXPLOSION;

	public Image[] WALK_UP;
	public Image[] WALK_LEFT;
	public Image[] WALK_DOWN;
	public Image[] WALK_RIGHT;
	public Image[] IDLE_UP;
	public Image[] IDLE_LEFT;
	public Image[] IDLE_DOWN;
	public Image[] IDLE_RIGHT;
	
	public Image[] WALK_UP_EXPLOSIVE;
	public Image[] WALK_LEFT_EXPLOSIVE;
	public Image[] WALK_DOWN_EXPLOSIVE;
	public Image[] WALK_RIGHT_EXPLOSIVE;
	public Image[] IDLE_UP_EXPLOSIVE;
	public Image[] IDLE_LEFT_EXPLOSIVE;
	public Image[] IDLE_DOWN_EXPLOSIVE;
	public Image[] IDLE_RIGHT_EXPLOSIVE;

	public Image[] ARCHER_HOTBAR_ICONS;
	public Sound[] SFX_ARCHER = new Sound[5];

	public Image[] PORTRAITS ;

	public ArcherResource() {

		loadCharacter();
		loadAbilities();
		loadSounds();
		loadHotbarIcons();
		loadPortraits();
		
	}
	
	private void loadPortraits(){
		SpriteSheet portraits = new SpriteSheet("art/characters/archer/portraits.png");
		PORTRAITS = portraits.grabRow(0, 16, 16);
	}

	public void loadAbilities() {
		arrows = new SpriteSheet("art/characters/archer/arrows.png");
		explosion = new SpriteSheet("art/characters/archer/explosion.png");

		ICE_ARROW = arrows.grabRow(0, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2);
		FIRE_ARROW = arrows.grabRow(1, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2);
		EXPLOSIVE_ARROW = arrows.grabRow(2, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2);

		EXPLOSION = explosion.grabRow(0, 40, 40, 8);
	}

	public void loadHotbarIcons() {
		SpriteSheet hotbarIcons = new SpriteSheet("art/characters/archer/icons.png");
		ARCHER_HOTBAR_ICONS = hotbarIcons.grabRow(0, 16, 16);
	}

	public void loadSounds() {
		Res.loadSound("sound/sfx/archer/aa.ogg", SFX_ARCHER, 0);
		Res.loadSound("sound/sfx/misc/explosion.ogg", SFX_ARCHER, 1);
		Res.loadSound("sound/sfx/archer/icearrowshoot.ogg", SFX_ARCHER, 2);
		Res.loadSound("sound/sfx/archer/firearrowshoot.ogg", SFX_ARCHER, 3);
		Res.loadSound("sound/sfx/archer/ability4.ogg", SFX_ARCHER, 4);
	}

	public void loadCharacter() {
		SpriteSheet characterSheet = new SpriteSheet("art/characters/archer/archer2.png");
		
		IDLE_UP = characterSheet.grabCharacter(2, 32, 32, 1);
		IDLE_LEFT = characterSheet.grabCharacter(2, 32, 32, 1);
		IDLE_DOWN = characterSheet.grabCharacter(2, 32, 32, 1);
		IDLE_RIGHT = characterSheet.grabCharacter(2, 32, 32, 1);
		
		WALK_UP = characterSheet.grabCharacter(0, 32, 32, 6);
		WALK_LEFT = characterSheet.grabCharacter(0, 32, 32, 6, true);
		WALK_DOWN = characterSheet.grabCharacter(0, 32, 32, 6, true);
		WALK_RIGHT = characterSheet.grabCharacter(0, 32, 32, 6);
		
		Image[] characterUpExplosive = new Image[7];
		Image[] characterLeftExplosive = new Image[7];
		Image[] characterDownExplosive = new Image[7];
		Image[] characterRightExplosive = new Image[7];
		characterUpExplosive = characterSheet.grabCharacter(4, 24, 24, 7);
		characterLeftExplosive = characterSheet.grabCharacter(5, 24, 24, 7);
		characterDownExplosive = characterSheet.grabCharacter(6, 24, 24, 7);
		characterRightExplosive = characterSheet.grabCharacter(7, 24, 24, 7);

		IDLE_UP_EXPLOSIVE = characterUpExplosive;
		IDLE_LEFT_EXPLOSIVE = characterLeftExplosive;
		IDLE_DOWN_EXPLOSIVE = characterDownExplosive;
		IDLE_RIGHT_EXPLOSIVE = characterRightExplosive;
		
		WALK_UP_EXPLOSIVE = characterUpExplosive;
		WALK_LEFT_EXPLOSIVE = characterLeftExplosive;
		WALK_DOWN_EXPLOSIVE = characterDownExplosive;
		WALK_RIGHT_EXPLOSIVE = characterRightExplosive;

	}
}
