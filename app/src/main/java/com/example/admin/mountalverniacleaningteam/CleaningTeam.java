package com.example.admin.mountalverniacleaningteam;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CleaningTeam.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CleaningTeam#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CleaningTeam extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String initAddress =null;

    InputStream is = null;
    String line = null;
    String result = null;

    //for display beds
    String[] idData = new String[0];
    String[] bedNumData = new String[0];
    String[] wardNumData = new String[0];
    String[] status = new String[0];
    String[] patientLeaveTime = new String[0];
    String[] startCleaningTime = new String[0];
    String[] endcCleaningTime = new String[0];
    String[] isoStatus = new String[0];

    ListView lv;
    SwipeRefreshLayout swipeRefreshLayout;

    TableRow row;

    ArrayList<MyObject> list;

    MyListAdaper myListAdaper;

    //Swipe refresh method
    @Override
    public  void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getData();
        ArrayList<MyObject> refreshlist = new ArrayList<MyObject>();
        for(int i = 0; i < idData.length; i++) {
            MyObject myObject = new MyObject(idData[i],bedNumData[i],wardNumData[i],status[i],patientLeaveTime[i],startCleaningTime[i],endcCleaningTime[i],isoStatus[i]);
            refreshlist.add(myObject);

        }
        if(lv != null) {
            lv.setAdapter(myListAdaper);
            myListAdaper.updateResults(refreshlist);
            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
        }

    }


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CleaningTeam() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CleaningTeam.
     */
    // TODO: Rename and change types and number of parameters
    public static CleaningTeam newInstance(String param1, String param2) {
        CleaningTeam fragment = new CleaningTeam();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
//    FragmentTransaction ft = getFragmentManager().beginTransaction();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View cleaningTab = inflater.inflate(R.layout.fragment_cleaning_team, container, false);
        //get address from String.xml
        initAddress = Address.getAddress();
        //Find Listview and Table row, set table row to gray
        lv = (ListView)cleaningTab.findViewById(R.id.listview);
        row = (TableRow)cleaningTab.findViewById(R.id.tableRow);
        row.setBackgroundColor(Color.GRAY);
        //find swipe refresh layout
        swipeRefreshLayout = (SwipeRefreshLayout)cleaningTab.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        //Get all beds and store in list
        getData();
        list = new ArrayList<MyObject>();
        for(int i = 0; i < idData.length; i++) {
            MyObject myObject = new MyObject(idData[i],bedNumData[i],wardNumData[i],status[i],
                    patientLeaveTime[i],startCleaningTime[i],endcCleaningTime[i],isoStatus[i]);
            list.add(myObject);
        }
        //pass list and layout to base adapter
        myListAdaper = new MyListAdaper(getActivity(),R.layout.list_item1 ,list);
        lv.setAdapter(myListAdaper);

        return cleaningTab;
    }

    private class MyListAdaper extends BaseAdapter {

        private int layout;
        LayoutInflater mInflater;
        ArrayList<MyObject> listAdapter = new ArrayList<MyObject>();

        private MyListAdaper(Context c, int resource, ArrayList<MyObject> list) {
            layout = resource;
            mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.listAdapter = list;
        }

        //Triggers the list update without refreshing the fragment
        public void updateResults(ArrayList<MyObject> results) {
            listAdapter.clear();
            listAdapter.addAll(results);
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

//            if(idData[0] != null ) {
                //inflate layout
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                convertView = inflater.inflate(layout, parent, false);
                //finding element of list_item layout
                final Button startBtn = (Button) convertView.findViewById(R.id.startBtn);
                TextView bednumTV = (TextView) convertView.findViewById(R.id.bedNumTV);
                TextView timeTV = (TextView) convertView.findViewById(R.id.timeTV);
                TextView remarksTV = (TextView) convertView.findViewById(R.id.RemarksTV);
                TextView cleanTV = (TextView) convertView.findViewById(R.id.cleanTV);

                String patientLeaveTimeString = null;
                String startCleaningTimeString = null;
                String endCleaningTimeString = null;
                String P1 = null, S1 = null, E1 = null;
                //Get all object variables
                final String idDataString = listAdapter.get(position).idDataStr.toString();
                final String bednumString = listAdapter.get(position).bedNumDataStr.toString();
                final String spinnerValue = listAdapter.get(position).statusStr.toString();
                final String isoStatusString = listAdapter.get(position).isoStatusstr.toString();
                patientLeaveTimeString = listAdapter.get(position).patientLeaveTimeStr.toString();
                startCleaningTimeString = listAdapter.get(position).startCleaningTimeStr.toString();
                endCleaningTimeString = listAdapter.get(position).endcCleaningTimeStr.toString();

                //Only get the HH:MM instead of whole Datetime
                if (!patientLeaveTimeString.equals("null")) {
                    P1 = patientLeaveTimeString.substring(11, 16);
                }
                if (!startCleaningTimeString.equals("null")) {
                    S1 = startCleaningTimeString.substring(11, 16);
                }
                if (!endCleaningTimeString.equals("null")) {
                    E1 = endCleaningTimeString.substring(11, 16);
                }
                //set button to status string value, 0 = Cleaned, 1 = Patientleft,
                // 2 = Startcleaning, 3 = IsoAiring
                startBtn.setTag(spinnerValue);

                //Set type to the correct name and icon
                if(isoStatusString.equals("0")) {
                    remarksTV.setText("Discharge");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.home, 0, 0, 0);
                } else if (isoStatusString.equals("1")) {
                    remarksTV.setText("Toilet");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.toilet, 0, 0, 0);
                } else if (isoStatusString.equals("2")) {
                    remarksTV.setText("Mopping");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mopping, 0, 0, 0);
                } else if (isoStatusString.equals("3")) {
                    remarksTV.setText("Rubbish");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rubbishbin, 0, 0, 0);
                } else if (isoStatusString.equals("4")) {
                    remarksTV.setText("Internal Transfer");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.internaltransfer, 0, 0, 0);
                } else if (isoStatusString.equals("5")) {
                    remarksTV.setText("ISO Discharge");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.isodischarge, 0, 0, 0);
                } else if (isoStatusString.equals("6")) {
                    remarksTV.setText("ISO Toilet");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.toilet, 0, 0, 0);
                } else if (isoStatusString.equals("7")) {
                    remarksTV.setText("ISO Mopping");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mopping, 0, 0, 0);
                } else if (isoStatusString.equals("8")) {
                    remarksTV.setText("ISO Rubbish");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rubbishbin, 0, 0, 0);
                } else if (isoStatusString.equals("9")) {
                    remarksTV.setText("ISO Internal Transfer");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.internaltransfer, 0, 0, 0);
                }
                else if (isoStatusString.equals("10")) {
                    remarksTV.setText("Staff Room");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.staffroom, 0, 0, 0);
                }
                else if (isoStatusString.equals("11")) {
                    remarksTV.setText("Nurse Counter");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.nursescounter, 0, 0, 0);
                }
                else if (isoStatusString.equals("12")) {
                    remarksTV.setText("Kitchen");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.kitchen, 0, 0, 0);
                }
                else if (isoStatusString.equals("13")) {
                    remarksTV.setText("Corridor");
                    remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.corridor, 0, 0, 0);
                }


            if(bednumString.equals("")) {
                bednumTV.setText("-");
            }
            else {
                bednumTV.setText(bednumString);
            }

            //If patientleft, Check if it is normal or ISO cleaning.
            // Normal background white, ISO red
                if (spinnerValue.equals("1")) {
                    if(isoStatusString.equals("0")||isoStatusString.equals("1")
                            ||isoStatusString.equals("2")||isoStatusString.equals("3")
                            ||isoStatusString.equals("4") ||isoStatusString.equals("10")
                            ||isoStatusString.equals("11") ||isoStatusString.equals("12")
                            ||isoStatusString.equals("13")) {
                        convertView.setBackgroundColor(Color.WHITE);
                    }
                    else if(isoStatusString.equals("5")||isoStatusString.equals("6")
                            ||isoStatusString.equals("7")||isoStatusString.equals("8")
                            ||isoStatusString.equals("9"))
                    {
                        convertView.setBackgroundColor(Color.RED);
                    }
                    //Remove status, Set button, bednum, time
                    cleanTV.setVisibility(View.INVISIBLE);
                    startBtn.setText("Start");
                    timeTV.setText(P1);

               //If start cleaning, Set background color yellow
                } else if (spinnerValue.equals("2")) {
                    convertView.setBackgroundColor(Color.YELLOW);
                    //remove status, set button, bednum, time
                    cleanTV.setVisibility(View.INVISIBLE);
                    startBtn.setText("End");
//                    bednumTV.setText(bednumString);
                    timeTV.setText(S1);

                //If End cleaning setbackground green
                } else if (spinnerValue.equals("0")) {
                    convertView.setBackgroundColor(Color.GREEN);
                    //remove button set status , bednum, time
                    startBtn.setVisibility(View.INVISIBLE);
                    cleanTV.setText("Cleaned");
//                    bednumTV.setText(bednumString);
                    timeTV.setText(E1);
                }
                //If ISO Airing, setbackground orange
                else if (spinnerValue.equals("3")) {
                    convertView.setBackgroundColor(Color.parseColor("#FEBF3A"));
                    //remove button, set status, bednum, time
                    startBtn.setVisibility(View.INVISIBLE);
                    cleanTV.setText("ISO AIRING");
//                    bednumTV.setText(bednumString);
                    timeTV.setText(E1);
                }

                // button onclick
                startBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Start Cleaning
                        if (startBtn.getTag().equals("1")) {
                            String type = "start";
                            backgroundWorker BackgroundWorker = new backgroundWorker(getActivity());
                            BackgroundWorker.execute(type, idDataString, bednumString);
                                try {
                                    TimeUnit.MILLISECONDS.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            Toast.makeText(getActivity(), "Started Cleaning", Toast.LENGTH_SHORT).show();
//                            onRefresh();
                            onRefresh();
                        }
                        //End Cleaning
                        else if (startBtn.getTag().equals("2")) {
                            String type = "end";
                            backgroundWorker BackgroundWorker = new backgroundWorker(getActivity());
                            BackgroundWorker.execute(type, idDataString, bednumString,isoStatusString);
//
                                try {
                                    TimeUnit.MILLISECONDS.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            Toast.makeText(getActivity(), "Ended Cleaning", Toast.LENGTH_SHORT).show();
                            onRefresh();
//                            onRefresh();
                        }
                        }
                    });

