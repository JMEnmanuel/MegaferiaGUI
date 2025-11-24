/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.entities.*;
import model.repository.MegaFeriaRepositorio;
import java.util.ArrayList;

import java.util.HashSet;

/**
 * Controlador para gestionar consultas especiales y estadísticas del proyecto
 * @author Alexander
 */
public class ConsultasController {
     private MegaFeriaRepositorio repositorio;
    
    public ConsultasController() {
        this.repositorio = MegaFeriaRepositorio.getInstance();
    }
    
    
    // Busca todos los libros escritos por un autor específico.
    
    public Response<ArrayList<Book>> buscarLibrosPorAutor(long authorId) {
        // Verificar que el autor exista
        Person person = repositorio.buscarPersonId(authorId);
        if (person == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe una persona con el ID " + authorId, null);
        }
        
        if (!(person instanceof Author)) {
            return new Response<>(StatusCode.INVALID_DATA, 
                "La persona con ID " + authorId + " no es un autor", null);
        }
        
        Author author = (Author) person;
        ArrayList<Book> todosLibros = repositorio.getAllBooks();
        ArrayList<Book> librosDelAutor = new ArrayList<>();
        
        // Filtrar libros que contengan este autor
        for (Book book : todosLibros) {
            if (book.getAuthors().contains(author)) {
                librosDelAutor.add(book.clone());
            }
        }
        
        // Ordenar por ISBN
        librosDelAutor.sort((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        
        if (librosDelAutor.isEmpty()) {
            return new Response<>(StatusCode.OK, 
                "El autor " + author.getFullname() + " no tiene libros registrados", librosDelAutor);
        }
        
        return new Response<>(StatusCode.OK, 
            "Se encontraron " + librosDelAutor.size() + " libro(s) del autor " + author.getFullname(), 
            librosDelAutor);
    }
    
    
    // Busca todos los libros de un formato específico (Impreso, Digital, Audiolibro).
    
    public Response<ArrayList<Book>> buscarLibrosPorFormato(String formato) {
        // Validar formato
        String error = Validador.validarNoVacio(formato, "El formato");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        ArrayList<Book> todosLibros = repositorio.getAllBooks();
        ArrayList<Book> librosPorFormato = new ArrayList<>();
        
        // Filtrar por formato (case insensitive)
        for (Book book : todosLibros) {
            if (book.getFormat().equalsIgnoreCase(formato)) {
                librosPorFormato.add(book.clone());
            }
        }
        
        // Ordenar por ISBN
        librosPorFormato.sort((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        
        if (librosPorFormato.isEmpty()) {
            return new Response<>(StatusCode.OK, 
                "No se encontraron libros con el formato " + formato, librosPorFormato);
        }
        
        return new Response<>(StatusCode.OK, 
            "Se encontraron " + librosPorFormato.size() + " libro(s) con formato " + formato, 
            librosPorFormato);
    }
    
    
    // Obtiene los autores con más libros en diferentes editoriales.
    
    public Response<ArrayList<AutorEstadistica>> obtenerAutoresConMasLibrosEnEditoriales() {
        ArrayList<Person> todasPersonas = repositorio.getAllPersons();
        ArrayList<AutorEstadistica> estadisticas = new ArrayList<>();
        
        // Para cada autor, contar en cuántas editoriales distintas tiene libros
        for (Person p : todasPersonas) {
            if (p instanceof Author) {
                Author author = (Author) p;
                ArrayList<Book> todosLibros = repositorio.getAllBooks();
                HashSet<Publisher> editorialesDistintas = new HashSet<>();
                int totalLibros = 0;
                
                // Contar libros y editoriales distintas
                for (Book book : todosLibros) {
                    if (book.getAuthors().contains(author)) {
                        totalLibros++;
                        editorialesDistintas.add(book.getPublisher());
                    }
                }
                
                // Solo agregar si tiene libros en al menos una editorial
                if (totalLibros > 0) {
                    estadisticas.add(new AutorEstadistica(
                       (Author) author.clone(),
                        totalLibros, 
                        editorialesDistintas.size()
                    ));
                }
            }
        }
        
        // Ordenar por número de editoriales (mayor a menor), luego por total de libros
        estadisticas.sort((e1, e2) -> {
            int comparacionEditoriales = Integer.compare(e2.numEditoriales, e1.numEditoriales);
            if (comparacionEditoriales != 0) {
                return comparacionEditoriales;
            }
            return Integer.compare(e2.totalLibros, e1.totalLibros);
        });
        
        if (estadisticas.isEmpty()) {
            return new Response<>(StatusCode.OK, 
                "No hay autores con libros registrados", estadisticas);
        }
        
        return new Response<>(StatusCode.OK, 
            "Estadísticas de autores obtenidas exitosamente", estadisticas);
    }
    

    
    public static class AutorEstadistica {
        public final Author autor;
        public final int totalLibros;
        public final int numEditoriales;
        
        public AutorEstadistica(Author autor, int totalLibros, int numEditoriales) {
            this.autor = autor;
            this.totalLibros = totalLibros;
            this.numEditoriales = numEditoriales;
        }
    }
}
