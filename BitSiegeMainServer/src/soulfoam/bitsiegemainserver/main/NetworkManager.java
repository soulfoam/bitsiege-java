package soulfoam.bitsiegemainserver.main;

import soulfoam.bitsiegemainserver.main.accounts.AccountManager;
import soulfoam.bitsiegemainserver.main.accounts.ActiveAccountManager;
import soulfoam.bitsiegemainserver.main.avatars.AvatarManager;
import soulfoam.bitsiegemainserver.main.command.CommandHandler;
import soulfoam.bitsiegemainserver.main.friends.FriendManager;
import soulfoam.bitsiegemainserver.main.gameserver.ActiveGameServerManager;
import soulfoam.bitsiegemainserver.main.logs.LogManager;
import soulfoam.bitsiegemainserver.main.misc.IDPool;
import soulfoam.bitsiegemainserver.main.party.PartyManager;
import soulfoam.bitsiegemainserver.main.store.StoreManager;
import soulfoam.bitsiegemainserver.main.unlockables.ChallengerManager;
import soulfoam.bitsiegemainserver.main.unlockables.SkinManager;
import soulfoam.bitsiegemainserver.main.unlockables.UnderglowManager;

public class NetworkManager {
	
	private static NetworkManager nm = null;

	private CommandHandler commandHandler = new CommandHandler();
	private LogManager logManager = new LogManager();
	private PartyManager partyManager = new PartyManager();
	private IDPool idPool = new IDPool();
	
	private ActiveAccountManager activeAccountManager = new ActiveAccountManager();
	private ActiveGameServerManager activeServerManager = new ActiveGameServerManager();
	private AccountManager accountManager = new AccountManager();
	private FriendManager friendManager = new FriendManager();
	private AvatarManager avatarManager = new AvatarManager();
	private ChallengerManager challengerManager = new ChallengerManager();
	private SkinManager skinManager = new SkinManager();
	private UnderglowManager underglowManager = new UnderglowManager();
	private StoreManager storeManager = new StoreManager();
	
	public static NetworkManager getManager(){
		if (nm == null){
			nm = new NetworkManager();
		}
		
		return nm;
	}

	public void init(){
		this.commandHandler.start();
	}

	public ActiveAccountManager getActiveAccountManager() {
		return activeAccountManager;
	}
	
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	public LogManager getLogManager() {
		return logManager;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public FriendManager getFriendManager() {
		return friendManager;
	}
	
	public AvatarManager getAvatarManager() {
		return avatarManager;
	}
	
	public PartyManager getPartyManager() {
		return partyManager;
	}

	public IDPool getIDPool() {
		return idPool;
	}

	public ActiveGameServerManager getActiveGameServerManager() {
		return activeServerManager;
	}

	public ChallengerManager getChallengerManager() {
		return challengerManager;
	}

	public SkinManager getSkinManager() {
		return skinManager;
	}

	public UnderglowManager getUnderglowManager() {
		return underglowManager;
	}

	public StoreManager getStoreManager() {
		return storeManager;
	}

}
