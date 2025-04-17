package rsa.quad;

import java.io.Serializable;
import java.util.List;

import rsa.match.Location;
import mpjp.shared.HasPoint;

public abstract class Trie<T extends HasPoint> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected double minX, minY, maxX, maxY;

    public Trie(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public abstract Trie<T> insert(T point);

    public abstract boolean remove(T point);

    public abstract T find(T point);

    public abstract void collect(List<T> points);

    public abstract void collectNear(Location center, double radius, List<T> points);

    public boolean overlaps(Location center, double radius) {
        double x = center.x();
        double y = center.y();

        // distancia do ponto ao retângulo (0 se está dentro)
        double dx = Math.max(Math.max(minX - x, 0), x - maxX);
        double dy = Math.max(Math.max(minY - y, 0), y - maxY);

        return (dx * dx + dy * dy) <= radius * radius;
    }
}
