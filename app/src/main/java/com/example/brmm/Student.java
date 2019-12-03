package com.example.brmm;

import java.util.ArrayList;

public class Student extends BandMember {
    private String section;
    private boolean sectionLeader;
    private String notes;
    private ArrayList<Rentable> instruments;

    public ArrayList<Rentable> getInstruments() {
        return instruments;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean isSectionLeader() {
        return sectionLeader;
    }

    public void setSectionLeader(boolean sectionLeader) {
        this.sectionLeader = sectionLeader;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Student(){

    }

    public Student(String firstname, String lastname, String ulid, String section, boolean sectionLeader, int UID, String notes){
        setFname(firstname);
        setLname(lastname);
        setUlid(ulid);
        this.section = section;
        this.sectionLeader = sectionLeader;
        setUID(UID);
        this.notes = notes;
    }

}
