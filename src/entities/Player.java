package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import base.Game;
import entities.frutas.Fruta;
import world.Camera;
import world.Normaldoor;
import world.Tiledoor;
import world.World;

public class Player extends Entity {

	public boolean left, right, up, down;
	public int right_dir = 1, left_dir = 2, up_dir = 3, down_dir = 4;
	public int dir = 1;
	public double speed = 5;
	private static int keys = 0;
	public int premium = 0;

	private static int dodgeChance = 20;
	private static int armor = 0;

	private boolean hasBagpack = false;

    private static List<Fruta> frutasColetadas;

	private int qtdSprites = 4;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = (qtdSprites - 1);
	private boolean moved;

	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] upPlayer;

	public double life = 100;
	public static double maxLife = 100;
	public double stamine = 0;
	public static double maxStamine = 100;

	public boolean usingPower = false;
	public boolean atirar = false;
	public double balas = 0;
	public double maxBalas = 600;
	
	public double nivel;
	public double qtdNivel;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[qtdSprites];
		leftPlayer = new BufferedImage[qtdSprites];
		upPlayer = new BufferedImage[qtdSprites];
		downPlayer = new BufferedImage[qtdSprites];

		for (int i = 0; i < qtdSprites; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite((i * 32), 0, 32, 32);
		}

		for (int i = 0; i < qtdSprites; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite((i * 32), 32, 32, 32);
		}
		for (int i = 0; i < qtdSprites; i++) {
			upPlayer[i] = Game.spritesheet.getSprite((i * 32), 64, 32, 32);
		}

		for (int i = 0; i < qtdSprites; i++) {
			downPlayer[i] = Game.spritesheet.getSprite((i * 32), 96, 32, 32);
		}

	}

	public int danoRecebido(int dano) {

		int danoRecebido = dano - armor;

		if (Game.random(Game.maximumDodge) <= dodgeChance) {
			return 0;
		}

		return danoRecebido;
	}

	public boolean beingAttacked(int dano) {
		int danoReal = danoRecebido(dano);

		if (danoReal > 0) {
			life = life - danoReal;
		}

//		System.out.println(danoReal);
//		System.out.println(life);
		if (iamDead())
			Game.gameState = "GAME_OVER";

		return true;
	}

	public boolean iamDead() {
		if (life <= 0) {
			life = 0;
			return true;
		}

		return false;
	}

	public void checkItems() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if (hasBagpack)
				speed = 4;

			if (e instanceof BagPack) {
				if (Entity.isColliding(this, e)) {
					hasBagpack = true;
//					armor = armor + 5;

					Game.entities.remove(i);

					return;
				}
			}

	         // Itens que são guardados
            if (hasBagpack && e instanceof Fruta) {
                if (Entity.isColliding(this, e)) {
                    Fruta frutaColetada = (Fruta) e;
                    frutasColetadas.add(frutaColetada);
                    Game.entities.remove(i);
                    return;
                }
            }
		}
	}

	public void checkDoor() {
		for (int i = 0; i < Game.tiledoors.size(); i++) {
			Tiledoor t = Game.tiledoors.get(i);

//			System.out.println(Game.tiledoors.size());
			if (Tiledoor.willCollide(this, t, (int) speed)) {
//				System.out.println("teste");
				if (t instanceof Normaldoor) {
					if (keys > 0) {
						keys--;
						Game.tiledoors.remove(i);
						World.troca();

					}
				}
				return;

			}

		}
	}

	public void tick() {
			if (atirar) {
				atirar = false;
				if (balas > 0) {
//					System.out.println("teste");
					balas--;
					atirar = false;
					int dx = 0;
					int dy = 0;
					if (dir == right_dir) {
						dx = 1;
					} else if (dir == left_dir) {
						dx = -1;
					} else if (dir == down_dir) {
						dy = 1;
					} else if (dir == up_dir) {
						dy = -1;
					}

					Bala bala = new Bala(this.getX(), this.getY(), 6, 6, null, dx, dy);
					bala.setMask(13, 13, 6, 6);
					Game.balas.add(bala);
				}
			}

		setMoved(false);

		int midy = (int) (y + masky + mheight / 2);
		int plusy = (int) (y + masky + mheight);
		int minusy = (int) (y + masky);

		int midx = (int) (x + maskx + mwidth / 2);
		int plusx = (int) (x + maskx + mwidth);
		int minusx = (int) (x + maskx);

		if (right) {
			setMoved(true);
			dir = right_dir;
			if (World.isFree(plusx, midy, "right"))
				x += speed;
			else if (World.isDoor())
				checkDoor();

		} else if (left) {
			setMoved(true);
			dir = left_dir;
			if (World.isFree(minusx, midy, "left"))
				x -= speed;
			else if (World.isDoor())
				checkDoor();
		}

		if (down) {
			setMoved(true);
			dir = down_dir;
			if (World.isFree(midx, plusy, "down"))
				y += speed;
			else if (World.isDoor())
				checkDoor();
		} else if (up) {
			setMoved(true);
			dir = up_dir;
			if (World.isFree(midx, minusy, "up"))
				y -= speed;
			else if (World.isDoor())
				checkDoor();
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex)
					index = 0;
			}
		}
		checkItems();

		Camera.x = Camera.clamp(this.getX() - (Game.getWIDTH() / 2), 0, World.WIDTH * 32 - Game.getWIDTH());
		Camera.y = Camera.clamp(this.getY() - (Game.getHEIGHT() / 2), 0, World.HEIGHT * 32 - Game.getHEIGHT());

	}

	public void render(Graphics g) {
		if (dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (hasBagpack) {
				g.drawImage(Entity.BAGPACK_RIGHT, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else if (dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (hasBagpack) {
				g.drawImage(Entity.BAGPACK_LEFT, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else if (dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (hasBagpack) {
				g.drawImage(Entity.BAGPACK_UP, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else if (dir == down_dir)
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - (int)speed - Camera.x, this.getY() + masky - (int)speed - Camera.y, mwidth, mheight);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth + (int)speed, mheight + (int)speed);
//
//		g.setColor(Color.black);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);

	}

	public double getLife() {
		return life;
	}

	public void setLife(int newLife) {
		this.life = newLife;
	}

	public double getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int newMaxLife) {
		Player.maxLife = newMaxLife;
	}

	public static int getDodgeChance() {
		return dodgeChance;
	}

	public void setDodgeChance(int newDodgeChance) {
		Player.dodgeChance = newDodgeChance;
	}

	public static int getArmor() {
		return armor;
	}

	public void setArmor(int newArmor) {
		Player.armor = newArmor;
	}

	public static int getKeys() {
		return keys;
	}

	public static void setKeys(int keys) {
		Player.keys = keys;
	}

	public double getStamine() {
		return stamine;
	}

	public void setStamine(double stamine) {
		this.stamine = stamine;
	}

	public static double getMaxStamine() {
		return maxStamine;
	}

	public static void setMaxStamine(double maxStamine) {
		Player.maxStamine = maxStamine;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}


	public static List<Fruta> getFrutasColetadas() {
		return frutasColetadas;
	}

	public static void setFrutasColetadas(List<Fruta> frutasColetadas) {
		Player.frutasColetadas = frutasColetadas;
	}

}
