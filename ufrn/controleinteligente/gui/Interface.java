
package br.ufrn.controleinteligente.gui;

import br.ufrn.controleinteligente.estruturas.Labirinto;
import br.ufrn.controleinteligente.estruturas.No;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Interface gráfica do Interface usando o algoritmo A*
 *
 * @author Andre
 */
public class Interface extends javax.swing.JFrame implements ActionListener {


    /**Temporarios*/
    private Integer[] marcouPartida;
    private Integer[] marcouObjetivo;
    private List<No> obstaculos;
    private java.util.Vector dados = new java.util.Vector();
    private boolean percorreu;

    /** Janela de um Novo Jogo */
    private NovoJogo novoJogo;

    /** Janela de Sobre o jogo */
    private SobreJogo about;

    /** Janela de listas */
    private Listas listas;

    /** Posicoes do labirinto */
    private JButton[][] posicao;

    /** Dimensão do grid */
    private int linha;
    private int coluna;

    /** Jogabilidade */
    private double movHorizontal;
    private double movVertical;
    private double movDiagonal;

    /** Jogo */
    private Labirinto labirinto;

    /** Imagens */
    private Icon fundo = new ImageIcon(getClass().getResource("/imagens/fundoBranco.png"));
    private Icon partida = new ImageIcon(getClass().getResource("/imagens/partida.png"));
    private Icon partidaAlcancado = new ImageIcon(getClass().getResource("/imagens/partida_alcancado.png"));
    private Icon objetivo = new ImageIcon(getClass().getResource("/imagens/objetivo.png"));
    private Icon objetivoAlcancado = new ImageIcon(getClass().getResource("/imagens/objetivo_alcancado.png"));
    private Icon obstaculo = new ImageIcon(getClass().getResource("/imagens/obstaculo.png"));
    private Icon caminhoS = new ImageIcon(getClass().getResource("/imagens/caminho.png"));
    private Icon caminhoS2 = new ImageIcon(getClass().getResource("/imagens/abertos.png"));
    private Icon caminhoS3 = new ImageIcon(getClass().getResource("/imagens/fechados.png"));

    public double getMovDiagonal() {
        return movDiagonal;
    }

    public void setMovDiagonal(double movDiagonal) {
        this.movDiagonal = movDiagonal;
    }

    public double getMovHorizontal() {
        return movHorizontal;
    }

    public void setMovHorizontal(double movHorizontal) {
        this.movHorizontal = movHorizontal;
    }

    public double getMovVertical() {
        return movVertical;
    }

