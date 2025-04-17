package rsa.match;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    double latitude, longitude;

    public Location(double x, double y) {
        this.longitude = x;
        this.latitude = y;
    }

    public double x() { return longitude; }
    public double y() { return latitude; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Location)) return false;
        Location other = (Location) obj;
        return Double.compare(latitude, other.latitude) == 0 &&
               Double.compare(longitude, other.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("Location(%.5f, %.5f)", latitude, longitude);
    }
}
