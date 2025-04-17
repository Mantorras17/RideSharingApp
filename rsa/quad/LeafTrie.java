package rsa.quad;

import mpjp.shared.HasPoint;
import rsa.match.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LeafTrie<T extends HasPoint> extends Trie<T> {

    private static int capacity = 4;

    public static int getCapacity() {
        return capacity;
    }

    public static void setCapacity(int c) {
        capacity = c;
    }

    private List<T> points = new ArrayList<>();

    public LeafTrie(double minX, double minY, double maxX, double maxY) {
        super(minX, minY, maxX, maxY);
    }

    @Override
    public Trie<T> insert(T point) {
        if (points.size() < capacity) {
            points.add(point);
            return this;
        }

        NodeTrie<T> node = new NodeTrie<>(minX, minY, maxX, maxY);
        for (T p : points) {
            node.insert(p);
        }
        node.insert(point);
        return node;
    }

    @Override
    public boolean remove(T point) {
        return points.remove(point);
    }

    @Override
    public T find(T point) {
        for (T p : points)
            if (p.x() == point.x() && p.y() == point.y())
                return p;
        return null;
    }

    @Override
    public void collect(List<T> out) {
        out.addAll(points);
    }

    @Override
    public void collectNear(Location center, double radius, List<T> out) {
        double radius2 = radius * radius;
        for (T p : points) {
            double dx = center.x() - p.x();
            double dy = center.y() - p.y();
            if (dx * dx + dy * dy <= radius2)
                out.add(p);
        }
    }
}
