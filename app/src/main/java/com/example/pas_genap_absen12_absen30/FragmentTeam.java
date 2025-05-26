package com.example.pas_genap_absen12_absen30;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTeam extends Fragment {
    RecyclerView recyclerView;
    PlayerAdapter playerAdapter;
    List<Player> playerList = new ArrayList<>();
    ProgressBar pbRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        pbRV = view.findViewById(R.id.pbRV);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playerAdapter = new PlayerAdapter(playerList);
        recyclerView.setAdapter(playerAdapter);

        getTeamData();

        return view;
    }

    private void getTeamData() {
        pbRV.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

        Call<PlayerResponse> call = apiService.getPlayerDetails("34145937");

        call.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> response) {
                pbRV.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null && response.body().player != null) {
                    playerList.clear();
                    playerList.addAll(response.body().player);
                    playerAdapter.notifyDataSetChanged();
                } else {
                    playerList.clear();
                    playerAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Data pemain tidak ditemukan", Toast.LENGTH_SHORT).show();
                    Log.w("API_RESPONSE", "players null atau format salah");
                }
            }

            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
                pbRV.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Gagal ambil data", t);
            }
        });
    }
}
