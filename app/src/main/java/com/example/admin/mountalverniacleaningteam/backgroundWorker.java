package com.example.admin.mountalverniacleaningteam;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**`
 * Created by admin on 10/8/2017.
 */

public class backgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
//    InputStream is =null;
//    String line = null;
//    String result = null;
    String initAddress = Address.getAddress();


    backgroundWorker(Context ctx){

        context = ctx;
    }



    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
//        String initAddress ="http://weijietest.000webhostapp.com/Testing/";
        //Start Button
        if (type.equals("start")) {
            try {
                String insert_url = initAddress+"updateStatus.php";
                String id = params[1];
                String bednum = params[2];

                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")
                        + "&" + URLEncoder.encode("bednum", "UTF-8") + "=" + URLEncoder.encode(bednum, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //End Button
       else if (type.equals("end")) {
            try {
                String insert_url1 = initAddress+"updateStatus2.php";
                String id = params[1];
                String bednum = params[2];
                String isostatus = params[3];
                URL url = new URL(insert_url1);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")
                        + "&" + URLEncoder.encode("bednum", "UTF-8") + "=" + URLEncoder.encode(bednum, "UTF-8")
                        + "&" + URLEncoder.encode("iso", "UTF-8") + "=" + URLEncoder.encode(isostatus, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
//        alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Card Detected");
    }

//    @Override
    protected  void onPostExecute(String result) {
//        alertDialog.setMessage(result);
//        alertDialog.show();

//        Toast.makeText(this.context, "Started Cleaning", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
