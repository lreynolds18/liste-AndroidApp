package com.example.lucas.liste;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ShoppingListActivity extends AppCompatActivity {

    private Button mainActivityButton;
    private ArrayList<String> values;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        values = new ArrayList<>();
        rv = findViewById(R.id.shopping_list_rv);
        getCrypto();
        setContentView(R.layout.activity_shopping_list);

        mainActivityButton = findViewById(R.id.btn_main_activity);
        mainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingListActivity.this, MainActivity.class));
            }
        });
    }

    public void callUpdate() {
        for (int i=0; i<values.size(); i++) {
            System.out.println(values.get(i));
        }
    }

    public void getCrypto() {
        HttpUtils.get("listings/", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray data = response.getJSONArray("data");

                    for (int i=0; i<data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        values.add(item.get("name").toString());
                    }
                } catch (JSONException e) {
                    System.out.println("ERROR");
                };
                callUpdate();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
