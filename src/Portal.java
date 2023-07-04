import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Portal extends ElementoBasico {
    private boolean fechada;
    private Tabuleiro tabuleiro = getTabuleiro();
    private ImageIcon imagem = new ImageIcon(Tabuleiro.createImageIcon("gato.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

    public Portal(String id, String iconPath, int linInicial, int colInicial, Tabuleiro tabuleiro) {
        super(id, iconPath, linInicial, colInicial, tabuleiro);
        this.fechada = true;
    }


    @Override
    public void acao(ElementoBasico outro) {
        if (fechada){
            fechada = false;
           setImage(Tabuleiro.createImageIcon("arbusto.jpg"));
        } else{
            fechada = true;
            JOptionPane.showMessageDialog(null, "Você caiu num portal!", "Pista Falsa!", JOptionPane.INFORMATION_MESSAGE, this.imagem);
            tabuleiro.reseta();
            }
    } 

}