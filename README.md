```markdown
# 🌟 Gadget Collaboratif DigiTech pour eXo Platform

Bienvenue sur le repository du gadget interactif DigiTech, conçu pour enrichir l’intranet collaboratif **eXo Platform** avec une interface utilisateur moderne, personnalisée et dynamique ! 🚀

---

## ✨ Fonctionnalités clés

- 🙋‍♂️ **Accueil personnalisé** : Message de bienvenue affichant le nom de l’utilisateur.
- 🌤️ **Météo locale & ville** : Récupération automatique de la météo et de votre ville grâce à la géolocalisation.
- 🚦 **Gestion intelligente des erreurs** : Fallback (valeur par défaut) si la géolocalisation échoue ou n’est pas autorisée.
- 🎨 **Affichage responsive** : UI adaptatif et propre grâce à Tailwind CSS.
- 🗂 **Architecture modulaire** : Séparation claire entre logique d’appel API, traitement des données et affichage.

---



# 🚀 Maven Project Starter

Bienvenue dans ce projet Maven ! Ce guide rapide vous aidera à configurer, compiler, et lancer votre application Java basée sur Maven.

---

## 📋 Prérequis

- 💻 **Java JDK 17+** installé  
- 📦 **Maven 3.6+** installé  
- 🛠️ Un éditeur de code ou IDE ( Eclipse)

---

## ⚙️ Création du projet Maven
```
mvn archetype:generate -DgroupId=com.example -DartifactId=mon-projet
-DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false


> Cette commande génère un squelette de projet Java Maven avec un exemple simple.

---
```
## 🔨 Construction et compilation

Depuis la racine du projet, lancez :

mvn clean package


Cette commande compile le code, lance les tests et génère un fichier JAR dans `target/`.

---

## ▶️ Exécuter l’application

Pour exécuter la classe principale spécifiée dans le `pom.xml` :

mvn exec:java -Dexec.mainClass="com.example.App"

## 📚 Ressources utiles

- [Documentation Maven](https://maven.apache.org/guides/index.html)  
- [Tutoriel Maven - Baeldung](https://www.baeldung.com/maven)  

---

```

## 🗂️ Développement Front-end
---
```

## ⚙️ Pré-requis

- 🦊 **Navigateur moderne** supportant les modules ES (``).
- 🛰 **Clé API** OpenWeatherMap (ou une autre API météo accessible avec lat/lon).
- ⚡ **Serveur HTTP local ou distant** (obligatoire, pas d’ouverture en `file://`) : ex : Python, VSCode Live Server, http-server Node…
- 📶 **Connexion Internet** pour charger Tailwind CSS via CDN.

---

## 🚀 Mise en route rapide

### 1️⃣ Cloner le projet

```
git clone https://github.com/tonCompte/gadget-exoplatform-digitech.git
cd gadget-exoplatform-digitech
```

---

### 2️⃣ Configurer la clé API météo

Ouvre `weatherApi.js` et remplace `'TON_API_KEY'` par ta vraie clé API (gratuite sur openweathermap.org).

---

### 3️⃣ Lancer le serveur local

- Avec l'extention live server :
  ```http://127.0.0.1:5500

  ```
*Accède ensuite à* :  
```
http://localhost:8000/index.html
```

---

### 4️⃣ 🖥️ Intégration recommandée sur eXo Platform

- Ajoute le contenu de `index.html` ou le gadget packagé comme extension sur la plateforme, selon la documentation eXo.
- Personnalise les IDs ou la structure HTML selon ton thème ou besoins.

---

## 💡 Utilisation & personnalisation

- **WelcomeMessage.js**  
  Personnalise le message d’accueil ici. Utilisant `getUserInfo()`, il affiche un message convivial dans l’ID cible.

- **Weather.js**  
  Récupère la position (ou une valeur par défaut), appelle `fetchWeather()` puis affiche la météo et la ville dans les bons éléments HTML.

- **weatherApi.js**  
  Implémente l’appel à l’API météo :  
  - Récupère la température, la ville et la description météo selon les coordonnées GPS de l’utilisateur.
  - Retourne un objet `{ temp, city, description }`.

- **Styling (CSS / Tailwind)**  
  Adapte la largeur max, la couleur, le padding, etc. avec Tailwind CSS :
  ```
  
  ```

---

## 🛠️ Bonnes pratiques

- Toujours servir via un **serveur HTTP/HTTPS** pour éviter les soucis CORS 👮‍♂️.
- **Appliquer les classes `break-words`, `whitespace-pre-wrap`, `max-w-md`** pour que les textes ne débordent jamais.
- Ne jamais publier de **clé API exposée** en production sans filtrage IP ou solution backend.

---

## 🩺 Dépannage (FAQ rapide)

- **Rien ne s’affiche, ou météo = “Indisponible” ?**
  - Vérifie ta clé API, la connexion au serveur HTTP, et la structure de tes IDs HTML.
- **`getUserLocation is not a function` ?**
  - Assure-toi qu’elle existe bien dans `Weather.js` et que tu l’importes avec `import Weather from './Weather.js';`.
- **Le texte du message déborde ?**
  - Ajoute ou ajuste les classes CSS/Tailwind recommandées sur ton bloc (`break-words`, `max-w-md`, etc.).

---

## 👩‍💻 Contribution

- Forkez le projet, proposez vos PR !
- Ouvrez une issue GitHub pour tout bug ou demande d’amélioration.

---

## 📬 Besoin d’aide ?

N’hésitez pas à ouvrir un ticket GitHub ou contacter l’équipe DigiTech.

---

Merci d’utiliser le gadget DigiTech 🚀  
**Boostez la convivialité et la productivité sur eXo Platform !** 🌱
```
