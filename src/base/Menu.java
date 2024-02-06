package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import entities.Player;
import world.World;

public class Menu {
	
	public String[] options = {"novo jogo", "carregar", "sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;

	public static boolean pause = false;
	
	public static boolean saveExists = false;

	public void tick() {
		File file = new File("save.txt");
		if(file.exists())
			saveExists = true;
		else
			saveExists = false; 
		
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "novo jogo" || options[currentOption] == "continuar" ) {
				Game.gameState = "NORMAL";
				pause = false;
				file = new File("save.txt");
				file.delete();
			} else if(options[currentOption] == "carregar") {
				file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(0);
					applySave(saver);
				}
			} else if(options[currentOption] == "sair") {
				System.exit(1);
			}
		}
	}
	public static void applySave(String str) {
		System.out.println(str);
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			System.out.println("spl2[0]");
			System.out.println(spl2[0]);
			System.out.println("spl2[1]");
			System.out.println(spl2[1]);
			switch (spl2[0]) {


				case "fase": {
					int spl2inteiro = (int) Double.parseDouble(spl2[1]);
					System.out.println("spl2inteiro");
					System.out.println(spl2inteiro);

					World.restartGame("fase" + spl2inteiro + ".png");
					Game.player.setArmor(0);
					Game.player.setDodgeChance(20);
					Player.setKeys(0);
					Game.gameState = "NORMAL";
					pause = false;
					break;
				}
				case "vida": {
					Game.player.life = Integer.parseInt(spl2[1]);

					break;
				}
				case "estamina": {
					Game.player.stamine = Integer.parseInt(spl2[1]);

					break;
				}
				case "premium": {
					Game.player.premium = Integer.parseInt(spl2[1]);


					break;
				}
				case "gameState": {


					break;
				}
				default:
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						 String[] trans = singleLine.split(":");
						 char[] val = trans[1].toCharArray();
						 trans[1] = "";
						 for (int i = 0; i < val.length; i++) {
							val[i] -= encode;
							trans[1] += val[i];
						}
						 line += trans[0];
						 line += ":";
						 line += trans[1];
						 line += "/";

					}
				} catch (IOException e) {}
			} catch (FileNotFoundException e) {}
		}
		System.out.println("line");
		System.out.println(line);

		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		System.out.println("salvou");
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();

			for (int j = 0; j < value.length; j++) {
				value[j] += encode;
				current += value[j];
			}
			try {
				write.write(current);
				if(i < val1.length - 1)
					write.newLine();
				
			} catch (IOException e) {}
		}
		try {
			write.flush();
			write.close();
		} catch (IOException e) {}
	}
	
	public void render(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 64));
		g.fillRect(0, 0,Game.getWIDTH()*Game.getSCALE(), Game.getHEIGHT()*Game.getSCALE());
		g.setColor(Color.WHITE);
		g.drawString("Sobrevivência Jogo",  ((Game.getWIDTH()*Game.getSCALE()/3)), (Game.getHEIGHT()*Game.getSCALE()/5));

		
		// menu 
		g.setFont(new Font("Arial", Font.BOLD, 48));
		
		if(pause == false)
			g.drawString("Novo Jogo",  ((Game.getWIDTH()*Game.getSCALE()/3)), ((Game.getHEIGHT()*Game.getSCALE()/3)+100));
		else 
			g.drawString("Continuar",  ((Game.getWIDTH()*Game.getSCALE()/3)), ((Game.getHEIGHT()*Game.getSCALE()/3)+100));

		g.drawString("Carregar Jogo",  ((Game.getWIDTH()*Game.getSCALE()/3)), ((Game.getHEIGHT()*Game.getSCALE()/3)+200));
		g.drawString("Sair",  ((Game.getWIDTH()*Game.getSCALE()-250)), ((Game.getHEIGHT()*Game.getSCALE())-50));
		
		if(options[currentOption] == "novo jogo") {
			g.drawString(" > ",  (((Game.getWIDTH()*Game.getSCALE()/3)-100)), ((Game.getHEIGHT()*Game.getSCALE()/3)+100));
		} else if(options[currentOption] == "carregar") {
			g.drawString(" > ",  (((Game.getWIDTH()*Game.getSCALE()/3)-100)), ((Game.getHEIGHT()*Game.getSCALE()/3)+200));
		} else if(options[currentOption] == "sair") {
			g.drawString(" > ",  (((Game.getWIDTH()*Game.getSCALE()-350))), ((Game.getHEIGHT()*Game.getSCALE())-50));
		}

	}
}
