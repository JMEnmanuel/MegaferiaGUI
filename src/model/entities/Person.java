/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;
import java.util.Objects;

/**
 * Clase base para todas las personas del sistema (autores, gerentes, narradores).
 * @author edangulo
 */
public abstract class Person implements Cloneable{
    
    protected final long id;
    protected String firstname;
    protected String lastname;

    public Person(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    
    public String getFullname() {
        return firstname + " " + lastname;
    }
    
    /**
     * Compara dos personas por su ID.
     * Agregado para que contains() y otras operaciones funcionen correctamente.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    /**
     * Genera hash basado en el ID.
     * Necesario para usar Person en HashMap y HashSet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    /**
     * Crea una copia de la persona. 
     */
    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            // En producción puedes lanzar excepción más adecuada
            throw new RuntimeException(e);
        }
    }
}