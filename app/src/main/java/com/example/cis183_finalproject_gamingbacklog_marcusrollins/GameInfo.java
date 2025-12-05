package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

public class GameInfo
{
    private String gameName;
    private String systemName;
    private String status; //This is for if a game is marked as main / completed / backlog
    private int earnedXp; //If the game isn't complete, then it = 0

    public GameInfo(String gameName, String systemName, String status, int xp)
    {
        this.gameName = gameName;
        this.systemName = systemName;
        this.status = status;
        this.earnedXp = xp;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getEarnedXp()
    {
        return earnedXp;
    }

    public void setEarnedXp(int earnedXp)
    {
        this.earnedXp = earnedXp;
    }
}
