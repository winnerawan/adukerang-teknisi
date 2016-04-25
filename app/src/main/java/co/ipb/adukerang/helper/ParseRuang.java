package co.ipb.adukerang.helper;

/**
 * Created by winnerawan on 3/26/16.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.model.IDRuangan;

public class ParseRuang {
    String id_ruang,nama_ruang;

    public ParseRuang(){

    }
    public ParseRuang(String id_ruang,String nama_ruang){
        this.id_ruang=id_ruang;
        this.nama_ruang=nama_ruang;
    }
    public List<IDRuangan> getParseJsonWCF(String sName)
    {
        List<IDRuangan> ListData = new ArrayList<IDRuangan>();
        try {
            String temp=sName.replace(" ", "%20");
            URL js = new URL(AppConfig.URL_GET_ID_RUANG+temp);
            URLConnection jc = js.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            JSONObject jsonResponse = new JSONObject(line);
            JSONArray jsonArray = jsonResponse.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject r = jsonArray.getJSONObject(i);
                ListData.add(new IDRuangan(r.getString("nama_ruang"),r.getString("id_ruang")));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }

}