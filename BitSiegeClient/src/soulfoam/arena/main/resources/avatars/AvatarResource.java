package soulfoam.arena.main.resources.avatars;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arenashared.main.ids.AvatarLibrary;

public class AvatarResource {

	public ArrayList<AvatarInfo> avatarBackgrounds = new ArrayList<AvatarInfo>();
	public ArrayList<AvatarInfo> avatarBorders = new ArrayList<AvatarInfo>();
	public ArrayList<AvatarInfo> avatarIcons = new ArrayList<AvatarInfo>();

	private SpriteSheet avatarSheet;

	public AvatarResource() {
		loadAvatars();
	}

	public void loadAvatars() {

		avatarSheet = new SpriteSheet("art/avatars/avatars.png");

		avatarBackgrounds.add(new AvatarInfo(AvatarLibrary.BACKGROUND_BLACK, AvatarLibrary.SLOT_BACKGROUND,
				avatarSheet.grabImage(0, 0, 16, 16)));
		avatarBackgrounds.add(new AvatarInfo(AvatarLibrary.BACKGROUND_RED, AvatarLibrary.SLOT_BACKGROUND,
				avatarSheet.grabImage(1, 0, 16, 16)));
		avatarBackgrounds.add(new AvatarInfo(AvatarLibrary.BACKGROUND_BLUE, AvatarLibrary.SLOT_BACKGROUND,
				avatarSheet.grabImage(2, 0, 16, 16)));
		avatarBackgrounds.add(new AvatarInfo(AvatarLibrary.BACKGROUND_GREEN, AvatarLibrary.SLOT_BACKGROUND,
				avatarSheet.grabImage(3, 0, 16, 16)));
		avatarBackgrounds.add(new AvatarInfo(AvatarLibrary.BACKGROUND_PURPLE, AvatarLibrary.SLOT_BACKGROUND,
				avatarSheet.grabImage(4, 0, 16, 16)));
		avatarBackgrounds.add(new AvatarInfo(AvatarLibrary.BACKGROUND_DARK_GREEN, AvatarLibrary.SLOT_BACKGROUND,
				avatarSheet.grabImage(5, 0, 16, 16)));

		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_GOLD_0, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(0, 1, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_GOLD_1, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(1, 1, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_GOLD_2, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(2, 1, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_GOLD_3, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(3, 1, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_GOLD_4, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(4, 1, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_GOLD_5, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(5, 1, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_SILVER_0, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(0, 3, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_SILVER_1, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(1, 3, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_SILVER_2, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(2, 3, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_SILVER_3, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(3, 3, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_SILVER_4, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(4, 3, 16, 16)));
		avatarBorders.add(new AvatarInfo(AvatarLibrary.BORDER_SILVER_5, AvatarLibrary.SLOT_BORDER,
				avatarSheet.grabImage(5, 3, 16, 16)));

		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_GOLD_LION, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(0, 2, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_GOLD_WOLF, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(1, 2, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_GOLD_HORSE, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(2, 2, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_GOLD_TOWER, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(3, 2, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_GOLD_SHIELD, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(4, 2, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_GOLD_SWORD, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(5, 2, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_SILVER_LION, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(0, 4, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_SILVER_WOLF, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(1, 4, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_SILVER_HORSE, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(2, 4, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_SILVER_TOWER, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(3, 4, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_SILVER_SHIELD, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(4, 4, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_SILVER_SWORD, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(5, 4, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_GOLD_COIN, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(0, 5, 16, 16)));
		avatarIcons.add(new AvatarInfo(AvatarLibrary.ICON_GOLD_COCK_BIRD, AvatarLibrary.SLOT_ICON,
				avatarSheet.grabImage(1, 5, 16, 16)));

	}

	public Image getAvatarImage(int id, int slot) {

		if (slot == AvatarLibrary.SLOT_BACKGROUND) {
			for (AvatarInfo ap : avatarBackgrounds) {
				if (id == ap.getID() && ap.getSlot() == slot) {
					return ap.getImage();
				}
			}
		}
		if (slot == AvatarLibrary.SLOT_BORDER) {
			for (AvatarInfo ap : avatarBorders) {
				if (id == ap.getID() && ap.getSlot() == slot) {
					return ap.getImage();
				}
			}
		}
		if (slot == AvatarLibrary.SLOT_ICON) {
			for (AvatarInfo ap : avatarIcons) {
				if (id == ap.getID() && ap.getSlot() == slot) {
					return ap.getImage();
				}
			}
		}

		return avatarBackgrounds.get(0).getImage();
	}

	public AvatarInfo getAvatarInfo(int id, int slot) {

		if (slot == AvatarLibrary.SLOT_BACKGROUND) {
			for (AvatarInfo ap : avatarBackgrounds) {
				if (id == ap.getID() && ap.getSlot() == slot) {
					return ap;
				}
			}
		}
		if (slot == AvatarLibrary.SLOT_BORDER) {
			for (AvatarInfo ap : avatarBorders) {
				if (id == ap.getID() && ap.getSlot() == slot) {
					return ap;
				}
			}
		}
		if (slot == AvatarLibrary.SLOT_ICON) {
			for (AvatarInfo ap : avatarIcons) {
				if (id == ap.getID() && ap.getSlot() == slot) {
					return ap;
				}
			}
		}

		return avatarBackgrounds.get(0);
	}

	public void getAvatarCombined(int bgID, int borderID, int iconID) {

		Graphics avatarRenderer = null;
		Image img = null;

		Image bgImage = getAvatarImage(bgID, AvatarLibrary.SLOT_BACKGROUND);
		Image borderImage = getAvatarImage(borderID, AvatarLibrary.SLOT_BORDER);
		Image iconImage = getAvatarImage(iconID, AvatarLibrary.SLOT_ICON);

		try {
			avatarRenderer = img.getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		if (avatarRenderer != null) {
			avatarRenderer.drawImage(bgImage, 0, 0);
			avatarRenderer.drawImage(borderImage, 0, 0);
			avatarRenderer.drawImage(iconImage, 0, 0);
			avatarRenderer.flush();
		}

	}
}
