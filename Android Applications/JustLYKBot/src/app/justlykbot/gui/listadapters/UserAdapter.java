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
import app.justlykbot.datatypes.User;

public class UserAdapter extends ArrayAdapter<User>{

    Context context; 
    int layoutResourceId;    
    List<User> data;
    
    boolean withFooter = false;
    
    public UserAdapter(Context context, int layoutResourceId, int addNewFriendFooter,  List<User> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        
        withFooter=true;
    }
    
    public UserAdapter(Context context, int layoutResourceId,  List<User> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        
        withFooter = false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FriendsHolder holder = null;
        
        if(row == null)
        {
        	LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            
            if(withFooter) { 
            	inflater.inflate(R.layout.add_more_footer, null);         	
            }
            
            holder = new FriendsHolder();
            holder.text = (TextView)row.findViewById(R.id.list_TextView_itemText_listItem);
            holder.imgIcon = (ImageView)row.findViewById(R.id.list_imageView_request_listItem); 
            
            row.setTag(holder);
        }
        else
        {
            holder = (FriendsHolder)row.getTag();
        }
        
        User friend = data.get(position);
        holder.text.setText(friend.getName());
        
        int resID = context.getResources().getIdentifier(friend.getImageResourceId(), "id", "app.justlykbot.gui.layouts");        
        holder.imgIcon.setImageResource(resID); //FIXME
        
        return row;
    }
    
    static class FriendsHolder
    {
        ImageView imgIcon;
        TextView text;
    }
}
