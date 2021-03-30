package service;

import model.*;

public class UserService {


    public void register(Platform platform, User user){
        int index = getNumberOfUsers(platform);
        platform.getUsers()[index] = user;
    }

    public boolean authentication(Platform platform, String username, String password){
        User u = findUser(platform, username);
        return u.getPassword().equals(password);
    }

    public User findUser(Platform platform, String username) {
        for(User u : platform.getUsers()){
            if(u != null && u.getUsername().equals(username))
                return u;
        }
        return null;
    }

    private int getNumberOfUsers(Platform platform) {
        int number = 0;
        for(User u : platform.getUsers()){
            if(u != null)
                number++;
        }
        return number;
    }

    public void printUsers(Platform platform) {
        int index = 1;
        for (User u : platform.getUsers()) {
            if (u != null) {
                System.out.print(index + ") ");
                System.out.println(u);
                index++;
            }
        }
    }

}
