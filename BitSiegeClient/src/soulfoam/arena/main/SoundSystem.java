package soulfoam.arena.main;

import java.util.ArrayList;

import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

public class SoundSystem {

	public Music currentBackgroundMusic;
	public boolean PLAY_SOUNDS = true;

	public static int SOUND_EFFECT_DISTANCE = 220;

	public void setSoundSettings() {

		SoundStore.get().setMusicOn(!SettingManager.MUTE_MUSIC);
		SoundStore.get().setSoundsOn(!SettingManager.MUTE_SOUND_EFFECTS);

		SoundStore.get().setMusicVolume(SettingManager.MUSIC_VOLUME);
		SoundStore.get().setSoundVolume(SettingManager.SFX_VOLUME);

	}

	public void setSoundSettingsMenu() {

		SoundStore.get().setMusicOn(!SettingManager.MUTE_MENU_MUSIC);
		SoundStore.get().setSoundsOn(!SettingManager.MUTE_MENU_MUSIC);

	}

	public void playSound(Sound theSound, float volume) {
		if (PLAY_SOUNDS && theSound != null) {
			theSound.play(1.0f, volume);
		}
	}

	public void playSound(Sound theSound, float pitch, float volume) {
		if (PLAY_SOUNDS && theSound != null) {
			theSound.play(pitch, volume);
		}
	}

	public void playBackgroundMusic(Music theMusic, float volume) {

		currentBackgroundMusic = theMusic;
		theMusic.play(1.0f, volume);

	}

	public void chooseRandomBackgroundMusic() {
		ArrayList<Music> randomMusic = new ArrayList<Music>();

		randomMusic.add(Res.BATTLE_MUSIC[0]);
		randomMusic.add(Res.BATTLE_MUSIC[1]);
		randomMusic.add(Res.BATTLE_MUSIC[2]);
		playBackgroundMusic(randomMusic.get(Res.randInt(0, 2)), 0.5f);
	}

	public void chooseRandomBackroundMusicMenu() {
		playBackgroundMusic(Res.MENU_MUSIC[0], 0.5f);
	}

	public void loopSound(Sound theSound, float volume) {
		if (PLAY_SOUNDS && theSound != null) {
			theSound.loop(1.0f, volume);
		}
	}

	public void loopSound(Sound theSound, float pitch, float volume) {
		if (PLAY_SOUNDS && theSound != null) {
			theSound.loop(pitch, volume);
		}
	}

	public void stopSound(Sound theSound) {
		if (theSound != null) {
			theSound.stop();
		}
	}

	public void stopMusic() {
		if (currentBackgroundMusic != null) {
			currentBackgroundMusic.stop();
		}
	}

	public void stopAllSoundEffects() {
		for (int i = 0; i < Game.getGame().getAbilities().size(); i++) {
			Game.getGame().getAbilities().get(i).getSoundEffect().stop();
		}
	}

}
