/**
 * Ce test va vérifier :
 * La création d’un objet avec le constructeur par défaut.
 * L’utilisation des setters et getters générés par Lombok.
 * La création d’un objet avec le constructeur qui prend latitude et longitude.
 */


package org.exo.gadget.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeatherDataTest {

    @Test
    void testDefaultConstructorAndSetters() {
        // Création d'un objet avec le constructeur par défaut
        WeatherData data = new WeatherData();

        // Utilisation des setters pour affecter des valeurs
        data.setTemperature(28.5);
        data.setDescription("Ciel dégagé");
        data.setLatitude("3.848");
        data.setLongitude("11.502");

        // Vérification avec les getters
        assertEquals(28.5, data.getTemperature());
        assertEquals("Ciel dégagé", data.getDescription());
        assertEquals("3.848", data.getLatitude());
        assertEquals("11.502", data.getLongitude());
    }

    @Test
    void testConstructorWithCoordinates() {
        // Création d'un objet avec latitude et longitude
        WeatherData data = new WeatherData("3.848", "11.502");

        // Vérification que les coordonnées sont bien initialisées
        assertEquals("3.848", data.getLatitude());
        assertEquals("11.502", data.getLongitude());

        // Température et description doivent être null ou zéro
        assertEquals(0.0, data.getTemperature()); //assertEquals : compare la valeur attendue avec la valeur réelle.
        assertNull(data.getDescription()); //assertNull : vérifie qu’une variable est bien null (utile pour description car on ne l’a pas initialisée).
    }
}
