package entities.frutas;

import java.awt.image.BufferedImage;

import base.Game;
import entities.Entity;

public class Fruta extends Entity implements Comparable<Fruta> {

    private String nome;
    private int quantidade;
    private double regen;
    private int tickRegen;
    private double curaTotal;
    private BufferedImage sprite;

    // Nomes das frutas
    private static final String[] NOMES_FRUTAS = {"MACA", "BANANA", "LARANJA", "PERA", "UVA"};

    // Array para armazenar as imagens das frutas
    public static BufferedImage[] FRUTAS_EN = new BufferedImage[NOMES_FRUTAS.length];

    static {
        // Carregar automaticamente as imagens das frutas
        for (int i = 0; i < NOMES_FRUTAS.length; i++) {
            FRUTAS_EN[i] = Game.spritesheet.getSprite((i + 1)  * 32, 128, 32, 32);
        }
    }
    public Fruta(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.nome = nome;
        this.quantidade = 0;
        this.regen = regen;
        this.tickRegen = tickRegen;
        this.curaTotal = curaTotal;
        this.setSprite(sprite);
        
        // Configurar a imagem da fruta com base no nome
        for (int i = 0; i < NOMES_FRUTAS.length; i++) {
            if (NOMES_FRUTAS[i].equals(nome)) {
                this.sprite = FRUTAS_EN[i];
                break;
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getRegen() {
        return regen;
    }

    public void setRegen(double regen) {
        this.regen = regen;
    }

    public int getTickRegen() {
        return tickRegen;
    }

    public void setTickRegen(int tickRegen) {
        this.tickRegen = tickRegen;
    }

    public double getCuraTotal() {
        return curaTotal;
    }

    public void setCuraTotal(double curaTotal) {
        this.curaTotal = curaTotal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Incrementa a quantidade quando uma fruta é coletada
    public void coletar() {
        quantidade++;
    }

    // Decrementa a quantidade quando uma fruta é comida
    public void comer() {
    	if (quantidade > 0) {
    		quantidade--;
    	}
    		
        if (quantidade < 0) {
            quantidade = 0; // Garante que a quantidade não seja negativa
        }
    }

    @Override
    public int compareTo(Fruta outraFruta) {
        return this.nome.compareTo(outraFruta.getNome());
    }

    @Override
    public String toString() {
        return "Fruta{" +
                "nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", regen=" + regen +
                ", tickRegen=" + tickRegen +
                ", curaTotal=" + curaTotal +
                '}';
    }

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
}
