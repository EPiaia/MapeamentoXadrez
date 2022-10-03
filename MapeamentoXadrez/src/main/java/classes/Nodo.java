package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Eduardo Piaia
 */
public class Nodo {

    private Posicao posicao;
    private Nodo nodoEsquerda;
    private Nodo nodoBaixo;
    private Nodo nodoDireita;
    private Nodo nodoCima;
    private List<Tarefa> tarefas = new ArrayList<>();

    public Nodo() {
    }

    public Nodo(Posicao posicao) {
        this.posicao = posicao;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public Nodo getNodoEsquerda() {
        return nodoEsquerda;
    }

    public void setNodoEsquerda(Nodo nodoEsquerda) {
        this.nodoEsquerda = nodoEsquerda;
    }

    public Nodo getNodoBaixo() {
        return nodoBaixo;
    }

    public void setNodoBaixo(Nodo nodoBaixo) {
        this.nodoBaixo = nodoBaixo;
    }

    public Nodo getNodoDireita() {
        return nodoDireita;
    }

    public void setNodoDireita(Nodo nodoDireita) {
        this.nodoDireita = nodoDireita;
    }

    public Nodo getNodoCima() {
        return nodoCima;
    }

    public void setNodoCima(Nodo nodoCima) {
        this.nodoCima = nodoCima;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.posicao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nodo other = (Nodo) obj;
        return Objects.equals(this.posicao, other.posicao);
    }
}
