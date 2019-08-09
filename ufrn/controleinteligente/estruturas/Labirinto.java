package br.ufrn.controleinteligente.estruturas;

import br.ufrn.controleinteligente.gui.Interface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Classe que representa o relacionamentoo entre as posições do labirinto.
 * <br/>
 * <br/>
 * Esta classe modela as posições do labirinto como Grafos
 * </br>
 * @author Andre
 */
public class Labirinto {

    private Integer[][] labirinto;

    /** Posição de partida */
    private No partida;

    private No objetivo;

    private List<No> obstaculos;

    /** Lista de nós que já foram abertos para ser analisados pelo algoritmo */
    private List<No> abertos;

    /** Lista de nós inexplorados pelo algoritmo */
    private List<No> fechados;

    /** Lista de nós que constituem a solução do labiritno */
    private List<No> solucao;

    private Integer nLinha;

    private Integer nColuna;

    /** Valor do custo do movimento horizontal */
    private double movHorizontal;

    /** Valor do custo do movimento vertical */
    private double movVertical;

    /** Valor do custo do movimento diagonal */
    private double movDiagonal;

    /** Indica o caminho corrente */
    private int nCaminho;


    private Interface Interface;

    /**
     * Executa o algoritmo A*
     */
    public void executar() {

        //Iniciar lista de nós expandidos com o nó de partida e iniciar a lista de nós fechados vazia
        abertos.add(partida);
        
        Iterator it = abertos.iterator();
        boolean achou = false;

        No noAux;
        No noAuxOld;
        int i = 0;
        while (it.hasNext() && !achou) {
            noAux = menorCusto();
            if (noAux.equals(objetivo)) {
                achou = true;
                solucao.add(noAux);
                Interface.preencherCaminho(noAux.getCaminho(),0);
                Interface.preencherCaminho(abertos,1);
                Interface.preencherCaminho(fechados,2);
                Iterator it2 = noAux.getCaminho().iterator();
                int cont = 0;
                while(it2.hasNext()) {
                    No no2 = (No) it2.next();
                    if(cont>0)
                        System.out.println(cont+":g(n):"+no2.getG()+",h(n):"+no2.getH()+",f(n):"+no2.getF());
                    cont++;
                }
            }

            noAux.setLabirinto(this);
            noAux.setMovDiagonal(movDiagonal);
            noAux.setMovHorizontal(movHorizontal);
            noAux.setMovVertical(movVertical);
            //
            abertos.remove(0);
            if (obstaculos.contains(noAux))
                continue;
            solucao.add(noAux);
            //System.out.println("\n\nX:"+solucao.get(i).getX()+" - Y:"+solucao.get(i).getY()+"("+"g(n):"+solucao.get(i).getG()+")\n\n");
    
            //Verifica nos nós fechados se o caminho atual é melhor
            if (fechados.contains(noAux)) {
                int indice = fechados.indexOf(noAux);
                noAuxOld = fechados.get(indice);
                if (noAux.getF() <= noAuxOld.getF()) {
                    fechados.add(indice, noAux);
                }
            } else {
                //Se não pertencer ao fechados significa que não teve sucessores expandidos
                fechados.add(noAux);
                expandirNo(noAux);
            }

            
            i++;
            it = abertos.iterator();
        }

        if (!achou)
            System.out.println("Não achou caminho válido");


        //partida.getVizinhanca();

        //nosAbertos.add(noPartida);
        //nosFechados = new ArrayList<No>();

        //Iterator it = nosAbertos.iterator();
        //while(true) {
            //if (nosAbertos.isEmpty())
              //  return;
        //}
        
    }

    /**
     * Calcula a distância euclidiana entre dois pontos.
     * @param no
     * @return
     */
    protected double distanciaDoisPontos(No no) {
        int x1,y1;
        x1 = objetivo.getX();
        y1 = objetivo.getY();
        
        double cateto1, cateto2, hipotenusa;
        cateto1 = Math.abs(x1-no.getX());
        cateto2 = Math.abs(y1-no.getY());
        hipotenusa = Math.pow(cateto1, 2.)+Math.pow(cateto2, 2.);
        return Math.sqrt(hipotenusa);
    }

