package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.menu.settings.KeybindUI;
import soulfoam.arena.main.menu.settings.SettingsMenuUI;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class EscapeHUD extends BaseHUDUI {

	private HUDButton leaveGameButton;
	private HUDButton resumeGameButton;

	private HUDButton musicButton;
	private HUDButton musicPlusButton;
	private HUDButton musicMinusButton;
	private HUDButton sfxPlusButton;
	private HUDButton sfxMinusButton;

	private HUDButton sfxButton;

	private HUDButton settingsButton;

	private SettingsMenuUI smui;
	private KeybindUI kbui = new KeybindUI(this);

	public EscapeHUD(GameContainer gc) {

		x = GameInfo.RES_WIDTH / 2 - 75;
		y = 100;

		leaveGameButton = new HUDButton("Leave Game", x, y, 80, 8);
		resumeGameButton = new HUDButton("Resume Game", x, y, 80, 8);

		musicButton = new HUDButton("Music", x, y, 28, 8);
		musicPlusButton = new HUDButton("+", x, y, 10, 8);
		musicMinusButton = new HUDButton("-", x, y, 10, 8);

		sfxButton = new HUDButton("SFX", x, y, 28, 8);
		sfxPlusButton = new HUDButton("+", x, y, 10, 8);
		sfxMinusButton = new HUDButton("-", x, y, 10, 8);
		settingsButton = new HUDButton("Settings", x, y, 80, 8);

		smui = new SettingsMenuUI(this, gc);

		setButtons();
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		if (smui.isRendering()) {
			smui.update(gc, s, delta);
		}
		if (kbui.isRendering()) {
			kbui.update(gc, s, delta);
		}

		if (smui.isRendering() || kbui.isRendering()) {
			return;
		}

		leaveGameButton.update(gc);
		resumeGameButton.update(gc);
		musicButton.update(gc);
		sfxButton.update(gc);
		musicPlusButton.update(gc);
		musicMinusButton.update(gc);
		sfxPlusButton.update(gc);
		sfxMinusButton.update(gc);
		settingsButton.update(gc);

		if (resumeGameButton.isClicked()) {
			Game.getGame().getInput().setEscapePressed(!Game.getGame().getInput().isEscapePressed());
		}
		if (leaveGameButton.isClicked()) {
			Game.getGame().getClientFunctions().leaveCurrentGame(s);
		}
		if (musicButton.isClicked()) {
			if (SettingManager.MUTE_MUSIC) {
				SoundStore.get().setMusicOn(true);
				SettingManager.MUTE_MUSIC = false;
				SettingManager.setConfigFile("mutemusic", "false");
			} else {
				SoundStore.get().setMusicOn(false);
				SettingManager.MUTE_MUSIC = true;
				SettingManager.setConfigFile("mutemusic", "true");
			}
		}

		if (sfxButton.isClicked()) {
			if (SettingManager.MUTE_SOUND_EFFECTS) {
				SoundStore.get().setSoundsOn(true);
				SettingManager.MUTE_SOUND_EFFECTS = false;
				SettingManager.setConfigFile("mutesoundeffects", "false");
			} else {
				SoundStore.get().setSoundsOn(false);
				SettingManager.MUTE_SOUND_EFFECTS = true;
				SettingManager.setConfigFile("mutesoundeffects", "true");
			}

		}

		if (sfxPlusButton.isClicked()) {
			SettingManager.SFX_VOLUME += 0.25f;
			if (SettingManager.SFX_VOLUME >= 1) {
				SettingManager.SFX_VOLUME = 1;
			}
			SettingManager.setConfigFile("sfxvolume", SettingManager.SFX_VOLUME + "");
			SoundStore.get().setSoundVolume(SettingManager.SFX_VOLUME);
		}

		if (sfxMinusButton.isClicked()) {
			SettingManager.SFX_VOLUME -= 0.25f;
			if (SettingManager.SFX_VOLUME <= 0) {
				SettingManager.SFX_VOLUME = 0;
			}
			SettingManager.setConfigFile("sfxvolume", SettingManager.SFX_VOLUME + "");
			SoundStore.get().setSoundVolume(SettingManager.SFX_VOLUME);
		}

		if (musicPlusButton.isClicked()) {
			SettingManager.MUSIC_VOLUME += 0.25f;
			if (SettingManager.MUSIC_VOLUME >= 1) {
				SettingManager.MUSIC_VOLUME = 1;
			}
			SettingManager.setConfigFile("musicvolume", SettingManager.MUSIC_VOLUME + "");
			SoundStore.get().setMusicVolume(SettingManager.MUSIC_VOLUME);
		}

		if (musicMinusButton.isClicked()) {
			SettingManager.MUSIC_VOLUME -= 0.25f;
			if (SettingManager.MUSIC_VOLUME <= 0) {
				SettingManager.MUSIC_VOLUME = 0;
			}
			SettingManager.setConfigFile("musicvolume", SettingManager.MUSIC_VOLUME + "");
			SoundStore.get().setMusicVolume(SettingManager.MUSIC_VOLUME);
		}

		if (settingsButton.isClicked()) {
			smui.setWindow(true);
		}

		setButtons();
	}

	public void render(GameContainer gc, Graphics g) {

		g.setColor(new Color(0, 0, 0, 170));
		g.fillRect(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);

		if (smui.isRendering()) {
			smui.render(gc, g);
		}

		if (kbui.isRendering()) {
			kbui.render(gc, g);
		}

		if (smui.isRendering() || kbui.isRendering()) {
			return;
		}

		Color musicColor = new Color(255, 100, 100, 255);
		Color sfxColor = new Color(255, 100, 100, 255);

		if (SoundStore.get().musicOn()) {
			musicColor = new Color(9, 183, 25);
		} else {
			musicColor = new Color(183, 29, 9);
		}

		if (SoundStore.get().soundsOn()) {
			sfxColor = new Color(9, 183, 25);
		} else {
			sfxColor = new Color(183, 29, 9);
		}

		g.setColor(Res.COLOR_RESOURCE.WINDOW_BLUE_STRIP);
		g.fillRect(x, y, 170, 41);
		g.fillRect(x + 35, y + 41, 100, 14);

		g.setColor(new Color(53, 146, 212));
		g.drawRect(x, y, 170, 41);
		g.drawRect(x + 35, y + 41, 100, 14);

		Res.bitFont.drawString(x + 5, y + 6, "Map: " + Game.getGame().getWorld().getMap().getMapName());
		Res.bitFont.drawString(x + 62, y + 19, "" + SoundStore.get().getMusicVolume(), Color.green);
		Res.bitFont.drawString(x + 62, y + 29, "" + SoundStore.get().getSoundVolume(), Color.green);

		musicButton.setButtonColor(musicColor);
		sfxButton.setButtonColor(sfxColor);

		leaveGameButton.render(g, 1);
		resumeGameButton.render(g, 1);
		musicButton.render(g, 1);
		sfxButton.render(g, 1);

		musicPlusButton.render(g, 1);
		musicMinusButton.render(g, 1);
		sfxPlusButton.render(g, 1);
		sfxMinusButton.render(g, 1);

		settingsButton.render(g, 1);

	}

	public void setButtons() {

		leaveGameButton.setX(x + 84);
		leaveGameButton.setY(y + 28);

		resumeGameButton.setX(x + 84);
		resumeGameButton.setY(y + 18);

		musicButton.setX(x + 5);
		musicButton.setY(y + 18);

		sfxButton.setX(x + 5);
		sfxButton.setY(y + 28);

		musicPlusButton.setX(x + 37);
		musicPlusButton.setY(y + 18);

		musicMinusButton.setX(x + 48);
		musicMinusButton.setY(y + 18);

		sfxPlusButton.setX(x + 37);
		sfxPlusButton.setY(y + 28);

		sfxMinusButton.setX(x + 48);
		sfxMinusButton.setY(y + 28);

		settingsButton.setX(x + 45.5f);
		settingsButton.setY(y + 44);
	}

	public SettingsMenuUI getSettingsMenuUI() {
		return smui;
	}

	public KeybindUI getKeybindUI() {
		return kbui;
	}

}
