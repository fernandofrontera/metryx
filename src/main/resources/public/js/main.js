let titles = {
	"/cuentas": "Cuentas",
	"/indicadores": "Indicadores",
	"/indicadores/nuevo": "Crear indicador",
	"/methodologies": "Metodologías",
	"/login": "Iniciar sesión"
};

function setCurrentPage() {
	let pathname = document.location.pathname;
	
	let title = titles[pathname];
	document.title = title !== undefined ? "Metryx \u2022 " + title : "Metryx";
	
	let page = pathname.substring(1);
	let slash = page.indexOf("/");
	if(slash != -1) page = page.substring(0, slash);
	$("#" + page + "-navitem").addClass("active");
}

function main() {
	setCurrentPage();
}

main();