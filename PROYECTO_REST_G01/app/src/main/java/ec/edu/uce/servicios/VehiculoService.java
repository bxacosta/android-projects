package ec.edu.uce.servicios;

import java.util.List;

import ec.edu.uce.modelo.Vehiculo;

public interface VehiculoService {

    Vehiculo save(Vehiculo vehiculo);
    List<Vehiculo> saveAll(List<Vehiculo> vehiculos);
    List<Vehiculo> findAll();
    Vehiculo update(String placa, Vehiculo vehiculo);
    void delete(String placa);
}
