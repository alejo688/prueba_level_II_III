package com.alejo688.prueba_level_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alejo688.prueba_level_1.modelo.Item;

import java.util.List;

public class CategoriaAdapter extends ArrayAdapter<Item> {
    public CategoriaAdapter(Context context, List<Item> items) {
        super(context, R.layout.list_template, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_template, null);

        TextView titulo = item.findViewById(R.id.titulo);
        TextView subtitulo = item.findViewById(R.id.subtitulo);

        titulo.setText(getItem(position).getName());
        subtitulo.setText(String.format("%s%s", getContext().getString(R.string.SubItemTitle), getItem(position).getListeners()));

        return item;
    }
}
