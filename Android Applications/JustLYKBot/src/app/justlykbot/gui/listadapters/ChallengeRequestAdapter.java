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
import app.justlykbot.datatypes.ChallengeRequest;

public class ChallengeRequestAdapter extends ArrayAdapter<ChallengeRequest>{

    Context context; 
    int layoutResourceId;    
    List<ChallengeRequest> data;
    
    
    public ChallengeRequestAdapter(Context context, int layoutResourceId, List<ChallengeRequest> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChallengeRequestHolder holder = null;
        
        if(row == null)
        {
        	
        	LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            
            
            holder = new ChallengeRequestHolder();
            holder.gameIcon = (ImageView)row.findViewById(R.id.list_imageView_request_listItem);
            holder.text = (TextView)row.findViewById(R.id.list_TextView_itemText_listItem);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ChallengeRequestHolder)row.getTag();
        }
        
       
        
        ChallengeRequest challengeRequest = data.get(position);
        
        holder.text.setText(challengeRequest.toString());     
        //holder.imgIcon.setImageResource(challengeRequest.getGame().getDpURL()); //FIXME
        
  
  
        
        return row;
    }
    
    static class ChallengeRequestHolder
    {
        ImageView gameIcon;
        TextView text;
      
    }
}
