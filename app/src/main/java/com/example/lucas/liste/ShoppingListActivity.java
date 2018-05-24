package com.example.lucas.liste;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ShoppingListActivity extends AppCompatActivity
        implements RVAdapter.ItemClickListener {

    private Button mainActivityButton;
    private ArrayList<String> values;
    private RecyclerView rv;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        values = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.shopping_list_rv);
        getCrypto();

        mainActivityButton = (Button) findViewById(R.id.btn_main_activity);
        mainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingListActivity.this, MainActivity.class));
            }
        });

        // ArrayList<String> al = new ArrayList<>();
        // al.add("item1");
        // al.add("item2");
        // al.add("item3");

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RVAdapter(this, values);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void callUpdate() {
        adapter.setItems(values);
        // adapter.notifyDataSetChanged();
//        for (int i=0; i<values.size(); i++) {
//            System.out.println(values.get(i));
//        }
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
