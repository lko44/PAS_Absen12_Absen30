package com.example.pas_genap_absen12_absen30;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search_all_teams.php")
    Call<TeamResponse> getTeamsByCountry(@Query("s") String sport, @Query("c") String country);

    @GET("lookup_all_players.php")
    Call<PlayerResponse> getPlayerDetails(@Query("id") String id);
}