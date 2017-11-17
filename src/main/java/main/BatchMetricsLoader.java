package main;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.repositorios.RepositorioDeEmpresas;
import model.repositorios.fuentes.jpa.FuenteJPADeEmpresa;
import model.repositorios.fuentes.json.FuenteJsonDeEmpresa;

public class BatchMetricsLoader {
	
	private final String batchDir = "batch-metrics";
	private FuenteJPADeEmpresa jpa = new FuenteJPADeEmpresa();

	public static void main(String[] args) {
		new BatchMetricsLoader(); 
	}
	
	private BatchMetricsLoader() {
		File dir = batchLocation();
		List<File> files = Arrays.asList(dir.listFiles()).stream()
				.filter(f -> f.getName().endsWith(".json")).collect(Collectors.toList());
		if(files.size() == 0) return;
		jpa.cargar();
		files.forEach(this::processFile);
	}
	
	private void processFile(File file) {
		FuenteJsonDeEmpresa source = new FuenteJsonDeEmpresa(batchDir + "/" + file.getName());
		RepositorioDeEmpresas repo = new RepositorioDeEmpresas(source);
		jpa.guardar(repo, repo.todos());
		file.delete();
	}
	
	private File batchLocation() {
		String path = BatchMetricsLoader.class.getClassLoader().getResource("").getPath() + batchDir;
		File dir = new File(path);
		if(!dir.exists()) dir.mkdir();
		return dir;
	}

}
