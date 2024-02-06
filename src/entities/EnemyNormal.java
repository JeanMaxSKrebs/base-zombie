package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.Camera;

public class EnemyNormal extends Enemy {

	private static int dano = 10;
	private static int criticalChance = 10;
	private static int criticalDamage = 10;
	
	protected static int reloadingTime = 180, reload = 0;
	protected static boolean preparedAttack = true;
	
	private BufferedImage[] rightNormalEnemy;
	private BufferedImage[] leftNormalEnemy;
	private BufferedImage[] upNormalEnemy;
	private BufferedImage[] downNormalEnemy;

	
	public EnemyNormal(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightNormalEnemy = new BufferedImage[4];
		leftNormalEnemy = new BufferedImage[4];
		upNormalEnemy = new BufferedImage[1];
		downNormalEnemy = new BufferedImage[1];
		
		for(int i=0; i<qtdDirecoes; i++) {
			rightNormalEnemy[i] = Game.spritesheet.getSprite((i*32), 160, 32, 32);			
		}
		
		for(int i=0; i<qtdDirecoes; i++) {
			leftNormalEnemy[i] = Game.spritesheet.getSprite((i*32), 192, 32, 32);			
		}
		upNormalEnemy[0] = Game.spritesheet.getSprite(32, 128, 32, 32);
		downNormalEnemy[0] = Game.spritesheet.getSprite(0, 128, 32, 32);
	}
	public void tick() {
		moved = false;
		iamAlive();

		preparadoAtacar();
		
		movimentar();
		verificaMovimento();
		
	}
	
	public void preparadoAtacar() {
		if(isPreparedAttack() == false) {
			reloading();
		} else if(isColliddingWithPlayer() == true) {
			reloading();

			Game.player.beingAttacked(this.danoCompleto(dano, criticalChance, criticalDamage));
		}
	}
	
	public void reloading() {

//		System.out.println(reload);
//		System.out.println(reloadingTime);
		if(reload >= reloadingTime) {
			setPreparedAttack(true);
			reload = 0;
		} else {
			setPreparedAttack(false);
			reload++;
		}
	}
	
	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightNormalEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if(dir == left_dir) {
			g.drawImage(leftNormalEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if(dir == up_dir) {
			g.drawImage(upNormalEnemy[0], this.getX() - Camera.x, this.getY() - Camera.y, null);	
		} else if(dir == down_dir) {				
			g.drawImage(downNormalEnemy[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
	}
			
	public static boolean isPreparedAttack() {
		return preparedAttack;
	}
	public static void setPreparedAttack(boolean preparedAttack) {
		EnemyNormal.preparedAttack = preparedAttack;
	}
	
	
}
