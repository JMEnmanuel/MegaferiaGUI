/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import model.entities.*;
import model.repository.MegaFeriaRepositorio;
import java.util.ArrayList;

/**
 * Controlador para gestionar personas (Author, Manager, Narrator).
 * Maneja creación, validación y consulta de personas.
 * @author Alexander
 */



public class PersonController {
    
    private MegaFeriaRepositorio repositorio;
    
    public PersonController() {
        this.repositorio = MegaFeriaRepositorio.getInstance();
    }
    
    
     // Crea un nuevo autor con validaciones.
    
    public Response<Author> crearAuthor(long id, String firstname, String lastname) {
        // Validar ID
        String error = Validador.validarId(id);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar nombre
        error = Validador.validarNoVacio(firstname, "El nombre");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar apellido
        error = Validador.validarNoVacio(lastname, "El apellido");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Verificar que no exista
        if (repositorio.buscarPersonId(id) != null) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "Ya existe una persona con el ID " + id, null);
        }
        
        // Crear y guardar
        Author author = new Author(id, firstname, lastname);
        repositorio.addPerson(author);
        
        return new Response<>(StatusCode.OK, 
            "Autor creado exitosamente", (Author) author.clone());
    }
    
    
     //Crea un nuevo gerente con validaciones.
     
    public Response<Manager> crearManager(long id, String firstname, String lastname) {
        // Validar ID
        String error = Validador.validarId(id);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar nombre
        error = Validador.validarNoVacio(firstname, "El nombre");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar apellido
        error = Validador.validarNoVacio(lastname, "El apellido");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Verificar que no exista
        if (repositorio.buscarPersonId(id) != null) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "Ya existe una persona con el ID " + id, null);
        }
        
        // Crear y guardar
        Manager manager = new Manager(id, firstname, lastname);
        repositorio.addPerson(manager);
        
        return new Response<>(StatusCode.OK, 
            "Gerente creado exitosamente", (Manager) manager.clone());
    }
    
    
     //Crea un nuevo narrador con validaciones.
     
    public Response<Narrator> crearNarrator(long id, String firstname, String lastname) {
        // Validar ID
        String error = Validador.validarId(id);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar nombre
        error = Validador.validarNoVacio(firstname, "El nombre");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar apellido
        error = Validador.validarNoVacio(lastname, "El apellido");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Verificar que no exista
        if (repositorio.buscarPersonId(id) != null) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "Ya existe una persona con el ID " + id, null);
        }
        
        // Crear y guardar
        Narrator narrator = new Narrator(id, firstname, lastname);
        repositorio.addPerson(narrator);
        
        return new Response<>(StatusCode.OK, 
            "Narrador creado exitosamente", (Narrator) narrator.clone());
    }
    
    
     //Obtiene todas las personas ordenadas por ID.
     
    public Response<ArrayList<Person>> obtenerTodos() {
        ArrayList<Person> persons = repositorio.getAllPersons();
        
        // Ordenar por ID
        persons.sort((p1, p2) -> Long.compare(p1.getId(), p2.getId()));
        
        return new Response<>(StatusCode.OK, 
            "Personas obtenidas exitosamente", persons);
    }
    
    
     //Obtiene solo los autores ordenados por ID.
     
    public Response<ArrayList<Author>> obtenerAutores() {
        ArrayList<Person> todasPersonas = repositorio.getAllPersons();
        ArrayList<Author> autores = new ArrayList<>();
        
        for (Person p : todasPersonas) {
            if (p instanceof Author) {
                autores.add((Author) p.clone());
            }
        }
        
        // Ordenar por ID
        autores.sort((a1, a2) -> Long.compare(a1.getId(), a2.getId()));
        
        return new Response<>(StatusCode.OK, 
            "Autores obtenidos exitosamente", autores);
    }
    
    
     //Obtiene solo los gerentes ordenados por ID.
     
    public Response<ArrayList<Manager>> obtenerManagers() {
        ArrayList<Person> todasPersonas = repositorio.getAllPersons();
        ArrayList<Manager> managers = new ArrayList<>();
        
        for (Person p : todasPersonas) {
            if (p instanceof Manager) {
                managers.add((Manager) p.clone());
            }
        }
        
        // Ordenar por ID
        managers.sort((m1, m2) -> Long.compare(m1.getId(), m2.getId()));
        
        return new Response<>(StatusCode.OK, 
            "Gerentes obtenidos exitosamente", managers);
    }
    
    
     //Obtiene solo los narradores ordenados por ID.
     
    public Response<ArrayList<Narrator>> obtenerNarradores() {
        ArrayList<Person> todasPersonas = repositorio.getAllPersons();
        ArrayList<Narrator> narradores = new ArrayList<>();
        
        for (Person p : todasPersonas) {
            if (p instanceof Narrator) {
                narradores.add((Narrator) p.clone());
            }
        }
        
        // Ordenar por ID
        narradores.sort((n1, n2) -> Long.compare(n1.getId(), n2.getId()));
        
        return new Response<>(StatusCode.OK, 
            "Narradores obtenidos exitosamente", narradores);
    }
    
    
     // Busca una persona por ID.
     
    public Response<Person> buscarPorId(long id) {
        Person person = repositorio.buscarPersonId(id);
        
        if (person == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No se encontró una persona con el ID " + id, null);
        }
        
        return new Response<>(StatusCode.OK, 
            "Persona encontrada", person.clone());
    }
}