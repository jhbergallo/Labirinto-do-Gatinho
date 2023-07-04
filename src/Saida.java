import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Saida extends ElementoBasico {
    private ImageIcon imagem = new ImageIcon(Tabuleiro.createImageIcon("gato.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
    public Saida(String id, int linInicial, int colInicial, Tabuleiro tabuleiro) {
        super(id, "porta.jpg", linInicial, colInicial, tabuleiro);
    }
    
    @Override
    public void acao(ElementoBasico outro) {

        String codigo = JOptionPane.showInputDialog(getRootPane(), "Digite a solução da receita:").toLowerCase();
        if (codigo.equals("macacos comem banana")) {
            getTabuleiro().atualizaVisualizacao();
            JOptionPane.showMessageDialog(getRootPane(), "Parabéns, você ganhou!", "Saída", JOptionPane.INFORMATION_MESSAGE, this.imagem);
            
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "Código inválido! Continue tentando", "Saída", JOptionPane.INFORMATION_MESSAGE, this.imagem);
        }

    }
}