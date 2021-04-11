package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponsAsync extends  AsyncTask<JSONArray,JSONArray,JSONArray>{

//    /0411更新
    View result_view;
    ListView lvResult;
    Context con;


    public HttpResponsAsync(View view,Context context){
        result_view = view;
        con = context;

    }


    @Override
    protected JSONArray doInBackground(JSONArray... jsonArrays) {

        HttpURLConnection con = null;
        URL url = null;
        String urlSt = "https://api.syosetu.com/novelapi/api/?out=json?order=yearlypoint";

        JSONArray jarray = null;
        JSONObject json_object = null;

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

//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//            String str = bufferedReader.readLine();
//
//            JSONObject json = new JSONObject(str);

            String readSt = readInputStream(in);

//            jarray = new JSONArray(readSt);
//              json_object = new JSONObject(readSt).getJSONObject("allcount");
              jarray = new JSONObject(readSt).getJSONArray("allcount");

            String title = json_object.getString("title");
            String writer = json_object.getString("writer");

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

        List<Map<String,String>> menuList = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){

            try {
                JSONObject json = jsonArray.getJSONObject(i);
                String title = json.getString("title");
                String writer = json.getString("writer");
                String story = json.getString("story");

                lvResult = result_view.findViewById(R.id.lvResult);


                Map<String,String> menu = new HashMap<>();
                menu.put("title",title);
                menu.put("writer",writer);
                menuList.add(menu);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        String[] from = {"title","writer"};
        int[] to = {android.R.id.text1,android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(con,menuList,
                android.R.layout.simple_list_item_2,from,to);

        lvResult.setAdapter(adapter);

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
