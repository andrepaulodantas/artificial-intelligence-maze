package br.ufrn.controleinteligente.estruturas;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um nó para o labirinto
 * 
 * @author Andre
 */
public class No {

    /** Identifica o No */
    private int id;

    /** Identificador do caminho */
    private int idCaminho;

    //Posiçao
    private int x;                     //linha
    private int y;                     //coluna


    /** Corresponde ao nó pai */
    private No pai;

    /** Atributo que informa se o nó corrente está na lista de abertos */
    private boolean aberto;

    /** Valor da função de custo */
    private double f;
    
    /** Valor do cálculo da heurística usada para estimar o caminho do nó atual ao nó objetivo*/
    private double h;

    /** Valor que armazena o cálculo do custo do nó raiz ao nó atual */
    private double g;

    /** Nós que fazem parte da vizinhança do nó atual */
    private List<No> vizinhos;

    /* Nós do caminho do nó raiz até o nó atual */
    private List<No> caminho;

    /** Informa se a posição atual corresponde a:
     * 0 - LIVRE; 1- OBSTACULO; 2-PARTIDA; 3- CHEGADA
     */
    private int status;

    /** Valor do custo do movimento horizontal */
    private double movHorizontal;

    /** Valor do custo do movimento vertical */
    private double movVertical;

    /** Valor do custo do movimento diagonal */
    private double movDiagonal;

    /** Referência para o labirinto o qual faz parte */
    private Labirinto labirinto;

    /**
     * Método que retorna uma lista com todos os vizinhos do nó atual calculando
     * seu g(n).
     * @return vizinhos
     */
    public void getVizinhanca(){
        //List<No> vizinhos = new ArrayList<No>();
        if (pai==null) {
            caminho = new ArrayList<No>();
            caminho.add(this);
        }
        int xAux, yAux = 0;

        //[i-1,j-1]
        xAux = x-1;
        yAux = y-1;
        if ((xAux >= 0 && xAux < labirinto.getnLinha()) && (yAux >= 0 && yAux < labirinto.getnColuna())) {
            No vizinho = new No(labirinto,xAux,yAux);
            if(!labirinto.getObstaculos().contains(vizinho)) {
                vizinho.setPai(this);
                vizinho.setG(g+movDiagonal);
                vizinho.setH(labirinto.distanciaDoisPontos(vizinho));
                vizinho.setF(vizinho.getG()+vizinho.getH());
                vizinho.gerarCaminho(vizinho.getPai());
                vizinhos.add(vizinho);
            }
        }

        //[i-1,j]
        xAux = x-1;
        if (xAux >= 0 && xAux < labirinto.getnLinha()) {
            No vizinho = new No(labirinto,xAux,y);
            if(!labirinto.getObstaculos().contains(vizinho)) {
                vizinho.setPai(this);
                vizinho.setG(g+movVertical);
                vizinho.setH(labirinto.distanciaDoisPontos(vizinho));
                vizinho.setF(vizinho.getG()+vizinho.getH());
                vizinho.gerarCaminho(vizinho.getPai());
                vizinhos.add(vizinho);
            }
        }

        //[i-1,j+1]
        xAux = x-1;
        yAux = y+1;
        if ((xAux >= 0 && xAux < labirinto.getnLinha()) && (yAux >= 0 && yAux < labirinto.getnColuna())) {
            No vizinho = new No(labirinto,xAux,yAux);
            if(!labirinto.getObstaculos().contains(vizinho)) {
                vizinho.setPai(this);
                vizinho.setG(g+movDiagonal);
                vizinho.setH(labirinto.distanciaDoisPontos(vizinho));
                vizinho.setF(vizinho.getG()+vizinho.getH());
                vizinho.gerarCaminho(vizinho.getPai());
                vizinhos.add(vizinho);
            }
        }

        //[i,j-1]
        yAux = y-1;
        if (yAux >= 0 && yAux < labirinto.getnColuna()) {
            No vizinho = new No(labirinto,x,yAux);
            if(!labirinto.getObstaculos().contains(vizinho)) {
                vizinho.setPai(this);
                vizinho.setG(g+movHorizontal);
                vizinho.setH(labirinto.distanciaDoisPontos(vizinho));
                vizinho.setF(vizinho.getG()+vizinho.getH());
                vizinho.gerarCaminho(vizinho.getPai());
                vizinhos.add(vizinho);
            }
        }

        //[i,j+1]
        yAux = y+1;
        if (yAux >= 0 && yAux < labirinto.getnColuna()) {
            No vizinho = new No(labirinto,x,yAux);
            if(!labirinto.getObstaculos().contains(vizinho)) {
                vizinho.setPai(this);
                vizinho.setG(g+movHorizontal);
                vizinho.setH(labirinto.distanciaDoisPontos(vizinho));
                vizinho.setF(vizinho.getG()+vizinho.getH());
                vizinho.gerarCaminho(vizinho.getPai());
                vizinhos.add(vizinho);
            }
        }

        //[i+1,j-1]
        xAux = x+1;
        yAux = y-1;
        if ((xAux >= 0 && xAux < labirinto.getnLinha()) && (yAux >= 0 && yAux < labirinto.getnColuna())) {
            No vizinho = new No(labirinto,xAux,yAux);
            if(!labirinto.getObstaculos().contains(vizinho)) {
                vizinho.setPai(this);
                vizinho.setG(g+movDiagonal);
                vizinho.setH(labirinto.distanciaDoisPontos(vizinho));
                vizinho.setF(vizinho.getG()+vizinho.getH());
                vizinho.gerarCaminho(vizinho.getPai());
                vizinhos.add(vizinho);
            }
        }

        //[i+1,j]
        xAux = x+1;
        if (xAux >= 0 && xAux < labirinto.getnLinha()) {
            No vizinho = new No(labirinto,xAux,y);
            if(!labirinto.getObstaculos().contains(vizinho)) {
                vizinho.setPai(this);
                vizinho.setG(g+movVertical);
                vizinho.setH(labirinto.distanciaDoisPontos(vizinho));
                vizinho.setF(vizinho.getG()+vizinho.getH());
                vizinho.gerarCaminho(vizinho.getPai());
                vizinhos.add(vizinho);
            }
        }

        //[i+1,j+1]
        xAux = x+1;
        yAux = y+1;
        if ((xAux >= 0 && xAux < labirinto.getnLinha()) && (yAux >= 0 && yAux < labirinto.getnColuna())) {
            No vizinho = new No(labirinto,xAux,yAux);
            if(!labirinto.getObstaculos().contains(vizinho)) {
                vizinho.setPai(this);

                //calcular custos
                vizinho.setG(g+movDiagonal);
                vizinho.setH(labirinto.distanciaDoisPontos(vizinho));
                vizinho.setF(vizinho.getG()+vizinho.getH());

                vizinho.gerarCaminho(vizinho.getPai());
                vizinhos.add(vizinho);
            }
        }
    }

