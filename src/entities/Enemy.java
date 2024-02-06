package entities;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;
import world.World;

public abstract class Enemy extends Entity{
	protected double speed = 1.7;
	protected static int dano;
	protected int criticalChance;
	protected int criticalDamage;
	protected static int reloadingTime, reload;
	
	protected int frames = 0, maxFrames = 60, index = 0;
	
	protected int qtdDirecoes = 4;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = Game.random(qtdDirecoes);
	
	protected int life = 50;
	
	protected boolean moved;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);

	}
	
	public void tick() {
	}
	
	public void iamAlive() {
		if(this.life<=0) {
			destroySelf();
			return;
		}
	}
		
	public void movimentar() {
		int midy = (int)(y + masky + mheight/2);
		int plusy = (int)(y + masky + mheight);
		int minusy = (int)(y + masky);
		
		int midx = (int)(x + maskx + mwidth/2);
		int plusx = (int)(x + maskx + mwidth);
		int minusx = (int)(x + maskx);
		switch (dir) {
		   case 0:
			   if(World.isFree(plusx, midy, "right")
					   &&!isCollidding((int)(x + speed), this.getY())) {
				   moved = true;
					x+=speed;
					index = 0;
			   } else {
				   dir = Game.random(qtdDirecoes);
			   }

			   break;

		   case 1:
				if(World.isFree(minusx, midy, "left")
						&&!isCollidding((int)(x - speed), this.getY())) {
					moved = true;
					x-=speed;
					index = 1;
				} else {
					   dir = Game.random(qtdDirecoes);
				}
				
				break;

		   case 2:
			   if(World.isFree(midx, plusy, "down")
					   &&!isCollidding(this.getX(), (int)(y + speed))) {				   
				   moved = true;
				   y+=speed;
					index = 2;
			   } else {
				   dir = Game.random(qtdDirecoes);
			   }

			   break;
		           
		   case 3:
				if(World.isFree(midx, minusy, "up")
						&&!isCollidding(this.getX(),  (int)(y - speed))) {
					moved = true;
					y-=speed;
					index = 3;
				} else {
					   dir = Game.random(qtdDirecoes);
				}			
				 
				break;
		  default:
		}
	}
	
	public void verificaMovimento() {
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				dir = Game.random(qtdDirecoes);
			}
		}
	}
	
	public void destroySelf() {
//		Game.entities.remove(this);
		Game.enemies.remove(this);
	}

	public int danoCompleto(int dano, int criticalChance, int criticalDamage) {
		if(Game.random(Game.maximumCritic) <= criticalChance) {
			int danoCompleto = dano * 2 * (100 + criticalDamage) / 100;

			return danoCompleto;
		}
		
		return dano;
	}
	
	public boolean isColliddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY()  + masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX() + maskx, Game.player.getY() + masky,  mwidth, mheight);
		
		return enemyCurrent.intersects(player);
	}

	public boolean isCollidding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext + this.maskx, yNext + this.masky, mwidth, mheight);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + e.maskx, e.getY() + e.masky, mwidth, mheight);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public abstract void reloading();
	public abstract void preparadoAtacar();
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
		
//		g.setColor(Color.yellow);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}

	public static int getDano() {
		return dano;
	}

	public void setDano(int newDano) {
		Enemy.dano = newDano;
	}

	public static int getReloadingTime() {
		return reloadingTime;
	}

	public static void setReloadingTime(int reloadingTime) {
		Enemy.reloadingTime = reloadingTime;
	}

	public static int getReload() {
		return reload;
	}

	public static void setReload(int reload) {
		Enemy.reload = reload;
	}
	
}

