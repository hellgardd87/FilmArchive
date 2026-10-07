package com.hellgardd.filmarchive;
import com.hellgardd.filmarchive.repository.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CatalogIT {
 @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:17-alpine").withDatabaseName("cinema_db");
 @DynamicPropertySource static void database(DynamicPropertyRegistry r) {
 r.add("spring.datasource.url",POSTGRES::getJdbcUrl); r.add("spring.datasource.username",POSTGRES::getUsername);
 r.add("spring.datasource.password",POSTGRES::getPassword); r.add("spring.jpa.hibernate.ddl-auto",()->"create-drop");
 }
 @LocalServerPort int port;
 @Autowired FilmRepository films; @Autowired PersonRepository people; @Autowired GenreRepository genres;
 @Autowired CountryRepository countries; @Autowired AgeLimitRepository ages; @Autowired ShootingQualityRepository qualities;
 private final HttpClient client=HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER).build();
 HttpResponse<String> get(String path) throws Exception { return client.send(HttpRequest.newBuilder(URI.create("http://localhost:"+port+path)).GET().build(),HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)); }
 HttpResponse<String> post(String path,Map<String,String> fields) throws Exception {
 String body=fields.entrySet().stream().map(e -> URLEncoder.encode(e.getKey(),StandardCharsets.UTF_8)+"="+URLEncoder.encode(e.getValue(),StandardCharsets.UTF_8)).collect(Collectors.joining("&"));
 return client.send(HttpRequest.newBuilder(URI.create("http://localhost:"+port+path)).header("Content-Type","application/x-www-form-urlencoded").POST(HttpRequest.BodyPublishers.ofString(body)).build(),HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
 }
 @Test void fullCatalogWorkflowWithRealPostgresAndTemplates() throws Exception {
 for(String route:List.of("/","/edit","/search","/aggregate","/info")) assertEquals(200,get(route).statusCode(),route);
 long initialFilms=films.count(),initialActors=people.count(),initialLinks=films.countActorLinks();
 assertEquals(4,initialFilms); assertEquals(8,initialLinks);
 assertEquals(302,post("/add_person",Map.of("fullName","Тестовый актёр")).statusCode());
 var actor=people.findAllByOrderByFullNameAsc().stream().filter(p->p.getFullName().equals("Тестовый актёр")).findFirst().orElseThrow();
 var genre=genres.findAllByOrderByTitleAsc().stream().filter(g->g.getTitle().equals("Фантастика")).findFirst().orElseThrow();
 var country=countries.findAllByOrderByTitleAsc().stream().filter(c->c.getTitle().equals("США")).findFirst().orElseThrow();
 Map<String,String> form=new HashMap<>();
 form.put("title","Тестовый фильм"); form.put("description","Описание"); form.put("duration","100"); form.put("budget","1000000"); form.put("totalBoxOffice","2000000"); form.put("releaseDate","2020-01-01");
 form.put("genreId",genre.getId().toString()); form.put("countryId",country.getId().toString()); form.put("ageLimitId",ages.findAllByOrderByCategoryAsc().getFirst().getId().toString()); form.put("shootingQualityId",qualities.findAllByOrderByTypeAsc().getFirst().getId().toString()); form.put("actorIds",actor.getId().toString());
 assertEquals(302,post("/add_film",form).statusCode());
 var film=films.findAllByOrderByTitleAsc().stream().filter(f->f.getTitle().equals("Тестовый фильм")).findFirst().orElseThrow();
 assertEquals(initialFilms+1,films.count()); assertEquals(initialLinks+1,films.countActorLinks());
 String html=get("/edit_film/"+film.getId()).body(); assertTrue(html.contains("Тестовый фильм")); assertTrue(html.contains("checked"));
 var json=get("/get_countries?genre="+genre.getId()); assertEquals(200,json.statusCode()); assertTrue(json.body().contains("США"));
 assertTrue(get("/search?genre="+genre.getId()+"&country="+country.getId()).body().contains("Тестовый фильм"));
 form.put("duration","301"); String invalid=post("/update_film/"+film.getId(),form).body(); assertTrue(invalid.contains("300")); assertEquals(100,films.findById(film.getId()).orElseThrow().getDuration());
 form.put("duration","110"); form.put("title","Обновлённый фильм"); assertEquals(302,post("/update_film/"+film.getId(),form).statusCode());
 assertEquals("Обновлённый фильм",films.findById(film.getId()).orElseThrow().getTitle());
 var otherActor=people.findAllByOrderByFullNameAsc().stream().filter(p -> !p.getId().equals(actor.getId())).findFirst().orElseThrow();
 form.put("actorIds",otherActor.getId().toString()); assertEquals(302,post("/update_film/"+film.getId(),form).statusCode());
 assertEquals(otherActor.getFullName(),films.findById(film.getId()).orElseThrow().getActorNames());
 form.put("actorIds",actor.getId().toString()); assertEquals(302,post("/update_film/"+film.getId(),form).statusCode());
 assertEquals(302,post("/delete_film/"+film.getId(),Map.of()).statusCode()); assertTrue(people.existsById(actor.getId()));
 assertEquals(302,post("/add_film",form).statusCode());
 film=films.findAllByOrderByTitleAsc().stream().filter(f -> f.getTitle().equals("Обновлённый фильм")).findFirst().orElseThrow();
 assertEquals(302,post("/delete_person/"+actor.getId(),Map.of()).statusCode()); assertTrue(films.findById(film.getId()).orElseThrow().getActors().isEmpty()); assertEquals(initialLinks,films.countActorLinks());
 assertEquals(302,post("/delete_film/"+film.getId(),Map.of()).statusCode()); assertEquals(initialFilms,films.count()); assertEquals(initialActors,people.count());
 assertEquals(404,get("/edit_film/999999").statusCode()); assertEquals(400,get("/get_countries?genre=abc").statusCode());
 assertEquals(200,get("/aggregate").statusCode());
 }
}
