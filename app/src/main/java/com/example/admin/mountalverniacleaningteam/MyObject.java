package com.example.admin.mountalverniacleaningteam;

/**
 * Created by admin on 1/21/2018.
 */

public class MyObject {

    String idDataStr = null;
    String bedNumDataStr = null;
    String wardNumDataStr = null;
    String statusStr = null;
    String patientLeaveTimeStr = null;
    String startCleaningTimeStr = null;
    String endcCleaningTimeStr = null;
    String isoStatusstr = null;

    public MyObject(String IDS, String BNDS, String WNDS,String SS,String PLTS, String SCTS, String ECTS, String iss) {
        idDataStr = IDS;
        bedNumDataStr = BNDS;
        wardNumDataStr = WNDS;
        statusStr = SS;
        patientLeaveTimeStr = PLTS;
        startCleaningTimeStr = SCTS;
        endcCleaningTimeStr = ECTS;
        isoStatusstr = iss;
    }
}

