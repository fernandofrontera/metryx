$(function()    {
    $("#combo-empresas, #combo-periodos").on("change", function()    {
        var empresa = $("#combo-empresas").val();
        var periodo = $("#combo-periodos").val();
        if(empresa != 0 && periodo != 0)  {
            $.ajax({
                url: "./empresas/" + empresa + "/cuentas?periodo=" + periodo,
                method: "get",
                success: function(respuesta)    {
                    var cuentas = JSON.parse(respuesta);
                    var celdas = "";
                    for(var i=0; i < cuentas.length; i++) {
                        celdas += "<tr><td>" + cuentas[i].companyName + "</td><td>"+ cuentas[i].cuenta.nombre + "</td><td>" + cuentas[i].period + "</td><td>" + cuentas[i].value + "</td></tr>";
                    }
                    $("#cuentas tbody").html(celdas);
                },
                error: function(error)  {
                    console.log(error);
                }
            });
        }
    });
});