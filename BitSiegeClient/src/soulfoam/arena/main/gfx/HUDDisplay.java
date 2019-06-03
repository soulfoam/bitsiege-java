package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;


public class HUDDisplay {

	private Rectangle leaveGameButton = new Rectangle(125, 90, 140, 16);

	private Rectangle muteMusicButton = new Rectangle(105, 30, 80, 16);
	private Rectangle unMuteMusicButton = new Rectangle(25, 30, 75, 16);

	private Rectangle muteSoundEffectsButton = new Rectangle(285, 30, 75, 16);
	private Rectangle unMuteSoundEffectsButton = new Rectangle(205, 30, 75, 16);

	private boolean showHUD = true;

	private ScreenShake screenShake;
	private MinimapHUD miniMap;
	private NotificationHUD notificationLog;
	private HotbarHUD hotBar;
	private TeamHUD teamHUD;
	private StatHUD statHUD;
	private ChatHUD chatHUD;
	private GoldHUD goldHUD;
	private ScoreHUD scoreHUD;
	private ScoreboardHUD scoreboardHUD;
	private EscapeHUD escapeHUD;
	private TimerHUD timerHUD;

	private BaseHUDUI pickedUpItem;

	private boolean editHUD;

	public HUDDisplay(GameContainer gc) {

		screenShake = new ScreenShake();
		miniMap = new MinimapHUD(SettingManager.CONFIG_MINIMAP_X, SettingManager.CONFIG_MINIMAP_Y,
				SettingManager.CONFIG_MINIMAP_SCALE, SettingManager.CONFIG_MINIMAP_PLAYERSCALE,
				SettingManager.CONFIG_MINIMAP_TEXTSCALE, SettingManager.CONFIG_MINIMAP_OPACITY);
		notificationLog = new NotificationHUD(SettingManager.CONFIG_NOTIFICATION_LOG_X,
				SettingManager.CONFIG_NOTIFICATION_LOG_Y, SettingManager.CONFIG_NOTIFICATION_LOG_SCALE,
				SettingManager.CONFIG_NOTIFICATION_LOG_OPACITY, SettingManager.CONFIG_NOTIFICATION_LOG_REMOVELOGS,
				SettingManager.CONFIG_NOTIFICATION_TEXT_X, SettingManager.CONFIG_NOTIFICATION_TEXT_Y,
				SettingManager.CONFIG_NOTIFICATION_TEXT_SCALE, SettingManager.CONFIG_NOTIFICATION_TEXT_OPACITY);
		hotBar = new HotbarHUD(SettingManager.CONFIG_HOTBAR_X, SettingManager.CONFIG_HOTBAR_Y,
				SettingManager.CONFIG_HOTBAR_SCALE, SettingManager.CONFIG_HOTBAR_TEXTSCALE,
				SettingManager.CONFIG_HOTBAR_SPACING, SettingManager.CONFIG_HOTBAR_OPACITY,
				SettingManager.CONFIG_HOTBAR_VERTICAL);
		teamHUD = new TeamHUD(SettingManager.CONFIG_TEAMHUD_X, SettingManager.CONFIG_TEAMHUD_Y,
				SettingManager.CONFIG_TEAMHUD_SCALE, SettingManager.CONFIG_TEAMHUD_OPACITY);
		statHUD = new StatHUD(SettingManager.CONFIG_STATHUD_X, SettingManager.CONFIG_STATHUD_Y,
				SettingManager.CONFIG_STATHUD_SCALE, SettingManager.CONFIG_STATHUD_OPACITY,
				SettingManager.CONFIG_STATHUD_SHOWTIPS);
		chatHUD = new ChatHUD(SettingManager.CONFIG_CHATHUD_X, SettingManager.CONFIG_CHATHUD_Y,
				SettingManager.CONFIG_CHATHUD_WIDTH, SettingManager.CONFIG_CHATHUD_HEIGHT,
				SettingManager.CONFIG_CHATHUD_TEXTSCALE, SettingManager.CONFIG_CHATHUD_OPACITY, gc);
		goldHUD = new GoldHUD(SettingManager.CONFIG_GOLDHUD_X, SettingManager.CONFIG_GOLDHUD_Y,
				SettingManager.CONFIG_GOLDHUD_OPACITY);
		scoreHUD = new ScoreHUD(SettingManager.CONFIG_SCOREHUD_X, SettingManager.CONFIG_SCOREHUD_Y, 4,
				SettingManager.CONFIG_SCOREHUD_OPACITY, SettingManager.CONFIG_SCOREHUD_SHOWTIPS);
		scoreboardHUD = new ScoreboardHUD(105, 16);
		escapeHUD = new EscapeHUD(gc);
		timerHUD = new TimerHUD(SettingManager.CONFIG_TIMERHUD_X, SettingManager.CONFIG_TIMERHUD_Y, 4,
				SettingManager.CONFIG_TIMERHUD_OPACITY);
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		screenShake.update(delta);

		miniMap.update(gc, delta);
		notificationLog.update(gc, delta);
		hotBar.update(gc, delta);
		teamHUD.update(gc, delta);
		statHUD.update(gc, delta);
		chatHUD.update(gc, delta);
		goldHUD.update(gc, delta);
		scoreHUD.update(gc, delta);
		scoreboardHUD.update(gc);
		timerHUD.update(gc, delta);

		if (Game.getGame().getInput().isEscapePressed()) {
			escapeHUD.update(gc, s, delta);
		} else {
			escapeHUD.getSettingsMenuUI().setWindow(false);
			escapeHUD.getKeybindUI().setWindow(false);
		}

	}

