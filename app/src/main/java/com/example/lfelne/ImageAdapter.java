package com.example.lfelne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<ImageItem> {
    Context mContext;
    ArrayList<ImageItem> image_uris;

    private static class ViewHolder {
        ImageView image_item;
        TextView image_number;
        TextView image_date;
    }

    public ImageAdapter(@NonNull Context context, ArrayList<ImageItem> image_uris) {
        super(context, R.layout.image_item, image_uris);
        this.image_uris = image_uris;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItem image_item = getItem(position);
        ViewHolder viewHolder;

        // If convertView does not already exist
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.image_item, parent, false);

            viewHolder.image_item = convertView.findViewById(R.id.image_item);
            viewHolder.image_date = convertView.findViewById(R.id.image_date);
            viewHolder.image_number = convertView.findViewById(R.id.image_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.image_number.setText(String.valueOf(position + 1));
        viewHolder.image_date.setText(image_item.getDate());

        if (image_item.getUri() == null) {
            viewHolder.image_item.setImageResource(R.drawable.blank_image);
        }
        else {
            Bitmap image_bitmap = BitmapFactory.decodeFile(image_item.getUri().toString());
            viewHolder.image_item.setImageBitmap(image_bitmap);
        }

        return convertView;
    }
}