    public int getIdCaminho() {
        return idCaminho;
    }

    public void setIdCaminho(int idCaminho) {
        this.idCaminho = idCaminho;
    }
    

    /**
     * Método para iniciar um caminho.
     * <br/>
     * Usado no labirinto quando se tratar do nó raiz. É utilizado no primeiro passo do algoritmo A*
     */
    public void iniciarCaminho(){
        caminho = new ArrayList<No>();
        caminho.add(this);
    }

    /**
     * Método que retorna o id do nó.
     * <br/>
     * <br/>
     * Id corresponde a posicao linear do nó.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Método que retorna a posição(x,y) do nó no labirinto dado ID
     * @return
     */
    public Integer[] getPosicao(int id) {
        //0 -> x
        //1 -> y
        Integer posicao[] = new Integer[2];

        posicao[0] = (id%labirinto.getnColuna())-1;
        posicao[1] = (Integer) id/labirinto.getnColuna();
        return posicao;
    }

    /**
     * Método para completar o caminho de um nó.
     * <br/>
     * Inicia a lista 'caminho' pegando a lista caminho do nó pai e adicionando o atual.
     * <br/>
     * <br/>
     * <b>Pode haver problema de referência. Checar!</b>
     * <b>Se der problema, fazer dentro do método da vizinhança.</b>
     * @return
     */
    public void gerarCaminho(No pai) {
        caminho = new ArrayList<No>();
        caminho.addAll(pai.getCaminho());
        caminho.add(this);
    }

    public Labirinto getLabirinto() {
        return labirinto;
    }

    public void setLabirinto(Labirinto labirinto) {
        this.labirinto = labirinto;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

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

    public List<No> getCaminho() {
        return caminho;
    }

    public void setCaminho(List<No> caminho) {
        this.caminho = caminho;
    }

    public List<No> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(List<No> vizinhos) {
        this.vizinhos = vizinhos;
    }

    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    @Override
    public boolean equals(Object obj) {
        No no = (No) obj;
        return (x==no.getX() && y==no.getY());
    }


    public No(Labirinto labirinto, int x, int y) {
        //this.id = labirinto.getnColuna() + (y+1);
        this.x = x;
        this.y = y;
        this.pai = null;
        
        this.aberto = false;
        this.status = 0;

        this.f = 0;
        this.g = 0;
        this.h = 0;

        this.vizinhos = new ArrayList<No>();
        this.caminho = new ArrayList<No>();

        this.labirinto = labirinto;
        this.movHorizontal = labirinto.getMovHorizontal();
        this.movVertical = labirinto.getMovVertical();
        this.movDiagonal = labirinto.getMovDiagonal();
        //this.vizinhanca = getVizinhanca();

    }

    public No(int x, int y) {
        //this.id = labirinto.getnColuna() + (y+1);
        this.x = x;
        this.y = y;
        this.pai = null;

        this.aberto = false;
        this.status = 0;

        this.f = 0;
        this.g = 0;
        this.h = 0;

        this.vizinhos = new ArrayList<No>();
        this.caminho = new ArrayList<No>();

        this.labirinto = null;
        this.movHorizontal = 0.0;
        this.movVertical = 0.0;
        this.movDiagonal = 0.0;
        //this.vizinhanca = getVizinhanca();

    }


}
