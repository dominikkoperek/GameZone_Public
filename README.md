
![Logo](https://i.imgur.com/JUUVWtn.png)

## 🛠 Techstack
Java, Spring, Thymeleaf, Hibernate, SQL, Liquibase, Javascript, HTML, CSS


# GameZone (work in progress)


GameZone is an online video game service, inspired by platforms like gryonline.pl. The service was developed using technologies such as Java, Spring, Hibernate, SQL, JavaScript, Liquibase, and Thymeleaf.

Currently, on the service, you can:
---
-   Display the main page with a list of games along with their details.
-   View a game, read its detailed description, and watch its trailer.
-   Display a list of games based on category or the platform they were released on.
-   View a collection of game-producing companies and all the games associated with them.
-   In the administrative panel, you can add a new game, category, platform, and company.




## API Reference
The service utilizes a simple API and, in conjunction with AJAX technology, it allows for asynchronous checking of values in the database for an even better user experience

----
#### Check company availability

```http
  GET /api/company/availability
  example: GET /api/company/availability?name=ExampleCompanyName
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Not Required**. Name of the company |

#### All companies

```http
  GET /api/company/allCompanies
```
#### All companies that are producers

```http
  GET /api/company/allProducers
```
#### All companies that are publishers

```http
  GET /api/company/allPublishers

```


#### Get all countries
```http
  GET /api/country/allCountries
```

#### Game category availability
       
```http
  GET /api/category/availability
  example: GET /api/company/availability?name=ExampleCompanyName
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `category` | `string` | **Not Required**. Name of the game category |

#### Get all game categories
```http
  GET /api/games/company
  example: GET /api/games/company?id=1
```

#### Find game by company id
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `string` | **Not Required**. Id of the company |

#### All games
       
```http
  GET /api/games/allGames
```
#### Check game availability
```http
  GET /api/games/availability
  example: GET /api/games/availability?title=ExampleTitle
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `title` | `string` | **Not Required**. Game title |

#### Check game platform availability
```http
  GET /api/platform/availability
  example: GET/api/platform/availability?platform=ExamplePlatform
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `platform` | `string` | **Not Required**. Game platform name |

#### Get all game platforms
```http
  GET /api/games/allPlatforms
```
## Screenshots
Game list page
![App Screenshot](https://i.imgur.com/p7WLQhI.png)
Game page
![App Screenshot](https://i.imgur.com/bA6YDD8.png)
Company list page
![App Screenshot](https://i.imgur.com/xWt7SWM.png)
Company page
![App Screenshot](https://i.imgur.com/7j4IUaA.png)
Admin panel
![App Screenshot](https://i.imgur.com/T2GLsHQ.png)
Admin part of add game form
![App Screenshot](https://i.imgur.com/leVno9a.png)




## Roadmap


- Add to admin panel content edit options/ users options (ban,shadow ban)
- Add comment system
- Add recommender algorithm
- Add remove option for content (game/category/platform)
- Add user sites

- Implement game rating system
  
~~- Implement Company seraching buttons~~

~~- Implement adding company pictures in form~~

~~- Improve validation (server site)~~

~~- Add spring security (login system)~~

