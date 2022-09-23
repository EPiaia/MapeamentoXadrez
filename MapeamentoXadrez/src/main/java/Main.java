
import classes.Nodo;
import classes.Posicao;
import classes.Tarefa;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utils.Pecas;

/**
 *
 * @author Eduardo Piaia
 */
public class Main {

    public static void main(String[] args) {
        // Percorrer a matriz com o eixo X na horizontal e eixo Y na vertical, com o 0,0 sendo o canto inferior esquerdo
        // Sortear cor para pegar a direção para se mover (baixo ou cima), a direção horizontal quando houver e diagonal quando houver

        int altura = 5;
        int largura = 7;
        String nodos[][] = new String[largura][altura];
        for (int y = altura - 1; y >= 0; y--) {
            for (int x = 0; x < largura; x++) {
                nodos[x][y] = "0";
            }
        }

        int posX = 2;
        int posY = 2;

        Nodo nodo = new Nodo(new Posicao(posX, posY));
        Tarefa tarefa = new Tarefa("A");
        tarefa.setPeca(Pecas.TORRE);
        tarefa.setNodo(nodo);
        nodo.setTarefa(tarefa);
        nodos[nodo.getPosicao().getPosX()][nodo.getPosicao().getPosY()] = "X";

//        List<Posicao> posicoes = tarefa.getPosicoesPossiveis(largura, altura, 2);
//        for (Posicao posicao : posicoes) {
//            nodos[posicao.getPosX()][posicao.getPosY()] = "1";
//        }
        Posicao posicao = tarefa.getPosicaoEscolhida(largura, altura, 1);
        nodos[posicao.getPosX()][posicao.getPosY()] = "1";

        for (int y = altura - 1; y >= 0; y--) {
            for (int x = 0; x < largura; x++) {
                System.out.print(nodos[x][y] + " ");
            }
            System.out.println("");
        }
    }

}
