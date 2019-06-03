package soulfoam.arena.net.lobby;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.chat.ChatUIPrivateObject;
import soulfoam.arena.main.menu.friend.FriendUIObject;
import soulfoam.arena.main.menu.request.RequestObject;
import soulfoam.arena.main.resources.avatars.AvatarInfo;
import soulfoam.arenashared.main.entityinfo.ChallengerInfo;
import soulfoam.arenashared.main.entityinfo.SkinInfo;
import soulfoam.arenashared.main.entityinfo.UnderglowInfo;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;

public class MultiplayerAccount {

	private ArrayList<ChallengerInfo> unlockedChallengers = new ArrayList<ChallengerInfo>();
	private ArrayList<SkinInfo> unlockedSkins = new ArrayList<SkinInfo>();
	private ArrayList<UnderglowInfo> unlockedUnderglows = new ArrayList<UnderglowInfo>();
	private ArrayList<AvatarInfo> unlockedAvatars = new ArrayList<AvatarInfo>();
	private ArrayList<RequestObject> requests = new ArrayList<RequestObject>();
	private ArrayList<FriendUIObject> friends = new ArrayList<FriendUIObject>();
	private ArrayList<ChatUIPrivateObject> privateChats = new ArrayList<ChatUIPrivateObject>();

	private AvatarInfo[] avatarInfo = new AvatarInfo[3];

	private int id = -1;
	private String username = "";
	private String email = "";
	private int bits;
	private int siegePoints;
	private int level;
	private int exp;
	private long creationDate;
	private boolean invisible;
	private boolean loggedIn;
	private Color nameColor;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAvatarInfo(AvatarInfo bg, AvatarInfo border, AvatarInfo icon) {
		avatarInfo[AvatarLibrary.SLOT_BACKGROUND] = bg;
		avatarInfo[AvatarLibrary.SLOT_BORDER] = border;
		avatarInfo[AvatarLibrary.SLOT_ICON] = icon;
	}

	public AvatarInfo[] getAvatarInfo() {
		return avatarInfo;
	}

	public ArrayList<AvatarInfo> getUnlockedAvatars(int slot) {

		if (slot == AvatarLibrary.SLOT_BACKGROUND) {
			ArrayList<AvatarInfo> bgs = new ArrayList<AvatarInfo>();
			for (AvatarInfo ai : unlockedAvatars) {
				if (ai.getSlot() == slot) {
					bgs.add(ai);
				}
			}
			return bgs;
		}
		if (slot == AvatarLibrary.SLOT_BORDER) {
			ArrayList<AvatarInfo> borders = new ArrayList<AvatarInfo>();
			for (AvatarInfo ai : unlockedAvatars) {
				if (ai.getSlot() == slot) {
					borders.add(ai);
				}
			}
			return borders;
		}
		if (slot == AvatarLibrary.SLOT_ICON) {
			ArrayList<AvatarInfo> icons = new ArrayList<AvatarInfo>();
			for (AvatarInfo ai : unlockedAvatars) {
				if (ai.getSlot() == slot) {
					icons.add(ai);
				}
			}
			return icons;
		}

		return unlockedAvatars;
	}

	public void setUnlockedChallengers(String unlockedChallengers) {

		if (unlockedChallengers.trim().isEmpty()) {
			return;
		}

		String[] info = unlockedChallengers.split(",");

		for (int i = 0; i < info.length; i++) {
			getUnlockedChallengers().add(new ChallengerInfo(Integer.parseInt(info[i])));
		}

	}

	public void setUnlockedSkins(String unlockedSkins) {

		if (unlockedSkins.trim().isEmpty()) {
			return;
		}

		String[] split = unlockedSkins.split("]");

		for (int i = 0; i < split.length; i++) {
			String[] info = split[i].split(",");
			getUnlockedSkins().add(new SkinInfo(Integer.parseInt(info[0]), Integer.parseInt(info[1])));
		}

	}

	public void setUnlockedUnderglows(String unlockedUnderglows) {

		if (unlockedUnderglows.trim().isEmpty()) {
			return;
		}

		String[] info = unlockedUnderglows.split(",");
		for (int i = 0; i < info.length; i++) {
			getUnlockedUnderglows().add(new UnderglowInfo(Integer.parseInt(info[i])));
		}

	}

