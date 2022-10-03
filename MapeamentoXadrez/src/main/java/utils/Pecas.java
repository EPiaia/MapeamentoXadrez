package utils;

import java.util.Random;

/**
 *
 * @author Eduardo Piaia
 */
public enum Pecas {
    PEAO {
        @Override
        public int getCodigo() {
            return 1;
        }

        @Override
        public String getNome() {
            return "Peão";
        }

        @Override
        public int[] getFormasMovimento() {
            return new int[]{Constantes.VERTICAL};
        }

        @Override
        public Integer getQtdCasasMovPrincipal(int rodada) {
            if (rodada == 1) {
                return 2;
            } else {
                return 1;
            }
        }

        @Override
        public Integer getQtdCasasMovSecundario() {
            return 0;
        }

    }, TORRE {
        @Override
        public int getCodigo() {
            return 2;
        }

        @Override
        public String getNome() {
            return "Torre";
        }

        @Override
        public int[] getFormasMovimento() {
            return new int[]{Constantes.VERTICAL, Constantes.HORIZONTAL};
        }

        @Override
        public Integer getQtdCasasMovPrincipal(int rodada) {
            return null;
        }

        @Override
        public Integer getQtdCasasMovSecundario() {
            return 0;
        }

    }, CAVALO {
        @Override
        public int getCodigo() {
            return 3;
        }

        @Override
        public String getNome() {
            return "Cavalo";
        }

        @Override
        public int[] getFormasMovimento() {
            return new int[]{Constantes.L};
        }

        @Override
        public Integer getQtdCasasMovPrincipal(int rodada) {
            return 2;
        }

        @Override
        public Integer getQtdCasasMovSecundario() {
            return 1;
        }

    }, BISPO {
        @Override
        public int getCodigo() {
            return 4;
        }

        @Override
        public String getNome() {
            return "Bispo";
        }

        @Override
        public int[] getFormasMovimento() {
            return new int[]{Constantes.DIAGONAL};
        }

        @Override
        public Integer getQtdCasasMovPrincipal(int rodada) {
            return null;
        }

        @Override
        public Integer getQtdCasasMovSecundario() {
            return 0;
        }

    }, RAINHA {
        @Override
        public int getCodigo() {
            return 5;
        }

        @Override
        public String getNome() {
            return "Rainha";
        }

        @Override
        public int[] getFormasMovimento() {
            return new int[]{Constantes.HORIZONTAL, Constantes.VERTICAL, Constantes.DIAGONAL};
        }

        @Override
        public Integer getQtdCasasMovPrincipal(int rodada) {
            return null;
        }

        @Override
        public Integer getQtdCasasMovSecundario() {
            return 0;
        }

    }, REI {
        @Override
        public int getCodigo() {
            return 6;
        }

        @Override
        public String getNome() {
            return "Rei";
        }

        @Override
        public int[] getFormasMovimento() {
            return new int[]{Constantes.HORIZONTAL, Constantes.VERTICAL, Constantes.DIAGONAL};
        }

        @Override
        public Integer getQtdCasasMovPrincipal(int rodada) {
            return 1;
        }

        @Override
        public Integer getQtdCasasMovSecundario() {
            return 0;
        }

    };

    public abstract int getCodigo();

    public abstract String getNome();

    public abstract int[] getFormasMovimento();

    public abstract Integer getQtdCasasMovPrincipal(int rodada);

    public abstract Integer getQtdCasasMovSecundario();

    public static Pecas getPecaPorCodigo(int codigo) {
        for (Pecas peca : values()) {
            if (peca.getCodigo() == codigo) {
                return peca;
            }
        }
        return null;
    }

    public static Pecas getPecaAleatoria() {
        int min = 1;
        int max = values().length;
        Random rnd = new Random();
        int codPeca = rnd.ints(min, max)
                .findFirst()
                .getAsInt();
        return getPecaPorCodigo(codPeca);
    }
}
