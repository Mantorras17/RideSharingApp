package rsa.quad;

import mpjp.shared.HasPoint;
import rsa.match.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Representa uma quadtree que guarda objetos que têm um ponto (x,y),
 * isto é, que implementam HasPoint.
 */
public class PointQuadtree<T extends HasPoint> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    private Trie<T> root;
    private double minX, minY, maxX, maxY;

    public PointQuadtree(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        root = new LeafTrie<>(minX, minY, maxX, maxY);
    }

    public PointQuadtree(double width, double height) {
        this(0, 0, width, height);
    }

    public PointQuadtree(double width, double height, double margin) {
        this(-margin, -margin, width + margin, height + margin);
    }

    public void insert(T point) {
        if (!inside(point))
            throw new PointOutOfBoundException(point);

        root = root.insert(point); // substitui raiz se Leaf virar Node
    }

    public boolean remove(T point) {
        return root.remove(point);
    }

    public T find(T point) {
        return root.find(point);
    }

    public List<T> findNear(Location center, double radius) {
        List<T> result = new ArrayList<>();
        if (root.overlaps(center, radius))
            root.collectNear(center, radius, result);
        return result;
    }

    public boolean inside(HasPoint point) {
        return point.x() >= minX && point.x() < maxX &&
               point.y() >= minY && point.y() < maxY;
    }

    @Override
    public Iterator<T> iterator() {
        List<T> all = new ArrayList<>();
        root.collect(all);
        return all.iterator();
    }

    public int size() {
        List<T> all = new ArrayList<>();
        root.collect(all);
        return all.size();
    }
}