	public void setUnlockedAvatars(String unlockedAvatarsString) {

		String[] avatarsUnlocked = unlockedAvatarsString.split("]");
		String[] bgsUnlocked = avatarsUnlocked[AvatarLibrary.SLOT_BACKGROUND].split(",");
		String[] bordersUnlocked = avatarsUnlocked[AvatarLibrary.SLOT_BORDER].split(",");
		String[] iconsUnlocked = avatarsUnlocked[AvatarLibrary.SLOT_ICON].split(",");

		for (int i = 0; i < bgsUnlocked.length; i++) {
			unlockedAvatars.add(new AvatarInfo(Integer.parseInt(bgsUnlocked[i]), AvatarLibrary.SLOT_BACKGROUND));
		}
		for (int i = 0; i < bordersUnlocked.length; i++) {
			unlockedAvatars.add(new AvatarInfo(Integer.parseInt(bordersUnlocked[i]), AvatarLibrary.SLOT_BORDER));
		}
		for (int i = 0; i < iconsUnlocked.length; i++) {
			unlockedAvatars.add(new AvatarInfo(Integer.parseInt(iconsUnlocked[i]), AvatarLibrary.SLOT_ICON));
		}
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public ArrayList<RequestObject> getRequests() {
		return requests;
	}

	public int getFriendsOnlineCount() {

		int count = 0;

		FriendUIObject[] tempList = new FriendUIObject[getFriends().size()];
		getFriends().toArray(tempList);

		for (FriendUIObject friend : tempList) {
			if (friend.getConnectionStatus() != ConnectionStatus.OFFLINE) {
				count++;
			}
		}

		return count;
	}

	public void setFriendConnectionStatus(int id, int status) {
		FriendUIObject[] tempList = new FriendUIObject[getFriends().size()];
		getFriends().toArray(tempList);

		for (FriendUIObject friend : tempList) {
			if (friend.getID() == id) {
				friend.setConnectionStatus(status);
			}
		}

		MainMenuManager.getMainMenu().getFriendUI().getFriendTab().sortPages();
		
		ChatUIPrivateObject[] privateChatTempList = new ChatUIPrivateObject[getPrivateChats().size()];
		getPrivateChats().toArray(privateChatTempList);

		for (ChatUIPrivateObject privateChat : privateChatTempList) {
			if (privateChat.getID() == id) {
				privateChat.setConnectionStatus(status);
			}
		}
		
		MainMenuManager.getMainMenu().getChatUI().getPrivateTab().setPrivatePages();
		MainMenuManager.getMainMenu().getChatUI().getPrivateTab().sortPages();
	}

	public void setFriendAvatar(int id, int bgID, int borderID, int iconID) {
		FriendUIObject[] tempList = new FriendUIObject[getFriends().size()];
		getFriends().toArray(tempList);

		for (FriendUIObject friend : tempList) {
			if (friend.getID() == id) {
				friend.setBGID(bgID);
				friend.setBorderID(borderID);
				friend.setIconID(iconID);
			}
		}

	}

	public ArrayList<FriendUIObject> getFriends() {
		return friends;
	}

	public void setFriends(String friend) {

		if (friend.trim().isEmpty()) {
			return;
		}

		String[] requestsSplit = friend.split("]");

		for (int i = 0; i < requestsSplit.length; i++) {
			String[] requestInfo = requestsSplit[i].split(",");

			getFriends().add(
					new FriendUIObject(Integer.parseInt(requestInfo[0]), requestInfo[1], Integer.parseInt(requestInfo[2]),
							Integer.parseInt(requestInfo[3]), Integer.parseInt(requestInfo[4]),
							Integer.parseInt(requestInfo[5]), Integer.parseInt(requestInfo[6]),
							Integer.parseInt(requestInfo[7]), Integer.parseInt(requestInfo[8])));
			
			getPrivateChats().add(
					new ChatUIPrivateObject(Integer.parseInt(requestInfo[0]), requestInfo[1], Integer.parseInt(requestInfo[2]),
							Integer.parseInt(requestInfo[3]), Integer.parseInt(requestInfo[4]),
							Integer.parseInt(requestInfo[5]), Integer.parseInt(requestInfo[6]),
							Integer.parseInt(requestInfo[7]), Integer.parseInt(requestInfo[8])));
		
		}

		
		
		MainMenuManager.getMainMenu().getFriendUI().getFriendTab().setFriendPages();
		MainMenuManager.getMainMenu().getFriendUI().getFriendTab().sortPages();
		
		MainMenuManager.getMainMenu().getChatUI().getPrivateTab().setPrivatePages();
		MainMenuManager.getMainMenu().getChatUI().getPrivateTab().sortPages();
	}

	public void setFriendRequests(String requests) {

		if (requests.trim().isEmpty()) {
			return;
		}

		String[] requestsSplit = requests.split("]");

		for (int i = 0; i < requestsSplit.length; i++) {
			String[] requestInfo = requestsSplit[i].split(",");

			getRequests().add(new RequestObject(Integer.parseInt(requestInfo[0]), requestInfo[1],
					Integer.parseInt(requestInfo[2]), Integer.parseInt(requestInfo[3]),
					Integer.parseInt(requestInfo[4]), Integer.parseInt(requestInfo[5]),
					Integer.parseInt(requestInfo[6]), Integer.parseInt(requestInfo[7])));

		}

		MainMenuManager.getMainMenu().getRequestUI().setRequestPages();
		MainMenuManager.getMainMenu().getRequestUI().sortPages();
	}

	public void addPartyRequest(String requests) {

		if (requests.trim().isEmpty()) {
			return;
		}

		String[] requestInfo = requests.split(",");

		getRequests().add(new RequestObject(Integer.parseInt(requestInfo[0]), Integer.parseInt(requestInfo[1]),
				requestInfo[2], Integer.parseInt(requestInfo[3]), Integer.parseInt(requestInfo[4]),
				Integer.parseInt(requestInfo[5]), Integer.parseInt(requestInfo[6]), Integer.parseInt(requestInfo[7]),
				Integer.parseInt(requestInfo[8])));

		MainMenuManager.getMainMenu().getRequestUI().setRequestPages();
		MainMenuManager.getMainMenu().getRequestUI().sortPages();
	}

	public RequestObject getRequestObjectByRequestID(int requestID) {

		RequestObject[] tempList = new RequestObject[getRequests().size()];
		getRequests().toArray(tempList);

		for (RequestObject request : tempList) {
			if (request.getRequestID() == requestID) {
				return request;
			}
		}

		return null;
	}

	public RequestObject getRequestObjectByPartyID(int partyID) {

		RequestObject[] tempList = new RequestObject[getRequests().size()];
		getRequests().toArray(tempList);

		for (RequestObject request : tempList) {
			if (request.getPartyID() == partyID) {
				return request;
			}
		}

		return null;
	}

	public RequestObject getRequestObjectByPartyIDAndSenderID(int partyID, int senderID) {

		RequestObject[] tempList = new RequestObject[getRequests().size()];
		getRequests().toArray(tempList);

		for (RequestObject request : tempList) {
			if (request.getPartyID() == partyID && request.getUserID() == senderID) {
				return request;
			}
		}

		return null;
	}

	public boolean hasAvatarUnlocked(int id, int slotID) {
		for (AvatarInfo ai : unlockedAvatars) {
			if (ai.getID() == id && ai.getSlot() == slotID) {
				return true;
			}
		}

		return false;
	}

	public boolean hasChallengerUnlocked(int challengerID) {
		for (ChallengerInfo ci : unlockedChallengers) {
			if (ci.getID() == challengerID) {
				return true;
			}
		}

		return false;
	}

	public boolean hasSkinUnlocked(int challengerID, int skinID) {
		for (SkinInfo si : unlockedSkins) {
			if (si.getChallengerID() == challengerID && si.getID() == skinID) {
				return true;
			}
		}

		return false;
	}

	public boolean hasUnderglowUnlocked(int underglowID) {
		for (UnderglowInfo ui : unlockedUnderglows) {
			if (ui.getID() == underglowID) {
				return true;
			}
		}

		return false;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	public int getBits() {
		return bits;
	}

	public void setBits(int bits) {
		this.bits = bits;
	}

	public int getSiegePoints() {
		return siegePoints;
	}

	public void setSiegePoints(int siegePoints) {
		this.siegePoints = siegePoints;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getEXP() {
		return exp;
	}

	public void setEXP(int exp) {
		this.exp = exp;
	}

	public Color getNameColor() {
		return nameColor;
	}

	public void setNameColor(int r, int g, int b) {
		nameColor = new Color(r, g, b);
	}

	public ArrayList<ChallengerInfo> getUnlockedChallengers() {
		return unlockedChallengers;
	}

	public ArrayList<SkinInfo> getUnlockedSkins() {
		return unlockedSkins;
	}

	public ArrayList<UnderglowInfo> getUnlockedUnderglows() {
		return unlockedUnderglows;
	}

	public ArrayList<AvatarInfo> getUnlockedAvatars() {
		return unlockedAvatars;
	}

	public ArrayList<ChatUIPrivateObject> getPrivateChats() {
		return privateChats;
	}

}
