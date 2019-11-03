package com.bxacosta.services;

import com.bxacosta.models.Usuario;
import com.bxacosta.models.Vehiculo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class InitApp implements ApplicationRunner {

    private List<Vehiculo> vehiculos = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        vehiculos.add(new Vehiculo("XTR-9784", "Audi", new GregorianCalendar(2015, 11, 13).getTime(), 79990.0, true, "Negro"));
        vehiculos.add(new Vehiculo("CCD-0789", "Honda", new GregorianCalendar(1998, 3, 5).getTime(), 15340.0, false, "Blanco"));

        usuarios.add(new Usuario("admin", "admin"));
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
