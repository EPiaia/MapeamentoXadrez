package utils;

import classes.Nodo;
import classes.Posicao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Piaia
 */
public class MapeamentoUtil {

    public static void printarMatriz(Nodo[][] matriz, int qtdProc, List<Posicao> posicoesPossiveis, Posicao posicaoTarefa) {
        if (posicoesPossiveis == null) {
            posicoesPossiveis = new ArrayList<>();
        }
        int largura = matriz.length;
        int altura = matriz[0].length;

        System.out.println("Matriz de nodos: ");
        System.out.println("");

        for (int y = altura - 1; y >= 0; y--) {
            for (int i = 0; i < qtdProc; i++) {
                for (int x = 0; x < largura; x++) {
                    Nodo nodo = matriz[x][y];
                    if (posicaoTarefa != null && posicaoTarefa.equals(nodo.getPosicao())) {
                        System.out.print("XXX  ");
                    } else if (posicoesPossiveis.contains(nodo.getPosicao())) {
                        System.out.print("111  ");
                    } else if (nodo.getTarefas().size() > i) {
                        System.out.print(nodo.getTarefas().get(i).getIdentificacao() + "  ");
                    } else {
                        System.out.print("000  ");
                    }
                }
                System.out.println("");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
