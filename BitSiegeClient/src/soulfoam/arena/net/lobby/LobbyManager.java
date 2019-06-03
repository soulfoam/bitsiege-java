package soulfoam.arena.net.lobby;

import org.newdawn.slick.Color;

import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.net.lobby.managers.AccountManager;
import soulfoam.arena.net.lobby.managers.AvatarManager;
import soulfoam.arena.net.lobby.managers.ChatManager;
import soulfoam.arena.net.lobby.managers.FriendManager;
import soulfoam.arena.net.lobby.managers.LoginManager;
import soulfoam.arena.net.lobby.managers.MatchMakingManager;
import soulfoam.arena.net.lobby.managers.PartyManager;
import soulfoam.arena.net.lobby.managers.RegisterManager;
import soulfoam.arena.net.lobby.managers.RequestManager;
import soulfoam.arena.net.lobby.managers.ServerInfoManager;
import soulfoam.arena.net.lobby.managers.StoreManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;

public class LobbyManager {

	private static LobbyManager lm = null;

	public static LobbyManager getManager() {
		if (lm == null) {
			lm = new LobbyManager();
		}

		return lm;
	}

	private LobbyClient lobbyClient = new LobbyClient();
	private MultiplayerAccount userAccount = new MultiplayerAccount();
	private RegisterManager registerManager = new RegisterManager();
	private LoginManager loginManager = new LoginManager();
	private MatchMakingManager matchMakingManager = new MatchMakingManager();
	private FriendManager friendManager = new FriendManager();
	private AvatarManager avatarManager = new AvatarManager();
	private AccountManager accountManager = new AccountManager();
	private RequestManager requestManager = new RequestManager();
	private PartyManager partyManager = new PartyManager();
	private ServerInfoManager serverInfoManager = new ServerInfoManager();
	private StoreManager storeManager = new StoreManager();
	private ChatManager chatManager = new ChatManager();

	private boolean lobbyClientAlive;

	public void init() {
		initLobbyClient();
	}

	public boolean initLobbyClient() {

		lobbyClient = new LobbyClient();

		if (lobbyClient.init()) {
			lobbyClient.start();
			setLobbyClientAlive(true);
			return true;
		} else {
			return false;
		}
	}

	public MultiplayerAccount getUserAccount() {
		return userAccount;
	}

	public void logoutUserAccount() {

		userAccount = new MultiplayerAccount();
		
		getLobbyClient().getOutput().println(LobbyOPCode.OP_LOGOUT);
		getLoginManager().setLoginText(getLoginManager().getDefaultLoginText(), Color.yellow);

		MainMenuManager.getMainMenu().hideMultiplayerUI();
		MainMenuManager.getMainMenu().getRequestUI().setRequestPages();
		MainMenuManager.getMainMenu().getTopBarUI().getPartyMembers().clear();
		MainMenuManager.getMainMenu().getPartyUI().getPartyMembers().clear();
	}

	public FriendManager getFriendManager() {
		return friendManager;
	}

	public RegisterManager getRegisterManager() {
		return registerManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public MatchMakingManager getMatchMakingManager() {
		return matchMakingManager;
	}

	public LobbyClient getLobbyClient() {
		return lobbyClient;
	}

	public AvatarManager getAvatarManager() {
		return avatarManager;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public RequestManager getRequestManager() {
		return requestManager;
	}

	public PartyManager getPartyManager() {
		return partyManager;
	}

	public ServerInfoManager getServerInfoManager() {
		return serverInfoManager;
	}

	public StoreManager getStoreManager() {
		return storeManager;
	}

	public boolean isLobbyClientAlive() {
		return lobbyClientAlive;
	}

	public void setLobbyClientAlive(boolean lobbyClientAlive) {
		this.lobbyClientAlive = lobbyClientAlive;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

}
