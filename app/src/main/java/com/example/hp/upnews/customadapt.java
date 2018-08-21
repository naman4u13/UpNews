package com.example.hp.upnews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by hp on 18-07-2018.
 */

public class customadapt extends ArrayAdapter<Event> {

    public customadapt(@NonNull Context context,  @NonNull List objects) {
        super(context,0,objects);  }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder holder;
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout1, parent, false);
            holder = new Viewholder();
            holder.title = listItemView.findViewById(R.id.Title);

            holder.publishedAt = listItemView.findViewById(R.id.pat);
            listItemView.setTag(holder);

        } else {
            holder = (Viewholder) listItemView.getTag();
        }
        final Event news = (Event) getItem(position);
        holder.title.setText(news.Title);

        holder.publishedAt.setText(news.PublishedAt);
        holder.title.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.ULink));

                    getContext().startActivity(browserIntent);

            }
        });
        return listItemView;
    }
}


