package soulfoam.arena.main.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.menu.chat.ChatUI;
import soulfoam.arena.main.menu.friend.FriendUI;
import soulfoam.arena.main.menu.login.LoginUI;
import soulfoam.arena.main.menu.login.RegisterUI;
import soulfoam.arena.main.menu.news.NewsUI;
import soulfoam.arena.main.menu.party.PartyUI;
import soulfoam.arena.main.menu.play.PlayUI;
import soulfoam.arena.main.menu.popup.ServerDisconnectUI;
import soulfoam.arena.main.menu.profile.ProfileUI;
import soulfoam.arena.main.menu.request.RequestUI;
import soulfoam.arena.main.menu.settings.KeybindUI;
import soulfoam.arena.main.menu.settings.SettingsMenuUI;
import soulfoam.arena.main.menu.store.StoreUI;
import soulfoam.arena.main.menu.topbar.TopBarUI;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class MainMenuManager {

	private static MainMenuManager mm = null;

	public static MainMenuManager getMainMenu() {
		if (mm == null) {
			mm = new MainMenuManager();
		}

		return mm;
	}

	private TopBarUI topBarUI = new TopBarUI();
	private NavigationBarUI navigationBarUI = new NavigationBarUI();
	private SocialBarUI socialBarUI;
	private NewsUI newsUI = new NewsUI();
	private FriendUI friendUI;
	private RequestUI requestUI = new RequestUI();
	private PlayUI playUI = new PlayUI();
	private PartyUI partyUI = new PartyUI();
	private ChatUI chatUI;
	private ProfileUI profileUI;
	private RegisterUI registerUI;
	private LoginUI loginUI;
	private SettingsMenuUI settingsMenuUI;
	private KeybindUI keybindUI = new KeybindUI(20, 10);
	private UpdateUI updateUI = new UpdateUI(20, 10);
	private StoreUI storeUI = new StoreUI();
	private ServerDisconnectUI serverDisconnectUI = new ServerDisconnectUI();
	
	private Rectangle selectedButton = new Rectangle(2, 63, 40, 16);

	private boolean inMenu = true;
	private int changeState = -1;

	public void init(GameContainer gc, StateBasedGame s) throws SlickException {

		chatUI = new ChatUI(gc);
		socialBarUI = new SocialBarUI();
		friendUI = new FriendUI(gc);
		profileUI = new ProfileUI(gc);
		settingsMenuUI = new SettingsMenuUI(gc);
		loginUI = new LoginUI(gc);
		registerUI = new RegisterUI(gc);

		LobbyManager.getManager().init();

	}

	public void showMultiplayerUI() {
		
		topBarUI.setWindow(true);
		navigationBarUI.setWindow(true);
		socialBarUI.setWindow(true);
		storeUI.setOwnedItems();

		setWindowUI(newsUI);

	}

	public void hideMultiplayerUI() {
		setWindowUI(loginUI);
	}

	public void setWindowUI(BaseMenuUI ui) {

		newsUI.setWindow(false);
		settingsMenuUI.setWindow(false);
		keybindUI.setWindow(false);
		requestUI.setWindow(false);
		playUI.setWindow(false);
		friendUI.setWindow(false);
		profileUI.setWindow(false);
		storeUI.setWindow(false);
		partyUI.setWindow(false);
		chatUI.setWindow(false);

		ui.setWindow(true);
	}
	
	public void setSelectedButton(Rectangle button){
		selectedButton = button;
	}

	public void enter(GameContainer gc, StateBasedGame s) throws SlickException {

		SoundStore.get().setMusicOn(!SettingManager.MUTE_MENU_MUSIC);
		SoundStore.get().setSoundsOn(!SettingManager.MUTE_MENU_MUSIC);

		SoundStore.get().setMusicVolume(SettingManager.MUSIC_VOLUME);
		SoundStore.get().setSoundVolume(SettingManager.SFX_VOLUME);

		// Res.MENU_MUSIC[0].loop();
		
		gc.setMouseCursor("cursor.png", 7, 0);

		setInMenu(true);
	}

	public void leave(GameContainer gc, StateBasedGame s) throws SlickException {
		setInMenu(false);
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
		
		if (LobbyManager.getManager().isLobbyClientAlive()) {
			serverDisconnectUI.setWindow(false);
			if (LobbyManager.getManager().getUserAccount().isLoggedIn()) {
				if (topBarUI.isFocused()) {
					topBarUI.update(gc, s, delta);
				}
				if (navigationBarUI.isFocused()) {
					navigationBarUI.update(gc, s, delta);
				}
				if (socialBarUI.isFocused()) {
					socialBarUI.update(gc, s, delta);
				}
				if (storeUI.isFocused()) {
					storeUI.update(gc, s, delta);
				}
				if (newsUI.isFocused()) {
					newsUI.update(gc, s, delta);
				}
				if (playUI.isFocused()) {
					playUI.update(gc, s, delta);
				}
				if (requestUI.isFocused()) {
					requestUI.update(gc, s, delta);
				}
				if (friendUI.isFocused()) {
					friendUI.update(gc, s, delta);
				}
				if (profileUI.isFocused()) {
					profileUI.update(gc, s, delta);
				}
				if (partyUI.isFocused()) {
					partyUI.update(gc, s, delta);
				}
				if (chatUI.isFocused()) {
					chatUI.update(gc, s, delta);
				}
			} else {
				if (loginUI.isFocused()) {
					loginUI.update(gc, s, delta);
				}
				if (updateUI.renderMe) {
					updateUI.update(gc, s, delta);
				}
				if (registerUI.isFocused()) {
					registerUI.update(gc, s, delta);
				}
			}

			if (settingsMenuUI.isFocused()) {
				settingsMenuUI.update(gc, s, delta);
			}
			if (keybindUI.isFocused()) {
				keybindUI.update(gc, s, delta);
			}
		} else {
			serverDisconnectUI.setWindow(true);
			serverDisconnectUI.update(gc, s, delta);
		}

		if (changeState != -1) {
			s.enterState(changeState, new FadeOutTransition(Color.black, 250), new FadeInTransition(Color.black, 250));
			changeState = -1;
		}

		Res.listenForScreenShot(gc);
	}

	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {

		Res.BS_BACKGROUND.draw(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);

		if (LobbyManager.getManager().isLobbyClientAlive()) {
			g.setColor(new Color(0, 0, 0, 60));
			g.fillRect(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);
			if (LobbyManager.getManager().getUserAccount().isLoggedIn()) {
				Res.UI_RESOURCE.MAINMENU_UI.draw();
				if (topBarUI.isRendering()) {
					topBarUI.render(gc, g);
				}
				if (navigationBarUI.isRendering()) {
					navigationBarUI.render(gc, g);
				}
				if (socialBarUI.isRendering()) {
					socialBarUI.render(gc, g); 
				}
				if (playUI.isRendering()) {
					playUI.render(gc, g);
				}
				if (newsUI.isRendering()) {
					newsUI.render(gc, g);
				}
				if (storeUI.isRendering()) {
					storeUI.render(gc, g);
				}
				if (requestUI.isRendering()) {
					requestUI.render(gc, g);
				}
				if (friendUI.isRendering()) {
					friendUI.render(gc, g);
				}
				if (profileUI.isRendering()) {
					profileUI.render(gc, g);
				}
				if (partyUI.isRendering()) {
					partyUI.render(gc, g);
				}
				if (chatUI.isRendering()) {
					chatUI.render(gc, g);
				}
				
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(selectedButton.getX(), selectedButton.getY(), new Color(100, 255, 100));
			} 
			else {
				if (loginUI.isRendering()) {
					loginUI.render(gc, g);
				}
				if (updateUI.renderMe) {
					updateUI.render(g, gc);
				}
				if (registerUI.isRendering()) {
					registerUI.render(gc, g);
				}
			}

			if (settingsMenuUI.isRendering()) {
				settingsMenuUI.render(gc, g);
			}
			if (keybindUI.isRendering()) {
				keybindUI.render(gc, g);
			}

		} 
		else {
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);
			serverDisconnectUI.render(gc, g);
		}
	}

	public void mouseWheelMoved(int m) {
		friendUI.getFriendTab().mouseWheelMoved(m);
		requestUI.mouseWheelMoved(m);
		storeUI.mouseWheelMoved(m);
	}

	public void keyReleased(int key, char c) {
		if (keybindUI.isFocused()) {
			if (keybindUI.setID != 4 && keybindUI.setID != 5) {
				keybindUI.setKeybind(key);
			} else {
				keybindUI.setMode = false;
			}
		}
	}

	public void mousePressed(int button, int x, int y) {
		if (keybindUI.isFocused()) {
			if (keybindUI.setID == 4 || keybindUI.setID == 5) {
				keybindUI.setKeybind(button);
			}
		}
		
		if (friendUI.isFocused()){
			friendUI.mousePressed(button, x, y);
		}
		if (settingsMenuUI.isFocused()) {
			settingsMenuUI.mousePressed(button, x, y);
		}
		if (topBarUI.isFocused()) {
			topBarUI.mousePressed(button, x, y);
		}
		if (loginUI.isFocused()) {
			loginUI.mousePressed(button, x, y);
		}
		if (registerUI.isFocused()) {
			registerUI.mousePressed(button, x, y);
		}
		if (navigationBarUI.isFocused()){
			navigationBarUI.mousePressed(button, x, y);
		}
		if (socialBarUI.isFocused()){
			socialBarUI.mousePressed(button, x, y);
		}
		if (chatUI.isFocused()){
			chatUI.mousePressed(button, x, y);
		}
		if (profileUI.isFocused()){
			profileUI.mousePressed(button, x, y);
		}
		if (storeUI.isFocused()){
			storeUI.mousePressed(button, x, y);
		}

	}

	public boolean isInMenu() {
		return inMenu;
	}

	public void setInMenu(boolean inMenu) {
		this.inMenu = inMenu;
	}

	public TopBarUI getTopBarUI() {
		return topBarUI;
	}

	public RegisterUI getRegisterUI() {
		return registerUI;
	}

	public LoginUI getLoginUI() {
		return loginUI;
	}

	public SettingsMenuUI getSettingsUI() {
		return settingsMenuUI;
	}

	public KeybindUI getKeybindUI() {
		return keybindUI;
	}

	public UpdateUI getUpdateUI() {
		return updateUI;
	}

	public StoreUI getStoreUI() {
		return storeUI;
	}

	public FriendUI getFriendUI() {
		return friendUI;
	}

	public RequestUI getRequestUI() {
		return requestUI;
	}

	public PlayUI getPlayUI() {
		return playUI;
	}

	public ProfileUI getProfileUI() {
		return profileUI;
	}

	public PartyUI getPartyUI() {
		return partyUI;
	}

	public NavigationBarUI getNavigationBarUI() {
		return navigationBarUI;
	}

	public ServerDisconnectUI getServerDisconnectUI() {
		return serverDisconnectUI;
	}

	public SocialBarUI getSocialBarUI() {
		return socialBarUI;
	}

	public NewsUI getNewsUI() {
		return newsUI;
	}
	
	public ChatUI getChatUI(){
		return chatUI;
	}

	public int isChangeState() {
		return changeState;
	}

	public void setChangeState(int changeState) {
		this.changeState = changeState;
	}

}
