package ec.edu.uce.rest;

import java.util.List;

import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.modelo.Vehiculo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestClient {

    // Usuario
    @POST("usuario")
    Call<Usuario> saveUsuario(@Body Usuario usuario);

    @GET("usuario/{username}")
    Call<Usuario> findUsuarioByUsername(@Path("username") String username);

    // Vehiculo
    @POST("vehiculo")
    Call<Vehiculo> saveVehiculo(@Body Vehiculo vehiculo);

    @POST("vehiculo/all")
    Call<List<Vehiculo>> saveAllVehiculo(@Body List<Vehiculo> vehiculo);

    @GET("vehiculo")
    Call<List<Vehiculo>> listAllVehiculo();

    @GET("vehiculo/{placa}")
    Call<Vehiculo> findVehiculoByPlaca(@Path("placa") String placa);

    @PUT("vehiculo/{placa}")
    Call<Vehiculo> updateVehiculo(@Path("placa") String placa, @Body Vehiculo vehiculo);

    @DELETE("vehiculo/{placa}")
    Call<Vehiculo> deleteVehiculo(@Path("placa") String placa);

}
