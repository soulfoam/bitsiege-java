package soulfoam.arena.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import soulfoam.arenashared.main.account.GameRegion;

public class SettingManager {

	public static int CONFIG_FULL_SCREEN;
	public static boolean CONFIG_SHOWFPS;
	public static boolean CONFIG_VSYNC = true;
	public static boolean CONFIG_MAINTAIN_ASPECT_RATIO = true;
	public static boolean CONFIG_SHOW_PLAYER_NAMES = true;
	public static int CONFIG_RES_WIDTH;
	public static int CONFIG_RES_HEIGHT;
	public static String CONFIG_USERNAME = "";
	public static boolean CONFIG_SMALL_CURSOR;
	public static int CONFIG_MAX_PING;

	public static boolean MUTE_MUSIC;
	public static boolean MUTE_MENU_MUSIC;
	public static boolean MUTE_SOUND_EFFECTS;

	public static float MUSIC_VOLUME;
	public static float SFX_VOLUME;
	public static float MUSIC_MENU_VOLUME;

	public static int SEARCH_REGION;

	public static int KEYBIND_MOVEUP;
	public static int KEYBIND_MOVELEFT;
	public static int KEYBIND_MOVEDOWN;
	public static int KEYBIND_MOVERIGHT;

	public static int KEYBIND_AUTOATTACK;
	public static int KEYBIND_CASTSPELL;
	public static int KEYBIND_SELECTSPELL1;
	public static int KEYBIND_SELECTSPELL2;
	public static int KEYBIND_SELECTSPELL3;
	public static int KEYBIND_SELECTSPELL4;
	public static int KEYBIND_SHOWSCOREBOARD;
	public static int KEYBIND_SHOWTOOLTIP;
	public static int KEYBIND_CHAT;
	public static int KEYBIND_SCREENSHOT;

	public static boolean SMART_CAST;

	public static float CONFIG_MINIMAP_X;
	public static float CONFIG_MINIMAP_Y;
	public static float CONFIG_MINIMAP_SCALE;
	public static float CONFIG_MINIMAP_PLAYERSCALE;
	public static float CONFIG_MINIMAP_TEXTSCALE;
	public static int CONFIG_MINIMAP_OPACITY;

	public static float CONFIG_NOTIFICATION_LOG_X;
	public static float CONFIG_NOTIFICATION_LOG_Y;
	public static float CONFIG_NOTIFICATION_LOG_SCALE;
	public static int CONFIG_NOTIFICATION_LOG_OPACITY;
	public static boolean CONFIG_NOTIFICATION_LOG_REMOVELOGS;

	public static float CONFIG_NOTIFICATION_TEXT_X;
	public static float CONFIG_NOTIFICATION_TEXT_Y;
	public static float CONFIG_NOTIFICATION_TEXT_SCALE;
	public static int CONFIG_NOTIFICATION_TEXT_OPACITY;

	public static float CONFIG_HOTBAR_X;
	public static float CONFIG_HOTBAR_Y;
	public static float CONFIG_HOTBAR_SCALE;
	public static float CONFIG_HOTBAR_TEXTSCALE;
	public static int CONFIG_HOTBAR_SPACING;
	public static int CONFIG_HOTBAR_OPACITY;
	public static boolean CONFIG_HOTBAR_VERTICAL;

	public static float CONFIG_TEAMHUD_X;
	public static float CONFIG_TEAMHUD_Y;
	public static float CONFIG_TEAMHUD_SCALE;
	public static int CONFIG_TEAMHUD_OPACITY;

	public static float CONFIG_STATHUD_X;
	public static float CONFIG_STATHUD_Y;
	public static float CONFIG_STATHUD_SCALE;
	public static int CONFIG_STATHUD_OPACITY;
	public static boolean CONFIG_STATHUD_SHOWTIPS;

	public static float CONFIG_CHATHUD_X;
	public static float CONFIG_CHATHUD_Y;
	public static float CONFIG_CHATHUD_WIDTH;
	public static float CONFIG_CHATHUD_HEIGHT;
	public static float CONFIG_CHATHUD_TEXTSCALE;
	public static int CONFIG_CHATHUD_OPACITY;

