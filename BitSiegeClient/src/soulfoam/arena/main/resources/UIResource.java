package soulfoam.arena.main.resources;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import soulfoam.arena.main.gfx.SpriteSheet;

public class UIResource {

	private SpriteSheet healthBars;

	public Image MENUBUTTON;
	public Image MENUBUTTON_HOVER;

	public Image MESSAGEBAR_GOLD;
	public Image MESSAGEBAR_BLUE;
	public Image MESSAGEBAR_GREEN;
	public Image MESSAGEBAR_RED;
	public Image MAINMENU_BUTTON_SELECT;
	public Image NEW_CHAT_BUTTON;
	public Image NEW_NOTIFICATION_BUTTON;
	
	public Image SCOREBOARD;
	public Image SCOREBOARD_BAR_BLUE;
	public Image SCOREBOARD_BAR_ORANGE;
	
	public Image HUD;
	public Image PLATE_ENEMY;
	public Image PLATE_ALLY;
	public Image PLATE_PLAYER;
	public Image HUD_COOLDOWN_TAB;
	public Image HUD_ABILITY_LEVEL_UP;
	public Image GREEN_HEALTH_BAR;
	public Image RED_HEALTH_BAR;
	public Image GREEN_HEALTH_BAR_BIG;
	public Image EXP_BAR;
	public Image LEVEL_BAR;
	public Image PLATE_HEALTH_BAR;
	public Image ABILITY_LEVELS_BAR;
	
	public Image MAINMENU_UI;
	public Image MAINMENU_PARTY_SETTINGS_UI;
	public Image MAINMENU_PARTY_SETTINGS_HOVER;
	
	public Image MAINMENU_FRIEND_UI_LIST;
	public Image MAINMENU_FRIEND_UI_ADD;
	public Image MAINMENU_FRIEND_UI_SELECT;
	public Image MAINMENU_FRIEND_UI_FRIENDONLINE;
	public Image MAINMENU_FRIEND_UI_FRIENDOFFLINE;
	
	public Image MAINMENU_SERVER_DISCONNECT_UI;
	
	public Image MAINMENU_PLAY_UI;
	public Image MAINMENU_NEWS_UI;
	public Image MAINMENU_SETTINGS_UI;
	public Image MAINMENU_KEYBIND_UI;
	
	public Image MAINMENU_LOGIN_UI;
	public Image MAINMENU_REGISTER_UI;
	
	public Image MAINMENU_REQUEST_UI;
	public Image MAINMENU_REQUEST_UI_FRIEND;
	public Image MAINMENU_REQUEST_UI_GAME;
	
	public Image MAINMENU_CHAT_UI_GLOBAL;
	public Image MAINMENU_CHAT_UI_PRIVATE;
	public Image MAINMENU_CHAT_UI_PRIVATE_CHAT;
	public Image MAINMENU_CHAT_UI_SELECT;
	public Image MAINMENU_CHAT_UI_BAR;
	
	public Image MAINMENU_PROFILE_UI_AVATAR;
	public Image MAINMENU_PROFILE_UI_PROFILE;
	public Image MAINMENU_PROFILE_UI_PASSWORD;
	public Image MAINMENU_PROFILE_UI_EMAIL;
	
	public Image MAINMENU_STORE_UI_CHALLENGERS;
	public Image MAINMENU_STORE_UI_CHALLENGERS_DISPLAY;
	public Image MAINMENU_STORE_UI_CHALLENGERS_DISPLAY_SIEGE_POINTS_ONLY;
	public Image MAINMENU_STORE_UI_CHALLENGERS_DISPLAY_BLANK;
	public Image MAINMENU_STORE_UI_SKINS;
	public Image MAINMENU_STORE_UI_SKINS_DISPLAY_BLANK;
	public Image MAINMENU_STORE_UI_AVATARS_BACKGROUNDS;
	public Image MAINMENU_STORE_UI_AVATARS_BORDERS;
	public Image MAINMENU_STORE_UI_AVATARS_ICONS;
	public Image MAINMENU_STORE_UI_AVATARS_DISPLAY;
	public Image MAINMENU_STORE_UI_AVATARS_DISPLAY_BLANK;
	public Image MAINMENU_STORE_UI_TAB_SELECT;
	public Image MAINMENU_STORE_UI_STORE_OBJECT;
	public Image MAINMENU_STORE_UI_STORE_OBJECT_SIEGE;
	public Image MAINMENU_STORE_UI_STORE_OBJECT_BITS;
	public Image MAINMENU_STORE_UI_STORE_OBJECT_BLANK;
	public Image MAINMENU_STORE_UI_STORE_OBJECT_SELECT;
	public Image MAINMENU_STORE_UI_DISPLAY_SELECT;
	
	public Image TOOLTIP;
	
	public Image GAME_STAT_NEXT_LEVEL;
	
