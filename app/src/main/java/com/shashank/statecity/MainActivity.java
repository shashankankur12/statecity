package com.shashank.statecity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItemsOnSpinner();
        addListenerOnButton();

    }
    public void addItemsOnSpinner() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        String list=loadJSONFromAsset() ;
        ArrayList<String> stateList=new ArrayList<String>();
        ArrayList<String> districtsList=new ArrayList<String>();
        try {
            JSONObject states=new JSONObject(loadJSONFromAsset());
            JSONArray state=states.getJSONArray("states");
            for (int i = 0; i < state.length(); i++) {
                JSONObject stateName = state.getJSONObject(i);
                stateList.add(stateName.getString("state"));
                JSONArray districts = stateName.getJSONArray("districts");
                for (int j = 0; j < districts.length(); j++){
                    districtsList.add(districts.getString(j));
                    String districtsName = districts.getString(j);
                   //districtsList.add(districtsName);
                    Map<Integer, String> districtsHashmap = new HashMap<>();

               Log.d("Details-->", "districts"+districtsName);
                    }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,stateList );
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(stateAdapter);
        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,districtsList );
        districtsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(districtsAdapter);
    }


    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Result.class);
                i.putExtra("state", String.valueOf(spinner1.getSelectedItem()));
                i.putExtra("districts", String.valueOf(spinner2.getSelectedItem()));

                startActivity(i);

              /*  Toast.makeText(MainActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();*/
            }

        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is =getResources().getAssets().open("state.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
