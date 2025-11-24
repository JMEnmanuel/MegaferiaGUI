/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;


import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa un stand en la feria.
 * @author edangulo
 */
public class Stand implements Cloneable{
    
    private long id;
    private double price;
    private ArrayList<Publisher> publishers;

    public Stand(long id, double price) {
        this.id = id;
        this.price = price;
        this.publishers = new ArrayList<>();
    }
    
    public void addPublisher(Publisher publisher) {
        this.publishers.add(publisher);
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Publisher> getPublishers() {
        return publishers;
    }
    
    public int getPublisherQuantity() {
        return this.publishers.size();
    }
    
    /**
     * Compara stands por ID.
     */
   
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stand stand = (Stand) o;
        return id == stand.id;
    }

    /**
     * Hash basado en ID.
     */
   
    public int hashCode() {
        return Objects.hash(id);
    }
    
     /**
     * Crea una copia del stand.
     */
    
    public Stand clone() {
        try {
            return (Stand) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
