package soulfoam.arena.main;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.states.GameState;
import soulfoam.arena.main.states.MenuState;
import soulfoam.arena.main.states.PregameLobbyState;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class ClientEngine extends StateBasedGame {

	public static AppGameContainer app;

	public ClientEngine(String name) {
		super(name);
	}

	public static void main(String[] args) {
		File f = new File("bitsiege.properties");

		if (!f.exists() && !f.isDirectory()) {
			SettingManager.makeConfigFile();
			SettingManager.readConfigFile();
		} else {
			SettingManager.readConfigFile();
		}
		
		try {
			Display.setIcon(new ByteBuffer[] {
			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/logo16.png")), false, false, null),
			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/logo32.png")), false, false, null)
			        });
		} catch (IOException e) {
			e.printStackTrace();
		}

		createGame();

	}

	public static void createGame() {

		try {

			app = new AppGameContainer(new ScalableGame(new ClientEngine("Bit Siege"), GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT, SettingManager.CONFIG_MAINTAIN_ASPECT_RATIO));
			if (SettingManager.CONFIG_FULL_SCREEN == 1) { // fullscreen
															// borderless
				System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
				app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), false);
			} else if (SettingManager.CONFIG_FULL_SCREEN == 2) { // fullscreen
																	// normal
				System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
				app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
			} else { // 0 or they changed config to a random num
				System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
				app.setDisplayMode(SettingManager.CONFIG_RES_WIDTH, SettingManager.CONFIG_RES_HEIGHT, false);
			}

			app.setAlwaysRender(true);
			app.setUpdateOnlyWhenVisible(false);
			app.setShowFPS(false);
			
			app.setTargetFrameRate(60);
			app.setMinimumLogicUpdateInterval(15);
			app.setMaximumLogicUpdateInterval(15);
			app.setVSync(SettingManager.CONFIG_VSYNC);
			app.setClearEachFrame(false);
			app.setMultiSample(0);
			app.start();

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void resetDisplay(GameContainer gc) throws SlickException {
		AppGameContainer agc = (AppGameContainer) gc;

		if (SettingManager.CONFIG_FULL_SCREEN == 1) { // fullscreen borderless
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
			app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), false);
		} else if (SettingManager.CONFIG_FULL_SCREEN == 2) { // fullscreen
																// normal
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
			app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
		} else { // 0 or they changed config to a random num
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
			app.setDisplayMode(SettingManager.CONFIG_RES_WIDTH, SettingManager.CONFIG_RES_HEIGHT, false);
		}

		agc.setTargetFrameRate(60);
		agc.setVSync(SettingManager.CONFIG_VSYNC);
	}

	public boolean closeRequested() {
		LobbyManager.getManager().logoutUserAccount();
		return true;
	}

	public void initStatesList(GameContainer gc) throws SlickException {

		new Res();

		addState(new MenuState());
		addState(new GameState());
		addState(new PregameLobbyState());

	}

}
