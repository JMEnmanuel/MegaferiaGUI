/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import model.entities.Stand;
import model.repository.MegaFeriaRepositorio;
import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class StandController {
    
    
      private MegaFeriaRepositorio repositorio;
    
    public StandController() {
        this.repositorio = MegaFeriaRepositorio.getInstance();
    }
    
    
     // Crea un nuevo stand con validaciones.
     
    public Response<Stand> crearStand(long id, double price) {
        // Validar ID
        String error = Validador.validarId(id);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar precio
        error = Validador.validarPrecio(price);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Verificar que no exista
        if (repositorio.buscarStandId(id) != null) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "Ya existe un stand con el ID " + id, null);
        }
        
        // Crear y guardar
        Stand stand = new Stand(id, price);
        repositorio.addStand(stand);
        
        // Retornar copia
        return new Response<>(StatusCode.OK, 
            "Stand creado exitosamente", stand.clone());
    }
    
    
     //Obtiene todos los stands ordenados por ID.
     
    public Response<ArrayList<Stand>> obtenerTodos() {
        ArrayList<Stand> stands = repositorio.getAllStands();
        
        // Ordenar por ID
        stands.sort((s1, s2) -> Long.compare(s1.getId(), s2.getId()));
        
        return new Response<>(StatusCode.OK, 
            "Stands obtenidos exitosamente", stands);
    }
    
    
    // Busca un stand por ID.
     
    public Response<Stand> buscarPorId(long id) {
        Stand stand = repositorio.buscarStandId(id);
        
        if (stand == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No se encontró un stand con el ID " + id, null);
        }
        
        return new Response<>(StatusCode.OK, 
            "Stand encontrado", stand.clone());
    }
}