	public void render(Graphics g) {

		Res.UI_RESOURCE.HUD.draw();
		
		renderHotbar(g);
		renderTeamHUD(g);
		renderTimerHUD(g);
		renderStatHUD(g);
	
	}
	
	public void renderScreenShake(Graphics g) {
		screenShake.applyTransform(g);
	}

	public void showAfterGameScreen(Graphics g) {

//			g.setColor(new Color(0, 0, 0, 200));
//			g.fillRect(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);
//
//			g.setColor(new Color(0, 0, 0, 200));
//			g.fillRect(GameInfo.RES_WIDTH / 2 / 2, 60, GameInfo.RES_WIDTH / 2, 55);
//
//			g.setColor(Color.white);
//			g.drawRect(GameInfo.RES_WIDTH / 2 / 2, 60, GameInfo.RES_WIDTH / 2, 55);
//
//			String outcome = "";
//			Color outcomeColor = Color.yellow;
//			String scoreText = "";
//			String statText = "";
//			String kdaText = "";
//			Color kdaColor = Color.white;
//
//			if (Game.getGame().getPlayer().getTeam() == Game.getGame().getClientFunctions()
//					.getGameOutCome()) {
//				outcome = "You Won! :)";
//				outcomeColor = Color.green;
//			} else {
//				if (Game.getGame().getClientFunctions().getGameOutCome() == 2) {
//					outcome = "Tie Game!";
//					outcomeColor = Color.yellow;
//				} else {
//					outcome = "You Lost! :(";
//					outcomeColor = Color.red;
//				}
//			}
//
//			if (Game.getGame().getPlayer().getTeam() == Res.TEAM_D) {
//				scoreText = "End Score: " + Game.getGame().getClientFunctions().getTeamDPoints() + " to "
//						+ Game.getGame().getClientFunctions().getTeamAPoints();
//			} else {
//				scoreText = "End Score: " + Game.getGame().getClientFunctions().getTeamAPoints() + " to "
//						+ Game.getGame().getClientFunctions().getTeamDPoints();
//			}
//
//			int deaths = Game.getGame().getPlayer().getDeaths();
//			float kda = 0;
//			if (deaths <= 0) {
//				kda = Res.twoDecimal(Game.getGame().getPlayer().getKills()
//						+ Game.getGame().getPlayer().getAssists() * 0.5f);
//			} else {
//				kda = Res.twoDecimal((Game.getGame().getPlayer().getKills()
//						+ Game.getGame().getPlayer().getAssists() * 0.5f)
//						/ Game.getGame().getPlayer().getDeaths());
//			}
//
//			statText = "Your K/D/A this game:";
//			kdaText = kda + "";
//
//			if (kda == 0) {
//				kdaColor = Color.white;
//			}
//			if (kda > 0) {
//				kdaColor = Color.green;
//			}
//			if (kda < 0) {
//				kdaColor = Color.red;
//			}
//
//			float centerOfOutcome = Res.bitFont.getWidth(outcome);
//			float centerOfScore = Res.bitFont.getWidth(scoreText);
//			float centerOfStats = Res.bitFont.getWidth(statText);
//			float centerOfKDA = Res.bitFont.getWidth(kdaText);
//
//			Res.bitFont.drawString(GameInfo.RES_WIDTH / 2 - centerOfOutcome, 65, outcome, outcomeColor);
//
//			Res.bitFont.drawString(GameInfo.RES_WIDTH / 2 - centerOfScore, 80, scoreText, outcomeColor);
//
//			Res.bitFont.drawString(GameInfo.RES_WIDTH / 2 - centerOfStats, 95, statText, Color.white);
//			Res.bitFont.drawString(GameInfo.RES_WIDTH / 2 - centerOfKDA, 105, kdaText, kdaColor);

		
	}

