package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import base.Game;
import entities.Player;

public class UI {
	
	int frame;
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(16, 8, 66, 32);
		g.setColor(Color.green);
		g.fillRect(24, 16, (int)((Game.player.life / Player.maxLife) * 50), 16);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 11));
		g.drawString((int)(Game.player.life)+" / "+(int)(Player.maxLife),  26, 28);
		


		if(Game.player.stamine == Player.getMaxStamine()) {
			if(frame >= 10) {	
					g.setColor(Color.yellow);
					g.fillRect(84, 8, 16, 32);
					g.setColor(Color.black);
					g.fillRect(88, 12, 8, (int)((Game.player.stamine / Player.maxStamine) * 24));
					frame = 0;
			}
			frame++;
		}
		
		g.setColor(Color.gray);
		g.fillRect(0, Game.getHEIGHT()-32, 64, 32);
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 8));
		
		g.drawString("Frutas:  "+Player.getFrutasColetadas() , 0,  Game.getHEIGHT()-35);
		g.drawString("Maçãs:  "+Player.getFrutasColetadas() , 0,  Game.getHEIGHT()-25);
		g.drawString("Bananas: "+Player.getFrutasColetadas() , 0,  Game.getHEIGHT()-15);
		g.drawString("PREMIUM: "+Game.player.premium , 0,  Game.getHEIGHT()-5);
		
		
		g.setColor(Color.gray);
		g.fillRect(Game.getWIDTH()-64, Game.getHEIGHT()-32, 64, 32);
		g.setColor(Color.white);
		g.setFont(new Font("roboto", Font.BOLD, 8));
		g.drawString("ARMADURA: "+Player.getArmor(), Game.getWIDTH()-64,  Game.getHEIGHT()-25);
		g.drawString("ESQUIVA: "+Player.getDodgeChance() , Game.getWIDTH()-64, Game.getHEIGHT()-15);

		
	}
}
