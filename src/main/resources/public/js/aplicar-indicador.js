$(function(){
    $("#empresas, #periodos").on("change", function()   {
        var empresa = $("#empresas").val()[0];
        var periodo = $("#periodos").val();
        if(empresa && periodo)  {
            $("#indicadores").prop("disabled", false);
            $.get("/empresas/" + empresa + "/indicadores?periodo=" + periodo, function(respuesta) {
                var options = "";
                var indicadores = JSON.parse(respuesta);
                console.log(indicadores);
                for(var i=0; i<indicadores.length; i++) {
                    options += '<option value="' + indicadores[i].name + '">' + indicadores[i].name + '</option>';
                }
                $("#indicadores").html(options);
            });
        }
        else $("#indicadores").prop("disabled", true);
    });

    $("#indicadores").on("change", function()   {
        var empresa = $("#empresas").val()[0];
        var periodo = $("#periodos").val();
        var indicador = $("#indicadores").val()[0];
        $.get("/indicadores/" + indicador + "/empresas/" + empresa + "/periodos/" + periodo, function(resultado)    {
            $("#modal-aplicar-nombre").html(indicador);
            $("#modal-aplicar-empresa").html(empresa);
            $("#modal-aplicar-periodo").html(periodo);
            $("#modal-aplicar-valor").html(resultado);
            $("#modal-aplicar").modal("show");
        });
    });
});