	public void renderNotificationLog(Graphics g) {
		notificationLog.render(g);
	}

	private void renderHotbar(Graphics g) {

		if (Game.getGame().getPlayer() != null) {
			
			float x = 199;
			float y = 247;
			float scale = 16;
			float spacing = 6;
			
			Color greenOpac = new Color(0, 255, 0);
			Color orangeOpac = new Color(255, 150, 0);
			Color whiteOpac = Color.red;
			
			String ability1CDString = "";
			String ability2CDString = "";
			String ability3CDString = "";
			String ability4CDString = "";
			
			ability1CDString = "" + (int) ((Game.getGame().getPlayer().getAbility1CDTimer()+1000)/1000);
			ability2CDString = "" + (int) ((Game.getGame().getPlayer().getAbility2CDTimer()+1000)/1000);
			ability3CDString = "" + (int) ((Game.getGame().getPlayer().getAbility3CDTimer()+1000)/1000);
			ability4CDString = "" + (int) ((Game.getGame().getPlayer().getAbility4CDTimer()+1000)/1000);
			
			if (Game.getGame().getPlayer().getSelectedAbility() == 1) {
				g.setColor(greenOpac);
				if (Game.getGame().getPlayer().getAbility1CDTimer() > 0) {
					g.setColor(orangeOpac);
				}
				g.fillRect(x - 1, y - 1, scale + 2, scale + 2);
			}
			if (Game.getGame().getPlayer().getSelectedAbility() == 2) {
				g.setColor(greenOpac);
				if (Game.getGame().getPlayer().getAbility2CDTimer() > 0) {
					g.setColor(orangeOpac);
				}
				g.fillRect(x + scale + spacing - 1, y - 1, scale + 2, scale + 2);
			}
			if (Game.getGame().getPlayer().getSelectedAbility() == 3) {
				g.setColor(greenOpac);
				if (Game.getGame().getPlayer().getAbility3CDTimer() > 0) {
					g.setColor(orangeOpac);
				}
				g.fillRect(x + scale * 2 + spacing * 2 - 1, y - 1, scale + 2, scale + 2);
			}
			if (Game.getGame().getPlayer().getSelectedAbility() == 4) {
				g.setColor(greenOpac);
				if (Game.getGame().getPlayer().getAbility4CDTimer() > 0) {
					g.setColor(orangeOpac);
				}
				g.fillRect(x + scale * 3 + spacing * 3 - 1, y - 1, scale + 2, scale + 2);
			}

			Game.getGame().getPlayer().getAbility1().getSkillIcon().draw(x, y, scale, scale);
			Game.getGame().getPlayer().getAbility2().getSkillIcon().draw(x + scale + spacing, y, scale,
					scale);
			Game.getGame().getPlayer().getAbility3().getSkillIcon().draw(x + scale * 2 + spacing * 2, y,
					scale, scale);
			Game.getGame().getPlayer().getAbility4().getSkillIcon().draw(x + scale * 3 + spacing * 3, y,
					scale, scale);

			g.setColor(new Color(0, 0, 0, 200));
			
			float cd1Box = -(scale * (Game.getGame().getPlayer().getAbility1CDTimer()
					/ Game.getGame().getPlayer().getAbility1CDTime()));
			float cd2Box = -(scale * (Game.getGame().getPlayer().getAbility2CDTimer()
					/ Game.getGame().getPlayer().getAbility2CDTime()));
			float cd3Box = -(scale * (Game.getGame().getPlayer().getAbility3CDTimer()
					/ Game.getGame().getPlayer().getAbility3CDTime()));
			float cd4Box = -(scale * (Game.getGame().getPlayer().getAbility4CDTimer()
					/ Game.getGame().getPlayer().getAbility4CDTime()));
			g.fillRect(x, y + scale, scale, cd1Box);
			g.fillRect(x + scale + spacing, y + scale, scale, cd2Box);
			g.fillRect(x + scale * 2 + spacing * 2, y + scale, scale, cd3Box);
			g.fillRect(x + scale * 3 + spacing * 3, y + scale, scale, cd4Box);

			if (Game.getGame().getPlayer().getAbility1CDTimer() > 0) {
				Res.UI_RESOURCE.HUD_COOLDOWN_TAB.draw(x - 2, y - 11);
				float cdCenter = (x - 1) - Res.getTextCenter(ability1CDString) + 19 / 2;
				Res.bitFont.drawString(cdCenter, y - 9, ability1CDString, whiteOpac);
			}
			if (Game.getGame().getPlayer().getAbility2CDTimer() > 0) {
				Res.UI_RESOURCE.HUD_COOLDOWN_TAB.draw(x + scale + spacing - 2, y - 11);
				float cdCenter = (x + scale + spacing - 1) - Res.getTextCenter(ability2CDString) + 19 / 2;
				Res.bitFont.drawString(cdCenter, y - 9, ability2CDString, whiteOpac);
			}
			if (Game.getGame().getPlayer().getAbility3CDTimer() > 0) {
				Res.UI_RESOURCE.HUD_COOLDOWN_TAB.draw(x + scale * 2 + spacing * 2 - 2, y - 11);
				float cdCenter = (x + scale * 2 + spacing * 2 - 1) - Res.getTextCenter(ability3CDString) + 19 / 2;
				Res.bitFont.drawString(cdCenter, y - 9, ability3CDString, whiteOpac);
			}
			if (Game.getGame().getPlayer().getAbility4CDTimer() > 0) {
				Res.UI_RESOURCE.HUD_COOLDOWN_TAB.draw(x + scale * 3 + spacing * 3 - 2, y - 11);
				float cdCenter = (x + scale * 3 + spacing * 3 - 1) - Res.getTextCenter(ability4CDString) + 19 / 2;
				Res.bitFont.drawString(cdCenter, y - 9, ability4CDString, whiteOpac);
			}
		}
		
	}

