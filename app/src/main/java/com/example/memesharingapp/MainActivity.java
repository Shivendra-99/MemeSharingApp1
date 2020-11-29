package com.example.memesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    String url1;
    ProgressBar pro;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        image=(ImageView)findViewById(R.id.imageView);
        pro=(ProgressBar)findViewById(R.id.pro);
        loadImage();
    }
    private void loadImage() {
        RequestQueue queue=Volley.newRequestQueue(this);
        String url ="https://meme-api.herokuapp.com/gimme";
          JsonObjectRequest json= new JsonObjectRequest(Request.Method.GET,url,null ,new Response.Listener<JSONObject>() {
              public void onResponse(JSONObject response) {
                  try {
                       url1=response.getString("url");
                     Glide.with(MainActivity.this).load(url1).into(image);
                     pro.setVisibility(View.INVISIBLE);
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  // TODO: Handle error

              }
          });
          queue.add(json);

    }
    public void Share(View view) {
        Intent b=new Intent(Intent.ACTION_SEND);
        b.setType("text/plain");
        b.putExtra(Intent.EXTRA_TEXT,url1);
        Intent l=Intent.createChooser(b,"Please click on this share");
        if(b.resolveActivity(getPackageManager())!=null) {
            startActivity(l);
        }
    }
    public void Next(View view) {
        loadImage();
        pro.setVisibility(View.VISIBLE);
    }
}