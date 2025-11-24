/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.entities.Publisher;
import model.entities.Manager;
import model.entities.Person;
import model.repository.MegaFeriaRepositorio;
import java.util.ArrayList;

/**
 * Controlador para gestionar editoriales.
 * @author Alexander
 */
public class PublisherController {
     private MegaFeriaRepositorio repositorio;
    
    public PublisherController() {
        this.repositorio = MegaFeriaRepositorio.getInstance();
    }
    
    
    // Crea una nueva editorial con validaciones.
    
    public Response<Publisher> crearPublisher(String nit, String name, String address, long managerId) {
        // Valida NIT
        String error = Validador.validarNIT(nit);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Valida nombre
        error = Validador.validarNoVacio(name, "El nombre");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Valida dirección
        error = Validador.validarNoVacio(address, "La dirección");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Verifica que no exista editorial con ese NIT
        if (repositorio.buscarPublisherNit(nit) != null) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "Ya existe una editorial con el NIT " + nit, null);
        }
        
        // Verifica que el manager exista
        Person person = repositorio.buscarPersonId(managerId);
        if (person == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe un gerente con el ID " + managerId, null);
        }
        
        // Verifica que sea un Manager
        if (!(person instanceof Manager)) {
            return new Response<>(StatusCode.INVALID_DATA, 
                "La persona con ID " + managerId + " no es un gerente", null);
        }
        
        Manager manager = (Manager) person;
        
        // Crear y guardar
        Publisher publisher = new Publisher(nit, name, address, manager);
        repositorio.addPublisher(publisher);
        
         manager.setPublisher(publisher);
        
        return new Response<>(StatusCode.OK, 
            "Editorial creada exitosamente", publisher.clone());
    }
    
    
    // Obtiene todas las editoriales ordenadas por NIT.
    
    public Response<ArrayList<Publisher>> obtenerTodos() {
        ArrayList<Publisher> publishers = repositorio.getAllPublishers();
        
        // Ordenar por NIT
        publishers.sort((p1, p2) -> p1.getNit().compareTo(p2.getNit()));
        
        return new Response<>(StatusCode.OK, 
            "Editoriales obtenidas exitosamente", publishers);
    }
    
    
    // Busca una editorial por NIT.
    
    public Response<Publisher> buscarPorNit(String nit) {
        Publisher publisher = repositorio.buscarPublisherNit(nit);
        
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No se encontró una editorial con el NIT " + nit, null);
        }
        
        return new Response<>(StatusCode.OK, 
            "Editorial encontrada", publisher.clone());
    }
}
