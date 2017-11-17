package main;

import controllers.HomeController;
import controllers.CuentaController;
import controllers.IndicadorController;
import controllers.SessionController;
import spark.template.handlebars.HandlebarsTemplateEngine;

import com.google.gson.Gson;

import static spark.Spark.*;

public class Routes	{
	
	public static void main(String[] args) {
		Gson gson = new Gson();

		System.out.println("Iniciando servidor");

		HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
		
		port(8080);
		staticFileLocation("/public");
		
		HomeController home = new HomeController();
		get("/", home::show, engine);
		
		SessionController session = new SessionController();
		get("/login", session::showLogin, engine);
		post("/login", session::validateLogin);
		get("/logout", session::logout);

		CuentaController cuentas = new CuentaController();
		get("/cuentas", cuentas::getAll, engine);

		get("/empresas/:nombre/cuentas", cuentas::getCuentasByNombreDeEmpresaAndPeriodo, gson::toJson);

		IndicadorController indicadores = new IndicadorController();
		get("/indicadores", indicadores::show, engine);
		post("/indicadores", indicadores::crear);

        get("/indicadores/aplicar", indicadores::showEvaluarIndicador, engine);
		get("/indicadores/:nombre", indicadores::getIndicador, gson::toJson);
        put("/indicadores", indicadores::modificar);
		delete("/indicadores/:nombre", indicadores::borrar);

		get("/api/indicadores", indicadores::all, gson::toJson);

		get("/indicadores/:indicador/empresas/:empresa/periodos/:periodo", indicadores::aplicar, gson::toJson);

		get("/empresas/:nombre/indicadores", indicadores::getIndicadoresByEmpresaAndPeriodo, gson::toJson);
	}
}
