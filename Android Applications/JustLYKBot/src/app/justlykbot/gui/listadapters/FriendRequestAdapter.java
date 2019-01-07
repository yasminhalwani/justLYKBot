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
import app.justlykbot.datatypes.FriendRequest;

public class FriendRequestAdapter extends ArrayAdapter<FriendRequest>{

    Context context; 
    int layoutResourceId;    
    List<FriendRequest> data;
    
  

    public FriendRequestAdapter(Context context, int layoutResourceId, List<FriendRequest> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
	}


	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FriendRequestHolder holder = null;
        
        if(row == null)
        {
        	
        	LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            
            
            holder = new FriendRequestHolder();
            holder.gameIcon = (ImageView)row.findViewById(R.id.list_imageView_request_listItem);
            holder.text = (TextView)row.findViewById(R.id.list_TextView_itemText_listItem);
            
            row.setTag(holder);
        }
        else
        {
            holder = (FriendRequestHolder)row.getTag();
        }
        
        FriendRequest FriendRequest = data.get(position);
        holder.text.setText(FriendRequest.toString());
        //holder.imgIcon.setImageResource(FriendRequest.getGame().getDpURL()); //FIXME
        
        return row;
    }
    
    static class FriendRequestHolder
    {
        ImageView gameIcon;
        TextView text;
      
    }
}
