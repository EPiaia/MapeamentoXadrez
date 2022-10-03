
import classes.Nodo;
import classes.Posicao;
import classes.Tarefa;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.MapeamentoUtil;
import utils.Pecas;

/**
 *
 * @author Eduardo Piaia
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        // Percorrer a matriz com o eixo X na horizontal e eixo Y na vertical, com o 0,0 sendo o canto inferior esquerdo
        // Sortear cor para pegar a direção para se mover (baixo ou cima), a direção horizontal quando houver e diagonal quando houver
//        for (int y = altura - 1; y >= 0; y--) {
//            for (int x = 0; x < largura; x++) {
//                nodos[x][y] = "0";
//            }
//        }

        String caminhoJson = "TrabalhoMapeamento/Test1.json";

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(caminhoJson));
        JSONObject json = (JSONObject) obj;
        int largura = Integer.parseInt(json.get("MPSOC_SIZE_X").toString());
        int altura = Integer.parseInt(json.get("MPSOC_SIZE_Y").toString());
        int tarefasProc = Integer.parseInt(json.get("TASKS_PER_PROCESSOR").toString());

        JSONArray testes = (JSONArray) json.get("TEST");
        Iterator<JSONObject> testeIt = testes.iterator();

        while (testeIt.hasNext()) {
            Nodo[][] nodos = new Nodo[largura][altura];
            for (int y = altura - 1; y >= 0; y--) {
                for (int x = 0; x < largura; x++) {
                    nodos[x][y] = new Nodo(new Posicao(x, y));
                }
            }

            for (int y = altura - 1; y >= 0; y--) {
                for (int x = 0; x < largura; x++) {
                    Nodo nodo = nodos[x][y];
                    if (x > 0) {
                        nodo.setNodoEsquerda(nodos[x - 1][y]);
                    }
                    if (x < largura - 1) {
                        nodo.setNodoDireita(nodos[x + 1][y]);
                    }
                    if (y > 0) {
                        nodo.setNodoBaixo(nodos[x][y - 1]);
                    }
                    if (y < altura - 1) {
                        nodo.setNodoCima(nodos[x][y + 1]);
                    }
                }
            }

            Set<String> tarefasGrafo = new HashSet<>();
            JSONObject test = testeIt.next();
            int qtdVezes = Integer.parseInt(test.get("QTD").toString());

            String app = test.get("APP").toString();
            System.out.println("------------- INÍCIO MAPEAMENTO " + app + " -------------");
            String caminhoJsonApp = "TrabalhoMapeamento/Applications/" + app + ".json";

            JSONParser parserApp = new JSONParser();
            Object objApp = parserApp.parse(new FileReader(caminhoJsonApp));
            JSONObject jsonApp = (JSONObject) objApp;
            JSONArray tarefasApp = (JSONArray) jsonApp.get("grafo_tarefas");
            Iterator<JSONObject> tarefasIt = tarefasApp.iterator();

            MapeamentoUtil.printarMatriz(nodos, tarefasProc, null, null);

            while (tarefasIt.hasNext()) {
                JSONObject tarefaApp = tarefasIt.next();
                tarefasGrafo.add(tarefaApp.get("tarefa_origem").toString());
                tarefasGrafo.add(tarefaApp.get("tarefa_destino").toString());
            }

            boolean primeira = true;

            List<Tarefa> tarefasCriadas = new ArrayList<>();
            for (int i = 1; i <= qtdVezes; i++) {
                for (String tarefaGrafo : tarefasGrafo) {
                    Tarefa tarefa = new Tarefa(tarefaGrafo + "x" + i);
                    if (primeira) {
                        tarefa.setPeca(Pecas.PEAO);
                        primeira = false;
                    } else {
                        tarefa.setPeca(Pecas.getPecaAleatoria());
                    }
                    tarefasCriadas.add(tarefa);
                }
            }

            System.out.println("");
            System.out.println(tarefasCriadas.size() + " tarefa(s) para mapear: ");
            for (Tarefa tarefaCriada : tarefasCriadas) {
                System.out.println(tarefaCriada.getIdentificacao());
            }
            System.out.println("");

            primeira = true;
            Tarefa tarefaAnterior = null;
            for (Tarefa tarefaCriada : tarefasCriadas) {
                if (primeira) {
                    primeira = false;
                    tarefaCriada.setNodo(nodos[0][0]);
                    tarefaCriada.atribuirTarefaANodo(nodos, 1, tarefasProc);
                } else {
                    tarefaCriada.setNodo(tarefaAnterior.getNodo());
                    tarefaCriada.atribuirTarefaANodo(nodos, 2, tarefasProc);
                }
                tarefaAnterior = tarefaCriada;
            }
            System.out.println("------------- FIM MAPEAMENTO " + app + " -------------");
            System.out.println("");

            System.out.println("------------- INÍCIO TESTE " + app + " -------------");
            System.out.println("");
            tarefasIt = tarefasApp.iterator();

            while (tarefasIt.hasNext()) {
                JSONObject tarefaApp = tarefasIt.next();
                Tarefa tarefaOrigem = MapeamentoUtil.getTarefa(tarefaApp.get("tarefa_origem").toString(), nodos);
                Tarefa tarefaDestino = MapeamentoUtil.getTarefa(tarefaApp.get("tarefa_destino").toString(), nodos);

                System.out.println("Enviando mensagem de " + tarefaOrigem.getIdentificacao() + " (" + tarefaOrigem.getNodo().getPosicao().getPosX() + ", "
                        + tarefaOrigem.getNodo().getPosicao().getPosY() + ") para " + tarefaDestino.getIdentificacao() + " ("
                        + tarefaDestino.getNodo().getPosicao().getPosX() + ", " + tarefaDestino.getNodo().getPosicao().getPosY() + ")");

                MapeamentoUtil.enviarMensagem(nodos, tarefaOrigem, tarefaDestino);
            }

        }
    }
}
