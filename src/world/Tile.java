package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;


public class Tile {
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(128, 0, 32, 32);
	public static BufferedImage TILE_WALL  = Game.spritesheet.getSprite(160, 0, 32, 32);
	public static BufferedImage TILE_DOOR = Game.spritesheet.getSprite(32, 224, 32, 32);
	
	protected BufferedImage sprite;
	protected int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		
//		g.setColor(Color.green);
//		g.fillRect(this.x - Camera.x, this.y - Camera.y, Game.WIDTH, Game.HEIGHT);
	}
	
	public void tick() {
	}

}
