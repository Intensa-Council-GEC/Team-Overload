package com.example.fitgenie.Database;

public class User {
    String name;
    String email;
    Boolean setprofile;

    public User()
    {

    }

//    public User(String name, String email, Boolean setprofile) {
//        this.name = name;
//        this.email = email;
//        this.setprofile = setprofile;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSetprofile() {
        return setprofile;
    }

    public void setSetprofile(Boolean setprofile) {
        this.setprofile = setprofile;
    }
}