	public Image RANDOM_ICON;
	public Image PORTRAIT_BORDER;
	public Image LOBBY_UI;
	public Image LOBBY_UI_BUTTON_SELECT;
	public Image LOBBY_UI_BUTTON_LOCKIN;
	public Image LOBBY_UI_BUTTON_LOCKEDIN;
	public Image LOBBY_UI_BUTTON_NOTOWNED;
	public Image LOBBY_UI_BUTTON_TAKEN;
	public Image LOBBY_TEAMA_BAR_NOTREADY;
	public Image LOBBY_TEAMA_BAR_READY;
	public Image LOBBY_TEAMD_BAR_NOTREADY;
	public Image LOBBY_TEAMD_BAR_READY;
	
	public UIResource() {
		loadUI();
		loadHUD();
		loadMenuUI();
		loadLobbyUI();
	}


	public void loadUI() {
		try {
			
			MENUBUTTON = new Image("art/ui/menubutton.png");
			MENUBUTTON_HOVER = new Image("art/ui/menubuttonhover.png");
			MESSAGEBAR_GOLD = new Image("art/ui/messagebargold.png");
			MESSAGEBAR_BLUE = new Image("art/ui/messagebarblue.png");
			MESSAGEBAR_GREEN = new Image("art/ui/messagebargreen.png");
			MESSAGEBAR_RED = new Image("art/ui/messagebarred.png");
			
			SCOREBOARD = new Image("art/ui/game/scoreboard.png");
			SCOREBOARD_BAR_BLUE = new Image("art/ui/game/scoreboardbluebar.png");			
			SCOREBOARD_BAR_ORANGE = new Image("art/ui/game/scoreboardorangebar.png");
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void loadHUD(){
		try {
			HUD = new Image("art/ui/game/hud.png");
			HUD_COOLDOWN_TAB = new Image("art/ui/game/cooldowntab.png");
			HUD_ABILITY_LEVEL_UP = new Image("art/ui/game/abilitylevelup.png");
			
			PLATE_ENEMY = new Image("art/ui/game/enemybar.png");
			PLATE_ALLY = new Image("art/ui/game/allybar.png");
			PLATE_PLAYER = new Image("art/ui/game/playerbar.png");
			
			healthBars = new SpriteSheet("art/ui/game/healthbars.png");
			GREEN_HEALTH_BAR = healthBars.grabImage(0, 0, 1, 2);
			RED_HEALTH_BAR = healthBars.grabImage(1, 0, 1, 2);
			GREEN_HEALTH_BAR_BIG = healthBars.grabImage(2, 0, 1, 7);
			EXP_BAR = healthBars.grabImage(3, 0, 1, 7);
			LEVEL_BAR = healthBars.grabImage(4, 0, 1, 5);
			PLATE_HEALTH_BAR = healthBars.grabImage(5, 0, 1, 3);
			ABILITY_LEVELS_BAR = healthBars.grabImage(6, 0, 1, 2);
			
			
			
			TOOLTIP = new Image("art/ui/game/tooltip.png");
			GAME_STAT_NEXT_LEVEL = new Image("art/ui/game/statnextlevel.png");
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMenuUI(){
		try {
			
			MAINMENU_UI = new Image("art/ui/menu/mainmenu.png");
			MAINMENU_BUTTON_SELECT = new Image("art/ui/menu/mainmenubuttonselect.png");
			NEW_CHAT_BUTTON = new Image("art/ui/menu/newchatbutton.png");
			NEW_NOTIFICATION_BUTTON = new Image("art/ui/menu/newnotificationbutton.png");
			
			MAINMENU_PARTY_SETTINGS_UI = new Image("art/ui/menu/partysettingsui.png"); 
			MAINMENU_PARTY_SETTINGS_HOVER = new Image("art/ui/menu/mainmenupartysettingshoverover.png"); 
			
			MAINMENU_FRIEND_UI_LIST = new Image("art/ui/menu/frienduilist.png"); 
			MAINMENU_FRIEND_UI_ADD = new Image("art/ui/menu/frienduiadd.png"); 
			MAINMENU_FRIEND_UI_SELECT = new Image("art/ui/menu/frienduiselect.png"); 
			MAINMENU_FRIEND_UI_FRIENDONLINE = new Image("art/ui/menu/friendonline.png"); 
			MAINMENU_FRIEND_UI_FRIENDOFFLINE = new Image("art/ui/menu/friendoffline.png"); 
			
			MAINMENU_LOGIN_UI = new Image("art/ui/menu/loginui.png"); 
			MAINMENU_REGISTER_UI = new Image("art/ui/menu/registerui.png");
			
			MAINMENU_PLAY_UI = new Image("art/ui/menu/playui.png"); 
			MAINMENU_NEWS_UI = new Image("art/ui/menu/newsui.png"); 
			
			MAINMENU_SETTINGS_UI = new Image("art/ui/menu/settingsui.png"); 
			MAINMENU_KEYBIND_UI = new Image("art/ui/menu/keybindui.png"); 
			
			MAINMENU_REQUEST_UI = new Image("art/ui/menu/requestui.png"); 
			MAINMENU_REQUEST_UI_FRIEND = new Image("art/ui/menu/requestuifriend.png"); 
			MAINMENU_REQUEST_UI_GAME = new Image("art/ui/menu/requestuigame.png"); 
			
			MAINMENU_CHAT_UI_GLOBAL = new Image("art/ui/menu/chatuiglobal.png"); 
			MAINMENU_CHAT_UI_PRIVATE = new Image("art/ui/menu/chatuiprivate.png"); 
			MAINMENU_CHAT_UI_PRIVATE_CHAT = new Image("art/ui/menu/chatuiprivatechat.png"); 
			MAINMENU_CHAT_UI_SELECT = new Image("art/ui/menu/chatuiselect.png"); 
			MAINMENU_CHAT_UI_BAR = new Image("art/ui/menu/chatuibar.png"); 
			
			MAINMENU_PROFILE_UI_AVATAR = new Image("art/ui/menu/profileuiavatar.png"); 
			MAINMENU_PROFILE_UI_PROFILE = new Image("art/ui/menu/profileuiprofile.png"); 
			MAINMENU_PROFILE_UI_PASSWORD = new Image("art/ui/menu/profileuipassword.png"); 
			MAINMENU_PROFILE_UI_EMAIL = new Image("art/ui/menu/profileuiemail.png"); 
			
			
			MAINMENU_STORE_UI_CHALLENGERS = new Image("art/ui/menu/storeuichallengers.png"); 
			MAINMENU_STORE_UI_CHALLENGERS_DISPLAY = new Image("art/ui/menu/storeuichallengersdisplay.png"); 
			MAINMENU_STORE_UI_CHALLENGERS_DISPLAY_BLANK = new Image("art/ui/menu/storeuichallengersdisplayblank.png"); 
			MAINMENU_STORE_UI_CHALLENGERS_DISPLAY_SIEGE_POINTS_ONLY = new Image("art/ui/menu/storeuichallengersdisplaysiege.png"); 
			MAINMENU_STORE_UI_SKINS = new Image("art/ui/menu/storeuiskins.png"); 
			MAINMENU_STORE_UI_SKINS_DISPLAY_BLANK = new Image("art/ui/menu/storeuiskinsdisplayblank.png"); 
			MAINMENU_STORE_UI_AVATARS_BACKGROUNDS = new Image("art/ui/menu/storeuiavatarsbackgrounds.png"); 
			MAINMENU_STORE_UI_AVATARS_BORDERS = new Image("art/ui/menu/storeuiavatarsborders.png"); 
			MAINMENU_STORE_UI_AVATARS_ICONS = new Image("art/ui/menu/storeuiavatarsicons.png"); 
			MAINMENU_STORE_UI_AVATARS_DISPLAY = new Image("art/ui/menu/storeuiavatardisplay.png"); 
			MAINMENU_STORE_UI_AVATARS_DISPLAY_BLANK = new Image("art/ui/menu/storeuiavatardisplayblank.png"); 
			MAINMENU_STORE_UI_TAB_SELECT = new Image("art/ui/menu/storeuitabselect.png"); 
			MAINMENU_STORE_UI_STORE_OBJECT = new Image("art/ui/menu/storeuistoreobject.png"); 
			MAINMENU_STORE_UI_STORE_OBJECT_SIEGE = new Image("art/ui/menu/storeuistoreobjectsiege.png"); 
			MAINMENU_STORE_UI_STORE_OBJECT_BITS = new Image("art/ui/menu/storeuistoreobjectbits.png"); 
			MAINMENU_STORE_UI_STORE_OBJECT_BLANK = new Image("art/ui/menu/storeuistoreobjectblank.png"); 
			MAINMENU_STORE_UI_STORE_OBJECT_SELECT = new Image("art/ui/menu/storeuistoreobjectselect.png"); 
			MAINMENU_STORE_UI_DISPLAY_SELECT = new Image("art/ui/menu/storeuidisplayselect.png"); 
			
			MAINMENU_SERVER_DISCONNECT_UI = new Image("art/ui/menu/serverdisconnectui.png");
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void loadLobbyUI(){
		try {
			RANDOM_ICON = new Image("art/ui/lobby/randomicon.png");
			PORTRAIT_BORDER = new Image("art/ui/lobby/portraitborder.png");
			LOBBY_UI = new Image("art/ui/lobby/lobbyui.png");
			LOBBY_UI_BUTTON_TAKEN = new Image("art/ui/lobby/takenbutton.png");
			LOBBY_UI_BUTTON_SELECT = new Image("art/ui/lobby/lockinselect.png");
			LOBBY_UI_BUTTON_LOCKIN = new Image("art/ui/lobby/lockinbutton.png");
			LOBBY_UI_BUTTON_LOCKEDIN = new Image("art/ui/lobby/lockedinbutton.png");
			LOBBY_UI_BUTTON_NOTOWNED = new Image("art/ui/lobby/notownedbutton.png");
			LOBBY_TEAMA_BAR_NOTREADY = new Image("art/ui/lobby/teamabarnotready.png");
			LOBBY_TEAMA_BAR_READY = new Image("art/ui/lobby/teamabarready.png");
			LOBBY_TEAMD_BAR_NOTREADY = new Image("art/ui/lobby/teamdbarnotready.png");
			LOBBY_TEAMD_BAR_READY = new Image("art/ui/lobby/teamdbarready.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	
}
