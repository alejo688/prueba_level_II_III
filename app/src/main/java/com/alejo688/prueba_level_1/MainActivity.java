package com.alejo688.prueba_level_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alejo688.prueba_level_1.controlador.AlbumController;
import com.alejo688.prueba_level_1.controlador.ArtistController;
import com.alejo688.prueba_level_1.modelo.Album;
import com.alejo688.prueba_level_1.modelo.Artist;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private ArtistController mArtistController;
    private AlbumController mAlbumController;

    private Artist mArtist;
    private Album mAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.TitleActivityMain));

        final ListView listView = findViewById(R.id.Opciones);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.menu_principal));
        listView.setAdapter(arrayAdapter);

        sync(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast toast;
                Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);

                switch (position) {
                    case 0:
                        intent.putExtra("Categoria", 1);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("Categoria", 2);
                        startActivity(intent);
                        break;
                    default:
                        toast = Toast.makeText(getApplicationContext(), String.format(String.valueOf(R.string.item_no_disponible), parent.getItemAtPosition(position)), Toast.LENGTH_LONG);
                        toast.show();
                        break;
                }
            }
        });
    }

    private void sync(final Context context) {

        String urlArtist = getString(R.string.url_top_artista);
        String urlAlbum = getString(R.string.url_top_album);
        final Toast msg = Toast.makeText(context, R.string.sin_resultados, Toast.LENGTH_LONG);
        final RequestQueue queue = Volley.newRequestQueue(context);
        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.please_wait));
        progressDialog.setMessage(getString(R.string.msg_initial_sync));
        progressDialog.show();

        /* Carga artista */

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlArtist, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        mArtistController = ArtistController.get(context);
                        mArtist = new Artist();

                        //truncate table
                        mArtistController.truncateArtist();

                        String ListsJson;
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

                                imageJson = jsonDetail.getString("image");
                                JSONArray jsonImage = new JSONArray(imageJson);

                                for (int j = 0; j < jsonImage.length(); j++) {
                                    JSONObject jsonImageDetail = jsonImage.getJSONObject(j);
                                    size = jsonImageDetail.getString("size");

                                    if (size.equals("large")) {

                                        image = jsonImageDetail.getString("#text");

                                        mArtist.setImage(image);

                                        break;
                                    }
                                }

                                mArtist.setId(jsonDetail.getString("mbid"));
                                mArtist.setName(jsonDetail.getString("name"));
                                mArtist.setListeners(jsonDetail.getString("listeners"));
                                mArtist.setUrl(jsonDetail.getString("url"));

                                mArtistController.addArtist(mArtist);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            msg.show();
                            Log.e("Error", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
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

        /* Carga album */

        request = new JsonObjectRequest(Request.Method.POST, urlAlbum, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        mAlbumController = AlbumController.get(context);
                        mAlbum = new Album();

                        //truncate table
                        mAlbumController.truncateTable();

                        String ListsJson;
                        String imageJson;
                        String size;
                        String image;

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());

                            String TopArtists = jsonObject.getString("tracks");
                            JSONObject jsonTopArtists = new JSONObject(TopArtists);
                            ListsJson = jsonTopArtists.getString("track");
                            JSONArray jsonArtists = new JSONArray(ListsJson);

                            for (int i = 0; i < jsonArtists.length(); i++) {
                                JSONObject jsonDetail = jsonArtists.getJSONObject(i);

                                imageJson = jsonDetail.getString("image");
                                JSONArray jsonImage = new JSONArray(imageJson);

                                for (int j = 0; j < jsonImage.length(); j++) {
                                    JSONObject jsonImageDetail = jsonImage.getJSONObject(j);
                                    size = jsonImageDetail.getString("size");

                                    if (size.equals("large")) {

                                        image = jsonImageDetail.getString("#text");

                                        mAlbum.setImage(image);

                                        break;
                                    }
                                }

                                JSONObject ArtistJson = new JSONObject(jsonDetail.getString("artist"));

                                if (mAlbumController.getArtist(jsonDetail.getString("mbid")) == null) {
                                    mAlbum.setId(jsonDetail.getString("mbid"));
                                    mAlbum.setName(jsonDetail.getString("name"));
                                    mAlbum.setListeners(jsonDetail.getString("listeners"));
                                    mAlbum.setUrl(jsonDetail.getString("url"));
                                    mAlbum.setMbidArtist(ArtistJson.getString("mbid"));

                                    mAlbumController.addArtist(mAlbum);
                                }
                            }

                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            msg.show();
                            Log.e("Error", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
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
