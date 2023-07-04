import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Pista extends ElementoBasico{
    private ImageIcon imagem = new ImageIcon(Tabuleiro.createImageIcon("gato.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    private boolean fechada;
    private int numPista;
    private String estaPista;
    private String[] pista = {"Receptino significa receita.", "Labani é banana em uma linguagem muito antiga", "Manacos é macacos", "Etos é o verbo comer"};

    public Pista(String id, int numPista, int linInicial, int colInicial, Tabuleiro tabuleiro) {
        super(id, "arbusto.jpg", linInicial, colInicial, tabuleiro);
        this.fechada = true;
        this.numPista = numPista;
        estaPista = getPista();
    }

    public int getNumPista(){
        return numPista;
    }

    public String getPista(){
        return pista[this.numPista];
    }

    @Override
    public void acao(ElementoBasico outro) {
            if (fechada){
            fechada = false;
            setImage(Tabuleiro.createImageIcon("arbusto.jpg"));
            }else{
                fechada = true;
                JOptionPane.showMessageDialog(null, estaPista, "Pista", JOptionPane.INFORMATION_MESSAGE, this.imagem);
            }
        
        
    }    
}
