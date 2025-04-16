package rsa.match;

import rsa.shared.HasPoint;

public class Location implements HasPoint {
    private double x;
    private double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double x() {
        return 0;
    }

    @Override
    public double y() {
        return 0;
    }

    @Override
    public boolean equals(Object location1) {
        if (!(location1 instanceof Location)) return false;
        Location location2 = (Location) this;
        double errorMargin = 1e-6;
        return Math.abs(this.x - location2.x()) < errorMargin &&
                Math.abs(this.y - location2.y()) < errorMargin;
    }
}
