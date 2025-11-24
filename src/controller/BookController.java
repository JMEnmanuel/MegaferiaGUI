/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.entities.*;
import model.repository.MegaFeriaRepositorio;
import java.util.ArrayList;

/**
 * Controlador para gestionar libros (PrintedBook, DigitalBook, Audiobook).
 * @author Alexander
 */
public class BookController {
    private MegaFeriaRepositorio repositorio;
    
    public BookController() {
        this.repositorio = MegaFeriaRepositorio.getInstance();
    }
    
    
    // Crea un nuevo libro impreso con validaciones.
    
    public Response<PrintedBook> crearPrintedBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, String publisherNit,  int pages, int copies) {
     
        // Validar ISBN
        String error = Validador.validarISBN(isbn);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar título
        error = Validador.validarNoVacio(title, "El título");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar género
        error = Validador.validarNoVacio(genre, "El género");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar formato
        error = Validador.validarNoVacio(format, "El formato");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar valor
        error = Validador.validarPrecio(value);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar número de páginas
        error = Validador.validarPositivo(pages, "El número de páginas");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar número de copias
        error = Validador.validarPositivo(copies, "El número de copias");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar autores
        error = Validador.validarAutores(authors);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Verificar que no exista libro con ese ISBN
        if (repositorio.buscarBookIsbn(isbn) != null) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "Ya existe un libro con el ISBN " + isbn, null);
        }
        
        // Verificar que el publisher exista
        Publisher publisher = repositorio.buscarPublisherNit(publisherNit);
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe una editorial con el NIT " + publisherNit, null);
        }
        
        // Verificar que todos los autores existan en el repositorio
        for (Author author : authors) {
            Person p = repositorio.buscarPersonId(author.getId());
            if (p == null) {
                return new Response<>(StatusCode.NOT_FOUND, 
                    "No existe el autor con ID " + author.getId(), null);
            }
            if (!(p instanceof Author)) {
                return new Response<>(StatusCode.INVALID_DATA, 
                    "La persona con ID " + author.getId() + " no es un autor", null);
            }
        }
        
        // Crear y guardar
        PrintedBook book = new PrintedBook(title, authors, isbn, genre, format, value, publisher, pages, copies);
        repositorio.addBook(book);
        
     
        for (Author author : authors) {
            Person p = repositorio.buscarPersonId(author.getId());
            ((Author) p).addBook(book);
        }
        publisher.addBook(book);
        
        return new Response<>(StatusCode.OK, 
            "Libro impreso creado exitosamente", (PrintedBook) book.clone());
    }
    
    
    // Crea un nuevo libro digital con validaciones.
    
    public Response<DigitalBook> crearDigitalBook(String title, ArrayList<Author> authors, String isbn, 
                                                   String genre, String format, double value, String publisherNit) {
        // Validar ISBN
        String error = Validador.validarISBN(isbn);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar título
        error = Validador.validarNoVacio(title, "El título");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar género
        error = Validador.validarNoVacio(genre, "El género");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar formato
        error = Validador.validarNoVacio(format, "El formato");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar valor
        error = Validador.validarPrecio(value);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar autores
        error = Validador.validarAutores(authors);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Verificar que no exista libro con ese ISBN
        if (repositorio.buscarBookIsbn(isbn) != null) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "Ya existe un libro con el ISBN " + isbn, null);
        }
        
        // Verificar que el publisher exista
        Publisher publisher = repositorio.buscarPublisherNit(publisherNit);
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe una editorial con el NIT " + publisherNit, null);
        }
        
        // Verificar que todos los autores existan
        for (Author author : authors) {
            Person p = repositorio.buscarPersonId(author.getId());
            if (p == null) {
                return new Response<>(StatusCode.NOT_FOUND, 
                    "No existe el autor con ID " + author.getId(), null);
            }
            if (!(p instanceof Author)) {
                return new Response<>(StatusCode.INVALID_DATA, 
                    "La persona con ID " + author.getId() + " no es un autor", null);
            }
        }
        
        // Crear y guardar
        DigitalBook book = new DigitalBook(title, authors, isbn, genre, format, value, publisher);
        repositorio.addBook(book);
   
        for (Author author : authors) {
            Person p = repositorio.buscarPersonId(author.getId());
            ((Author) p).addBook(book);
        }
        publisher.addBook(book);
        
        return new Response<>(StatusCode.OK, 
            "Libro digital creado exitosamente", (DigitalBook) book.clone());
    }
    
    
    // Crea un nuevo audiolibro con validaciones.
    
    public Response<Audiobook> crearAudiobook(String title, ArrayList<Author> authors, String isbn, 
                                               String genre, String format, double value, String publisherNit,
                                               int duration, long narratorId) {
        // Validar ISBN
        String error = Validador.validarISBN(isbn);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar título
        error = Validador.validarNoVacio(title, "El título");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar género
        error = Validador.validarNoVacio(genre, "El género");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar formato
        error = Validador.validarNoVacio(format, "El formato");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar valor
        error = Validador.validarPrecio(value);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar duración
        error = Validador.validarPositivo(duration, "La duración");
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Validar autores
        error = Validador.validarAutores(authors);
        if (error != null) {
            return new Response<>(StatusCode.INVALID_DATA, error, null);
        }
        
        // Verificar que no exista libro con ese ISBN
        if (repositorio.buscarBookIsbn(isbn) != null) {
            return new Response<>(StatusCode.ALREADY_EXISTS, 
                "Ya existe un libro con el ISBN " + isbn, null);
        }
        
        // Verificar que el publisher exista
        Publisher publisher = repositorio.buscarPublisherNit(publisherNit);
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe una editorial con el NIT " + publisherNit, null);
        }
        
        // Verificar que el narrador exista
        Person p = repositorio.buscarPersonId(narratorId);
        if (p == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No existe un narrador con el ID " + narratorId, null);
        }
        
        // Verificar que sea un Narrator
        if (!(p instanceof Narrator)) {
            return new Response<>(StatusCode.INVALID_DATA, 
                "La persona con ID " + narratorId + " no es un narrador", null);
        }
        
        Narrator narrator = (Narrator) p;
        
        // Verificar que todos los autores existan
        for (Author author : authors) {
            Person person = repositorio.buscarPersonId(author.getId());
            if (person == null) {
                return new Response<>(StatusCode.NOT_FOUND, 
                    "No existe el autor con ID " + author.getId(), null);
            }
            if (!(person instanceof Author)) {
                return new Response<>(StatusCode.INVALID_DATA, 
                    "La persona con ID " + author.getId() + " no es un autor", null);
            }
        }
        
        // Crear y guardar
        Audiobook book = new Audiobook(title, authors, isbn, genre, format, value, publisher, duration, narrator);
        repositorio.addBook(book);
        
       
        for (Author author : authors) {
            Person person = repositorio.buscarPersonId(author.getId());
            ((Author) person).addBook(book);
        }
        publisher.addBook(book);
        narrator.addBook(book);  // Audiobook también actualiza al narrador
        
        return new Response<>(StatusCode.OK, 
            "Audiolibro creado exitosamente", (Audiobook) book.clone());
    }
    
    
    // Obtiene todos los libros ordenados por ISBN.
    
    public Response<ArrayList<Book>> obtenerTodos() {
        ArrayList<Book> books = repositorio.getAllBooks();
        
        // Ordenar por ISBN
        books.sort((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        
        return new Response<>(StatusCode.OK, 
            "Libros obtenidos exitosamente", books);
    }
    
    
    // Obtiene solo los libros impresos ordenados por ISBN.
    
    public Response<ArrayList<PrintedBook>> obtenerPrintedBooks() {
        ArrayList<Book> todosLibros = repositorio.getAllBooks();
        ArrayList<PrintedBook> printedBooks = new ArrayList<>();
        
        for (Book b : todosLibros) {
            if (b instanceof PrintedBook) {
                printedBooks.add((PrintedBook) b.clone());
            }
        }
        
        // Ordenar por ISBN
        printedBooks.sort((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        
        return new Response<>(StatusCode.OK, 
            "Libros impresos obtenidos exitosamente", printedBooks);
    }
    
    
    // Obtiene solo los libros digitales ordenados por ISBN.
    
    public Response<ArrayList<DigitalBook>> obtenerDigitalBooks() {
        ArrayList<Book> todosLibros = repositorio.getAllBooks();
        ArrayList<DigitalBook> digitalBooks = new ArrayList<>();
        
        for (Book b : todosLibros) {
            if (b instanceof DigitalBook) {
                digitalBooks.add((DigitalBook) b.clone());
            }
        }
        
        // Ordenar por ISBN
        digitalBooks.sort((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        
        return new Response<>(StatusCode.OK, 
            "Libros digitales obtenidos exitosamente", digitalBooks);
    }
    
    
    // Obtiene solo los audiolibros ordenados por ISBN.
    
    public Response<ArrayList<Audiobook>> obtenerAudiobooks() {
        ArrayList<Book> todosLibros = repositorio.getAllBooks();
        ArrayList<Audiobook> audiobooks = new ArrayList<>();
        
        for (Book b : todosLibros) {
            if (b instanceof Audiobook) {
                audiobooks.add((Audiobook) b.clone());
            }
        }
        
        // Ordenar por ISBN
        audiobooks.sort((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        
        return new Response<>(StatusCode.OK, 
            "Audiolibros obtenidos exitosamente", audiobooks);
    }
    
    
    // Busca un libro por ISBN.
    
    public Response<Book> buscarPorIsbn(String isbn) {
        Book book = repositorio.buscarBookIsbn(isbn);
        
        if (book == null) {
            return new Response<>(StatusCode.NOT_FOUND, 
                "No se encontró un libro con el ISBN " + isbn, null);
        }
        
        return new Response<>(StatusCode.OK, 
            "Libro encontrado", book.clone());
    }
}
