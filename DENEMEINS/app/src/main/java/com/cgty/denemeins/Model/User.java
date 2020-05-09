package com.cgty.denemeins.Model;

public class User
{
    private String id;
    private String username;
    private String nameSurname;
    private String ppURL;
    private String bio;

    public User()
    {

    }

    public User( String id, String username, String nameSurname, String ppURL, String bio)
    {
        this.id = id;
        this.username = username;
        this.nameSurname = nameSurname;
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

    public String getNameSurname()
    {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname)
    {
        this.nameSurname = nameSurname;
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
