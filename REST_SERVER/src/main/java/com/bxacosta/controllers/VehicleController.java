package com.bxacosta.controllers;

import com.bxacosta.models.Vehiculo;
import com.bxacosta.services.VehiculoService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CommonsLog
@RestController
public class VehicleController {

    private final VehiculoService vehiculoService;

    public VehicleController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping("/vehiculo")
    public ResponseEntity<?> save(@RequestBody Vehiculo vehiculo) {
        return new ResponseEntity<>(vehiculoService.save(vehiculo), HttpStatus.OK);
    }

    @PostMapping("/vehiculo/all")
    public ResponseEntity<?> saveAll(@RequestBody List<Vehiculo> vehiculos) {
        return new ResponseEntity<>(vehiculoService.saveAll(vehiculos), HttpStatus.OK);
    }

    @GetMapping("/vehiculo")
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(vehiculoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/vehiculo/{placa}")
    public ResponseEntity<?> findByPlaca(@PathVariable String placa) {
        return new ResponseEntity<>(vehiculoService.findByPlaca(placa), HttpStatus.OK);
    }

    @PutMapping("/vehiculo/{placa}")
    public ResponseEntity<?> edit(@PathVariable String placa, @RequestBody Vehiculo vehiculo) {
        if (vehiculoService.findByPlaca(placa).isPresent()) {
            return new ResponseEntity<>(vehiculoService.update(placa, vehiculo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/vehiculo/{placa}")
    public ResponseEntity<?> delete(@PathVariable String placa) {
        return new ResponseEntity<>(vehiculoService.delete(placa), HttpStatus.NO_CONTENT);
    }
}
