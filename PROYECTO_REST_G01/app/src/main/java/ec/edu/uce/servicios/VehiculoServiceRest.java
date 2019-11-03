package ec.edu.uce.servicios;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.rest.AsyncRestClient;
import ec.edu.uce.rest.RestClient;

public class VehiculoServiceRest implements VehiculoService {

    private RestClient rest;

    public VehiculoServiceRest() {
        rest = AsyncRestClient.getRest();
    }

    @Override
    public Vehiculo save(Vehiculo vehiculo) {
        try {
            return new AsyncRestClient<Vehiculo>().execute(rest.saveVehiculo(vehiculo)).get();
        } catch (Exception e) {
            throw new CustomException("Error al guardar el vehiculo", e);
        }
    }

    @Override
    public List<Vehiculo> saveAll(List<Vehiculo> vehiculos) {
        try {
            return new AsyncRestClient<List<Vehiculo>>().execute(rest.saveAllVehiculo(vehiculos)).get();
        } catch (Exception e) {
            throw new CustomException("Error al guardar los vehiculos", e);
        }
    }

    @Override
    public List<Vehiculo> findAll() {
        try {
            return new AsyncRestClient<List<Vehiculo>>().execute(rest.listAllVehiculo()).get();
        } catch (Exception e) {
            throw new CustomException("Error al obtener los vehiculos", e);
        }
    }

    @Override
    public Vehiculo update(String placa, Vehiculo vehiculo) {
        try {
            return new AsyncRestClient<Vehiculo>().execute(rest.updateVehiculo(placa, vehiculo)).get();
        } catch (Exception e) {
            throw new CustomException("Error al editar el vehiculo", e);
        }
    }

    @Override
    public void delete(String placa) {
        try {
            new AsyncRestClient<Vehiculo>().execute(rest.deleteVehiculo(placa)).get();
        } catch (Exception e) {
            throw new CustomException("Error al eliminar el vehiculo", e);
        }
    }
}
