# Module subscriber-bc-core
Ce module contient l'implémentation des différents cas d'usage du domaine "subscriber". Il est conçu pour être utilisé comme dépendance:
- soit ajouté dans le classpath 
- ou via maven :
```XML
        <dependency>
            <groupId>com.canal.technical.test</groupId>
            <artifactId>subscriber-bc-core</artifactId>
        </dependency>
```

## Packages du module
- ***com.canal.technical.test.subscriber.domain.entity*** : Contient l'entité métier du domaine
- ***com.canal.technical.test.subscriber.domain.errors*** : Contient les erreurs pouvant être lancées par le domaine
- ***com.canal.technical.test.subscriber.domain.ports*** : Contient les contrat d'interface devant être implementé par les adapters de la couche d'accès au données
- ***com.canal.technical.test.subscriber.usecases*** : Contient l'implementation des differents cas d'usage 

