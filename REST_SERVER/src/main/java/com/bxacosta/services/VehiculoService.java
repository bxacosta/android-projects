package com.bxacosta.services;

import com.bxacosta.models.Vehiculo;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CommonsLog
@Service
public class VehiculoService {

    private final InitApp initApp;
    private List<Vehiculo> vehiculos;

    public VehiculoService(InitApp initApp) {
        this.initApp = initApp;
        this.vehiculos = initApp.getVehiculos();
    }

    public Vehiculo save(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
        return vehiculo;
    }

    public List<Vehiculo> saveAll(List<Vehiculo> vehiculos) {
        this.vehiculos = new ArrayList<>();
        this.vehiculos.addAll(vehiculos);
        return this.vehiculos;
    }

    public Optional<Vehiculo> findByPlaca(String placa) {
        return vehiculos.stream().filter(v -> v.getPlaca().equals(placa)).findFirst();
    }

    public List<Vehiculo> findAll() {
        return vehiculos;
    }

    public Vehiculo update(String placa, Vehiculo vehiculo) {
        int index = vehiculos.indexOf(findByPlaca(placa));
        return vehiculos.set(index, vehiculo);
    }

    public Vehiculo delete(String placa) {
        Optional<Vehiculo> optionalVehiculo = findByPlaca(placa);
        if (optionalVehiculo.isPresent()) vehiculos.remove(optionalVehiculo.get());
        return optionalVehiculo.get();
    }
}
