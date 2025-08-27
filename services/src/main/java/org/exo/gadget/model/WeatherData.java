/**Ce code définit une classe WeatherData utilisée pour représenter des informations météorologiques.
  *Elle contient des propriétés comme la température, une description du temps, ainsi que la latitude et la longitude d’un lieu.
  *Grâce à l’annotation Lombok (@Getter et @Setter),
  *les méthodes getters et setters sont générées automatiquement, ce qui simplifie le code.
 */
package org.exo.gadget.model; // Déclare le package auquel appartient la classe

import lombok.Getter;
import lombok.Setter;

/** @Getter et @Setter de Lombok génèrent automatiquement les méthodes d'accès (getX, setX).
    *Cela évite d'écrire manuellement ces méthodes pour chaque attribut.
 **/
@Getter
@Setter
public class WeatherData {

    // Champ représentant la température actuelle du lieu.
    private double temperature;

    // Champ pour décrire l'état météo (exemple : "ciel dégagé", "pluie", etc.)
    private String description;

    // Coordonnées géographiques : latitude
    private String latitude;

    // Coordonnées géographiques : longitude
    private String longitude;

    // Constructeur par défaut (nécessaire si on veut instancier sans passer de paramètres)
    public WeatherData() {
        // Rien de particulier, juste initialisation par défaut.
    }

    // Constructeur qui initialise la latitude et la longitude
    // Utile lorsqu'on crée un objet météo pour un lieu donné.
    public WeatherData(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

    }


    public WeatherData(double temperature, String description) {
        this.description = description;
        this.temperature = temperature;
    }


}
