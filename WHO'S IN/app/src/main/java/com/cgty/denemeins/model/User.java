package com.cgty.denemeins.model;

/**
 * User model class
 * @author Cagatay Safak
 * @version 1.0
 */
public class User
{
    private String id;
    private String username;
    private String age;
    private String ppURL;
    private String bio;

    public User()
    {

    }

    public User( String id, String username, String age, String ppURL, String bio)
    {
        this.id = id;
        this.username = username;
        this.age = age;
        this.ppURL = ppURL;
        this.bio = bio;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getPpURL() {
        return ppURL;
    }

    public void setPpURL(String ppURL)
    {
        this.ppURL = ppURL;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }
}