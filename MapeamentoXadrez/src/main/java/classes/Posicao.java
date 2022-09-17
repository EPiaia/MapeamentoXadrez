package classes;

import java.util.Objects;

/**
 *
 * @author Eduardo Piaia
 */
public class Posicao {

    private Integer posX;
    private Integer posY;

    public Posicao() {
    }

    public Posicao(Integer posX, Integer posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    @Override
    public String toString() {
        return "(" + posX + ", " + posY + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.posX);
        hash = 29 * hash + Objects.hashCode(this.posY);
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
        final Posicao other = (Posicao) obj;
        if (!Objects.equals(this.posX, other.posX)) {
            return false;
        }
        return Objects.equals(this.posY, other.posY);
    }
}
