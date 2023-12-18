# SUBSCRIBER-BC
Aplication de type Microservice permettant de gérer des abonnés.

# Prerequis
- Java 17+
- Maven

# Description
Le choix a été porté de construire l'application en respectant les principes de la clean architecture pour les avantages qu'elle proccure, à savoir : 
- Le testatbilité : une clean architecture est conçue pour tests : de part son découpage fin (un rôle défini pour un couche donnée), plusieurs scenarios de tests peuvent être définis et la détection d'erreur est beaucoup plus rapide
- Indépendance des framework : de part, les specifités du langage et eventuellement quelques librairies utilitaires, la clean archirecture ne s'appuie sur aucun framework. Les impacts d'une chagement dans le le framework sont minimes.
- Indépendance de la base de données :  grace au principe d'inversion des dépendances, le coeur de l'application intéragit avec la couche de l'accès aux données sur la base de contrats (interfaces en POO) définit par le coeur lui même. L'implémentation d'un contrat dépendra de la base de données choisie mais n'impact par le coeur
- Indépendance de l’interface utilisateur
  
L'application est composée de deux module :
- Un module [coeur](./subscriber-bc-core "subscriber-bc-core") implementant les differents cas d'usage "métier" décrits dans le cahier des charges.
- Un module [application](./subscriber-bc-app "subscriber-bc-app") implement les acpects techniques de l'application
