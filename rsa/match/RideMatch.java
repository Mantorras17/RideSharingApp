package rsa.match;

import rsa.ride.Ride;
import rsa.ride.RideRole;
import rsa.user.User;
import rsa.user.Car;
import rsa.RideSharingAppException;

public class RideMatch {
    private Ride driverRide;
    private Ride passengerRide;

    public RideMatch(Ride left, Ride right) throws RideSharingAppException {
        if (left == null || right == null) {
            throw new RideSharingAppException("Ride or passenger ride is null");
        }
        if (!left.isDriver() || right.isDriver()) {
            throw new RideSharingAppException("Incompatible ride types.");
        }
        if (left.isDriver() || right.isPassenger()) {
            this.driverRide = left;
            this.passengerRide = right;
        }
        else {
            this.driverRide = right;
            this.passengerRide = left;
        }
    }

    public long getId() {
        return driverRide.getId();
    }

    public Ride getRide(RideRole role) {
        return (role == RideRole.DRIVER) ? driverRide : passengerRide;
    }

    public String getName(RideRole role) {
        return getRide(role).getUser().getName();
    }

    public float getStars(RideRole role) {
        return getRide(role).getUser().getAverage(role);
    }

    public Location getWhere(RideRole role) {
        return getRide(role).getCurrent();
    }

    public Car getCar() {
        return driverRide.getMatch().getCar();
    }

    public float getCost() {
        return driverRide.getMatch().getCost();
    }

    boolean matchable() {
        return driverRide.getRole() != passengerRide.getRole();
    }
}
