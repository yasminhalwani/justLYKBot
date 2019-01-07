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
import app.justlykbot.datatypes.Game;

public class GameAdapter extends ArrayAdapter<Game>{

    Context context; 
    int layoutResourceId;    
    List<Game> data;
    
    boolean withFooter = false;
    
    public GameAdapter(Context context, int layoutResourceId, List<Game> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    
    public GameAdapter(Context context, int layoutResourceId, int addNewGameFooter, List<Game> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        
        withFooter = true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GamesHolder holder = null;
        
        if(row == null)
        {
        	
        	LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            
        	if(withFooter) { 
            	inflater.inflate(R.layout.add_more_footer, null);         	
            }
            
            holder = new GamesHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.list_imageView_request_listItem);
            holder.text = (TextView)row.findViewById(R.id.list_TextView_itemText_listItem);
            
            row.setTag(holder);
        }
        else
        {
            holder = (GamesHolder)row.getTag();
        }
        
        Game game = data.get(position);
        holder.text.setText(game.getGameTitle());
        //holder.imgIcon.setImageResource(game.getDpURL()); //FIXME
        
        return row;
    }
    
    static class GamesHolder
    {
        ImageView imgIcon;
        TextView text;
    }
}
