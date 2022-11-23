package com.example.lfelne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class RollAdapter extends ArrayAdapter<Roll> implements Filterable {
    Context mContext;
    ArrayList<Roll> roll_list;
    ArrayList<Roll> displayed_roll_list;

    private static class ViewHolder {
        ImageView roll_icon;
        TextView name;
        TextView date_range;
        TextView roll_type;
    }

    public RollAdapter(Context context, ArrayList<Roll> roll_list) {
        super(context, R.layout.roll_item, roll_list);
        this.roll_list = new ArrayList<>(roll_list);
        this.displayed_roll_list = roll_list;
        mContext = context;
    }

    public void updateAdapter() {
        roll_list.clear();
        roll_list.addAll(displayed_roll_list);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                displayed_roll_list.clear();
                displayed_roll_list.addAll((Collection<? extends Roll>) results.values);
                notifyDataSetChanged();  // notifies the data with new filtered values
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Roll> filter_results = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    results.count = 0;
                    results.values = roll_list;
                }
                else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < roll_list.size(); i++) {
                        String data = roll_list.get(i).toSearchable();
                        if (data.toLowerCase().contains(constraint))
                            filter_results.add(roll_list.get(i));
                    }
                    results.count = filter_results.size();
                    results.values = filter_results;
                }
                return results;
            }
        };
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Roll roll = displayed_roll_list.get(position);

        ViewHolder viewHolder;

        // If convertView does not already exist
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.roll_item, parent, false);

            viewHolder.roll_icon = convertView.findViewById(R.id.roll_icon);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.date_range = convertView.findViewById(R.id.date_range);
            viewHolder.roll_type = convertView.findViewById(R.id.roll_type);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Parse dates into one readable string
        String date_string = roll.getStringSTART_DATE() + " - " + roll.getStringEnd_date();

        // Initialize relative view
        viewHolder.name.setText(roll.getNAME());
        viewHolder.date_range.setText(date_string);
        viewHolder.roll_type.setText(roll.getROLL_TYPE());
        viewHolder.roll_icon.setImageResource(R.drawable.roll_icon);

        return convertView;
    }
}
