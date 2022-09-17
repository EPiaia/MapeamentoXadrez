package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.Constantes;
import utils.Pecas;

/**
 *
 * @author Eduardo Piaia
 */
public class Tarefa {

    private String identificacao;
    private Pecas peca;
    private Nodo nodo;

    public Tarefa() {
    }

    public Tarefa(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public Pecas getPeca() {
        return peca;
    }

    public void setPeca(Pecas peca) {
        this.peca = peca;
    }

    public Nodo getNodo() {
        return nodo;
    }

    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    public List<Posicao> getPosicoesPossiveis(int largura, int altura, int rodada) {
        List<Posicao> posicoes = new ArrayList<>();
        if (nodo == null || peca == null) {
            return posicoes;
        }
        for (int mov : peca.getFormasMovimento()) {
            if (mov == Constantes.VERTICAL) {
                // Pega todas acima
                int posY = nodo.getPosicao().getPosY();
                int limite = altura - 1;
                if (limite > posY) {
                    // Se possui um número máxima de casas para andar e esta quantidade é menor do que o restante do "tabuleiro"
                    if (peca.getQtdCasasMovPrincipal(rodada) != null && peca.getQtdCasasMovPrincipal(rodada) < altura - posY) {
                        limite = posY + peca.getQtdCasasMovPrincipal(rodada);
                    }
                    for (int i = posY + 1; i <= limite; i++) {
                        Posicao posicao = new Posicao(nodo.getPosicao().getPosX(), i);
                        posicoes.add(posicao);
                    }
                }

                // Pega todas abaixo
                if (posY > 0) {
                    limite = 0;
                    // Se possui um número máxima de casas para andar e esta quantidade é menor do que o restante do "tabuleiro"
                    if (peca.getQtdCasasMovPrincipal(rodada) != null && posY - peca.getQtdCasasMovPrincipal(rodada) > 0) {
                        limite = posY - peca.getQtdCasasMovPrincipal(rodada);
                    }
                    for (int i = posY - 1; i >= limite; i--) {
                        Posicao posicao = new Posicao(nodo.getPosicao().getPosX(), i);
                        posicoes.add(posicao);
                    }
                }
            } else if (mov == Constantes.HORIZONTAL) {
                int posX = nodo.getPosicao().getPosX();
                int limite = largura - 1;
                // Vai para a direita
                if (limite > posX) {
                    if (peca.getQtdCasasMovPrincipal(rodada) != null && peca.getQtdCasasMovPrincipal(rodada) < limite - posX) {
                        limite = posX + peca.getQtdCasasMovPrincipal(rodada);
                    }
                    for (int i = posX + 1; i <= limite; i++) {
                        Posicao posicao = new Posicao(i, nodo.getPosicao().getPosY());
                        posicoes.add(posicao);
                    }
                }

                // Para a esquerda
                if (posX > 0) {
                    limite = 0;
                    if (peca.getQtdCasasMovPrincipal(rodada) != null && posX - peca.getQtdCasasMovPrincipal(rodada) > 0) {
                        limite = posX - peca.getQtdCasasMovPrincipal(rodada);
                    }
                    for (int i = posX - 1; i >= limite; i--) {
                        Posicao posicao = new Posicao(i, nodo.getPosicao().getPosY());
                        posicoes.add(posicao);
                    }
                }
            } else if (mov == Constantes.DIAGONAL) {
                int posX = nodo.getPosicao().getPosX();
                int posY = nodo.getPosicao().getPosY();
                Integer qtdMovimentos = peca.getQtdCasasMovPrincipal(rodada);

                // Verifica acima
                if (altura - 1 > posY) {
                    int posXAtual = posX + 1;
                    int posYAtual = posY + 1;

                    int limiteY = altura - 1;
                    int limiteX = largura - 1;
                    if (qtdMovimentos != null && qtdMovimentos <= limiteX - posXAtual && qtdMovimentos <= limiteY - posYAtual) {
                        limiteY = posYAtual + qtdMovimentos - 1;
                        limiteX = posXAtual + qtdMovimentos - 1;
                    }
                    // Para a direita
                    while (posYAtual <= limiteY && posXAtual <= limiteX) {
                        Posicao posicao = new Posicao(posXAtual++, posYAtual++);
                        posicoes.add(posicao);
                    }

                    limiteY = altura - 1;
                    limiteX = 0;
                    posXAtual = posX - 1;
                    posYAtual = posY + 1;
                    if (qtdMovimentos != null && qtdMovimentos <= posXAtual - limiteX && qtdMovimentos <= limiteY - posYAtual) {
                        limiteY = posYAtual + qtdMovimentos - 1;
                        limiteX = posXAtual - qtdMovimentos + 1;
                    }
                    // Para a esquerda
                    while (posYAtual <= limiteY && posXAtual >= limiteX) {
                        Posicao posicao = new Posicao(posXAtual--, posYAtual++);
                        posicoes.add(posicao);
                    }
                }

                // Verifica abaixo
                if (posY > 0) {
                    int posXAtual = posX + 1;
                    int posYAtual = posY - 1;

                    int limiteY = 0;
                    int limiteX = largura - 1;
                    if (qtdMovimentos != null && qtdMovimentos <= limiteX - posXAtual && qtdMovimentos <= posYAtual - limiteY) {
                        limiteY = posYAtual - qtdMovimentos + 1;
                        limiteX = posXAtual + qtdMovimentos - 1;
                    }
                    // Para a direita
                    while (posYAtual >= limiteY && posXAtual <= limiteX) {
                        Posicao posicao = new Posicao(posXAtual++, posYAtual--);
                        posicoes.add(posicao);
                    }

                    posXAtual = posX - 1;
                    posYAtual = posY - 1;
                    limiteY = 0;
                    limiteX = 0;
                    if (qtdMovimentos != null && qtdMovimentos <= posXAtual - limiteX && qtdMovimentos <= posYAtual - limiteY) {
                        limiteY = posYAtual - qtdMovimentos + 1;
                        limiteX = posXAtual - qtdMovimentos + 1;
                    }
                    // Para a esquerda
                    while (posYAtual >= limiteY && posXAtual >= limiteX) {
                        Posicao posicao = new Posicao(posXAtual--, posYAtual--);
                        posicoes.add(posicao);
                    }
                }
            } else if (mov == Constantes.L) {
                int qtdCasaP = peca.getQtdCasasMovPrincipal(rodada);
                int qtdCasasS = peca.getQtdCasasMovSecundario();

            }
        }
        return posicoes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.identificacao);
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
        final Tarefa other = (Tarefa) obj;
        return Objects.equals(this.identificacao, other.identificacao);
    }
}
