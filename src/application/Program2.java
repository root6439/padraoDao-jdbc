package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {

		System.out.println("=== Teste 1 - department insert ===");
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Department department = new Department(null, "Adm");
		departmentDao.insert(department);
		System.out.println("Inserido, id: " + department.getId());
		
		System.out.println("\n=== Teste 2 - department update ===");
		department.setName("Seila");
		departmentDao.update(department);
		System.out.println("Atualizado");
		
		System.out.println("\n=== Teste 3 - department delete ===");
		departmentDao.deleteById(11);
		System.out.println("Deletado");
		
		System.out.println("\n=== Teste 4 - department findById ===");
		Department dep = departmentDao.findById(3);
		System.out.println(dep);
		
		System.out.println("\n=== Teste 5 - department findAll ===");
		List<Department> list = departmentDao.findAll();
		list.forEach(System.out::println);
		
		
		
		
		
	}

}
