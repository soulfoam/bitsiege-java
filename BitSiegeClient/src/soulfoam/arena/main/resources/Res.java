package soulfoam.arena.main.resources;

import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.imageout.ImageOut;

import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.resources.avatars.AvatarResource;
import soulfoam.arena.main.resources.challengers.ArcherResource;
import soulfoam.arena.main.resources.challengers.ClericResource;
import soulfoam.arena.main.resources.challengers.IllusionistResource;
import soulfoam.arena.main.resources.challengers.KnightResource;
import soulfoam.arena.main.resources.challengers.ShamanResource;
import soulfoam.arena.main.resources.challengers.VoidLordResource;
import soulfoam.arena.main.resources.challengers.WarlockResource;
import soulfoam.arena.main.resources.challengers.WaterQueenResource;
import soulfoam.arena.main.resources.cosmetic.CosmeticResource;

public class Res {

	public static ArrayList<String> WINDOW_SIZE_LIST = new ArrayList<String>();

	public static Random rand = new Random();
	public static int encryptOffSet = 23;

	public static byte TEAM_D = 0;
	public static byte TEAM_A = 1;
	public static byte TEAM_BOTH = 2;

	public static final byte SLOT_0 = 0;
	public static final byte SLOT_1 = 1;
	public static final byte SLOT_2 = 2;
	public static final byte SLOT_3 = 3;
	public static final byte SLOT_4 = 4;
	public static final byte SLOT_MISC = 5;

	public static Image BS_BACKGROUND;
	public static Image BS_LOGO;

	public static Music[] MENU_MUSIC = new Music[3];
	public static Sound[] MENU_SFX = new Sound[3];
	public static Music[] BATTLE_MUSIC = new Music[3];
	public static Sound[] SFX_EFFECTS = new Sound[6];

	public static Image[] pingAnimation = new Image[3];

	public static SpriteSheetFont bitFont;

	public static KnightResource KNIGHT_RESOURCE;
	public static ArcherResource ARCHER_RESOURCE;
	public static ClericResource CLERIC_RESOURCE;
	public static IllusionistResource ILLUSIONIST_RESOURCE;
	public static VoidLordResource VOIDLORD_RESOURCE;
	public static ShamanResource SHAMAN_RESOURCE;
	public static WaterQueenResource WATERQUEEN_RESOURCE;
	public static WarlockResource WARLOCK_RESOURCE;
	public static MapResource MAP_RESOURCE;
	public static ObjectiveResource OBJECTIVE_RESOURCE;
	public static GeneralResource GENERAL_RESOURCE;
	public static UIResource UI_RESOURCE;
	public static CosmeticResource COSMETIC_RESOURCE;
	public static AvatarResource AVATAR_RESOURCE;
	public static ColorResource COLOR_RESOURCE;

	public Res() {
		
		KNIGHT_RESOURCE = new KnightResource();
		ARCHER_RESOURCE = new ArcherResource();
		CLERIC_RESOURCE = new ClericResource();
		WARLOCK_RESOURCE = new WarlockResource();
		WATERQUEEN_RESOURCE = new WaterQueenResource();
		ILLUSIONIST_RESOURCE = new IllusionistResource();
		VOIDLORD_RESOURCE = new VoidLordResource();
		SHAMAN_RESOURCE = new ShamanResource();
		MAP_RESOURCE = new MapResource();
		OBJECTIVE_RESOURCE = new ObjectiveResource();
		GENERAL_RESOURCE = new GeneralResource();
		UI_RESOURCE = new UIResource();
		COSMETIC_RESOURCE = new CosmeticResource();
		AVATAR_RESOURCE = new AvatarResource();
		COLOR_RESOURCE = new ColorResource();

		MAP_RESOURCE.loadMaps();

		loadMusic("sound/music/menumusic0.ogg", Res.MENU_MUSIC, 0);
		loadMusic("sound/music/battlemusic0.ogg", Res.BATTLE_MUSIC, 0);
		loadMusic("sound/music/battlemusic1.ogg", Res.BATTLE_MUSIC, 1);
		loadMusic("sound/music/battlemusic2.ogg", Res.BATTLE_MUSIC, 2);

		loadSound("sound/sfx/misc/healthorb.ogg", Res.SFX_EFFECTS, 0);
		loadSound("sound/sfx/misc/dragonfireball.ogg", Res.SFX_EFFECTS, 1);
		loadSound("sound/sfx/misc/crystalcapture.ogg", Res.SFX_EFFECTS, 2);
		loadSound("sound/sfx/misc/voideyelaser.ogg", Res.SFX_EFFECTS, 3);
		loadSound("sound/sfx/misc/recall.ogg", Res.SFX_EFFECTS, 4);

		loadSound("sound/sfx/misc/menuselect.wav", Res.MENU_SFX, 0);
		loadSound("sound/sfx/misc/menuhover.wav", Res.MENU_SFX, 1);

		initWindowSizes();

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		try {
			Res.BS_BACKGROUND = new Image("bsbackground.png", false);
			Res.BS_LOGO = new Image("bslogo.png", false);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}

		org.newdawn.slick.SpriteSheet sheet = null;
		try {
			sheet = new org.newdawn.slick.SpriteSheet("smallfont.png", 6, 5);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		Res.bitFont = new SpriteSheetFont(sheet, ' ');

	}

	public void initWindowSizes() {
		WINDOW_SIZE_LIST.add("960x540");
		WINDOW_SIZE_LIST.add("1920x1080");
	}

	public static int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt(max - min + 1) + min;

		return randomNum;
	}

