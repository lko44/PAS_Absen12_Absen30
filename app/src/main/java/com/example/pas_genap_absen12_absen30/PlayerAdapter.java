package com.example.pas_genap_absen12_absen30;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<Player> playerList;

    public PlayerAdapter(List<Player> playerList) {
        this.playerList = (playerList != null) ? playerList : new ArrayList<>();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.tvNameTeam.setText(player.strPlayer);
        holder.tvStadium.setText(player.strSport);

        Glide.with(holder.itemView.getContext())
                .load(player.strThumb)
                .into(holder.imgBadge);
    }

    @Override
    public int getItemCount() {
        return (playerList != null) ? playerList.size() : 0;
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameTeam, tvStadium;
        ImageView imgBadge;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameTeam = itemView.findViewById(R.id.tvNameTeam);
            tvStadium = itemView.findViewById(R.id.tvStadium);
            imgBadge = itemView.findViewById(R.id.imgBadge);
        }
    }
}
