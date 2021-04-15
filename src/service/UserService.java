package service;

import model.*;

public class UserService {


    public void register(Platform platform, User user){
        int index = getNumberOfUsers(platform);
        platform.getUsers()[index] = user;
    }

    public boolean authentication(Platform platform, String username, String password){
        User user = findUser(platform, username);
        return user.getPassword().equals(password);
    }

    public User findUser(Platform platform, String username) {
        for(User user : platform.getUsers()){
            if(user != null && user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    private int getNumberOfUsers(Platform platform) {
        int number = 0;
        for(User user : platform.getUsers()){
            if(user != null)
                number++;
        }
        return number;
    }

    public void printUsers(Platform platform) {
        int index = 1;
        for (User user : platform.getUsers()) {
            if (user != null) {
                System.out.print(index + ") ");
                System.out.println(user);
                index++;
            }
        }
    }

}
