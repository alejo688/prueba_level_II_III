package com.alejo688.prueba_level_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alejo688.prueba_level_1.controlador.ArtistController;
import com.alejo688.prueba_level_1.modelo.Artist;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetalleArtistaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_artista);

        setTitle(getString(R.string.ArtistaTitle));

        final Context context = this;
        final ImageView imageView = findViewById(R.id.Imagen);
        final TextView artista = findViewById(R.id.Artista);
        final TextView escuchado = findViewById(R.id.Escuchado);
        final TextView url = findViewById(R.id.url);
        final RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        final String Mbid = intent.getStringExtra("mbid");
        final Artist[] artist = new Artist[1];
        final Toast msg = Toast.makeText(this, R.string.sin_resultados, Toast.LENGTH_LONG);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url_top_artista), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String ListsJson;
                        String id;
                        String imageJson;
                        String size;
                        String image;

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());

                            String TopArtists = jsonObject.getString("topartists");
                            JSONObject jsonTopArtists = new JSONObject(TopArtists);
                            ListsJson = jsonTopArtists.getString("artist");
                            JSONArray jsonArtists = new JSONArray(ListsJson);

                            for (int i = 0; i < jsonArtists.length(); i++) {
                                JSONObject jsonDetail = jsonArtists.getJSONObject(i);

                                id = jsonDetail.getString("mbid");

                                if (id.equals(Mbid)) {
                                    imageJson = jsonDetail.getString("image");
                                    JSONArray jsonImage = new JSONArray(imageJson);

                                    for (int j = 0; j < jsonImage.length(); j++) {
                                        JSONObject jsonImageDetail = jsonImage.getJSONObject(j);

                                        Log.e("json", jsonImageDetail.toString());

                                        size = jsonImageDetail.getString("size");
                                        if (size.equals("large")) {

                                            image = jsonImageDetail.getString("#text");

                                            Log.e("image", image);

                                            Picasso.get().load(image).into(imageView);

                                            break;
                                        }
                                    }

                                    artist[0] = gson.fromJson(jsonDetail.toString(), Artist.class);

                                    Log.e("json", jsonDetail.toString());

                                    artista.setText(artist[0].name);
                                    escuchado.setText(getString(R.string.label_veces_escuchado) + artist[0].listeners);
                                    url.setText(artist[0].url);

                                    break;
                                }
                            }

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
    }


}