	public void renderDeadScreen(Graphics g) {
		if (Game.getGame().getPlayer().isDead() && Game.getGame().getPlayer().isLocalPlayer()) {

			g.setColor(new Color(0, 0, 0, 60));
			g.fillRect(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(55, 1, 210, 21);
			g.setColor(Color.white);
			g.drawRect(55, 1, 210, 21);
			float centerOfYAD = Res.bitFont.getWidth("You are dead!");
			float centerOfRI = Res.bitFont.getWidth("You will respawn at the start of the next round!") / 2;

			String spectatingUsername = "";

			if (Game.getGame().getCam().getCurrentPlayer() != null) {
				spectatingUsername = Game.getGame().getCam().getCurrentPlayer().getUsername();
			} else {
				spectatingUsername = "";
			}

			float centerOfSpectate = Res.bitFont.getWidth("Spectating: " + spectatingUsername);

			Res.bitFont.drawString(GameInfo.RES_WIDTH / 2 - centerOfYAD, 3, "You are dead!", Color.yellow);

			Res.bitFont.drawString(GameInfo.RES_WIDTH / 2 - centerOfRI, 9,
					"You will respawn at the start of the next round!", Color.white);
			Res.bitFont.drawString(GameInfo.RES_WIDTH / 2 - centerOfSpectate, 15, "Spectating: " + spectatingUsername,
					Color.green);

		}
	}

	public void attemptToPickUpHUD(BaseHUDUI uiObject) {
		miniMap.pickedUp = false;
		notificationLog.pickedUp = false;
		hotBar.pickedUp = false;
		teamHUD.pickedUp = false;
		statHUD.pickedUp = false;
		chatHUD.pickedUp = false;
		goldHUD.pickedUp = false;
		scoreHUD.pickedUp = false;
		scoreboardHUD.pickedUp = false;
		escapeHUD.pickedUp = false;
		uiObject.pickedUp = true;
	}

	public void renderEscapeMenu(GameContainer gc, Graphics g) {
		escapeHUD.render(gc, g);
	}

	public void renderMap(Graphics g) {
		miniMap.render(g);
	}

	public void renderChat(GameContainer gc, Graphics g) {
		chatHUD.render(gc, g);
	}

	public void renderGoldHUD(Graphics g) {
		goldHUD.render(g);
	}

	public void renderScoreHUD(Graphics g, GameContainer gc) {
		scoreHUD.render(g, gc);
	}

	public void renderScoreboardHUD(Graphics g, GameContainer gc) {
		scoreboardHUD.render(g, gc);
	}

	public void renderStatHUD(Graphics g) {
		
		float healthBarSize = 148;
		float healthBarWidth = healthBarSize * (Game.getGame().getPlayer().getHealth() * 1.0f / (Game.getGame().getPlayer().getBaseHealth() * 1.0f));
		float healthBarHeight = 7;
		
		Res.UI_RESOURCE.GREEN_HEALTH_BAR_BIG.draw(1, 243, healthBarWidth, healthBarHeight);
		Res.bitFont.drawString(20, 244, Math.round(Game.getGame().getPlayer().getHealth()) + "", Color.white);

		
		Res.bitFont.drawString(25, 258, Res.getMoveSpeedDisplay(Game.getGame().getPlayer().getMoveSpeed()), Color.white);
		Res.bitFont.drawString(71, 258, (int)Math.round(Game.getGame().getPlayer().getAbilityBasicAttack().getDamage()) + "", Color.white);
		Res.bitFont.drawString(118, 258, String.format("%.02f",EntityInfo.getAttackSpeed(Game.getGame().getPlayer().getAbilityBasicAttackCDTime(), Game.getGame().getPlayer().getAttackSpeedBuffAmount())), Color.white);
	}

	public void renderTeamHUD(Graphics g) {
		
		float spacing = 0;
		float x = -16;
		float y = 8;
		
		for (Entity p : Game.getGame().getPlayers()) {

			if (p.getTeam() == Game.getGame().getPlayer().getTeam()) {

				spacing += 19;

				p.getAnimation().getPortrait().draw(Res.roundForRendering(x + spacing), Res.roundForRendering(y));

				float health = p.getHealth();
				float maxHealth = p.getBaseHealth();
				float healthBarSize = 18;
				float healthBarWidth = healthBarSize * (health * 1.0f / (maxHealth * 1.0f));
				float healthBarHeight = 4;
				
				Res.UI_RESOURCE.GREEN_HEALTH_BAR.draw(x + spacing - 1, y - 6, healthBarWidth, healthBarHeight);
				
				if (p.getAbility4CDTimer() <= 0) {
					Res.UI_RESOURCE.GREEN_HEALTH_BAR.draw(x + spacing + 6, y + 19, 4, 2);
				} else {
					Res.UI_RESOURCE.RED_HEALTH_BAR.draw(x + spacing + 6, y + 19, 4, 2);
				}
				
			}
		}
		
	}

	public void renderTimerHUD(Graphics g) {
		
		float x = 224;
		float y = 5;
		
		int gcm = (int) Math.floor((Game.getGame().getClientFunctions().getGameClock()/1000) / 60);
		int gcs = (Game.getGame().getClientFunctions().getGameClock()/1000) - gcm * 60;

		Res.bitFont.drawString(x, y - 0.5f,String.format("%02d", gcm) + ":" + String.format("%02d", gcs), Color.yellow);
	}

	public void saveHUDGUI() {
		SettingManager.setConfigFile("guiminimapx", String.valueOf(miniMap.x));
		SettingManager.setConfigFile("guiminimapy", String.valueOf(miniMap.y));
		SettingManager.setConfigFile("guiminimapscale", String.valueOf(miniMap.miniMapScale));
		SettingManager.setConfigFile("guiminimapplayerscale", String.valueOf(miniMap.playerScale));
		SettingManager.setConfigFile("guiminimaptextscale", String.valueOf(miniMap.textScale));
		SettingManager.setConfigFile("guiminimapopacity", String.valueOf(miniMap.opacity));
		SettingManager.CONFIG_MINIMAP_X = miniMap.x;
		SettingManager.CONFIG_MINIMAP_Y = miniMap.y;
		SettingManager.CONFIG_MINIMAP_SCALE = miniMap.miniMapScale;
		SettingManager.CONFIG_MINIMAP_PLAYERSCALE = miniMap.playerScale;
		SettingManager.CONFIG_MINIMAP_TEXTSCALE = miniMap.textScale;
		SettingManager.CONFIG_MINIMAP_OPACITY = miniMap.opacity;

		SettingManager.setConfigFile("guinotificationlogx", String.valueOf(notificationLog.getLogX()));
		SettingManager.setConfigFile("guinotificationlogy", String.valueOf(notificationLog.getLogY()));
		SettingManager.setConfigFile("guinotificationlogscale", String.valueOf(notificationLog.getLogScale()));
		SettingManager.setConfigFile("guinotificationlogopacity", String.valueOf(notificationLog.getLogOpacity()));
		SettingManager.setConfigFile("guinotificationlogremovelogs", String.valueOf(notificationLog.isRemoveLogs()));
		SettingManager.CONFIG_NOTIFICATION_LOG_X = notificationLog.getLogX();
		SettingManager.CONFIG_NOTIFICATION_LOG_Y = notificationLog.getLogY();
		SettingManager.CONFIG_NOTIFICATION_LOG_SCALE = notificationLog.getLogScale();
		SettingManager.CONFIG_NOTIFICATION_LOG_OPACITY = notificationLog.getOpacity();
		SettingManager.CONFIG_NOTIFICATION_LOG_REMOVELOGS = notificationLog.isRemoveLogs();

		SettingManager.setConfigFile("guinotificationtextx", String.valueOf(notificationLog.getTextX()));
		SettingManager.setConfigFile("guinotificationtexty", String.valueOf(notificationLog.getTextY()));
		SettingManager.setConfigFile("guinotificationtextscale", String.valueOf(notificationLog.getTextScale()));
		SettingManager.setConfigFile("guinotificationtextopacity", String.valueOf(notificationLog.getTextOpacity()));
		SettingManager.CONFIG_NOTIFICATION_TEXT_X = notificationLog.getTextX();
		SettingManager.CONFIG_NOTIFICATION_TEXT_Y = notificationLog.getTextY();
		SettingManager.CONFIG_NOTIFICATION_TEXT_SCALE = notificationLog.getTextScale();
		SettingManager.CONFIG_NOTIFICATION_TEXT_OPACITY = notificationLog.getTextOpacity();

		SettingManager.setConfigFile("guihotbarx", String.valueOf(hotBar.x));
		SettingManager.setConfigFile("guihotbary", String.valueOf(hotBar.y));
		SettingManager.setConfigFile("guihotbarscale", String.valueOf(hotBar.scale));
		SettingManager.setConfigFile("guihotbartextscale", String.valueOf(hotBar.textScale));
		SettingManager.setConfigFile("guihotbarspacing", String.valueOf(hotBar.getSpacing()));
		SettingManager.setConfigFile("guihotbaropacity", String.valueOf(hotBar.opacity));
		SettingManager.setConfigFile("guihotbarvertical", String.valueOf(hotBar.isVertical()));
		SettingManager.CONFIG_HOTBAR_X = hotBar.x;
		SettingManager.CONFIG_HOTBAR_Y = hotBar.y;
		SettingManager.CONFIG_HOTBAR_SCALE = hotBar.scale;
		SettingManager.CONFIG_HOTBAR_TEXTSCALE = hotBar.textScale;
		SettingManager.CONFIG_HOTBAR_SPACING = hotBar.getSpacing();
		SettingManager.CONFIG_HOTBAR_OPACITY = hotBar.opacity;
		SettingManager.CONFIG_HOTBAR_VERTICAL = hotBar.isVertical();

		SettingManager.setConfigFile("guiteamhudx", String.valueOf(teamHUD.x));
		SettingManager.setConfigFile("guiteamhudy", String.valueOf(teamHUD.y));
		SettingManager.setConfigFile("guiteamhudscale", String.valueOf(teamHUD.scale));
		SettingManager.setConfigFile("guiteamhudopacity", String.valueOf(teamHUD.opacity));
		SettingManager.CONFIG_TEAMHUD_X = teamHUD.x;
		SettingManager.CONFIG_TEAMHUD_Y = teamHUD.y;
		SettingManager.CONFIG_TEAMHUD_SCALE = teamHUD.scale;
		SettingManager.CONFIG_TEAMHUD_OPACITY = teamHUD.opacity;

		SettingManager.setConfigFile("guistathudx", String.valueOf(statHUD.x));
		SettingManager.setConfigFile("guistathudy", String.valueOf(statHUD.y));
		SettingManager.setConfigFile("guistathudscale", String.valueOf(statHUD.scale));
		SettingManager.setConfigFile("guistathudopacity", String.valueOf(statHUD.opacity));
		SettingManager.setConfigFile("guistathudshowtips", String.valueOf(statHUD.showTips));
		SettingManager.CONFIG_STATHUD_X = statHUD.x;
		SettingManager.CONFIG_STATHUD_Y = statHUD.y;
		SettingManager.CONFIG_STATHUD_SCALE = statHUD.scale;
		SettingManager.CONFIG_STATHUD_OPACITY = statHUD.opacity;
		SettingManager.CONFIG_STATHUD_SHOWTIPS = statHUD.showTips;

		SettingManager.setConfigFile("guichathudx", String.valueOf(chatHUD.x));
		SettingManager.setConfigFile("guichathudy", String.valueOf(chatHUD.y));
		SettingManager.setConfigFile("guichathudwidth", String.valueOf(chatHUD.width));
		SettingManager.setConfigFile("guichathudheight", String.valueOf(chatHUD.height));
		SettingManager.setConfigFile("guichathudtextscale", String.valueOf(chatHUD.textScale));
		SettingManager.setConfigFile("guichathudopacity", String.valueOf(chatHUD.opacity));
		SettingManager.CONFIG_CHATHUD_X = chatHUD.x;
		SettingManager.CONFIG_CHATHUD_Y = chatHUD.y;
		SettingManager.CONFIG_CHATHUD_WIDTH = chatHUD.width;
		SettingManager.CONFIG_CHATHUD_HEIGHT = chatHUD.height;
		SettingManager.CONFIG_CHATHUD_TEXTSCALE = chatHUD.textScale;
		SettingManager.CONFIG_CHATHUD_OPACITY = chatHUD.opacity;

		SettingManager.setConfigFile("guigoldhudx", String.valueOf(goldHUD.x));
		SettingManager.setConfigFile("guigoldhudy", String.valueOf(goldHUD.y));
		SettingManager.setConfigFile("guigoldhudopacity", String.valueOf(goldHUD.opacity));
		SettingManager.CONFIG_GOLDHUD_X = goldHUD.x;
		SettingManager.CONFIG_GOLDHUD_Y = goldHUD.y;
		SettingManager.CONFIG_GOLDHUD_OPACITY = goldHUD.opacity;

		SettingManager.setConfigFile("guiscorehudx", String.valueOf(scoreHUD.x));
		SettingManager.setConfigFile("guiscorehudy", String.valueOf(scoreHUD.y));
		SettingManager.setConfigFile("guiscorehudopacity", String.valueOf(scoreHUD.opacity));
		SettingManager.setConfigFile("guiscorehudshowtips", String.valueOf(scoreHUD.showTips));
		SettingManager.CONFIG_SCOREHUD_X = scoreHUD.x;
		SettingManager.CONFIG_SCOREHUD_Y = scoreHUD.y;
		SettingManager.CONFIG_SCOREHUD_OPACITY = scoreHUD.opacity;
		SettingManager.CONFIG_SCOREHUD_SHOWTIPS = scoreHUD.showTips;

		SettingManager.setConfigFile("guitimerhudx", String.valueOf(timerHUD.x));
		SettingManager.setConfigFile("guitimerhudy", String.valueOf(timerHUD.y));
		SettingManager.setConfigFile("guitimerhudopacity", String.valueOf(timerHUD.opacity));
		SettingManager.CONFIG_TIMERHUD_X = timerHUD.x;
		SettingManager.CONFIG_TIMERHUD_Y = timerHUD.y;
		SettingManager.CONFIG_TIMERHUD_OPACITY = timerHUD.opacity;

	}

	public Rectangle getLeaveGameButton() {
		return leaveGameButton;
	}

	public Rectangle getMuteMusicButton() {
		return muteMusicButton;
	}

	public Rectangle getUnMuteMusicButton() {
		return unMuteMusicButton;
	}

	public Rectangle getMuteSoundEffectsButton() {
		return muteSoundEffectsButton;
	}

	public Rectangle getUnMuteSoundEffectsButton() {
		return unMuteSoundEffectsButton;
	}

	public boolean showHUD() {
		return showHUD;
	}

	public ScreenShake getScreenShake() {
		return screenShake;
	}

	public MinimapHUD getMiniMap() {
		return miniMap;
	}

	public NotificationHUD getNotificationLog() {
		return notificationLog;
	}

	public HotbarHUD getHotBar() {
		return hotBar;
	}

	public TeamHUD getTeamHUD() {
		return teamHUD;
	}

	public StatHUD getStatHUD() {
		return statHUD;
	}

	public ChatHUD getChatHUD() {
		return chatHUD;
	}

	public GoldHUD getGoldHUD() {
		return goldHUD;
	}

	public ScoreHUD getScoreHUD() {
		return scoreHUD;
	}

	public ScoreboardHUD getScoreboardHUD() {
		return scoreboardHUD;
	}

	public EscapeHUD getEscapeHUD() {
		return escapeHUD;
	}

	public TimerHUD getTimerHUD() {
		return timerHUD;
	}

	public BaseHUDUI getPickedUpItem() {
		return pickedUpItem;
	}

	public boolean isEditingHUD() {
		return editHUD;
	}

	public void setEditHUD(boolean editHUD) {
		this.editHUD = editHUD;
	}

	public void setShowHUD(boolean showHUD) {
		this.showHUD = showHUD;
	}

}
