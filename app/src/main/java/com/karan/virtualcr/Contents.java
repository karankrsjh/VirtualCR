package com.karan.virtualcr;

import java.io.Serializable;

public class Contents implements Serializable {

    private int firstinteger;
    private int secondinteger;
    private int thirdinteger;
    private int fourthinteger;
    private String firstString;
    private String secondString;
    private String thirdString;
    private String fourthstring;

    public Contents (int firstinteger,int secondinteger,int thirdinteger,int fourthinteger, String firstString,String secondString,String thirdString,String fourthString)
    {
        this.firstinteger=firstinteger;
        this.secondinteger=secondinteger;
        this.thirdinteger=thirdinteger;
        this.fourthinteger=fourthinteger;
        this.firstString=firstString;
        this.secondString=secondString;
        this.thirdString=thirdString;
        this.fourthstring=fourthString;
    }
    public int getfirstinteger()
    {
        return firstinteger;
    }
    public int getsecondinteger()
    {
        return secondinteger;
    }
    public int getthirdinteger()
    {
        return thirdinteger;
    }
    public int getfourthinteger()
    {
        return fourthinteger;
    }
    public String getfirstString()

    {
        return firstString;
    }
    public String getsecondString()
    {
        return secondString;
    }
    public String getthirdstring()
    {
        return thirdString;
    }
    public String getFourthstring() {return fourthstring;}
}
