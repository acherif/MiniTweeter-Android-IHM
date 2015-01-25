package com.example.hamid.minitweeter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hamid.minitweeter.R;
import com.example.hamid.minitweeter.loaders.FollowingsLoader;
import com.example.hamid.minitweeter.model.User;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hamid on 26/12/2014.
 *
 * Populates the list of users.
 * Fills each one with his handle, his status and his profile picture
 */
public class UsersAdapter extends BaseAdapter {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return users != null ? users.size() : 0;
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        }

        User user = getItem(position);
        TextView handleView = (TextView) convertView.findViewById(R.id.handle);
        handleView.setText(user.getHandle());
        TextView statusView = (TextView) convertView.findViewById(R.id.status);
        switch (user.getStatus()) {
            case "online": statusView.setText(R.string.online); break;
            case "offline": statusView.setText(R.string.offline); break;
            default: statusView.setText("");
                }
        ImageView profilePictureView = (ImageView) convertView.findViewById(R.id.profile_picture);
        Picasso.with(convertView.getContext()).load(user.getProfilePicture()).into(profilePictureView);


        return convertView;
    }

}
