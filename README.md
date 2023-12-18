# SUBSCRIBER-BC
Application de type Microservice permettant de gérer des abonnés.

# Prerequis
- Java 17+
- Maven

# Description
L'application est construite en respectant les principes de la clean architecture pour les avantages qu'elle procure, à savoir : 
- Haute testatbilité : une clean architecture est conçue pour tests : de par son découpage fin (un rôle défini pour une couche donnée), plusieurs scenarios de tests peuvent être définis et la détection d'erreur est beaucoup plus rapide
- Indépendance des framework : de part, les specifités du langage et eventuellement quelques librairies utilitaires, la clean archirecture ne s'appuie sur aucun framework. Les impacts d'une chagement dans le framework sont minimes.
- Indépendance de la base de données :  grace au principe d'inversion des dépendances, le coeur de l'application intéragit avec la couche de l'accès aux données sur la base de contrats (interfaces en POO) définit par le coeur lui-même. L'implémentation d'un contrat dépendra de la base de données choisie mais n'impact pas le coeur
- Indépendance de l’interface utilisateur
  
L'application est composée de deux modules :
- Un module [coeur](./subscriber-bc-core "subscriber-bc-core") implementant les differents cas d'usage "métier" décrits dans le cahier des charges.
- Un module [application](./subscriber-bc-app "subscriber-bc-app") implement les acpects techniques de l'application


## Build du projet
Pour la construction des deux modules du projet :
````shell
.\mvnw clean package
````

## Lancement de l'application
```shell
.\mvnw spring-boot:run -pl subscriber-bc-app
```