//            }



            return convertView;
        }

        @Override
        public int getCount() {
            return listAdapter.size();
        }

        @Override
        public Object getItem(int i) {
            return listAdapter.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
    }





    //Get all bed result
    private void getData()
    {
        String address = initAddress + "cleaningteam.php";
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);

            is = new BufferedInputStream(con.getInputStream());

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //Read content
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while((line=br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
            String firstLetter = result.substring(0,1);
            if(firstLetter.equals("<")) {

                idData[0] = null;
                status[0] = null;
                bedNumData[0] = null;
                wardNumData[0] = null;
                patientLeaveTime[0] = null;
                startCleaningTime[0] = null;
                endcCleaningTime[0] = null;
                isoStatus[0] = null;
                lv.setAdapter(null);
                return;

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //Parse JSON DATA

        try
        {
            JSONArray ja = new JSONArray(result);
            if(ja.length() == 0) {

            } else {
                JSONObject jo = null;

                idData = new String[ja.length()];
                status = new String[ja.length()];
                bedNumData = new String[ja.length()];
                wardNumData = new String[ja.length()];
                patientLeaveTime = new String[ja.length()];
                startCleaningTime = new String[ja.length()];
                endcCleaningTime = new String[ja.length()];
                isoStatus = new String[ja.length()];

                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    idData[i] = jo.getString("id");
                    status[i] = jo.getString("status");
                    bedNumData[i] = jo.getString("bednum");
                    wardNumData[i] = jo.getString("wardID");
                    patientLeaveTime[i] = jo.getString("patientLeaveTime");
                    startCleaningTime[i] = jo.getString("startCleaning");
                    endcCleaningTime[i] = jo.getString("endCleaning");
                    isoStatus[i] = jo.getString("cleanStatus");
                }
            }



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }




    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
