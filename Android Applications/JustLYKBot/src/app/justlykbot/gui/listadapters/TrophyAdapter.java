package app.justlykbot.gui.listadapters;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.justlykbot.R;
import app.justlykbot.datatypes.Trophy;

public class TrophyAdapter extends ArrayAdapter<Trophy>{

    Context context; 
    int layoutResourceId;    
    List<Trophy> data;
    
    public TrophyAdapter(Context context, int layoutResourceId, List<Trophy> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TrophiesHolder holder = null;
        
        if(row == null)
        {
        	LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new TrophiesHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.list_imageView_request_listItem);
            holder.text = (TextView)row.findViewById(R.id.list_TextView_itemText_listItem);
            
            row.setTag(holder);
        }
        else
        {
            holder = (TrophiesHolder)row.getTag();
        }
        
        Trophy trophy = data.get(position);
        holder.text.setText(trophy.toString());
       // holder.imgIcon.setImageResource(trophy.getGame().getDpURL()); //FIXME
        
        return row;
    }
    
    static class TrophiesHolder
    {
        ImageView imgIcon;
        TextView text;
    }
}
