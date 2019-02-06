package kg.t_media.tabarman.utilites;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TabarmanApi {

    @GET("app/appSettings.php")
//    @GET("f1.json")
    Call<AppSettings> appSettings();

    @POST("players/add.php")
    Call<RegistrationResult> playersAdd(@Body RegistrationData body);

    @POST("players/playerAuthorization.php")
    Call<LoginResult> Authorization(@Body LoginData body);

    @GET("players/playerSettings.php")
    Call<PlayerSettingsResult> playerSettings(@Query("playerId") String playerId, @Query ("sessionId") String SessionId);

    @GET("quests/questList.php")
    Call<QuestResult> questList(@Query("playerId") String playerId, @Query ("sessionId") String SessionId, @Query ("questId") Integer questId);


}
