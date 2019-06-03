package soulfoam.arena.main.resources.avatars;

import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;

public class AvatarInfo {

	private int id;
	private int slot;
	private Image image;
	private String name;

	public AvatarInfo(int id, int slot, Image image) {
		setID(id);
		setSlot(slot);
		setImage(image);
	}

	public AvatarInfo(int id, int slot) {
		setID(id);
		setSlot(slot);
		setImage(Res.AVATAR_RESOURCE.getAvatarImage(id, slot));
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public int getSlot() {
		return slot;
	}

}