package com.example.myapplication;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpResponsAsync extends  AsyncTask<JSONArray,JSONArray,JSONArray>{


    @Override
    protected JSONArray doInBackground(JSONArray... jsonArrays) {

        HttpURLConnection con = null;
        URL url = null;
        String urlSt = "https://api.syosetu.com/novelapi/api/?out=json?order=yearlypoint";

        JSONArray jarray = null;

        try{
            url = new URL(urlSt);

            con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("GET");

            con.setInstanceFollowRedirects(false);

            con.setDoInput(true);

            con.setDoOutput(true);

            //接続
            con.connect();

            //本文の取得
            InputStream in = con.getInputStream();
            String readSt = readInputStream(in);

            jarray = new JSONArray(readSt);


//
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jarray;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {

        for(int i = 0; i < jsonArray.length(); i++){

            try {
                JSONObject json = jsonArray.getJSONObject(i);
                String title = json.getString("title");
                String writer = json.getString("writer");
                String story = json.getString("story");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public String readInputStream(InputStream in ) throws IOException {
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while((st = br.readLine()) != null){
            sb.append(st);
        }
        try{
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return sb.toString();
    }


}
