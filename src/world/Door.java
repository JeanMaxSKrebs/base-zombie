package world;

import java.awt.Rectangle;

import entities.Entity;

public interface Door {
	public static boolean isCollidding(Entity e1, Tiledoor e2) {
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.x, e2.y, 32, 32);
		return e1Mask.intersects(e2Mask);
	}
}
