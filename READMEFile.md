```markdown
# 🌟 Gadget Collaboratif DigiTech pour eXo Platform

Bienvenue sur le repository du gadget interactif DigiTech, conçu pour enrichir l’intranet collaboratif **eXo Platform** avec une interface utilisateur moderne, personnalisée et dynamique ! 🚀
 Cette documentation retrace le parcours passionnant (et parfois épique !) du développement du backend de notre gadget personnalisé pour eXo Platform. Dans le cadre d'un projet de fin de stage chez KOZAO AFRICA à Ndogpassi, Douala, nous avons construit un gadget qui affiche la date/heure, la météo, les documents récents, une citation motivante, et un message de bienvenue. Le tout en respectant une structure multi-modulaire (services JAR pour la logique, webapp WAR pour l'interface), avec appels API en backend et frontend en HTML/CSS/Tailwind.
chaque étape est un checkpoint avec des défis rencontrés (😩) et des victoires (🏆).
---

## ✨ Fonctionnalités clés

- 🙋‍♂️ **Accueil personnalisé** : Message de bienvenue affichant le nom de l’utilisateur.
- 🌤️ **Météo locale & ville** : Récupération automatique de la météo et de votre ville grâce à la géolocalisation.
- 🚦 **Gestion intelligente des erreurs** : Fallback (valeur par défaut) si la géolocalisation échoue ou n’est pas autorisée.
- 🎨 **Affichage responsive** : UI adaptatif et propre grâce à Tailwind CSS.
- 🗂 **Architecture modulaire** : Séparation claire entre logique d’appel API, traitement des données et affichage.

---

# Structure du projet


# 🚀 Maven Project Starter

Bienvenue dans ce projet Maven ! Ce guide rapide vous aidera à configurer, compiler, et lancer votre application Java basée sur Maven.

---

## 📋 Prérequis

- 💻 **Java JDK 21 installé  
- 📦 **Maven 4.0 installé  
- 🛠️ Un éditeur de code ou IDE (INtellij Idea)

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
- [Télécjargement des dépendances]:( https://https://mvnrepository.com)
---

```
# 🐳 Déploiement & Docker
```
  ## 📚 Ressources utiles
-[Dossier d'istallation Docker]: Docker/docker-compose.yml, .env, Dockerfile
-[Documentation exo platform]: (http://https://docs-old.exoplatform.org/)

Pour lancer les conteneurs d'Exo on tape la commande: docker-compose -f docker-compose.yml  up -d
```

## 🗂️ Développement Backend
```
  ## 🛠️Structure du du module Services
Type : JAR (logique backend).
Dossiers :

src/main/java/org/exo/gadget/api : Interfaces (WeatherService, QuoteService, DocumentService) et implémentations (WeatherServiceImpl, QuoteServiceImpl, DocumentServiceImpl).
src/main/java/org/exo/gadget/client:  classes  responsables de l'appel HTTP à l'API Quotable et du parsing JSON.
src/main/java/org/exo/gadget/model : POJOs (WeatherData, Quote, Document).
src/main/java/org/exo/gadget/exposition : Controller REST (GadgetController).
src/main/java/org/exo/gadget/service:Service REST pour exposer chaque API individuellement
src/main/java/org/exo/gadget/util : Constantes (API keys, URLs).
src/test/java : Tests unitaires (DocumentServiceImplTest, etc.) avec JUnit/Mockito.

📋 Explication des Composants

Projet Parent 🏠 : exo-gadget-perso orchestre les modules via pom.xml. Dépendances globales : exo.social, exo.jcr, exo.kernel.
Services 🛠️ :

api : Contient WeatherServiceImpl (météo), QuoteServiceImpl (citation), DocumentServiceImpl (documents via JCR ou API sociale).
client: Contient QuoteApiClient et WeatherApiClient.
model : Classes comme Document (titre, date), WeatherData (température, ville), Quote (texte, auteur).
service: classe GadgetRestService
exposition : GadgetController expose /rest/gadget/data pour le frontend.
util : Clés API, URLs constantes.
## Documentation
La documentation Javadoc est générée dans `target/site/apidocs`. Consultez [ici](target/site/apidocs/index.html) après build.
```
