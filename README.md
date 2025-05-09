# StuffTracker
StuffTracker is a stupid little inventory management for my house, so I won't forget every time where I've put something down.
It uses *KTOR* server, a simple *bearer* authentication system and uses exposed for interoperability across databases.

## ER Schema

```mermaid
erDiagram

    User {
        string uid PK
        string name
        string surname
        string email
        string pwHash
        enum role
    }
        
    UserGroup {
        string uid PK
        string name UK
    }

    Item {
        string uid PK
        string name UK
        string description
        string shelfUid FK
        string owner FK
        string group FK
    }

    Room {
        string uid PK
        string name UK
    }

    Storage {
        string uid PK
        string name UK
        string roomUid FK
    }

    Shelf {
        string uid PK
        string storageUid FK
        int number
        int type
    }

    Checkout {
        string uid PK
        string itemUid FK
        string userUid FK
        datetime start
        datetime end
    }
    
    Tag {
        string uid PK
        string name UK
    }

    Item ||--o{ Shelf : "stored_on"
    Shelf ||--o{ Storage : "in"
    Storage ||--o{ Room : "located_in"
    Checkout }o--|| Item : "takes"
    Checkout }o--|| User : "by"
    Item }o--o{ Tag : "is tagged as"
    User }|--o{ UserGroup : "is in"
    Item ||--o| User  : "owned by"
    Item ||--o| UserGroup  : "commonly owned by"
```


# Ktor stuff...

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Exposed](https://start.ktor.io/p/exposed)                             | Adds Exposed database to your application                                          |
| [Authentication](https://start.ktor.io/p/auth)                         | Provides extension point for handling the Authorization header                     |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

