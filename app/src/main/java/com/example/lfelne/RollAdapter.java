package com.example.lfelne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RollAdapter extends ArrayAdapter<Roll> {
    Context mContext;
    ArrayList<Roll> roll_list;

    private static class ViewHolder {
        ImageView roll_icon;
        TextView name;
        TextView date_range;
        TextView roll_type;
    }

    public RollAdapter(Context context, ArrayList<Roll> roll_list) {
        super(context, R.layout.roll_item, roll_list);
        this.roll_list = roll_list;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Roll roll = getItem(position);

        ViewHolder viewHolder;

        // If convertView does not already exist
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.roll_item, parent, false);

            viewHolder.roll_icon = (ImageView) convertView.findViewById(R.id.roll_icon);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.date_range = (TextView) convertView.findViewById(R.id.date_range);
            viewHolder.roll_type = (TextView) convertView.findViewById(R.id.roll_type);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Parse dates into one readable string
        String date_string = roll.getSTART_DATE() + " - " + roll.getEnd_date();

        // Initialize relative view
        viewHolder.name.setText(roll.getNAME());
        viewHolder.date_range.setText(date_string);
        viewHolder.roll_type.setText(roll.getROLL_TYPE());
        viewHolder.roll_icon.setImageResource(R.drawable.roll_icon);

        return convertView;
    }
}
