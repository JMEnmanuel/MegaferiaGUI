/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import model.entities.Stand;
import model.entities.Publisher;
import model.repository.MegaFeriaRepositorio;
import java.util.ArrayList;

/**
 * Controlador para gestionar la compra de stands por editoriales.
 * @author Alexander
 */
public class StandComprasController {
    private MegaFeriaRepositorio repositorio;
    
    public StandComprasController() {
        this.repositorio = MegaFeriaRepositorio.getInstance();
    }
    
    
    // Asigna un stand a una editorial (compra).
    
    public Response<Void> comprarStand(long standId, String publisherNit) {
        // Verificar que el stand exista
        Stand stand = repositorio.buscarStandId(standId);
        if (stand == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe un stand con el ID " + standId, null);
        }
        
        // Verificar que la editorial exista
        Publisher publisher = repositorio.buscarPublisherNit(publisherNit);
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe una editorial con el NIT " + publisherNit, null);
        }
        
        // Verificar si el stand ya está comprado por esta editorial
        if (stand.getPublishers().contains(publisher)) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "El stand " + standId + " ya fue comprado por la editorial " + publisher.getName(), null);
        }
        
        // Realizar la compra
        stand.addPublisher(publisher);
        publisher.addStand(stand); 
        
        return new Response<>(StatusCode.OK, 
            "Stand comprado exitosamente por " + publisher.getName(), null);
    }
    
    
    // Obtiene todas las editoriales que compraron un stand específico.
    
    public Response<ArrayList<Publisher>> obtenerEditorialesPorStand(long standId) {
        Stand stand = repositorio.buscarStandId(standId);
        
        if (stand == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe un stand con el ID " + standId, null);
        }
        
        ArrayList<Publisher> publishers = new ArrayList<>();
        for (Publisher p : stand.getPublishers()) {
            publishers.add(p.clone());
        }
        
        // Ordenar por NIT
        publishers.sort((p1, p2) -> p1.getNit().compareTo(p2.getNit()));
        
        return new Response<>(StatusCode.OK, 
            "Editoriales obtenidas exitosamente", publishers);
    }
    
    
    // Obtiene todos los stands comprados por una editorial.
    
    public Response<ArrayList<Stand>> obtenerStandsPorEditorial(String publisherNit) {
        Publisher publisher = repositorio.buscarPublisherNit(publisherNit);
        
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe una editorial con el NIT " + publisherNit, null);
        }
        
        ArrayList<Stand> stands = new ArrayList<>();
        for (Stand s : publisher.getStands()) {
            stands.add(s.clone());
        }
        
        // Ordenar por ID
        stands.sort((s1, s2) -> Long.compare(s1.getId(), s2.getId()));
        
        return new Response<>(StatusCode.OK, 
            "Stands obtenidos exitosamente", stands);
    }
    
    
    // Obtiene stands disponibles (sin editoriales asignadas).
    
    public Response<ArrayList<Stand>> obtenerStandsDisponibles() {
        ArrayList<Stand> todosStands = repositorio.getAllStands();
        ArrayList<Stand> disponibles = new ArrayList<>();
        
        for (Stand s : todosStands) {
            if (s.getPublishers().isEmpty()) {
                disponibles.add(s.clone());
            }
        }
        
        // Ordenar por ID
        disponibles.sort((s1, s2) -> Long.compare(s1.getId(), s2.getId()));
        
        return new Response<>(StatusCode.OK, 
            "Stands disponibles obtenidos exitosamente", disponibles);
    }
}
