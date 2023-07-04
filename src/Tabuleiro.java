import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Tabuleiro extends JPanel {
    private static final int MAXLIN = 11;
    private static final int MAXCOL = 20;
    private ElementoBasico[][] celulas;
    private Set<Integer> pistasSelecionadas = new HashSet<>();
    private int posPis;
    private Personagem principal;
    private int linPer;
    private int colPer;
    private int[] posIniPers;

    public Tabuleiro() {
        super();
        posIniPers = new int[2];
        posIniPers[0] = 9;
        posIniPers[1] = 2;
        // Cria o conjunto de células vazia e as insere no layout
        celulas = new ElementoBasico[MAXLIN][MAXCOL];
        this.setLayout(new GridLayout(MAXLIN, MAXCOL));
        for (int i = 0; i < MAXLIN; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                celulas[i][j] = new Fundo("Fundo[" + i + "][" + j + "]", i, j, this);
                this.add(celulas[i][j]);
            }
        }
    }

    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = App.class.getResource("imagens/"+path);
        
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            System.exit(0);
            return null;
        }
    }

    public static int getMaxlin() {
        return MAXLIN;
    }

    public static int getMaxcol() {
        return MAXCOL;
    }

    public boolean posicaoValida(int lin, int col) {
        if ((lin < 0) || (col < 0) ||
                (lin >= MAXLIN) || (col >= MAXCOL)) {
            return false;
        } else {
            return true;
        }
    }

    // Retorna referencia em determinada posição
    public ElementoBasico getElementoNaPosicao(int lin, int col) {
        if (!posicaoValida(lin, col)) {
            return null;
        }
        return celulas[lin][col];
    }

    public ElementoBasico insereElemento(ElementoBasico elemento) {
        int lin = elemento.getLin();
        int col = elemento.getCol();
        if (!posicaoValida(lin, col)) {
            throw new IllegalArgumentException("Posicao invalida:" + lin + " ," + col);
        }
        ElementoBasico elementoAnterior = celulas[lin][col];
        celulas[lin][col] = elemento;
        return elementoAnterior;
    }

    public void atualizaVisualizacao() {
        // Atualiza o conteúdo do JPanel (ver algo melhor)
        this.removeAll(); // erase everything from your JPanel
        this.revalidate();
        this.repaint();// I always do these steps after I modify my JPanel
        for (int i = 0; i < MAXLIN; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                this.add(celulas[i][j]);
            }
        }
    }

    //joga o personagem de volta pro início
    public void reseta() {
        // Atualiza o conteúdo do JPanel (ver algo melhor)
        this.removeAll(); // erase everything from your JPanel
        this.revalidate();
        this.repaint();// I always do these steps after I modify my JPanel
       
        ElementoBasico princ = getPrincipal();

        for (int i = 0; i < MAXLIN; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                if(celulas[i][i] == princ){
                    if (i != posIniPers[0] && j != posIniPers[1]) {
                            celulas[i][j] = new Fundo("Fundo", i, j, this);

                    }  
                }    
                this.add(celulas[i][j]);
            }
        }

        // Posição inicial do personagem
        linPer = posIniPers[0];
        colPer = posIniPers[1];
        principal.setLin(linPer);
        principal.setCol(colPer);
    
    }
    
    public void loadLevel() {
        Random random = new Random();
        int nivel = random.nextInt(5) + 1;
        String osName = System.getProperty("os.name").toLowerCase();
        Path path1;
        if(osName.contains("linux")){
            path1 = Paths.get(String.format("niveis/nivel%d.txt", nivel));
        } else{
            path1 = Paths.get(String.format("src/niveis/nivel%d.txt", nivel));
        }
        try (BufferedReader reader = Files.newBufferedReader(path1)) {
            String line = null;
            int lin = 0;
            while ((line = reader.readLine()) != null) {
   
                for (int col=0; col<MAXCOL; col++) {
                    if (line.charAt(col) != ' ') {
                        ElementoBasico elem = this.getElem(line.charAt(col), lin, col);
                        this.insereElemento(elem);
                    }
                }
                lin++;
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    //guarda a posição inicial do personagem    
    private void posInicial(int lin, int col){
        posIniPers[0] = lin;
        posIniPers[1] = col;
    }
        
    public int[] getPosIniPers() {
        return posIniPers;
    }



    //retorna um número aleatório para as pistas
    public int aleatorio(){
        Random random = new Random();
        int posicao;
        do {
            posicao = random.nextInt(4); 

        } while (pistasSelecionadas.contains(posicao));

        return posicao; 
    }
        
    public ElementoBasico getElem(char elem, int lin, int col) {
        switch (elem) {
        case ' ': return new Fundo("Fundo",lin,col,this);
        case '-': return new Tronco("Tronco",lin,col,this);
        case '?': 
                  do {
                        this.posPis = aleatorio();
                  } while (pistasSelecionadas.contains(this.posPis));
             
                    pistasSelecionadas.add(this.posPis);
                  return new Pista("Pista", this.posPis, lin,col, this);
                  
        case '^': return new Saida("Saida", lin, col, this);
        case '+': return new Portal("Portal","arbusto.jpg",lin,col,this);     
        case '*': {
                        ElementoBasico anterior = new Fundo("Fundo",lin,col,this);
                        posInicial(lin, col);
                        principal = new Personagem("Gato", "gatoDireita.jpg", lin, col, this);
                        principal.setAnterior(anterior);
                        return principal;
                    }
        default: throw new IllegalArgumentException("Personagem invalido: " + elem);
        }
    }


    public Personagem getPrincipal() {
        return principal;
    }
}
