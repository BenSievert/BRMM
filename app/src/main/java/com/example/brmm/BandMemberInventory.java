package com.example.brmm;

import java.util.ArrayList;

public class BandMemberInventory {

    private ArrayList<BandMember> bandMembers;


    public void clearInventory()
    {
        bandMembers.clear();
    }
    public void removeBandMember(BandMember newBM)
    {
        bandMembers.remove(newBM);
        //update db
    }
    public void addBandMember(BandMember newBM)
    {
        bandMembers.add(newBM);
        //update db
    }

}