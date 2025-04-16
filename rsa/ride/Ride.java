package rsa.ride;

import rsa.RideSharingAppException;
import rsa.match.Location;
import rsa.match.RideMatch;
import rsa.match.PreferredMatch;
import rsa.shared.HasPoint;
import rsa.user.User;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Comparator;

public class Ride implements HasPoint,RideMatchSorter {
    private static final AtomicLong idGenerator = new AtomicLong(0);
    private long id;
    private User user;
    private Location from;
    private Location to;
    private Location current;
    private float cost;
    private String plate;
    private RideMatch match;

    public Ride(User user, Location from, Location to, String plate, float cost) throws RideSharingAppException {
        if (user == null || from == null || to == null) {
            throw new RideSharingAppException("Invalid parameters for the ride.");
        }
        this.id = idGenerator.getAndIncrement();
        this.user = user;
        this.from = from;
        this.to = to;
        this.current = from;
        this.plate = plate;
        this.cost = cost;
    }

    public long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isDriver() { return plate != null; }
    public boolean isPassenger() { return !isDriver(); }

    public Location getFrom() { return from; }
    public void setFrom(Location from) { this.from = from; }

    public Location getTo() { return to; }
    public void setTo(Location to) { this.to = to; }

    public Location getCurrent() { return current; }
    public void setCurrent(Location current) { this.current = current; }

    public double x() { return current.x(); }
    public double y() { return current.y(); }

    public float getCost() { return cost; }
    public void setCost(float cost) { this.cost = cost; }

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public RideMatch getMatch() { return match; }
    public void setMatch(RideMatch match) { this.match = match; }
    public boolean isMatch() { return match != null; }

    public RideRole getRideRole() {
        return isDriver() ? RideRole.DRIVER : RideRole.PASSENGER;
    }

    @Override
    public Comparator<RideMatch> getComparator() {
        return (m1,m2) -> {
            switch (m1.getRide().getRideRole()) {
                case BETTER:
                    double avg1 = m1.getDriverRide().getUser().getAverage(RideRole.DRIVER);
                    double avg2 = m2.getDriverRide().getUser().getAverage(RideRole.DRIVER);
                    return Double.compare(avg1, avg2);
                case CHEAPER:
                    return Float.compare(m1.getDriverRide().getCost(), m2.getDriverRide().getCost());
                case CLOSER:
                    double d1 = distance(m1.getDriverRide().getCurrent(), m1.getDriverRide().getFrom());
                    double d2 = distance(m2.getDriverRide().getCurrent(), m2.getDriverRide().getFrom());
                    return Double.compare(d1, d2);
                default:
                    return 0;
            }
        };
    }

    //implement
    private double distance(Location a, Location b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
