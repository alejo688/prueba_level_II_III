package com.alejo688.prueba_level_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alejo688.prueba_level_1.modelo.Item;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {
    private List<Item> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        final Context context = this;
        final ListView listView = findViewById(R.id.detalle);
        final RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        final int Categoria = intent.getIntExtra("Categoria", 0);
        String url = "";
        final Toast msg = Toast.makeText(this, R.string.sin_resultados, Toast.LENGTH_LONG);

        switch (Categoria) {
            case 1:
                setTitle(getString(R.string.titulo_top_artista));
                url = getString(R.string.url_top_artista);
                break;
            case 2:
                setTitle(getString(R.string.titulo_top_albumes));
                url = getString(R.string.url_top_album);
                break;
            default:
                msg.show();
                break;
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String ListsJson;

                        try {

                            Type type = new TypeToken<List<Item>>() {}.getType();

                            JSONObject jsonObject = new JSONObject(response.toString());

                            if (Categoria == 1) {
                                String TopArtists = jsonObject.getString("topartists");
                                JSONObject jsonTopArtists = new JSONObject(TopArtists);
                                ListsJson = jsonTopArtists.getString("artist");
                            } else {
                                String TopArtists = jsonObject.getString("tracks");
                                JSONObject jsonTopArtists = new JSONObject(TopArtists);
                                ListsJson = jsonTopArtists.getString("track");
                            }

                            items = gson.fromJson(ListsJson, type);

                            CategoriaAdapter adapter = new CategoriaAdapter(context, items);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        msg.show();
                        Log.e("JSON", error.toString());
                    }
                }
        );

        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Intent intent;

                if (Categoria == 1) {
                    intent = new Intent(CategoriaActivity.this, DetalleArtistaActivity.class);
                } else {
                    intent = new Intent(CategoriaActivity.this, DetalleAlbumActivity.class);
                }

                intent.putExtra("mbid", items.get(position).mbid);
                startActivity(intent);
            }
        });
    }
}
