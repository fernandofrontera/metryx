/**
 * Correr para recrear la base de datos.
 */

package main;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

public class DatabaseBuilder {

	public static void main(String[] args) {
		PerThreadEntityManagers.getEntityManager();
	}

}
