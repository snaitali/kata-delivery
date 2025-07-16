# Delivery Service

Ce projet est une implémentation du Kata Delivery, un service de gestion de modes de livraison et de créneaux horaires,
avec une architecture réactive et orientée événements.

## Technologies utilisées

- Java 21
- Spring Boot 3.2.x
- Spring WebFlux (approche non-bloquante)
- Spring Data R2DBC
- Apache Kafka
- Base de données H2
- OpenAPI (Swagger)

## Fonctionnalités

### MVP

- Choix du mode de livraison (DRIVE, DELIVERY, DELIVERY_TODAY, DELIVERY_ASAP)
- Choix du jour et du créneau horaire spécifique au mode de livraison
- Réservation de créneaux horaires

### Fonctionnalités bonus implémentées

- API REST complète avec documentation OpenAPI
- Solution non-bloquante avec WebFlux et R2DBC
- Architecture orientée événements avec Kafka
- Persistence des données avec Spring Data R2DBC
- Notifications en temps réel

## Prérequis

- Java 21
- Maven
- Docker et Docker Compose (pour Kafka)

## Installation et démarrage

1. Cloner le repository
    ```bash
    git clone https://github.com/snaitali/kata-delivery.git
2. Démarrer Kafka avec Docker Compose
    ```bash
    cd delivery-service
    docker-compose up -d
3. Compiler le projet 
    ```bash
    mvn clean install
4. Exécuter l'application 
    ```bash
    mvn spring-boot:run
5. Accéder à l'application
   - API: http://localhost:8080/api
   - Documentation Swagger: http://localhost:8080/swagger-ui.html
   - Kafka UI: http://localhost:8090

## Architecture

Le projet suit une architecture hexagonale (ports & adapters) avec une approche DDD (Domain-Driven Design) et une
communication orientée événements:

- **Domain**: Contient les modèles de domaine, les interfaces de repository et les services métier
- **Application**: Contient les services d'application, les DTOs et les mappers
- **Infrastructure**: Contient les implémentations des repositories, les contrôleurs REST, la configuration et les
  composants de messagerie

## API REST

L'API REST est organisée autour des ressources suivantes:

- `/api/delivery-modes`: Gestion des modes de livraison
- `/api/time-slots`: Gestion des créneaux horaires
- `/api/bookings`: Gestion des réservations
- `/api/customers`: Gestion des clients

## Intégration avec Kafka

Ce projet utilise Apache Kafka pour implémenter une architecture orientée événements. Les événements suivants sont
publiés et consommés:

- `booking-created`: Publié lorsqu'une réservation est créée
- `time-slot-created`: Publié lorsqu'un créneau horaire est créé
- `time-slot-booked`: Publié lorsqu'un créneau horaire est réservé
- `time-slot-released`: Publié lorsqu'un créneau horaire est libéré

### Prérequis pour Kafka

- Docker et Docker Compose

### Démarrage de Kafka

  ```bash
  docker-compse up -d
  ```

#### Cela démarrera:

 - Zookeeper sur le port 2181
 - Kafka sur le port 9092
 - Kafka UI sur le port 8090 (accessible via http://localhost:8090)

### Fonctionnalités basées sur les événements

 - Notifications en temps réel lors de la création
 - Architecture découplée permettant une meilleure scalabilité

## Choix d'implémentation
 - **WebFlux:** Choix d'une approche non-bloquante pour une meilleure scalabilité
 - **R2DBC:** Persistence réactive pour compléter l'approche non-bloquante
 - **Kafka:** Communication asynchrone et architecture orientée événements
 - **Architecture hexagonale:** Séparation claire entre le domaine et l'infrastructure
 - **H2:** Base de données en mémoire pour faciliter les tests et le développement

## Données de tests:
Dans le fichier "data.sql" on a mis des commandes pour l'insertion des "Delivery_moes", "Time_slots" et "Customers".
Il suffit donc de créer des bookings.