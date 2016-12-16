package com.adnet.mytraffictestapp.datatyple;

public class Level_Info {

    //private variables
    int _MainLevel;
    int _SubLevel;
    int _ProblemNo;
    int _Answer;
    String _Description = "";
    // Empty constructor
    public Level_Info(){
        super();
    }
    // constructor
    public Level_Info(int MainLevel, int SubLevel, int ProblemNo, int Answer, String Description){
        _MainLevel = MainLevel;
        _SubLevel = SubLevel;
        _ProblemNo = ProblemNo;
        _Answer = Answer;
        _Description = Description;

    }
    // getting ID
    public int get_MainLevel(){
        return _MainLevel;
    }
    // setting id
    public void set_MainLevel(int id){
        _MainLevel = id;
    }
    public int get_SubLevel(){ return _SubLevel;}
    public void set_SubLevel(int level){
        _SubLevel = level;
    }
    public int get_ProblemNo(){ return _ProblemNo;}
    public void set_ProblemNo(int gpa){
        _ProblemNo = gpa;
    }
    public int get_Answer(){ return _Answer;}
    public void set_Answer(int institude){
        _Answer = institude;
    }

    public String get_Description(){
        return _Description;
    }
    public void set_Description(String str){
        _Description = str;
    }
}