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
    private String imageURL;
    private String bio;

    public User()
    {

    }

    public User( String id, String username, String age, String imageURL, String bio)
    {
        this.id = id;
        this.username = username;
        this.age = age;
        this.imageURL = imageURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setPpURL(String ppURL)
    {
        this.imageURL = ppURL;
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