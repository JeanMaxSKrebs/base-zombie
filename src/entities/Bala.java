package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class Bala extends Entity {
	
	private int dx, dy;
	private double speed = 3.5;
	private int dano = 1;
	
	private int life = 100, curLife = 0;

	public Bala(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		
		this.dx = dx;
		this.dy = dy;

	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		curLife++;
		if(curLife == life) {
			Game.balas.remove(this);
			return;
		}
		
		colidi();
	}
	public void colidi() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			
			if(isColliding(this, e)) {
				e.life -= this.dano;
				return;
			}
		}
	}


	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x + 13, this.getY() - Camera.y + 13, width, height);
	}
}