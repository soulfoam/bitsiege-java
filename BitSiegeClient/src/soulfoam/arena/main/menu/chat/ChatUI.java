package soulfoam.arena.main.menu.chat;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GameUtil;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class ChatUI extends BaseMenuUI {

	private Rectangle basePrivateChat;
	private Rectangle baseGlobalChat;

	private ChatUIPrivateTab privateTab;
	private ChatUIGlobalTab globalTab;
	private ChatUIPrivateChat privateChatTab;
	private String headerText = "Chat";
	
	private int render = 0;

	public ChatUI(GameContainer gc) {

		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);
		
		basePrivateChat = new Rectangle(getX() + 1, getY() + 11, 78, 9);
		baseGlobalChat = new Rectangle(getX() + 81, getY() + 11, 78, 9);
		
		privateTab = new ChatUIPrivateTab(getX(), getY() + 17);
		globalTab = new ChatUIGlobalTab(gc, getX(), getY() + 17);
		privateChatTab = new ChatUIPrivateChat(gc, getX(), getY() + 17);
		
		toggleTab(0);

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
		
		if (globalTab.isActive()) {
			globalTab.update(gc, delta);
		}
		if (privateTab.isActive()) {
			privateTab.update(gc, delta);
		}
		if (privateChatTab.isActive()){
			privateChatTab.update(gc, delta);
		}
	}
	
	public void startPrivateChat(ChatUIPrivateObject friend){
		headerText = "Chat with: " + friend.getUsername();
		privateChatTab.setChatPartner(friend);
		friend.setUnreadMessages(false);
		toggleTab(1);
	}

	public void toggleTab(int tab) {
		if (tab == 0) {
			headerText = "Private Chat";
			privateTab.setActive(true);
			globalTab.setActive(false);
			privateChatTab.setActive(false);
			render = 0;
		}
		if (tab == 1) {
			privateChatTab.setActive(true);
			privateTab.setActive(false);
			globalTab.setActive(false);
			render = 1;
		}
		if (tab == 2) {
			headerText = "Global Chat";
			privateChatTab.setActive(false);
			privateTab.setActive(false);
			globalTab.setActive(true);
			render = 2;
		}
	}

	public void setWindow(boolean set) {
		setFocus(set);
		setRender(set);
		privateTab.setPageIndex(0);
		toggleTab(0);
	}
	
	public boolean hasUnreadMessages(){
		for (int i = 0 ; i < privateTab.getPrivatePages().size(); i++){
			for (int j = 0; j < privateTab.getPrivatePages().get(i).getPrivateItems().size(); j++){
				if (privateTab.getPrivatePages().get(i).getPrivateItems().get(j).hasUnreadMessages()){
					return true;
				}
			}
		}
		
		return false;
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {

			renderWindow(g);

			if (privateChatTab.isActive()) {
				privateChatTab.render(gc, g);
			}
			if (privateTab.isActive()) {
				privateTab.render(gc, g);
			}
			if (globalTab.isActive()) {
				globalTab.render(gc, g);
			}
			
			if (basePrivateChat.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_CHAT_UI_SELECT.draw(basePrivateChat.getX(), basePrivateChat.getY());
			}
			if (baseGlobalChat.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_CHAT_UI_SELECT.draw(baseGlobalChat.getX(), baseGlobalChat.getY());
			}
		}
	}

	public void renderWindow(Graphics g) {
		
		if (render == 0){
			Res.UI_RESOURCE.MAINMENU_CHAT_UI_PRIVATE.draw(getX(), getY());
		}
		else if (render == 1){
			Res.UI_RESOURCE.MAINMENU_CHAT_UI_PRIVATE_CHAT.draw(getX(), getY());
		}
		else if (render == 2){
			Res.UI_RESOURCE.MAINMENU_CHAT_UI_GLOBAL.draw(getX(), getY());
		}
		Res.bitFont.drawString(getX() + getWidth()/2 - Res.getTextCenter(headerText), getY() + 3, headerText, Color.white);

	}
	
	public void addPrivateChatMessage(int friendID, String message){
		ChatUIPrivateObject chatPartner = null;
		
		for (int i = 0 ; i < privateTab.getPrivatePages().size(); i++){
			for (int j = 0; j < privateTab.getPrivatePages().get(i).getPrivateItems().size(); j++){
				if (privateTab.getPrivatePages().get(i).getPrivateItems().get(j).getID() == friendID){
					chatPartner = privateTab.getPrivatePages().get(i).getPrivateItems().get(j);
				}
			}
		}
		
		if (chatPartner == null){
			return;
		}
		
		message = chatPartner.getUsername() + ": " + message;
		for (String chatTextPart : GameUtil.getStringParts(message, 62)) {
			if (chatTextPart != null) {
				chatPartner.addChatHistory(new ChatUIHistoryMessage(chatTextPart, chatPartner.getNameColor()));
			}
		}
		
		chatPartner.setUnreadMessages(true);

		chatPartner.setLastMessageTime(new Timestamp(System.currentTimeMillis()));
		
		if (render == 1 && privateChatTab.getChatPartner().getID() == friendID){
			chatPartner.setUnreadMessages(false);
		}
		
		MainMenuManager.getMainMenu().getChatUI().getPrivateTab().sortPages();
	}
	
	public void addGlobalChatMessage(String message, int r, int g, int b){
		for (String chatTextPart : GameUtil.getStringParts(message, 62)) {
			if (chatTextPart != null) {
				if (globalTab.getChatHistory().size() >= 24){
					globalTab.getChatHistory().remove(0);
				}
				globalTab.getChatHistory().add(new ChatUIHistoryMessage(chatTextPart, new Color(r, g, b)));
			}
		}
	}

	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (basePrivateChat.contains(x, y)) {
				toggleTab(0);
			}
			if (baseGlobalChat.contains(x, y)) {
				toggleTab(2);
			}

		}
	}

	public ChatUIPrivateTab getPrivateTab() {
		return privateTab;
	}

	public ChatUIGlobalTab getGlobalTab() {
		return globalTab;
	}

	public ChatUIPrivateChat getPrivateChatTab() {
		return privateChatTab;
	}


}
