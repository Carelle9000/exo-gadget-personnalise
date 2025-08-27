package org.exo.gadget.util;

/**
 * Cette classe contient des constantes utilisées pour appeler des API externes
 * (météo et citations) dans l'application Exo Gadget.
 * Elle centralise les URL et les clés nécessaires pour effectuer les requêtes.
 */
public class Constants {

    /**
     * URL de base pour accéder à l'API OpenWeather (données météorologiques).
     * Exemple : <a href="https://api.openweathermap.org/data/2.5/weather?q=Paris&appid=VOTRE_CLE">...</a>
     */
    public static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather";

    /**
     * Clé API pour authentifier les requêtes vers OpenWeather.
     * Cette clé est récupérée sur le site officiel : <a href="https://openweathermap.org/">...</a>
     * ⚠️ Ne jamais exposer cette clé dans un code public ou un dépôt Git.
     */
    public static final String OPENWEATHER_API_KEY = "245f0e0e8437c44992b6c2b7752e801a";

    /**
     * URL pour accéder à l'API de citations aléatoires (Quotable).
     * Permet de récupérer des citations motivantes ou inspirantes.
     */
    public static final String QUOTE_API_URL = "https://api.quotable.io/quotes/random?limit=1";
}
