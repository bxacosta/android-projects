package ec.edu.uce.rest;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.modelo.Vehiculo;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AsyncRestClient<T> extends AsyncTask<Call, Integer, T> {

    private static final String BASE_URL = "http://192.168.137.1:8080/";

    @Override
    protected T doInBackground(Call... calls) {
        Call<T> call = calls[0];
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new CustomException("Error al llamar al servicio rest", e);
        }
    }

    public static RestClient getRest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(RestClient.class);
    }
}
