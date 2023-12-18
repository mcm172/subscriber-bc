# Module subscriber-bc-app
Module correspondant au microservice de la gestion des abonnés

## Lancement de l'application en local
Pour le lancement de l'application :
````shell
.\mvnw spring-boot:run
````

## Remarques/commentaires

- Deux profils spring sont définis : ***local*** et ***production***
- Les données des abonnés sont stockées dans une base H2 en mémoire
- La configuration de la base pour le profil ***production*** reste à mettre en place
- Les dependances spring-cloud sont managées mais pas toutes utilisées
- Le service discovery n'est actif que pour le profil ***production*** mais la configuration du service de registry doit être adaptée