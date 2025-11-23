/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repository;
import model.entities.*;
import java.util.ArrayList;

/**
 * Repositorio central que almacena todas las entidades de la Megaferia.
 *
 * @author Alexander
 */
public class MegaFeriaRepositorio {
    
    private ArrayList<Stand> stands;
    private ArrayList<Person> persons;
    private ArrayList<Publisher> publishers;
    private ArrayList<Book> books;

    private static MegaFeriaRepositorio instance;

    private MegaFeriaRepositorio() {
        stands = new ArrayList<>();
        persons = new ArrayList<>();
        publishers = new ArrayList<>();
        books = new ArrayList<>();
    }

    public static MegaFeriaRepositorio getInstance() {
        if (instance == null) {
            instance = new MegaFeriaRepositorio();
        }
        return instance;
    }

    //  Métodos para STAND 
    public void addStand(Stand stand) {
        stands.add(stand);
    }

    public Stand buscarStandId(long id) {
        for (Stand stand : stands) {
            if (stand.getId() == id) return stand;
        }
        return null;
    }

    public ArrayList<Stand> getAllStands() {
        ArrayList<Stand> copy = new ArrayList<>();
        for (Stand s : stands) {
            copy.add(s.clone());
        }
        return copy;
    }

    // Métodos para PERSON
    public void addPerson(Person person) {
        persons.add(person);
    }

    public Person buscarPersonId(long id) {
        for (Person person : persons) {
            if (person.getId() == id) return person;
        }
        return null;
    }

    public ArrayList<Person> getAllPersons() {
        ArrayList<Person> copy = new ArrayList<>();
        for (Person p : persons) {
            copy.add(p.clone());
        }
        return copy;
    }

    // Métodos para PUBLISHER 
    public void addPublisher(Publisher publisher) {
        publishers.add(publisher);
    }

    public Publisher buscarPublisherNit(String nit) {
        for (Publisher publisher : publishers) {
            if (publisher.getNit().equals(nit)) return publisher;
        }
        return null;
    }

    public ArrayList<Publisher> getAllPublishers() {
        ArrayList<Publisher> copy = new ArrayList<>();
        for (Publisher p : publishers) {
            copy.add(p.clone());
        }
        return copy;
    }

    // Métodos para BOOK
    public void addBook(Book book) {
        books.add(book);
    }

    public Book buscarBookIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) return book;
        }
        return null;
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> copy = new ArrayList<>();
        for (Book b : books) {
            copy.add(b.clone());
        }
        return copy;
    }
}

