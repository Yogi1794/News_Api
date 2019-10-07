package com.example.api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    Adapter adapter;
private List<Article> articles=new ArrayList<>();
    private String url="https://newsapi.org/v2/top-headlines?country=in&apiKey=e78c6b90617449b3a0c22e4c192cd31f&pageSize=100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNews();
        rv=findViewById(R.id.newsList);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setNestedScrollingEnabled(true);
        rv.setLayoutManager(layoutManager);

    }
    public void loadNews(){
        articles=new ArrayList<>();

        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Hello",response);
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray  jsonArray=object.getJSONArray("articles");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject wallObj = jsonArray.getJSONObject(i);
                        Article article=new Article();
                        article.setTitle(wallObj.getString("title"));
                        article.setPublishAt(wallObj.getString("publishedAt"));
                        article.setUrltoImage(wallObj.getString("urlToImage"));
                        articles.add(article);
                        adapter=new Adapter(articles,MainActivity.this);
                        rv.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(sr);
    }
}
