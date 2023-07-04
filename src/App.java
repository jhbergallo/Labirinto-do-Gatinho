import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class App extends JFrame implements ActionListener{
    private Tabuleiro tabuleiro;
    private Personagem personagem;
    private ImageIcon imagem = new ImageIcon(Tabuleiro.createImageIcon("gato.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
    private final String niveis = "Receptino: " + 
    "\n1. Manacos etos labani" +
    "\n2. Bloqueado" + 
    "\n3. Bloqueado" + 
    "\n4. Bloqueado";

    private final String regras = "Bem-vindo ao labirinto do gatinho!" + "\n" +" \n" +
            "O jogo consiste em traduzir um texto para descobrir a senha e" +
            "\nassim conseguir sair da floresta. Para traduzir o texto, deve-se" +
            "\nprocurar pistas nos arbustos que estão no labirinto. Para sair," +
            "\nvocê deve ir até a porta e digitar a senha";
    
    private final String historia = "\n\nUm gatinho quer se tornar um gato selvagem, feroz e livre. "+
    "\nPorém, ele não faz ideia de como se tornar um. Depois de" + 
    "\nconversar com outros animais, ele encontrou uma tartaruga" +
    "\nmuito velha que lhe disse que tinha uma fórmula que faria" +
    "\nqualquer animal, de qualquer idade e raça, se tornar livre e" + 
    "\nautônomo. A tartaruga então lhe entregou um pergaminho" +
    "\ncom a receita, mas estava todo em uma linguagem estranha." +
    "\nAgora o gatinho vai precisar da sua ajuda para poder continuar" +
    "\na jornada.";
    

    public App(){
        super();

        //fiz isso aqui pra trocar o ícone do aplicativo
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        final URL imageResource = App.class.getClassLoader().getResource("imagens/gato.png");
        final Image image = defaultToolkit.getImage(imageResource);


        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("mac") || osName.contains("darwin")) {
            //this is new since JDK 9
            final Taskbar taskbar = Taskbar.getTaskbar();
            try {
                //set icon for mac os (and other systems which do support this method)
                taskbar.setIconImage(image);
                
            } catch (final UnsupportedOperationException e) {
                System.out.println("The os does not support: 'taskbar.setIconImage'");
            } catch (final SecurityException e) {
                System.out.println("There was a security exception for: 'taskbar.setIconImage'");
            }
        }

        //set icon for windows os (and other systems which do support this method)
        this.setIconImage(image);


        // Define os componentes da tela
        tabuleiro = new Tabuleiro();


        JButton butRegras = new JButton("Regras");
        butRegras.setPreferredSize(new Dimension(80, 20));
        butRegras.addActionListener(this);

        JButton butHistoria = new JButton("História");
        butHistoria.setPreferredSize(new Dimension(80, 20));
        butHistoria.addActionListener(this);

        JButton butPerg = new JButton("Pergaminho");
        butPerg.setPreferredSize(new Dimension(110, 20));
        butPerg.addActionListener(this);
        
        JPanel botoesDirecao = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        
        JButton butDir = new JButton("→");
        butDir.addActionListener(this);
        JButton butEsq = new JButton("←");
        butEsq.addActionListener(this);
        JButton butCima = new JButton("↑");
        butCima.addActionListener(this);
        JButton butBaixo = new JButton("↓");
        butBaixo.addActionListener(this);

        
        constraints.gridx = 2;
        constraints.gridy = 1;
        botoesDirecao.add(butDir, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        botoesDirecao.add(butEsq, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        botoesDirecao.add(butCima, constraints);


        constraints.gridx = 1;
        constraints.gridy = 1;
        botoesDirecao.add(butBaixo, constraints);


        JPanel painelButTexto = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                painelButTexto.add(butRegras);
                painelButTexto.add(butHistoria);
                painelButTexto.add(butPerg);
        
        
        JPanel painelGeral = new JPanel();                                
        painelGeral.setLayout(new BorderLayout());
        painelGeral.add(painelButTexto, BorderLayout.NORTH);
        painelGeral.add(botoesDirecao, BorderLayout.CENTER);
        painelGeral.add(tabuleiro, BorderLayout.SOUTH);

        
        // Insere os personagens no tabuleiro
        
        tabuleiro.loadLevel();
        personagem = tabuleiro.getPrincipal();
        personagem.setAnterior(personagem.getAnterior());


        // Exibe a janela
        this.add(painelGeral);



        this.setSize(1100,1100);
        this.setTitle("Labirinto do Gatinho");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        tabuleiro.atualizaVisualizacao();
        JOptionPane.showMessageDialog(null, this.regras, "Regras", JOptionPane.INFORMATION_MESSAGE, this.imagem);
        JOptionPane.showMessageDialog(null, this.historia, "História", JOptionPane.INFORMATION_MESSAGE, this.imagem);
        JOptionPane.showMessageDialog(null, this.niveis, "Conteúdo do Pergaminho", JOptionPane.INFORMATION_MESSAGE, this.imagem);

    }
    
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        JButton but = (JButton)arg0.getSource();
        
        if (but.getText().trim().equals("História")){
             JOptionPane.showMessageDialog(null, this.historia, "História", JOptionPane.INFORMATION_MESSAGE, this.imagem);
        }
        if (but.getText().trim().equals("Regras")){
             JOptionPane.showMessageDialog(null, this.regras, "Regras", JOptionPane.INFORMATION_MESSAGE, this.imagem);
        }
        if (but.getText().equals("Pergaminho")){
             JOptionPane.showMessageDialog(null, this.niveis, "Conteúdo do Pergaminho", JOptionPane.INFORMATION_MESSAGE, this.imagem);
        }
        if (but.getText().equals("→")){
            personagem.moveDireita();
        }
        if (but.getText().equals("←")){
            personagem.moveEsquerda();
        }
        if (but.getText().equals("↑")){
            personagem.moveCima();
        }
        if (but.getText().equals("↓")){
            personagem.moveBaixo();
        }
        tabuleiro.atualizaVisualizacao();
    }


    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}