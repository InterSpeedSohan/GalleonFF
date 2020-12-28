package com.example.galleonff.model;

import android.content.SharedPreferences;

public class User {
    String name, teamId, userId, area, teamName, userType;
    static User user;
    private User(String name,String teamId,String userId, String area, String teamName, String userType)
    {
        this.name = name;
        this.teamId = teamId;
        this.userId = userId;
        this.area = area;
        this.teamName = teamName;
        this.userType = userType;
    }
    private User()
    {

    }
    public static User getInstance()
    {
        if(user == null)
        {
            user = new User();
        }
        return user;
    }
    public void setValuesFromSharedPreference(SharedPreferences sharedPreference)
    {
        this.name = sharedPreference.getString("name",null);
        this.teamId = sharedPreference.getString("id",null);
        this.userId = sharedPreference.getString("id",null);
        this.area = sharedPreference.getString("area",null);
        this.teamName = sharedPreference.getString("team",null);
        this.userType = sharedPreference.getString("user_type",null);
    }
    public static User createInstance(String name,String teamId,String userId,String area, String teamName, String userType)
    {
        if(user == null)
        {
            user = new User(name, teamId, userId,area, teamName, userType);
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(SharedPreferences.Editor editor,String id, String name) {
        this.name = name;
        saveToSharepreference(editor, id, name);
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(SharedPreferences.Editor editor,String id, String userType) {
        this.userType = userType;
        saveToSharepreference(editor,id,userType);
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(SharedPreferences.Editor editor,String id, String teamId) {
        this.teamId = teamId;
        saveToSharepreference(editor, id, teamId);
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(SharedPreferences.Editor editor,String id, String userId) {
        this.userId = userId;
        saveToSharepreference(editor, id, userId);
    }

    public String getArea() {
        return area;
    }

    public void setArea(SharedPreferences.Editor editor,String id,String area) {
        this.area = area;
        saveToSharepreference(editor, id, area);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(SharedPreferences.Editor editor,String id,String teamName) {
        this.teamName = teamName;
        saveToSharepreference(editor, id, teamName);
    }

    public void saveToSharepreference(SharedPreferences.Editor editor, String id, String value)
    {
        editor.putString(id, value);
        editor.apply();
    }

    public boolean isUserInSharedpreference(SharedPreferences sharedPreferences, String id)
    {
        if(sharedPreferences.contains(id))
        {
            return true;
        }
        return false;
    }

    public void clear(SharedPreferences.Editor editor)
    {
        editor.clear();
        editor.apply();
    }

}
