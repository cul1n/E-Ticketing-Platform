package service;

import model.*;

public class UserService {

    private ReaderService readerService = ReaderService.getInstance();

    private static UserService INSTANCE;
    private UserService() { }

    public static UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }

    public void Initialization(Platform platform) {
        String fileText = readerService.readFile("src/csvfiles/Users.csv");
        String[] usersText = fileText.split("\n");
        for(String userText : usersText) {
            String[] userComponents = userText.split(",");
            User user = new User(userComponents[1],userComponents[2],userComponents[3]);
            register(platform, user);
            user.setFunds(Double.parseDouble(userComponents[4]));
            user.setAdmin(Boolean.parseBoolean(userComponents[5]));

        }
    }

    public void register(Platform platform, User user){
        platform.getUsers().add(user);
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
