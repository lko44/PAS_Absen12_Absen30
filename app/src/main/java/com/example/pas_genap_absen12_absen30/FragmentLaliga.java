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


public class FragmentLaliga extends Fragment {
    RecyclerView recyclerView;
    TeamAdapter teamAdapter;
    List<Team> teamList = new ArrayList<>();
    ProgressBar pbRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laliga, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        pbRV = view.findViewById(R.id.pbRV);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        teamAdapter = new TeamAdapter(teamList);
        recyclerView.setAdapter(teamAdapter);

        getTeamData();

        return view;
    }

    private void getTeamData() {
        pbRV.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<TeamResponse> call = apiService.getTeamsByCountry("Soccer", "Spain");

        call.enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                pbRV.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    teamList.clear();
                    teamList.addAll(response.body().teams);
                    teamAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Respon tidak valid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                pbRV.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LaLiga", "API error", t);
            }
        });
    }

}