	public static Color getPingColor(int ping) {
		if (ping >= 150 && ping <= 249) {
			return Color.yellow;
		} else if (ping >= 250 && ping <= 299) {
			return Color.orange;
		} else if (ping >= 300) {
			return Color.red;
		} else {
			return Color.green;
		}
	}

	public static void loadImage(String path, Image[] image, int index) {
		try {
			System.out.println("Loading Image: " + path);
			image[index] = new Image(path, false);
		} catch (SlickException e) {
			System.out.println("Image could not be loaded");
			e.printStackTrace();
		}
	}

	public static void loadImage(String path, Image[] image, int index, boolean flipped) {
		try {
			System.out.println("Loading Image: " + path);
			image[index] = new Image(path, flipped);
		} catch (SlickException e) {
			System.out.println("Image could not be loaded");
			e.printStackTrace();
		}
	}

	public static void loadSound(String path, Sound[] arrayPath, int index) {

		try {
			arrayPath[index] = new Sound(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void loadMusic(String path, Music[] arrayPath, int index) {

		try {
			arrayPath[index] = new Music(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static Image scaleImage(int width, int height, Image toScale) {

		Image scaled = null;
		try {
			scaled = new Image(width, height);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}

		toScale.draw(0, 0, width, height);

		return scaled;

	}

	public static void drawNewLineText(float x, float y, String text, Color color) {
		for (String line : text.split("\n")) {
			bitFont.drawString(x, y += 7, line, color);
		}
	}
	
	public static void drawNewLineText(float x, float y, String text, Color color, float spacing) {
		for (String line : text.split("\n")) {
			bitFont.drawString(x, y += spacing, line, color);
		}
	}

	public static String getMoveSpeedDisplay(float moveSpeed) {
		String moveSpeedDisplay = String.valueOf(Math.round(moveSpeed * 1000));
		return moveSpeedDisplay;
	}

	public static String getAttackSpeedDisplay(float attackSpeed) {
		String attackSpeedDisplay = String.format("%.02f", attackSpeed);
		return attackSpeedDisplay;
	}

	public static String trimLeadingZeros(String source) {
		for (int i = 0; i < source.length(); ++i) {
			char c = source.charAt(i);
			if (c != '0' && !Character.isSpaceChar(c)) {
				return source.substring(i);
			}
		}
		return source;
	}

	public static boolean inBounds(int index, int size) {
		return index >= 0 && index < size;
	}

	public static String decode(String enc, int offset) {
		return encode(enc, 26 - offset);
	}

	public static String encode(String enc, int offset) {
		offset = offset % 26 + 26;
		StringBuilder encoded = new StringBuilder();
		for (char i : enc.toCharArray()) {
			if (Character.isLetter(i)) {
				if (Character.isUpperCase(i)) {
					encoded.append((char) ('A' + (i - 'A' + offset) % 26));
				} else {
					encoded.append((char) ('a' + (i - 'a' + offset) % 26));
				}
			} else {
				encoded.append(i);
			}
		}
		return encoded.toString();
	}

	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static String getDisplayMoveSpeed(float moveSpeed){
		return "" + Math.round((moveSpeed * 1000));
	}
	
	public static String getDisplayDuration(float duration){
		return (float)Math.round((duration / 1000)) + " seconds";
	}


	public static float roundForRendering(float num) {
		return (float) (Math.round(5.0 * num) / 5.0);
	}

	public static float roundForNetwork(float num) {
		return Math.round(1000f * num) / 1000f;
	}

	public static float percentOf(float value, int percent) {
		return value * (percent / 100.0f);
	}

	public static float lerp(float start, float finish, float t) {
		if (t < 0) {
			return start;
		}

		return start + t * (finish - start);
	}

	public static String parse(double num) {
		if ((int) num == num) {
			return Integer.toString((int) num);
		}
		return String.valueOf(num);
	}

	public static void openURL(String theURL) {
		String url = theURL;
		if (Desktop.isDesktopSupported()) {
			// Windows
			try {
				Desktop.getDesktop().browse(new URI("http://" + url));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		} else {
			// Ubuntu
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("/usr/bin/firefox -new-window " + url);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void listenForScreenShot(GameContainer gc) {
		if (gc.getInput().isKeyPressed(SettingManager.KEYBIND_SCREENSHOT)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			String dateAndTime = dateFormat.format(new Date());
			String filename = "screenshots/" + dateAndTime + ".png";
			File file = new File(filename);
			file.getParentFile().mkdirs();

			try {
				Image target = new Image(gc.getWidth(), gc.getHeight());
				gc.getGraphics().copyArea(target, 0, 0);
				ImageOut.write(target, filename);
				target.destroy();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

	public static float getTextCenter(String text) {
		return bitFont.getWidth(text)/2;
	}

	public static float oneDecimal(float f) {
		return Math.round(f * 10) / 10.0f;
	}

	public static float twoDecimal(float f) {
		return Math.round(f * 100) / 100.0f;
	}
}