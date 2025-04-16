package rsa.user;

import java.io.Serializable;

import rsa.ride.RideRole;
import rsa.match.PreferredMatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.prefs.PreferencesFactory;

public class User implements Serializable {
    private String nick;
    private String name;
    private String key;
    private Map<String,Car> cars;
    private PreferredMatch preferredMatch;
    private int totalStarsDriver;
    private int countDriver;
    private int totalStarsPassenger;
    private int countPassenger;

    User(String nick, String name) {
        this.nick = nick;
        this.name = name;
        this.cars = new HashMap<String,Car>();
        this.preferredMatch = PreferredMatch.BETTER;
        totalStarsDriver = 0;
        countDriver = 0;
        totalStarsPassenger = 0;
        countPassenger = 0;
    }

    public String getName() { return name;}

    public void setName(String name) { this.name = name; }

    public String getNick() { return nick; }

    public void setNick(String nick) { this.nick = nick; }

    String generateKey() {
        String toKey = nick + name;
        UUID uuid = UUID.nameUUIDFromBytes(toKey.getBytes());
        this.key = uuid.toString();
        return key;
    }

    public String getKey() {
        if (key == null) {
            return generateKey();
        }
        return key;
    }

    boolean authenticate(String testKey) {
        return getKey().equals(testKey);
    }

    public void addCar(Car car) {
        cars.put(car.getPlate(), car);
    }

    public Car getCar(String plate) {
        return cars.get(plate);
    }

    void deleteCar(String plate) {
        cars.remove(plate);
    }

    public void addStars(UserStars moreStars, RideRole role) {
        if (role == RideRole.DRIVER) {
            totalStarsDriver += moreStars.getStars();
            countDriver++;
        }
        else {
            totalStarsPassenger += moreStars.getStars();
            countPassenger++;
        }
    }

    public float getAverage(RideRole role) {
        if (role == RideRole.DRIVER) {
            return countDriver == 0 ? 0 : (float) totalStarsDriver / countDriver;
        }
        else {
            return countPassenger == 0 ? 0 : (float) totalStarsPassenger / countPassenger;
        }
    }

    public PreferredMatch getPreferredMatch() {
        return (preferredMatch == null) ? PreferredMatch.BETTER : preferredMatch;
    }
}