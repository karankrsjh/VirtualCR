package com.karan.virtualcr;

import java.io.Serializable;
import java.util.ArrayList;

public class group_details_models implements Serializable{

    private String Group_name;
    private String Group_lastchat;
    private String lastchat_time;
    private String Group_logo;
    private ArrayList<Contents> GroupMembers;
    private String Group_admin;
    public group_details_models()
    {

    }
    public group_details_models(String Group_name,String Group_lastchat,String lastchat_time,String Group_logo,ArrayList<Contents> GroupMembers,String Group_admin)
    {
        this.Group_name=Group_name;
        this.Group_lastchat=Group_lastchat;
        this.lastchat_time=lastchat_time;
        this.Group_logo=Group_logo;
        this.GroupMembers=GroupMembers;
        this.Group_admin=Group_admin;
    }

    public String getGroup_name()

    {
        return Group_name;
    }
    public String getGroup_lastchat()
    {
        return Group_lastchat;
    }
    public String getLastchat_time()
    {
        return lastchat_time;
    }
    public String getGroup_logo()
    {
        return Group_logo;
    }
    public String getGroupadmin()
    {
        return Group_admin;
    }
    public ArrayList<Contents> getGroupMembers()
    {
        return GroupMembers;
    }

    public void setGroup_name(String Group_name)

    {
        this.Group_name=Group_name;
    }
    public void setGroup_lastchat(String Group_lastchat)
    {
        this.Group_lastchat=Group_lastchat;
    }
    public void setLastchat_time(String lastchat_time)
    {
        this.lastchat_time=lastchat_time;
    }
    public void setGroup_logo(String Grouplogo)
    {
        this.Group_logo=Grouplogo;
    }
    public void setGroup_admin(String Group_admin)

    {
        this.Group_admin=Group_admin;
    }
    public void setGroupMembers(ArrayList<Contents> Groupmembers)
    {
        this.GroupMembers=Groupmembers;
    }

}
