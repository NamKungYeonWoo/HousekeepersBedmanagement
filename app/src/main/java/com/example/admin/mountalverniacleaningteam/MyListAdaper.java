//package com.example.admin.mountalverniacleaningteam;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
///**
// * Created by admin on 1/21/2018.
// */
//
//
//public class MyListAdaper extends BaseAdapter {
//
//    private CleaningTeam cleaningTeam;
//    Context context;
//    private int layout;
//    LayoutInflater mInflater;
//    ArrayList<MyObject> listAdapter = new ArrayList<MyObject>();
//
//    public MyListAdaper(Context c, int resource, ArrayList<MyObject> list) {
////        this.cleaningTeam = cleaningTeam;
//        layout = resource;
//        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.listAdapter = list;
//    }
//
//    //Triggers the list update without refreshing the fragment
//    public void updateResults(ArrayList<MyObject> results) {
//        listAdapter.clear();
//        listAdapter.addAll(results);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//
//            //inflate layout
//
//            convertView = mInflater.inflate(layout,null);
//            //finding element of list_item layout
//            final Button startBtn = (Button) convertView.findViewById(R.id.startBtn);
//            TextView bednumTV = (TextView) convertView.findViewById(R.id.bedNumTV);
//            TextView timeTV = (TextView) convertView.findViewById(R.id.timeTV);
//            TextView remarksTV = (TextView) convertView.findViewById(R.id.RemarksTV);
//            TextView cleanTV = (TextView) convertView.findViewById(R.id.cleanTV);
//
//            String patientLeaveTimeString = null;
//            String startCleaningTimeString = null;
//            String endCleaningTimeString = null;
//            String P1 = null, S1 = null, E1 = null;
//            //Get all object variables
//            final String idDataString = listAdapter.get(position).idDataStr.toString();
//            final String bednumString = listAdapter.get(position).bedNumDataStr.toString();
//            final String spinnerValue = listAdapter.get(position).statusStr.toString();
//            final String isoStatusString = listAdapter.get(position).isoStatusstr.toString();
//            patientLeaveTimeString = listAdapter.get(position).patientLeaveTimeStr.toString();
//            startCleaningTimeString = listAdapter.get(position).startCleaningTimeStr.toString();
//            endCleaningTimeString = listAdapter.get(position).endcCleaningTimeStr.toString();
//
//            //Only get the HH:MM instead of whole Datetime
//            if (!patientLeaveTimeString.equals("null")) {
//                P1 = patientLeaveTimeString.substring(11, 16);
//            }
//            if (!startCleaningTimeString.equals("null")) {
//                S1 = startCleaningTimeString.substring(11, 16);
//            }
//            if (!endCleaningTimeString.equals("null")) {
//                E1 = endCleaningTimeString.substring(11, 16);
//            }
//            //set button to status string value, 0 = Cleaned, 1 = Patientleft,
//            // 2 = Startcleaning, 3 = IsoAiring
//            startBtn.setTag(spinnerValue);
//
//            //Set type to the correct name and icon
//            if(isoStatusString.equals("0")) {
//                remarksTV.setText("Discharge");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.home, 0, 0, 0);
//            } else if (isoStatusString.equals("1")||isoStatusString.equals("10")) {
//                remarksTV.setText("Toilet");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.toilet, 0, 0, 0);
//            } else if (isoStatusString.equals("2")) {
//                remarksTV.setText("Mopping");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mopping, 0, 0, 0);
//            } else if (isoStatusString.equals("3")) {
//                remarksTV.setText("Rubbish");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rubbishbin, 0, 0, 0);
//            } else if (isoStatusString.equals("4")) {
//                remarksTV.setText("Internal Transfer");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.internaltransfer, 0, 0, 0);
//            } else if (isoStatusString.equals("5")) {
//                remarksTV.setText("ISO Discharge");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.isodischarge, 0, 0, 0);
//            } else if (isoStatusString.equals("6")) {
//                remarksTV.setText("ISO Toilet");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.toilet, 0, 0, 0);
//            } else if (isoStatusString.equals("7")) {
//                remarksTV.setText("ISO Mopping");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mopping, 0, 0, 0);
//            } else if (isoStatusString.equals("8")) {
//                remarksTV.setText("ISO Rubbish");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rubbishbin, 0, 0, 0);
//            } else if (isoStatusString.equals("9")) {
//                remarksTV.setText("ISO Internal Transfer");
//                remarksTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.internaltransfer, 0, 0, 0);
//            }
//
//            //If patientleft, Check if it is normal or ISO cleaning.
//            // Normal background white, ISO red
//            if (spinnerValue.equals("1")) {
//                if(isoStatusString.equals("0")||isoStatusString.equals("1")
//                        ||isoStatusString.equals("2")||isoStatusString.equals("3")
//                        ||isoStatusString.equals("4")) {
//                    convertView.setBackgroundColor(Color.WHITE);
//                }
//                else if(isoStatusString.equals("5")||isoStatusString.equals("6")
//                        ||isoStatusString.equals("7")||isoStatusString.equals("8")
//                        ||isoStatusString.equals("9"))
//                {
//                    convertView.setBackgroundColor(Color.RED);
//                }
//                //Remove status, Set button, bednum, time
//                cleanTV.setVisibility(View.INVISIBLE);
//                startBtn.setText("Start");
//                bednumTV.setText(bednumString);
//                timeTV.setText(P1);
//
//                //If start cleaning, Set background color yellow
//            } else if (spinnerValue.equals("2")) {
//                convertView.setBackgroundColor(Color.YELLOW);
//                //remove status, set button, bednum, time
//                cleanTV.setVisibility(View.INVISIBLE);
//                startBtn.setText("End");
//                bednumTV.setText(bednumString);
//                timeTV.setText(S1);
//
//                //If End cleaning setbackground green
//            } else if (spinnerValue.equals("0")) {
//                convertView.setBackgroundColor(Color.GREEN);
//                //remove button set status , bednum, time
//                startBtn.setVisibility(View.INVISIBLE);
//                cleanTV.setText("Cleaned");
//                bednumTV.setText(bednumString);
//                timeTV.setText(E1);
//            }
//            //If ISO Airing, setbackground orange
//            else if (spinnerValue.equals("3")) {
//                convertView.setBackgroundColor(Color.parseColor("#FEBF3A"));
//                //remove button, set status, bednum, time
//                startBtn.setVisibility(View.INVISIBLE);
//                cleanTV.setText("ISO AIRING");
//                bednumTV.setText(bednumString);
//                timeTV.setText(E1);
//            }
//
//            // button onclick
//            startBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Start Cleaning
//                    if (startBtn.getTag().equals("1")) {
//                        String type = "start";
//                        backgroundWorker BackgroundWorker = new backgroundWorker(context);
//                        BackgroundWorker.execute(type, idDataString, bednumString);
////                                try {
////                                    TimeUnit.MILLISECONDS.sleep(500);
////                                } catch (InterruptedException e) {
////                                    e.printStackTrace();
////                                }
//                        Toast.makeText(context, "Started Cleaning", Toast.LENGTH_SHORT).show();
//                        cleaningTeam.onRefresh();
//                    }
//                    //End Cleaning
//                    else if (startBtn.getTag().equals("2")) {
//                        String type = "end";
//                        backgroundWorker BackgroundWorker = new backgroundWorker(context);
//                        BackgroundWorker.execute(type, idDataString, bednumString,isoStatusString);
////
////                                try {
////                                    TimeUnit.MILLISECONDS.sleep(500);
////                                } catch (InterruptedException e) {
////                                    e.printStackTrace();
////                                }
//                        Toast.makeText(context, "Ended Cleaning", Toast.LENGTH_SHORT).show();
//                        cleaningTeam.onRefresh();
//                    }
//                }
//            });
//
//
//
//
//        return convertView;
//    }
//
//    @Override
//    public int getCount() {
//        return listAdapter.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return listAdapter.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//}