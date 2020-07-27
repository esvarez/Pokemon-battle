# Pokemon-battle

Service built with java 11 to get stats and compare pokemon using [Pokemon Api](https://pokeapi.co/) to get the info.

Functions:
- Who have advantage
- Common moves among them

## Getting started

### Gradle 
The service use gradle, if you have java 11 and gradle install you can run it using `./gradlew bootRun`

### Dockers
If you have dockers you can build the image and run it with the next commands (Need gradle to compile the app):
- `./gradlew build`
- `docker build -t pokemon-battle .`
- `docker run --rm -p 8080:8080 pokemon-battle`

Or download the image
- `docker run --rm -p 8080:8080 suarez/pokemon-battle-java`

Check your localhost on the port 8080. In the root [http:://localhost:8080/](http:://localhost:8080/) you will see a 
swagger interface to test the endpoints. If you prefer you can see the endpoints:

- `/api/battle/{pokemonId}/vs/{pokemonId2}`
- `/api/compare?pokemons={pokemonId1,pokemonId2,...,pokemonIdN}&page={numberOfPage}&lang={lang}&limit={movesToShow}`

