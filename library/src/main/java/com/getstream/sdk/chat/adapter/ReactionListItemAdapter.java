package com.getstream.sdk.chat.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getstream.sdk.chat.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReactionListItemAdapter extends RecyclerView.Adapter<ReactionListItemAdapter.MyViewHolder>{

    private final String TAG = ReactionListItemAdapter.class.getSimpleName();

    private Context context;
    private List<String>reactions = new ArrayList<>();
    private int reactionCount;
    private Map<String, String>reactionTypes;

    public ReactionListItemAdapter(Context context, Map<String,Integer> reactionCountMap, Map<String, String>reactionTypes) {
        this.context = context;
        this.reactionTypes = reactionTypes;
        Set keys = reactionCountMap.keySet();
        reactionCount = 0;
        for(Object key: keys){
            this.reactions.add(key.toString());
            reactionCount += reactionCountMap.get(key);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stream_item_reaction, parent, false);

        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String emoji = "";
        if (position == reactions.size()){
            emoji = String.valueOf(reactionCount);
        }else {
            String reaction = reactions.get(position);
            try {
                emoji = reactionTypes.get(reaction);
            }catch (Exception e){
                e.printStackTrace();
            }
//            emoji = ReactionEmoji.valueOf(reaction).get();
        }
        holder.tv_emoji.setText(emoji);
    }

    @Override
    public int getItemCount() {
        return reactions.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_emoji;

        public MyViewHolder(View view) {
            super(view);
            tv_emoji = view.findViewById(R.id.tv_emoji);
        }

    }
}
