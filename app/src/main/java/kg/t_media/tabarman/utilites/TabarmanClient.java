package kg.t_media.tabarman.utilites;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabarmanClient {

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl("http://tabarman.t-media.kg/api/")
//                .baseUrl("http://192.168.88.246/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static TabarmanApi getTabarmanApi() {
        return getRetrofitInstance().create(TabarmanApi.class);
    }
}