    public void setMovVertical(double movVertical) {
        this.movVertical = movVertical;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void mostrarAbout() {
        about = new SobreJogo(new javax.swing.JFrame(),true);
        about.setVisible(true);
        about.setLocationRelativeTo(null);
    }

    public void iniciarNovoJogo() {
        novoJogo = new NovoJogo(new javax.swing.JFrame(),true, this);
        novoJogo.setVisible(true);
        novoJogo.setLocationRelativeTo(null);
    }

    public void abrirListas() {
        listas = new Listas(new javax.swing.JFrame(),true, labirinto);
        listas.setVisible(true);
        listas.setLocationRelativeTo(null);
    }

    public void carregarArquivo() {
        //arquivo = new CarregarArquivo(new javax.swing.JFrame(),true, this);
        //arquivo.setVisible(true);
        //arquivo.setLocationRelativeTo(null);

        String linha_str;

        try {
            JFileChooser escolhaArquivo = new JFileChooser();
            escolhaArquivo.setCurrentDirectory(new File("E:\\Windows\\Documentos\\Programação\\Java\\ControleInteligente\\Labirinto\\arquivos"));
            escolhaArquivo.setDialogTitle("Selecione o arquivo");
            if(escolhaArquivo.showOpenDialog(escolhaArquivo) == JFileChooser.APPROVE_OPTION){
                File file = escolhaArquivo.getSelectedFile();

                String textoEntrada = "";

                FileInputStream isTwo = new FileInputStream(""+file.getPath());

                BufferedReader dsTwo = new BufferedReader(new InputStreamReader(isTwo));

                dados.removeAllElements();

                //Cada linha do arquivo será atribuído a linha_str
                while((linha_str = dsTwo.readLine()) != null) {
                    dados.add(linha_str + " ");
                    textoEntrada += linha_str+"\n";
                }

                dsTwo.close();
                preencherParametros();
                //calcula();
                //this.dispose();
            }
        }catch(Exception e){

        }
    }

    /**
     * Método usado para preencher os parâmetros do labirinto a partir dos dados lidos
     * no arquivo txt fornecido pelo usuário.
     */
    private void preencherParametros(){
        String auxString, capturaLetras = "";
        int tamanhoString = 0;

        //Parametros do labirinto


        linha = dados.size()-1;

        auxString = dados.get(0).toString();
        tamanhoString = auxString.length();
        int posicao = 0;
        String letraAnt = "";
        for(int i=2;i<tamanhoString;i++) {
            capturaLetras = auxString.substring(i, i+1);
            if(!capturaLetras.equals(" ")) {
                if(!letraAnt.equals(" ") && !letraAnt.equals("")) {
                    capturaLetras = letraAnt+capturaLetras;
                    posicao--;
                }
                if(posicao==2) {
                    movVertical = Integer.parseInt(capturaLetras);
                    posicao++;
                }
                if(posicao==1) {
                    movHorizontal = Integer.parseInt(capturaLetras);
                    posicao++;
                }
                if(posicao==0) {
                    coluna = Integer.parseInt(capturaLetras);
                    posicao++;
                }
            }
            letraAnt = capturaLetras;
        }

        for(int j=1;j<dados.size();j++){
            auxString = dados.get(j).toString();
            tamanhoString = auxString.length();
            int espacos = 0;
            for(int i=0;i<tamanhoString;i++){
                capturaLetras = auxString.substring(i, i+1);
                if(!capturaLetras.equals(" ")) {
                    if(capturaLetras.equals("1")) {
                        No no = new No((j-1), (i-espacos));
                        obstaculos.add(no);
                    } else if(capturaLetras.equals("2")){
                        marcouPartida[0] = j-1;
                        marcouPartida[1] = i-espacos;
                    } else if(capturaLetras.equals("3")) {
                        marcouObjetivo[0] = j-1;
                        marcouObjetivo[1] = i-espacos;
                    }
                } else
                    espacos++;
            }
        }
                //Interface
                //movDiagonal = Math.sqrt(Math.pow(movHorizontal, 2.)+Math.pow(movVertical, 2.));
                movDiagonal = 1.0;
                criarGrid();
    }


    private void reiniciar(){
        Iterator it = labirinto.getSolucao().iterator();
        while (it.hasNext()) {
            No noAux = (No) it.next();
            if(noAux.getX() == marcouPartida[0] && noAux.getY() == marcouPartida[1])
                posicao[noAux.getX()][noAux.getY()].setIcon(partida);
            else if (noAux.getX() == marcouObjetivo[0] && noAux.getY() == marcouObjetivo[1])
                posicao[noAux.getX()][noAux.getY()].setIcon(objetivo);
            else
                posicao[noAux.getX()][noAux.getY()].setIcon(fundo);
        }

        labirinto = new Labirinto(this);

    }

    /**
     * Método para gerar o grid aleatório
     *
     */
    public void criarGridAleatorio(){
        //setSize(543,367);
        this.setSize(1254, 690);
        //fundoLabirinto.setSize(533,357);

        double sorteio;
        posicao = new JButton[linha][coluna];
        fundoLabirinto.setLayout(new GridLayout(linha,coluna));
        for(int i=0;i<linha;i++)
            for(int j=0;j<coluna;j++){
                sorteio = Math.random();
                posicao[i][j] = new JButton();
                posicao[i][j].addActionListener(this);
                if (sorteio > 0.2)
                    posicao[i][j].setIcon(fundo);
                else {
                    posicao[i][j].setIcon(obstaculo);
                    No no = new No(labirinto, i, j);
                    no.setStatus(1);
                    obstaculos.add(no);
                }
                //Escolher posição inicial aleatória
                //int posicao1 = (int) Math.floor(Math.random()*linha);
                //int posicao2 = (int) Math.floor(Math.random()*coluna);
                //posicao[posicao1][posicao2] = new JButton();
                //posicao[posicao1][posicao2].addActionListener(this);
                //posicao[posicao1][posicao2].setIcon(partida);


                posicao[i][j].setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
                posicao[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                //botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
                //JLabel rotulo = new JLabel("S");
                //botao.add(rotulo);
                //botao.setBackground(Color.red);
                fundoLabirinto.add(posicao[i][j]);
            }
    }

    public void limparJogo(){
        initComponents();
    }

    /**
     * Método para limpar o labirinto para uma nova configuração de jogo
     */
    private void limparGrid(){
        posicao[marcouObjetivo[0]][marcouObjetivo[1]].setIcon(fundo);
        posicao[marcouPartida[0]][marcouPartida[1]].setIcon(fundo);
        Iterator it = obstaculos.iterator();
        while (it.hasNext()) {
            No noAux = (No) it.next();
            posicao[noAux.getX()][noAux.getY()].setIcon(fundo);
        }
        it = labirinto.getSolucao().iterator();
        while (it.hasNext()) {
            No noAux = (No) it.next();
            posicao[noAux.getX()][noAux.getY()].setIcon(fundo);
        }

        marcouObjetivo = new Integer[]{-1,-1};
        marcouPartida = new Integer[]{-1,-1};
        obstaculos = new ArrayList<No>();
        labirinto = new Labirinto(this);

        
                

    }

    /**
     * Método para gerar o grid
     */
    public void criarGrid(){
        //setSize(543,367);
        this.setSize(1254, 690);
        //fundoLabirinto.setSize(533,357);
        posicao = new JButton[linha][coluna];
        //fundoLabirinto = new JPanel(new GridLayout(linha,coluna));
        
        if(fundoLabirinto.getComponents().length > 0)
            fundoLabirinto.removeAll();

        fundoLabirinto.setLayout(new GridLayout(linha,coluna));

            No noAux;

        for(int i=0;i<linha;i++)
            for(int j=0;j<coluna;j++){
                noAux = new No(labirinto, i, j);
                posicao[i][j] = new JButton();
                posicao[i][j].addActionListener(this);
                if(i == marcouPartida[0] && j == marcouPartida[1])
                    posicao[i][j].setIcon(partida);
                else if(i == marcouObjetivo[0] && j == marcouObjetivo[1])
                    posicao[i][j].setIcon(objetivo);
                else if(obstaculos.contains(noAux))
                    posicao[i][j].setIcon(obstaculo);
                else
                    posicao[i][j].setIcon(fundo);

                    posicao[i][j].setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
                    posicao[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                fundoLabirinto.add(posicao[i][j]);

            }
    }

    

    /**
     * Ação que será realizada com o clique do mouse sobre a posição no labirinto
     * @param evento
     */
    public void actionPerformed (ActionEvent evento){

        for(int i=0;i<linha;i++)
            for(int j=0;j<coluna;j++){
                if( (evento.getSource() == posicao[i][j]) && rbPosicaoInicial.isSelected()){
                    //Se já tiver sido marcado, desmarque o anterior e marque este
                    if (marcouPartida[0] != -1 && marcouPartida[1] != -1)
                        posicao[marcouPartida[0]][marcouPartida[1]].setIcon(fundo);
                    //Marca o indice de onde foi marcado a posicao de partida
                    posicao[i][j].setIcon(partida);
                    marcouPartida[0] = i;
                    marcouPartida[1] = j;
                }
                if( (evento.getSource() == posicao[i][j]) && rbObstaculo.isSelected()){
                    No no = new No(labirinto, i, j);
                    if (posicao[i][j].getIcon() == obstaculo) {
                        if(obstaculos.contains(no))
                            obstaculos.remove(no);
                        posicao[i][j].setIcon(fundo);
                    }
                    else {
                        posicao[i][j].setIcon(obstaculo);
                        no.setStatus(1);
                        obstaculos.add(no);
                    }
                    
                    //labirinto.getObstaculos().add(no);
                }
                if( (evento.getSource() == posicao[i][j]) && rbPosicaoFinal.isSelected()){
                    if (marcouObjetivo[0] != -1 && marcouObjetivo[1] != -1)
                        posicao[marcouObjetivo[0]][marcouObjetivo[1]].setIcon(fundo);
                    posicao[i][j].setIcon(objetivo);
                    marcouObjetivo[0] = i;
                    marcouObjetivo[1] = j;
                }
        }
    }

    /** Cria um novo form para o Interface */
    public Interface() {
        
        initComponents();

        getContentPane().setLayout(new BorderLayout());
        //super.setSize(800, 600);
        
        //fundoLabirinto.setLayout(new GridLayout(6, 6));
        
        //fundoLabirinto.setVisible(false);

        //Por enquanto
        linha = 4;
        coluna = 4;
        marcouObjetivo = new Integer[]{-1,-1};
        marcouPartida = new Integer[]{-1,-1};
        obstaculos = new ArrayList<No>();
        labirinto = new Labirinto(this);
        movHorizontal = 1.0;
        movVertical = 1.0;
        movDiagonal = 1.0;
        
    //    criarGrid();

        //
        this.setLocationRelativeTo(null);
    }

    /**
     * Métodos não editáveis chamados dentro do método construtor do Interface
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        fundoLabirinto = new javax.swing.JPanel();
        fundoOpcoes = new javax.swing.JPanel();
        rbPosicaoInicial = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        rbObstaculo = new javax.swing.JRadioButton();
        rbPosicaoFinal = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btPercorrer = new javax.swing.JButton();
        btReiniciar = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();
        btAbrirListas = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtMovHorizontal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtMovVertical = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuIniciar = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        btCarregarArquivo = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuAjuda = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Labirinto");
        setMinimumSize(new java.awt.Dimension(860, 520));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fundoLabirinto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        fundoLabirinto.setPreferredSize(new java.awt.Dimension(1100, 376));

        javax.swing.GroupLayout fundoLabirintoLayout = new javax.swing.GroupLayout(fundoLabirinto);
        fundoLabirinto.setLayout(fundoLabirintoLayout);
        fundoLabirintoLayout.setHorizontalGroup(
            fundoLabirintoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        fundoLabirintoLayout.setVerticalGroup(
            fundoLabirintoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        getContentPane().add(fundoLabirinto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 490));

        fundoOpcoes.setBackground(new java.awt.Color(113, 142, 110));
        fundoOpcoes.setLayout(null);

        rbPosicaoInicial.setBackground(new java.awt.Color(113, 142, 110));
        buttonGroup1.add(rbPosicaoInicial);
        rbPosicaoInicial.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rbPosicaoInicial.setSelected(true);
        rbPosicaoInicial.setText("Posição Inicial");
        rbPosicaoInicial.setBorder(null);
        fundoOpcoes.add(rbPosicaoInicial);
        rbPosicaoInicial.setBounds(10, 34, 93, 17);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setText("Ação do mouse:");
        fundoOpcoes.add(jLabel1);
        jLabel1.setBounds(10, 11, 86, 16);

        rbObstaculo.setBackground(new java.awt.Color(113, 142, 110));
        buttonGroup1.add(rbObstaculo);
        rbObstaculo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rbObstaculo.setText("Obstáculo");
        rbObstaculo.setBorder(null);
        fundoOpcoes.add(rbObstaculo);
        rbObstaculo.setBounds(10, 61, 71, 17);

        rbPosicaoFinal.setBackground(new java.awt.Color(113, 142, 110));
        buttonGroup1.add(rbPosicaoFinal);
        rbPosicaoFinal.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rbPosicaoFinal.setText("Posição Final");
        rbPosicaoFinal.setBorder(null);
        fundoOpcoes.add(rbPosicaoFinal);
        rbPosicaoFinal.setBounds(10, 88, 87, 17);

        jPanel2.setBackground(new java.awt.Color(113, 142, 110));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setText("Legenda:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pq_partida.png"))); // NOI18N
        jLabel6.setText("Posição de partida");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pq_obstaculo.png"))); // NOI18N
        jLabel7.setText("Obstáculo");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pq_objetivo.png"))); // NOI18N
        jLabel8.setText("Posição Objetivo");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pq_caminho.png"))); // NOI18N
        jLabel9.setText("Caminho percorrido");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pq_abertos.png"))); // NOI18N
        jLabel12.setText("Caminho lista ABERTOS");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pq_fechados.png"))); // NOI18N
        jLabel13.setText("Caminho lista FECHADOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        fundoOpcoes.add(jPanel2);
        jPanel2.setBounds(140, 20, 330, 100);

        btPercorrer.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btPercorrer.setText("Percorrer");
        btPercorrer.setMaximumSize(new java.awt.Dimension(77, 40));
        btPercorrer.setPreferredSize(new java.awt.Dimension(170, 28));
        btPercorrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPercorrerActionPerformed(evt);
            }
        });
        fundoOpcoes.add(btPercorrer);
        btPercorrer.setBounds(1010, 10, 170, 28);

        btReiniciar.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btReiniciar.setText("Reiniciar");
        btReiniciar.setPreferredSize(new java.awt.Dimension(150, 27));
        btReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReiniciarActionPerformed(evt);
            }
        });
        fundoOpcoes.add(btReiniciar);
        btReiniciar.setBounds(1020, 50, 150, 27);

        btLimpar.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btLimpar.setText("Limpar");
        btLimpar.setPreferredSize(new java.awt.Dimension(150, 27));
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });
        fundoOpcoes.add(btLimpar);
        btLimpar.setBounds(1020, 80, 150, 27);

        btAbrirListas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btAbrirListas.setText("Abrir Listas");
        btAbrirListas.setPreferredSize(new java.awt.Dimension(145, 23));
        btAbrirListas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAbrirListasActionPerformed(evt);
            }
        });
        fundoOpcoes.add(btAbrirListas);
        btAbrirListas.setBounds(820, 20, 145, 23);

        jPanel3.setBackground(new java.awt.Color(113, 142, 110));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setText("Parâmetros do algoritmo A*");

        txtMovHorizontal.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMovHorizontal.setText("1");
        txtMovHorizontal.setPreferredSize(new java.awt.Dimension(20, 20));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel10.setText("Custo do movimento horizontal:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel11.setText("Custo do movimento vertical:");

        txtMovVertical.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMovVertical.setText("1");
        txtMovVertical.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMovVertical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMovHorizontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMovHorizontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtMovVertical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        fundoOpcoes.add(jPanel3);
        jPanel3.setBounds(490, 20, 290, 100);

        getContentPane().add(fundoOpcoes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 1250, 140));

        jMenu1.setText("Jogo");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuIniciar.setText("Novo Jogo");
        menuIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuIniciarActionPerformed(evt);
            }
        });
        jMenu1.add(menuIniciar);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem4.setText("Reiniciar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        btCarregarArquivo.setText("Carregar arquivo");
        btCarregarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCarregarArquivoActionPerformed(evt);
            }
        });
        jMenu1.add(btCarregarArquivo);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ajuda");

        menuAjuda.setText("Sobre o jogo");
        menuAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAjudaActionPerformed(evt);
            }
        });
        jMenu2.add(menuAjuda);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuIniciarActionPerformed
        iniciarNovoJogo();
}//GEN-LAST:event_menuIniciarActionPerformed

    private void menuAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAjudaActionPerformed
        mostrarAbout();
    }//GEN-LAST:event_menuAjudaActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void btCarregarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCarregarArquivoActionPerformed
        carregarArquivo();
    }//GEN-LAST:event_btCarregarArquivoActionPerformed

    private void btReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReiniciarActionPerformed
        reiniciar();
        //marcouPartida = new Integer[]{-1,-1};
        //marcouObjetivo = new Integer[]{-1,-1};
        //obstaculos = new ArrayList<No>();
        //labirinto = new Labirinto(marcouPartida,obstaculos,marcouObjetivo, this, linha, coluna, movHorizontal, movVertical, movDiagonal);
        //criarGrid();
        

    }//GEN-LAST:event_btReiniciarActionPerformed

    private void btPercorrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPercorrerActionPerformed
        reiniciar();
        //Double tMovHorizontal = Double.parseDouble(txtMovHorizontal.getText());
        //Double tMovVertical = Double.parseDouble(txtMovVertical.getText());

        //Double tMovDiagonal = Math.sqrt(Math.pow(tMovHorizontal, 2.)+Math.pow(tMovVertical, 2.));
        labirinto = new Labirinto(marcouPartida,obstaculos,marcouObjetivo, this, linha, coluna, movHorizontal, movVertical, movDiagonal);
        labirinto.executar();
    }//GEN-LAST:event_btPercorrerActionPerformed

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        limparGrid();
    }//GEN-LAST:event_btLimparActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        reiniciar();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btAbrirListasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAbrirListasActionPerformed
        abrirListas();
    }//GEN-LAST:event_btAbrirListasActionPerformed

    public void preencherCaminho(List<No> solucao, int corCaminho) {
        Iterator it = solucao.iterator();
        No no;
        Integer[] pos = new Integer[2];
        while(it.hasNext()) {
            no = (No) it.next();
            pos[0] = no.getX();
            pos[1] = no.getY();
            if((!Arrays.equals(pos, marcouPartida) && !Arrays.equals(pos, marcouObjetivo)) && posicao[pos[0]][pos[1]].getIcon().equals(fundo)) {
                if (corCaminho == 0)
                    posicao[pos[0]][pos[1]].setIcon(caminhoS);
                if (corCaminho == 1)
                    posicao[pos[0]][pos[1]].setIcon(caminhoS2);
                if (corCaminho == 2)
                    posicao[pos[0]][pos[1]].setIcon(caminhoS3);

            }
            if(Arrays.equals(pos, marcouObjetivo))
                posicao[pos[0]][pos[1]].setIcon(objetivoAlcancado);
            if(Arrays.equals(pos, marcouPartida))
                posicao[pos[0]][pos[1]].setIcon(partidaAlcancado);
        }
    }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new WindowsLookAndFeel());
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                new Interface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAbrirListas;
    private javax.swing.JMenuItem btCarregarArquivo;
    private javax.swing.JButton btLimpar;
    private javax.swing.JButton btPercorrer;
    private javax.swing.JButton btReiniciar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel fundoLabirinto;
    private javax.swing.JPanel fundoOpcoes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JMenuItem menuAjuda;
    private javax.swing.JMenuItem menuIniciar;
    private javax.swing.JRadioButton rbObstaculo;
    private javax.swing.JRadioButton rbPosicaoFinal;
    private javax.swing.JRadioButton rbPosicaoInicial;
    private javax.swing.JTextField txtMovHorizontal;
    private javax.swing.JTextField txtMovVertical;
    // End of variables declaration//GEN-END:variables


}