	public static float CONFIG_GOLDHUD_X;
	public static float CONFIG_GOLDHUD_Y;
	public static int CONFIG_GOLDHUD_OPACITY;

	public static float CONFIG_SCOREHUD_X;
	public static float CONFIG_SCOREHUD_Y;
	public static int CONFIG_SCOREHUD_OPACITY;
	public static boolean CONFIG_SCOREHUD_SHOWTIPS;

	public static float CONFIG_TIMERHUD_X;
	public static float CONFIG_TIMERHUD_Y;
	public static int CONFIG_TIMERHUD_OPACITY;

	public static String whatsMyIP() {

		String myIP = null;

		URL bitSiegeSite = null;

		try {
			bitSiegeSite = new URL("http://bitsiege.com/grabip.php");
		} catch (MalformedURLException e3) {
			e3.printStackTrace();
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(bitSiegeSite.openStream()));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		String inputLine;
		try {
			while (in != null && (inputLine = in.readLine()) != null) {
				myIP = new String(inputLine);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return myIP;
	}

	public static void makeConfigFile() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("bitsiege.properties");

			prop.setProperty("username", "");

			prop.setProperty("smallcursor", "false");
			prop.setProperty("resolution", "1280x720");
			prop.setProperty("fullscreen", "0");
			prop.setProperty("maintainaspectratio", "true");
			prop.setProperty("vsync", "true");
			prop.setProperty("showfps", "false");
			prop.setProperty("showplayernames", "true");
			prop.setProperty("mutemusic", "false");
			prop.setProperty("mutemenumusic", "false");
			prop.setProperty("mutesoundeffects", "false");
			prop.setProperty("musicmenuvolume", "0.5");
			prop.setProperty("musicvolume", "0.25");
			prop.setProperty("sfxvolume", "0.5");
			prop.setProperty("maxping", "250");

			prop.setProperty("moveup", "17");
			prop.setProperty("moveleft", "30");
			prop.setProperty("movedown", "31");
			prop.setProperty("moveright", "32");
			prop.setProperty("autoattack", "0");
			prop.setProperty("castspell", "1");
			prop.setProperty("selectspell1", "2");
			prop.setProperty("selectspell2", "3");
			prop.setProperty("selectspell3", "4");
			prop.setProperty("selectspell4", "5");
			prop.setProperty("showscoreboard", "42");
			prop.setProperty("tooltipdisplay", "20");
			prop.setProperty("chat", "28");
			prop.setProperty("editguimode", "15");
			prop.setProperty("screenshot", "88");

			prop.setProperty("guiminimapx", "269.0");
			prop.setProperty("guiminimapy", "129.0");
			prop.setProperty("guiminimapscale", "50.0");
			prop.setProperty("guiminimapplayerscale", "4.0");
			prop.setProperty("guiminimaptextscale", "0.25");
			prop.setProperty("guiminimapopacity", "175");

			prop.setProperty("guinotificationlogx", "295.0");
			prop.setProperty("guinotificationlogy", "-3.0");
			prop.setProperty("guinotificationlogscale", "6.0");
			prop.setProperty("guinotificationlogopacity", "255");
			prop.setProperty("guinotificationlogremovelogs", "true");

			prop.setProperty("guinotificationtextx", "95.0");
			prop.setProperty("guinotificationtexty", "1.75");
			prop.setProperty("guinotificationtextscale", "0.5");
			prop.setProperty("guinotificationtextopacity", "220");

			prop.setProperty("guihotbarx", "257.0");
			prop.setProperty("guihotbary", "131.0");
			prop.setProperty("guihotbarscale", "10.0");
			prop.setProperty("guihotbartextscale", "0.75");
			prop.setProperty("guihotbarspacing", "2");
			prop.setProperty("guihotbaropacity", "215");
			prop.setProperty("guihotbarvertical", "true");

			prop.setProperty("guiteamhudx", "1.0");
			prop.setProperty("guiteamhudy", "26.0");
			prop.setProperty("guiteamhudscale", "6.0");
			prop.setProperty("guiteamhudopacity", "255");

			prop.setProperty("guistathudx", "3.0");
			prop.setProperty("guistathudy", "91.0");
			prop.setProperty("guistathudscale", "6.0");
			prop.setProperty("guistathudopacity", "155");
			prop.setProperty("guistathudshowtips", "true");

			prop.setProperty("guichathudx", "1.0");
			prop.setProperty("guichathudy", "139.0");
			prop.setProperty("guichathudwidth", "104.0");
			prop.setProperty("guichathudheight", "40.0");
			prop.setProperty("guichathudtextscale", "0.25f");
			prop.setProperty("guichathudopacity", "60");

			prop.setProperty("guigoldhudx", "28.0");
			prop.setProperty("guigoldhudy", "127.0");
			prop.setProperty("guigoldhudopacity", "155");

			prop.setProperty("guiscorehudx", "3.0");
			prop.setProperty("guiscorehudy", "127.0");
			prop.setProperty("guiscorehudopacity", "155");
			prop.setProperty("guiscorehudshowtips", "true");

			prop.setProperty("guitimerhudx", "53.0");
			prop.setProperty("guitimerhudy", "127.0");
			prop.setProperty("guitimerhudopacity", "155");

			prop.setProperty("smartcast", "true");
			prop.setProperty("region", String.valueOf(GameRegion.NA));

			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void readConfigFile() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("bitsiege.properties");

			prop.load(input);

			if (!prop.getProperty("username").isEmpty()) {
				CONFIG_USERNAME = prop.getProperty("username");
			}

			readKeyBinds(prop);

			String[] resoString;
			resoString = prop.getProperty("resolution").split("x");
			CONFIG_RES_WIDTH = Integer.parseInt(resoString[0]);
			CONFIG_RES_HEIGHT = Integer.parseInt(resoString[1]);

			CONFIG_SMALL_CURSOR = Boolean.parseBoolean(prop.getProperty("smallcursor"));

			CONFIG_FULL_SCREEN = Integer.parseInt(prop.getProperty("fullscreen"));
			CONFIG_MAINTAIN_ASPECT_RATIO = Boolean.parseBoolean(prop.getProperty("maintainaspectratio"));
			CONFIG_SHOWFPS = Boolean.parseBoolean(prop.getProperty("showfps"));
			CONFIG_VSYNC = Boolean.parseBoolean(prop.getProperty("vsync"));
			MUTE_MUSIC = Boolean.parseBoolean(prop.getProperty("mutemusic"));
			MUTE_SOUND_EFFECTS = Boolean.parseBoolean(prop.getProperty("mutesoundeffects"));
			MUTE_MENU_MUSIC = Boolean.parseBoolean(prop.getProperty("mutemenumusic"));
			MUSIC_VOLUME = Float.parseFloat(prop.getProperty("musicvolume"));
			MUSIC_MENU_VOLUME = Float.parseFloat(prop.getProperty("musicmenuvolume"));
			SFX_VOLUME = Float.parseFloat(prop.getProperty("sfxvolume"));
			CONFIG_SHOW_PLAYER_NAMES = Boolean.parseBoolean(prop.getProperty("showplayernames"));
			SMART_CAST = Boolean.parseBoolean(prop.getProperty("smartcast"));
			CONFIG_MAX_PING = Integer.parseInt(prop.getProperty("maxping"));
			SEARCH_REGION = Integer.parseInt(prop.getProperty("region"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void readKeyBinds(Properties prop) {
		if (prop == null) {
			prop = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream("bitsiege.properties");
				prop.load(input);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (prop.getProperty("guitimerhudx") == null) {
			prop.setProperty("guitimerhudx", "53.0");
			prop.setProperty("guitimerhudy", "127.0");
			prop.setProperty("guitimerhudopacity", "155");
		}

		SettingManager.KEYBIND_MOVEUP = Integer.parseInt(prop.getProperty("moveup"));
		SettingManager.KEYBIND_MOVELEFT = Integer.parseInt(prop.getProperty("moveleft"));
		SettingManager.KEYBIND_MOVEDOWN = Integer.parseInt(prop.getProperty("movedown"));
		SettingManager.KEYBIND_MOVERIGHT = Integer.parseInt(prop.getProperty("moveright"));
		SettingManager.KEYBIND_AUTOATTACK = Integer.parseInt(prop.getProperty("autoattack"));
		SettingManager.KEYBIND_CASTSPELL = Integer.parseInt(prop.getProperty("castspell"));
		SettingManager.KEYBIND_SELECTSPELL1 = Integer.parseInt(prop.getProperty("selectspell1"));
		SettingManager.KEYBIND_SELECTSPELL2 = Integer.parseInt(prop.getProperty("selectspell2"));
		SettingManager.KEYBIND_SELECTSPELL3 = Integer.parseInt(prop.getProperty("selectspell3"));
		SettingManager.KEYBIND_SELECTSPELL4 = Integer.parseInt(prop.getProperty("selectspell4"));
		SettingManager.KEYBIND_SHOWSCOREBOARD = Integer.parseInt(prop.getProperty("showscoreboard"));
		SettingManager.KEYBIND_SHOWTOOLTIP = Integer.parseInt(prop.getProperty("tooltipdisplay"));
		SettingManager.KEYBIND_CHAT = Integer.parseInt(prop.getProperty("chat"));
		SettingManager.KEYBIND_SCREENSHOT = Integer.parseInt(prop.getProperty("screenshot"));

		SettingManager.CONFIG_MINIMAP_X = Float.parseFloat(prop.getProperty("guiminimapx"));
		SettingManager.CONFIG_MINIMAP_Y = Float.parseFloat(prop.getProperty("guiminimapy"));
		SettingManager.CONFIG_MINIMAP_SCALE = Float.parseFloat(prop.getProperty("guiminimapscale"));
		SettingManager.CONFIG_MINIMAP_PLAYERSCALE = Float.parseFloat(prop.getProperty("guiminimapplayerscale"));
		SettingManager.CONFIG_MINIMAP_TEXTSCALE = Float.parseFloat(prop.getProperty("guiminimaptextscale"));
		SettingManager.CONFIG_MINIMAP_OPACITY = Integer.parseInt(prop.getProperty("guiminimapopacity"));

		SettingManager.CONFIG_NOTIFICATION_LOG_X = Float.parseFloat(prop.getProperty("guinotificationlogx"));
		SettingManager.CONFIG_NOTIFICATION_LOG_Y = Float.parseFloat(prop.getProperty("guinotificationlogy"));
		SettingManager.CONFIG_NOTIFICATION_LOG_SCALE = Float.parseFloat(prop.getProperty("guinotificationlogscale"));
		SettingManager.CONFIG_NOTIFICATION_LOG_OPACITY = Integer
				.parseInt(prop.getProperty("guinotificationlogopacity"));
		SettingManager.CONFIG_NOTIFICATION_LOG_REMOVELOGS = Boolean
				.parseBoolean(prop.getProperty("guinotificationlogremovelogs"));

		SettingManager.CONFIG_NOTIFICATION_TEXT_X = Float.parseFloat(prop.getProperty("guinotificationtextx"));
		SettingManager.CONFIG_NOTIFICATION_TEXT_Y = Float.parseFloat(prop.getProperty("guinotificationtexty"));
		SettingManager.CONFIG_NOTIFICATION_TEXT_SCALE = Float.parseFloat(prop.getProperty("guinotificationtextscale"));
		SettingManager.CONFIG_NOTIFICATION_TEXT_OPACITY = Integer
				.parseInt(prop.getProperty("guinotificationtextopacity"));

		SettingManager.CONFIG_NOTIFICATION_TEXT_X = Float.parseFloat(prop.getProperty("guinotificationtextx"));
		SettingManager.CONFIG_NOTIFICATION_TEXT_Y = Float.parseFloat(prop.getProperty("guinotificationtexty"));
		SettingManager.CONFIG_NOTIFICATION_TEXT_SCALE = Float.parseFloat(prop.getProperty("guinotificationtextscale"));
		SettingManager.CONFIG_NOTIFICATION_TEXT_OPACITY = Integer
				.parseInt(prop.getProperty("guinotificationtextopacity"));

		SettingManager.CONFIG_HOTBAR_X = Float.parseFloat(prop.getProperty("guihotbarx"));
		SettingManager.CONFIG_HOTBAR_Y = Float.parseFloat(prop.getProperty("guihotbary"));
		SettingManager.CONFIG_HOTBAR_SCALE = Float.parseFloat(prop.getProperty("guihotbarscale"));
		SettingManager.CONFIG_HOTBAR_TEXTSCALE = Float.parseFloat(prop.getProperty("guihotbartextscale"));
		SettingManager.CONFIG_HOTBAR_SPACING = Integer.parseInt(prop.getProperty("guihotbarspacing"));
		SettingManager.CONFIG_HOTBAR_OPACITY = Integer.parseInt(prop.getProperty("guihotbaropacity"));
		SettingManager.CONFIG_HOTBAR_VERTICAL = Boolean.parseBoolean(prop.getProperty("guihotbarvertical"));

		SettingManager.CONFIG_TEAMHUD_X = Float.parseFloat(prop.getProperty("guiteamhudx"));
		SettingManager.CONFIG_TEAMHUD_Y = Float.parseFloat(prop.getProperty("guiteamhudy"));
		SettingManager.CONFIG_TEAMHUD_SCALE = Float.parseFloat(prop.getProperty("guiteamhudscale"));
		SettingManager.CONFIG_TEAMHUD_OPACITY = Integer.parseInt(prop.getProperty("guiteamhudopacity"));

		SettingManager.CONFIG_STATHUD_X = Float.parseFloat(prop.getProperty("guistathudx"));
		SettingManager.CONFIG_STATHUD_Y = Float.parseFloat(prop.getProperty("guistathudy"));
		SettingManager.CONFIG_STATHUD_SCALE = Float.parseFloat(prop.getProperty("guistathudscale"));
		SettingManager.CONFIG_STATHUD_OPACITY = Integer.parseInt(prop.getProperty("guistathudopacity"));
		SettingManager.CONFIG_STATHUD_SHOWTIPS = Boolean.parseBoolean(prop.getProperty("guistathudshowtips"));

		SettingManager.CONFIG_CHATHUD_X = Float.parseFloat(prop.getProperty("guichathudx"));
		SettingManager.CONFIG_CHATHUD_Y = Float.parseFloat(prop.getProperty("guichathudy"));
		SettingManager.CONFIG_CHATHUD_WIDTH = Float.parseFloat(prop.getProperty("guichathudwidth"));
		SettingManager.CONFIG_CHATHUD_HEIGHT = Float.parseFloat(prop.getProperty("guichathudheight"));
		SettingManager.CONFIG_CHATHUD_TEXTSCALE = Float.parseFloat(prop.getProperty("guichathudtextscale"));
		SettingManager.CONFIG_CHATHUD_OPACITY = Integer.parseInt(prop.getProperty("guichathudopacity"));

		SettingManager.CONFIG_GOLDHUD_X = Float.parseFloat(prop.getProperty("guigoldhudx"));
		SettingManager.CONFIG_GOLDHUD_Y = Float.parseFloat(prop.getProperty("guigoldhudy"));
		SettingManager.CONFIG_GOLDHUD_OPACITY = Integer.parseInt(prop.getProperty("guigoldhudopacity"));

		SettingManager.CONFIG_SCOREHUD_X = Float.parseFloat(prop.getProperty("guiscorehudx"));
		SettingManager.CONFIG_SCOREHUD_Y = Float.parseFloat(prop.getProperty("guiscorehudy"));
		SettingManager.CONFIG_SCOREHUD_OPACITY = Integer.parseInt(prop.getProperty("guiscorehudopacity"));
		SettingManager.CONFIG_SCOREHUD_SHOWTIPS = Boolean.parseBoolean(prop.getProperty("guiscorehudshowtips"));

		SettingManager.CONFIG_TIMERHUD_X = Float.parseFloat(prop.getProperty("guitimerhudx"));
		SettingManager.CONFIG_TIMERHUD_Y = Float.parseFloat(prop.getProperty("guitimerhudy"));
		SettingManager.CONFIG_TIMERHUD_OPACITY = Integer.parseInt(prop.getProperty("guitimerhudopacity"));

	}

	public static void setConfigFile(String property, String value) {
		FileInputStream in = null;
		try {
			in = new FileInputStream("bitsiege.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("bitsiege.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		props.setProperty(property, value);

		try {
			props.store(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
