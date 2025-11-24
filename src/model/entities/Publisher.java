/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;


import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa una editorial en el sistema.
 * @author edangulo
 */
public class Publisher implements Cloneable{
    
    private final String nit;
    private String name;
    private String address;
    private Manager manager;
    private ArrayList<Book> books;
    private ArrayList<Stand> stands;

    public Publisher(String nit, String name, String address, Manager manager) {
        this.nit = nit;
        this.name = name;
        this.address = address;
        this.manager = manager;
        this.books = new ArrayList<>();
        this.stands = new ArrayList<>();
        
      
    }

    public String getNit() {
        return nit;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Manager getManager() {
        return manager;
    }
    
    public int getStandQuantity() {
        return this.stands.size();
    }
    
    public void addBook(Book book) {
        this.books.add(book);
    }
    
    public void addStand(Stand stand) {
        this.stands.add(stand);
    }

    public ArrayList<Stand> getStands() {
        return stands;
    }
    
    /**
     * Compara editoriales por NIT.
     * IMPORTANTE: Esto arregla el bug en Author.getPublisherQuantity()
     * donde contains() no funcionaba correctamente.
     */
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return nit.equals(publisher.nit);
    }

    /**
     * Hash basado en NIT.
     */
    
    public int hashCode() {
        return Objects.hash(nit);
    }
    
    /**
     * Crea una copia de la editorial.
     */
  
    public Publisher clone() {
        try {
            return (Publisher) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}