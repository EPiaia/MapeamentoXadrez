package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import utils.Constantes;
import utils.MapeamentoUtil;
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
                int qtdCasasP = peca.getQtdCasasMovPrincipal(rodada);
                int qtdCasasS = peca.getQtdCasasMovSecundario();
                int posX = nodo.getPosicao().getPosX();
                int posY = nodo.getPosicao().getPosY();

                // Para cima
                if (altura > posY + qtdCasasP) {
                    // Para a direita
                    if (largura > posX + qtdCasasS) {
                        Posicao posicao = new Posicao(posX + qtdCasasS, posY + qtdCasasP);
                        posicoes.add(posicao);
                    }

                    //Para a esquerda
                    if (posX - qtdCasasS >= 0) {
                        Posicao posicao = new Posicao(posX - qtdCasasS, posY + qtdCasasP);
                        posicoes.add(posicao);
                    }
                }

                // Para baixo
                if (posY - qtdCasasP >= 0) {
                    // Para a direita
                    if (largura > posX + qtdCasasS) {
                        Posicao posicao = new Posicao(posX + qtdCasasS, posY - qtdCasasP);
                        posicoes.add(posicao);
                    }

                    //Para a esquerda
                    if (posX - qtdCasasS >= 0) {
                        Posicao posicao = new Posicao(posX - qtdCasasS, posY - qtdCasasP);
                        posicoes.add(posicao);
                    }
                }

                // Para a direita
                if (posX + qtdCasasP < largura) {
                    // Para cima
                    if (posY + qtdCasasS < altura) {
                        Posicao posicao = new Posicao(posX + qtdCasasP, posY + qtdCasasS);
                        posicoes.add(posicao);
                    }

                    // Para baixo
                    if (posY - qtdCasasS >= 0) {
                        Posicao posicao = new Posicao(posX + qtdCasasP, posY - qtdCasasS);
                        posicoes.add(posicao);
                    }
                }

                // Para a esquerda
                if (posX - qtdCasasP >= 0) {
                    // Para cima
                    if (posY + qtdCasasS < altura) {
                        Posicao posicao = new Posicao(posX - qtdCasasP, posY + qtdCasasS);
                        posicoes.add(posicao);
                    }

                    // Para baixo
                    if (posY - qtdCasasS >= 0) {
                        Posicao posicao = new Posicao(posX - qtdCasasP, posY - qtdCasasS);
                        posicoes.add(posicao);
                    }
                }
            }
        }
        return posicoes;
    }

    private Posicao getPosicaoEscolhida(int largura, int altura, int rodada) {
        List<Posicao> posicoesPossiveis = getPosicoesPossiveis(largura, altura, rodada);
        int posYAtual = this.nodo.getPosicao().getPosY();
        int posXAtual = this.nodo.getPosicao().getPosX();
        boolean possuiPosicoesAcima = posicoesPossiveis.stream().filter(pos -> pos.getPosY() > posYAtual).count() > 0;
        boolean possuiPosicoesAbaixo = posicoesPossiveis.stream().filter(pos -> pos.getPosY() < posYAtual).count() > 0;
        boolean possuiPosicoesADireita = posicoesPossiveis.stream().filter(pos -> pos.getPosX() > posXAtual).count() > 0;
        boolean possuiPosicoesAEsquerda = posicoesPossiveis.stream().filter(pos -> pos.getPosX() < posXAtual).count() > 0;
        Integer formaMovimento;

        if (this.peca.getFormasMovimento().length > 1) {
            int max = this.peca.getFormasMovimento().length;
            int forma = ThreadLocalRandom.current().nextInt(max);
            formaMovimento = this.peca.getFormasMovimento()[forma];
        } else {
            formaMovimento = this.peca.getFormasMovimento()[0];
        }

        if (formaMovimento == Constantes.HORIZONTAL) {
            int lado;

            if (possuiPosicoesAEsquerda && possuiPosicoesADireita) {
                lado = ThreadLocalRandom.current().nextInt(2);
            } else if (possuiPosicoesAEsquerda) {
                lado = 0;
            } else {
                lado = 1;
            }

            if (lado == 0) { // Se for a esquerda
                if (this.peca.getQtdCasasMovPrincipal(rodada) == null) {
                    int maxCasas = posXAtual;
                    if (maxCasas == 1) {
                        maxCasas++;
                    }
                    int qtdCasas = ThreadLocalRandom.current().nextInt(1, maxCasas);
                    return posicoesPossiveis.stream().filter(pos -> pos.getPosX() == (posXAtual - qtdCasas)).findFirst().get();
                } else {
                    return posicoesPossiveis.stream().filter(pos -> pos.getPosX() < posXAtual).findFirst().get();
                }
            } else { // Se for a direita
                if (this.peca.getQtdCasasMovPrincipal(rodada) == null) {
                    int maxCasas = largura - posXAtual;
                    if (maxCasas == 1) {
                        maxCasas++;
                    }
                    int qtdCasas = ThreadLocalRandom.current().nextInt(1, maxCasas);
                    return posicoesPossiveis.stream().filter(pos -> pos.getPosX() == (posXAtual + qtdCasas)).findFirst().get();
                } else {
                    return posicoesPossiveis.stream().filter(pos -> pos.getPosX() < posXAtual).findFirst().get();
                }
            }
        } else if (formaMovimento == Constantes.VERTICAL) {
            int lado;

            if (possuiPosicoesAcima && possuiPosicoesAbaixo) {
                lado = ThreadLocalRandom.current().nextInt(2);
            } else if (possuiPosicoesAbaixo) {
                lado = 0;
            } else {
                lado = 1;
            }

            if (lado == 0) { // Se for abaixo
                if (this.peca.getQtdCasasMovPrincipal(rodada) == null) {
                    int maxCasas = posYAtual;
                    if (maxCasas == 1) {
                        maxCasas++;
                    }
                    int qtdCasas = ThreadLocalRandom.current().nextInt(1, maxCasas);
                    return posicoesPossiveis.stream().filter(pos -> pos.getPosY() == (posYAtual - qtdCasas)).findFirst().get();
                } else {
                    return posicoesPossiveis.stream().filter(pos -> pos.getPosY() < posYAtual).findFirst().get();
                }
            } else { // Se for acima
                if (this.peca.getQtdCasasMovPrincipal(rodada) == null) {
                    int maxCasas = altura - posYAtual;
                    if (maxCasas == 1) {
                        maxCasas++;
                    }
                    int qtdCasas = ThreadLocalRandom.current().nextInt(1, maxCasas);
                    return posicoesPossiveis.stream().filter(pos -> pos.getPosY() == (posYAtual + qtdCasas)).findFirst().get();
                } else {
                    return posicoesPossiveis.stream().filter(pos -> pos.getPosY() > posYAtual).findFirst().get();
                }
            }
        } else if (formaMovimento == Constantes.DIAGONAL) {
            List<Posicao> posicoesLadoEscolhido = new ArrayList<>();
            int direcao;
            if (possuiPosicoesAcima && possuiPosicoesAbaixo) {
                direcao = ThreadLocalRandom.current().nextInt(2);
            } else if (possuiPosicoesAbaixo) {
                direcao = 0;
            } else {
                direcao = 1;
            }

            boolean possuiPosDiagDireita = false;
            boolean possuiPosDiagEsquerda = false;
            int direcaoLado;
            // Se para baixo
            if (direcao == 0) {
                possuiPosDiagDireita = posicoesPossiveis.stream().filter(pos -> pos.getPosY() < posYAtual && pos.getPosX() > posXAtual).count() > 0;
                possuiPosDiagEsquerda = posicoesPossiveis.stream().filter(pos -> pos.getPosY() < posYAtual && pos.getPosX() < posXAtual).count() > 0;
            } else { // Para cima
                possuiPosDiagDireita = posicoesPossiveis.stream().filter(pos -> pos.getPosY() > posYAtual && pos.getPosX() > posXAtual).count() > 0;
                possuiPosDiagEsquerda = posicoesPossiveis.stream().filter(pos -> pos.getPosY() > posYAtual && pos.getPosX() < posXAtual).count() > 0;
            }

            if (possuiPosDiagDireita && possuiPosDiagEsquerda) {
                direcaoLado = ThreadLocalRandom.current().nextInt(2);
            } else if (possuiPosDiagEsquerda) {
                direcaoLado = 0;
            } else {
                direcaoLado = 1;
            }

            if (direcao == 0) {
                if (direcaoLado == 0) {
                    posicoesLadoEscolhido = posicoesPossiveis.stream().filter(pos -> pos.getPosY() < posYAtual && pos.getPosX() < posXAtual).collect(Collectors.toList());
                } else {
                    posicoesLadoEscolhido = posicoesPossiveis.stream().filter(pos -> pos.getPosY() < posYAtual && pos.getPosX() > posXAtual).collect(Collectors.toList());
                }
            } else {
                if (direcaoLado == 0) {
                    posicoesLadoEscolhido = posicoesPossiveis.stream().filter(pos -> pos.getPosY() > posYAtual && pos.getPosX() < posXAtual).collect(Collectors.toList());
                } else {
                    posicoesLadoEscolhido = posicoesPossiveis.stream().filter(pos -> pos.getPosY() > posYAtual && pos.getPosX() > posXAtual).collect(Collectors.toList());
                }
            }

            if (this.peca.getQtdCasasMovPrincipal(rodada) == null) {
                int qtdMaxCasas = posicoesLadoEscolhido.size();
                qtdMaxCasas = qtdMaxCasas == 1 ? 2 : qtdMaxCasas;
                int qtdCasas = ThreadLocalRandom.current().nextInt(1, qtdMaxCasas);

                if (direcaoLado == 0) { // Esquerda
                    return posicoesLadoEscolhido.stream().filter(pos -> pos.getPosX() == posXAtual - qtdCasas).findFirst().get();
                } else { // Direita
                    return posicoesLadoEscolhido.stream().filter(pos -> pos.getPosX() == posXAtual + qtdCasas).findFirst().get();
                }
            } else {
                return posicoesLadoEscolhido.get(0);
            }
        } else if (formaMovimento == Constantes.L) {
            List<Posicao> posicoesMovimento = new ArrayList<>();
            int qtdCasasMovP = this.peca.getQtdCasasMovPrincipal(rodada);
            possuiPosicoesAcima = posicoesPossiveis.stream().filter(pos -> pos.getPosY() == posYAtual + qtdCasasMovP).count() > 0;
            possuiPosicoesAbaixo = posicoesPossiveis.stream().filter(pos -> pos.getPosY() == posYAtual - qtdCasasMovP).count() > 0;
            possuiPosicoesADireita = posicoesPossiveis.stream().filter(pos -> pos.getPosX() == posXAtual + qtdCasasMovP).count() > 0;
            possuiPosicoesAEsquerda = posicoesPossiveis.stream().filter(pos -> pos.getPosX() == posXAtual - qtdCasasMovP).count() > 0;

            List<Integer> possiveisMovimentos = new ArrayList<>();
            if (possuiPosicoesAcima) {
                possiveisMovimentos.add(1);
            }
            if (possuiPosicoesAbaixo) {
                possiveisMovimentos.add(2);
            }
            if (possuiPosicoesADireita) {
                possiveisMovimentos.add(3);
            }
            if (possuiPosicoesAEsquerda) {
                possiveisMovimentos.add(4);
            }
            int movimento = possiveisMovimentos.get(ThreadLocalRandom.current().nextInt(possiveisMovimentos.size()));
            if (movimento == 1) { // Para cima
                posicoesMovimento = posicoesPossiveis.stream().filter(pos -> pos.getPosY() == posYAtual + qtdCasasMovP).collect(Collectors.toList());
            } else if (movimento == 2) { // Para baixo
                posicoesMovimento = posicoesPossiveis.stream().filter(pos -> pos.getPosY() == posYAtual - qtdCasasMovP).collect(Collectors.toList());
            } else if (movimento == 3) { // Para a direita
                posicoesMovimento = posicoesPossiveis.stream().filter(pos -> pos.getPosX() == posXAtual + qtdCasasMovP).collect(Collectors.toList());
            } else { // Para a esquerda
                posicoesMovimento = posicoesPossiveis.stream().filter(pos -> pos.getPosX() == posXAtual - qtdCasasMovP).collect(Collectors.toList());
            }

            if (posicoesMovimento.size() > 1) {
                int lado = ThreadLocalRandom.current().nextInt(posicoesMovimento.size());
                return posicoesMovimento.get(lado);
            } else {
                return posicoesMovimento.get(0);
            }
        }
        return null;
    }

    public void atribuirTarefaANodo(Nodo[][] nodos, int rodada, int qtdTarefasProc) {
        System.out.println("");
        System.out.println("------------------ TAREFA " + getIdentificacao() + " ------------------");
        System.out.println("Peça: " + getPeca().getNome());
        System.out.println("Possíveis Posições: ");
        System.out.println("");

        int largura = nodos.length;
        int altura = nodos[0].length;

        MapeamentoUtil.printarMatriz(nodos, qtdTarefasProc, getPosicoesPossiveis(largura, altura, rodada), getNodo().getPosicao());

        Posicao posicaoEscolhida = getPosicaoEscolhida(largura, altura, rodada);

        System.out.println("");
        System.out.println("Posição Escolhida: (" + posicaoEscolhida.getPosX() + ", " + posicaoEscolhida.getPosY() + ")");
        System.out.println("");

        Nodo nodoPosicao = nodos[posicaoEscolhida.getPosX()][posicaoEscolhida.getPosY()];
        nodoPosicao.getTarefas().add(this);
        this.nodo = nodoPosicao;
        if (this.nodo.getTarefas().size() > qtdTarefasProc) {
            Tarefa tarefaAntiga = this.nodo.getTarefas().get(0);
            System.out.println("A posição escolhida já contém " + qtdTarefasProc + " tarefa(s), reatribuindo tarefa " + tarefaAntiga.getIdentificacao());
            this.nodo.getTarefas().remove(tarefaAntiga);
            tarefaAntiga.setPeca(Pecas.getPecaAleatoria());
            tarefaAntiga.atribuirTarefaANodo(nodos, rodada, qtdTarefasProc);
        }

        MapeamentoUtil.printarMatriz(nodos, qtdTarefasProc, null, null);

        System.out.println("------------------ FIM TAREFA " + getIdentificacao() + " ------------------");
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
