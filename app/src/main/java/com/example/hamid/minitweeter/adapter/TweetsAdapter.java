package com.example.hamid.minitweeter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.model.Tweet;

import java.util.List;

/**
 * Created by hamid on 26/12/2014.
 */
public class TweetsAdapter extends BaseAdapter {

    private List<Tweet> tweets;

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public int getCount() {
        return tweets != null ? tweets.size() : 0;
    }

    @Override
    public Tweet getItem(int position) {
        return tweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false);
        }

        Tweet tweet = getItem(position);
        TextView contentView = (TextView) convertView.findViewById(R.id.content);
        contentView.setText(tweet.getContent());
        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        dateView.setText(tweet.getDate());
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
