package rsa.user;

import rsa.RideSharingAppException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Users instance = null;
    private Map<String, User> users = new HashMap<String, User>();
    private static File usersFile = new File("users.ser");

    public static Users getInstance() {
        if (instance == null) {
            instance = loadUsers();
            if (instance == null) {
                instance = new Users();
            }
        }
        return instance;
    }

    public static File getUsersFile() { return usersFile; }

    public static void setUsersFile(File file) { usersFile = file; }

    public User register(String nick, String name) {
        // exceptions if (nick == null || name == null)
        if (users.containsKey(nick)) {
            //throw exception
        }
        User newUser = new User(nick, name);
        users.put(nick, newUser);
        saveUsers();
        return newUser;
    }

    public User getUser(String nick) {
        return users.get(nick);
    }

    public User getOrCreateUser(String nick, String name) {
        User user = users.get(nick);
        if (user == null) {
            user = register(nick, name);
        }
        return user;
    }

    public boolean authenticate(String nick, String key) {
        User user = users.get(nick);
        return user!=null && user.authenticate(key);
    }

    public void reset() {
        users.clear();
        instance = null;
        if (usersFile.exists()) {
            usersFile.delete();
        }
    }



    //NEW METHODS, IMPLEMENT TESTS
    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(instance);
        }
        catch (IOException e) {
            throw new RuntimeException("Error saving users.", e);
        }
    }

    public static Users loadUsers() {
        if (!usersFile.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
            return (Users) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
