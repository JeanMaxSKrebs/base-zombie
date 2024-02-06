package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import base.Game;
import entities.frutas.Fruta;
import world.Camera;

public abstract class Entity {


	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(0, 224, 32, 32);
	
	public static BufferedImage FRUTA_EN = Game.spritesheet.getSprite(0, 256, 32, 32);


	public static BufferedImage HPBAG_EN = Game.spritesheet.getSprite(128, 32, 32, 32);
	public static BufferedImage STAMINEBAG_EN = Game.spritesheet.getSprite(160, 32, 32, 32);
	public static BufferedImage BAGPACK_EN = Game.spritesheet.getSprite(192, 32, 32, 32);
	
	public static BufferedImage BAGPACK_RIGHT = Game.spritesheet.getSprite(160, 64, 32, 32);
	public static BufferedImage BAGPACK_LEFT = Game.spritesheet.getSprite(192, 64, 32, 32);
	public static BufferedImage BAGPACK_UP = Game.spritesheet.getSprite(128, 64, 32, 32);
		
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	protected BufferedImage sprite;
	protected int maskx, masky, mwidth, mheight;
	
	public Entity() {
		// Lógica de inicialização, se necessário //fruta
	}
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite; 
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}

	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY()+e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY()+e2.masky, e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		g.setColor(Color.black);
		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
	
	public int getX() {
		return (int) x;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getWidth() {
		return width;
	}
 
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public void tick() {
		
	}

	
	
	
}
