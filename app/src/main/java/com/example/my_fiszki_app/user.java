package com.example.my_fiszki_app;

public class user {
    private int user_id;
    private String login;
    private String password;
    private int progress;

    public user(int user_id, String login, String password, Integer progress) {
        this.user_id = user_id;
        this.login = login;
        this.password = password;
        this.progress = progress;
    }


    public int get_user_id() { return user_id; }
    public  String get_login() { return login; }
    public String get_password() { return password; }
    public int get_progres() { return progress; }
}

