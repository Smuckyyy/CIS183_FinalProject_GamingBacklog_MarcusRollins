package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends BaseAdapter
{
    private Context context;
    private List<GameInfo> gameList;
    private LayoutInflater inflater;

    public GameAdapter(Context context, List<GameInfo> gameList)
    {
        this.context = context;
        this.gameList = gameList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Object getItem(int position) {
        return gameList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position; //or use a unique ID from GameInfo if available
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.game_cell, parent, false);
            holder = new ViewHolder();
            holder.tv_j_gameName = convertView.findViewById(R.id.tv_v_cell_gameName);
            holder.tv_j_systemName = convertView.findViewById(R.id.tv_v_cell_systemName);
            holder.tv_j_earnedXp = convertView.findViewById(R.id.tv_v_cell_xp);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        //Bind the data
        GameInfo game = gameList.get(position);
        holder.tv_j_gameName.setText(game.getGameName());
        holder.tv_j_systemName.setText(game.getSystemName());

        //Show XP only if the game is marked as complete
        if ("completed".equalsIgnoreCase(game.getStatus()))
        {
            holder.tv_j_earnedXp.setVisibility(View.VISIBLE);
            holder.tv_j_earnedXp.setText("XP: " + game.getEarnedXp());
        }
        else
        {
            holder.tv_j_earnedXp.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder
    {
        TextView tv_j_gameName;
        TextView tv_j_systemName;
        TextView tv_j_earnedXp;
    }
}

