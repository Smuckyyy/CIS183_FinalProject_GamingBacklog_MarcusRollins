package com.example.cis183_finalproject_gamingbacklog_marcusrollins;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CommunityAdapter extends ArrayAdapter<String>
{
    public CommunityAdapter(Context context, ArrayList<String> data)
    {
        super(context, android.R.layout.simple_list_item_1, data);
    }
}
