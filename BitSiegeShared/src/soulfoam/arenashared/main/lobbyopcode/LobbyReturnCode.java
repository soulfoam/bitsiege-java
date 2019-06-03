package soulfoam.arenashared.main.lobbyopcode;

public class LobbyReturnCode {

	public static final int ACCOUNT_CREATE_SUCCESS = 0;
	public static final int ACCOUNT_CREATE_USEREXISTS = 1;
	public static final int ACCOUNT_CREATE_SERVERERROR = 2;
	public static final int ACCOUNT_CREATE_PASSWORDSDONTMATCH = 3;
	public static final int ACCOUNT_CREATE_PASSWORDTOOSHORT = 4;
	public static final int ACCOUNT_CREATE_USERNAMETOOLONG = 5;
	public static final int ACCOUNT_CREATE_INVALIDEMAIL = 6;
	public static final int ACCOUNT_CREATE_BANNED = 7;
	public static final int ACCOUNT_CREATE_USERNAMEEMPTY = 8;
	public static final int ACCOUNT_CREATE_USERNAMECONTAINSSPECIALCHARACTERS = 9;
	
	public static final int ACCOUNT_LOGIN_SUCCESS = 0;
	public static final int ACCOUNT_LOGIN_USERDOESNTEXIST = 1;
	public static final int ACCOUNT_LOGIN_SERVERERROR = 2;
	public static final int ACCOUNT_LOGIN_INVALIDPASSWORD = 3;
	public static final int ACCOUNT_LOGIN_BANNED = 4;
	public static final int ACCOUNT_LOGIN_ALREADYLOGGEDIN = 5;
	public static final int ACCOUNT_LOGIN_BANNED_IP = 6;
	
	public static final int ACCOUNT_CHANGEPASSWORD_SUCCESS = 0;
	public static final int ACCOUNT_CHANGEPASSWORD_PASSWORDSDONTMATCH = 1;
	public static final int ACCOUNT_CHANGEPASSWORD_PASSWORDTOOSHORT = 2;
	public static final int ACCOUNT_CHANGEPASSWORD_CURRENTPASSWORDISWRONG = 3;
	
	public static final int ACCOUNT_CHANGEEMAIL_SUCCESS = 0;
	public static final int ACCOUNT_CHANGEEMAIL_INVALIDEMAIL = 1;
	public static final int ACCOUNT_CHANGEEMAIL_EMAILSDONTMATCH = 2;
	public static final int ACCOUNT_CHANGEEMAIL_CURRENTPASSWORDISWRONG = 3;
	
	public static final int MATCHMAKING_QUE_SUCCESS = 0;
	public static final int MATCHMAKING_QUE_SERVERERROR = 1;
	public static final int MATCHMAKING_QUE_YOUARENOTPARTYLEADER = 2;
	public static final int MATCHMAKING_QUE_GAMEFOUND = 3;
	public static final int MATCHMAKING_QUE_NOAVAILABLEGAMESERVERS = 4;
	public static final int MATCHMAKING_QUE_SEARCHINGSTOPPED = 5;
	
	public static final int FRIEND_SEARCH_SUCCESS = 0;
	public static final int FRIEND_SEARCH_USERDOESNOTEXIST = 1;
	public static final int FRIEND_SEARCH_SERVERERROR = 2;
	public static final int FRIEND_ADD_SUCCESS = 3;
	public static final int FRIEND_ADD_USERDOESNOTEXIST = 4;
	public static final int FRIEND_ADD_REQUESTALREADYSENT = 5;
	public static final int FRIEND_ADD_ALREADYFRIENDS = 6;
	public static final int FRIEND_ADD_USERISYOU = 7;
	
	public static final int AVATAR_CHANGE_SUCCESS = 0;
	public static final int AVATAR_CHANGE_NOTUNLOCKED = 1;
	
	public static final int REQUEST_FRIEND_ACCEPT_SUCCESS = 0;
	public static final int REQUEST_FRIEND_DECLINE_SUCCESS = 1;
	public static final int REQUEST_FRIEND_REQUESTDOESNTEXIST = 2;
	public static final int REQUEST_PARTY_ACCEPT_SUCCESS = 3;
	public static final int REQUEST_PARTY_DECLINE_SUCCESS = 4;
	public static final int REQUEST_PARTY_REQUESTDOESNTEXIST = 5;
	public static final int REQUEST_PARTY_FULL = 6;
	public static final int REQUEST_PARTY_ALREADYINPARTY = 7;
	public static final int REQUEST_PARTY_PRIVATEPARTY = 8;
	
	public static final int PARTY_INVITE_SUCCESS = 0;
	public static final int PARTY_INVITE_YOUARENOTFRIENDS = 1;
	public static final int PARTY_INVITE_INVITEALREADYEXISTS = 2;
	public static final int PARTY_INVITE_USEROFFLINE = 3;
	public static final int PARTY_INVITE_NOPERMISSION = 4;
	public static final int PARTY_INVITE_USERALREADYINPARTY = 5;
	
	public static final int PARTY_LEAVE_SUCCESS = 0;
	public static final int PARTY_KICK_SUCCESS = 1;
	public static final int PARTY_KICK_NOPERMISSION = 2;
	public static final int PARTY_MAKELEADER_SUCCESS = 3;
	public static final int PARTY_KICKED = 4;
	public static final int PARTY_MAKELEADER_SERVERERROR = 5;
	public static final int PARTY_MADELEADER = 6;
	
	public static final int STORE_NOT_ENOUGH_BITS = 0;
	public static final int STORE_NOT_ENOUGH_SIEGEPOINTS = 1;
	public static final int STORE_BOUGHT_SUCCESS_CHALLENGER = 2;
	public static final int STORE_ERROR = 3;
	public static final int STORE_ALREADY_OWNED = 4;
	public static final int STORE_BOUGHT_SUCCESS_SKIN = 5;
	public static final int STORE_BOUGHT_SUCCESS_UNDERGLOW = 6;
	public static final int STORE_BOUGHT_SUCCESS_ACCOUNT = 7;
	public static final int STORE_BOUGHT_SUCCESS_AVATAR_BG = 8;
	public static final int STORE_BOUGHT_SUCCESS_AVATAR_BORDER = 9;
	public static final int STORE_BOUGHT_SUCCESS_AVATAR_ICON = 10;
	public static final int STORE_CHALLENGER_NOT_OWNED = 11;

}
