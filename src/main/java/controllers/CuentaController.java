package controllers;

import java.util.HashMap;
import java.util.List;

import model.CuentaDeEmpresa;
import model.Empresa;
import model.repositorios.Repositorios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CuentaController {

    public ModelAndView getAll(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();
        List<Empresa> empresas = Repositorios.obtenerRepositorioDeEmpresas().todos();

        List<Short> periodos = Repositorios.obtenerRepositorioDeEmpresas().getAllPeriodos();

        map.put("empresas", empresas);
        map.put("periodos", periodos);

        return new ModelAndView(map, "cuentas.hbs");
    }

    public List<CuentaDeEmpresa> getCuentasByNombreDeEmpresaAndPeriodo(Request request, Response response) {
        String nombreEmpresa = request.params("nombre");
        Short periodo = Short.valueOf(request.queryParams("periodo"));

        List<CuentaDeEmpresa> cuentas = Repositorios.obtenerRepositorioDeEmpresas().encontrar(nombreEmpresa).obtenerCuentas(periodo);

        return cuentas;
    }
}