    /**
     * Método para efetuar a expansão de um nó.
     * <br/>
     * A lista 'abertos' vai receber os nós resultantes da expansão.
     */
    public void expandirNo(No no) {
        List<No> listaAux = new ArrayList<No>();
        listaAux.addAll(abertos);

        no.getVizinhanca();

        Iterator it = no.getVizinhos().iterator();
        abertos = new ArrayList<No>();

        No mOld;
        while (it.hasNext()) {
            No m = (No) it.next();
            m.setLabirinto(this);
            m.setIdCaminho(nCaminho);
            if (listaAux.contains(m)) {
                int indice = listaAux.indexOf(m);
                mOld = listaAux.get(indice);
                if (m.getF() <= mOld.getF()) {
                    listaAux.remove(indice);
                    abertos.add(m);
                }
            } else
                abertos.add(m);
        }

        //Se abertos tiver vazio, aí incrementa o contador para que o caminho seja pintado com cores diferentes
        if (listaAux.isEmpty())
            nCaminho++;
        
        abertos.addAll(listaAux);
    }

    /**
     * Percorre a lista 'abertos' e retorna o de menor custo.
     * @return
     */
    protected No menorCusto() {

        Collections.sort(abertos, new Comparator() {
            public int compare(Object o1, Object o2) {
                No no1 = (No) o1;
                No no2 = (No) o2;
                double num1 = (Double) no1.getF();
                double num2 = (Double) no2.getF();
                return num1 <= num2 ? -1 : (num1 > num2 ? +1 : 0);
            }
        });
        return abertos.get(0);
    }

    public Integer getnColuna() {
        return nColuna;
    }

    public void setnColuna(Integer nColuna) {
        this.nColuna = nColuna;
    }

    public Integer getnLinha() {
        return nLinha;
    }

    public void setnLinha(Integer nLinha) {
        this.nLinha = nLinha;
    }

    public No getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(No objetivo) {
        this.objetivo = objetivo;
    }

    public No getPartida() {
        return partida;
    }

    public void setPartida(No partida) {
        this.partida = partida;
    }

    public List<No> getObstaculos() {
        return obstaculos;
    }

    public void setObstaculos(List<No> obstaculos) {
        this.obstaculos = obstaculos;
    }

    public Integer[][] getLabirinto() {
        return labirinto;
    }

    public void setLabirinto(Integer[][] labirinto) {
        this.labirinto = labirinto;
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

    public List<No> getSolucao() {
        return solucao;
    }

    public void setSolucao(List<No> solucao) {
        this.solucao = solucao;
    }

    public List<No> getAbertos() {
        return abertos;
    }

    public void setAbertos(List<No> abertos) {
        this.abertos = abertos;
    }

    public List<No> getFechados() {
        return fechados;
    }

    public void setFechados(List<No> fechados) {
        this.fechados = fechados;
    }
    
    
    
    public Labirinto(Interface Interface) {
        this.abertos = new ArrayList<No>();
        this.fechados = new ArrayList<No>();
        this.Interface = Interface;
        this.solucao = new ArrayList<No>();
    }


    /**
     * Construtor completo da classe
     * @param partida
     * @param obstaculos
     * @param objetivo
     * @param Interface
     * @param linhas
     * @param colunas
     * @param movHorizontal
     * @param movVertical
     * @param movDiagonal
     */
    public Labirinto(Integer[] partida, List<No> obstaculos, Integer[] objetivo, Interface Interface,
                int linhas, int colunas, double movHorizontal, double movVertical, double movDiagonal) {
        this.partida = new No(this, partida[0], partida[1]);
        this.objetivo = new No(this, objetivo[0], objetivo[1]);
        this.abertos = new ArrayList<No>();
        this.fechados = new ArrayList<No>();
        this.obstaculos = new ArrayList<No>();
        this.obstaculos.addAll(obstaculos);
        this.Interface = Interface;
        this.nLinha = linhas;
        this.nColuna = colunas;
        this.labirinto = new Integer[linhas][colunas];
        this.movHorizontal = movHorizontal;
        this.movVertical = movVertical;
        this.movDiagonal = movDiagonal;
        this.solucao = new ArrayList<No>();
        this.nCaminho = 0;

    }


    //Para usar nos testes
    public Labirinto(int linhas, int colunas) {
        this.obstaculos = new ArrayList<No>();
        this.obstaculos.addAll(obstaculos);
        this.nLinha = linhas;
        this.nColuna = colunas;
        this.labirinto = new Integer[linhas][colunas];
        this.solucao = new ArrayList<No>();

    }


}
