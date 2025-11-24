/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import model.entities.Author;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * Clase que realiza todas las validaciones del sistema
 * @author Alexander
 */
public class Validador {
    
    /**
     * Valida que un ID sea válido (>= 0 y máximo 15 dígitos).
     * retorna null si es válido, mensaje de error si no lo es
     */
    public static String validarId(long id) {
        if (id < 0) {
            return "El ID debe ser mayor o igual a 0";
        }
        if (String.valueOf(id).length() > 15) {
            return "El ID debe tener máximo 15 dígitos";
        }
        return null;
    }
    
    public static String validarPrecio(double precio) {
        if (precio <= 0) {
            return "El precio debe ser mayor que 0";
        }
        return null;
    }
    
    
     // Valida que un campo de texto no esté vacío.
    
    public static String validarNoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            return nombreCampo + " no puede estar vacío";
        }
        return null;
    }
    
    //Valida que el NIT tenga formato XXX.XXX.XXX-X.
     
    public static String validarNIT(String nit) {
        // Verificar longitud exacta: XXX.XXX.XXX-X = 13 caracteres
        if (nit == null || nit.length() != 13) {
            return "NIT debe tener formato XXX.XXX.XXX-X";
        }
        
        // Verificar que los puntos y guion estén en las posiciones correctas
        if (nit.charAt(3) != '.' || nit.charAt(7) != '.' || nit.charAt(11) != '-') {
            return "NIT debe tener formato XXX.XXX.XXX-X";
        }
        
        // Verificar que las posiciones de dígitos sean números
        for (int i = 0; i < nit.length(); i++) {
            // Saltar las posiciones de punto y guion
            if (i == 3 || i == 7 || i == 11) continue;
            
            if (!Character.isDigit(nit.charAt(i))) {
                return "NIT debe contener solo dígitos en formato XXX.XXX.XXX-X";
            }
        }
        
        return null;
    }
    
    
     //Valida que el ISBN tenga formato XXX-X-XX-XXXXXX-X.
    
    public static String validarISBN(String isbn) {
        // Verificar longitud exacta: XXX-X-XX-XXXXXX-X = 17 caracteres
        if (isbn == null || isbn.length() != 17) {
            return "ISBN debe tener formato XXX-X-XX-XXXXXX-X";
        }
        
        // Verificar que los guiones estén en las posiciones correctas
        if (isbn.charAt(3) != '-' || isbn.charAt(5) != '-' || 
            isbn.charAt(8) != '-' || isbn.charAt(15) != '-') {
            return "ISBN debe tener formato XXX-X-XX-XXXXXX-X";
        }
        
        // Verificar que todas las demás posiciones sean dígitos
        for (int i = 0; i < isbn.length(); i++) {
            // Saltar las posiciones de los guiones
            if (i == 3 || i == 5 || i == 8 || i == 15) continue;
            
            if (!Character.isDigit(isbn.charAt(i))) {
                return "ISBN debe contener solo dígitos en formato XXX-X-XX-XXXXXX-X";
            }
        }
        
        return null;
    }
    
    
     // Valida que una lista de autores no esté vacía y no tenga duplicados.
     
    public static String validarAutores(ArrayList<Author> autores) {
        if (autores == null || autores.isEmpty()) {
            return "Debe haber al menos un autor";
        }
        
        // Verificar duplicados usando HashSet (usa equals/hashCode)
        HashSet<Author> set = new HashSet<>(autores);
        if (set.size() != autores.size()) {
            return "No puede haber autores duplicados";
        }
        
        return null;
    }
    
     
    public static String validarPositivo(int valor, String nombreCampo) {
        if (valor <= 0) {
            return nombreCampo + " debe ser mayor que 0";
        }
        return null;
    }
}