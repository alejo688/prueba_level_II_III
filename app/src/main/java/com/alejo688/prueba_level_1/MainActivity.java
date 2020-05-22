package com.alejo688.prueba_level_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.TitleActivityMain));

        final ListView listView = findViewById(R.id.Opciones);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.menu_principal));
        listView.setAdapter(arrayAdapter);

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
}
