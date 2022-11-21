package br.df.lhporto.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.log().all()
			.statusCode(200)
		;
	}
	
	@Test
	public void deveAdicionarTarefaSucesso() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2023-01-01\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201)
	;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2012-11-20\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
	;
	}
}