package edu.pucmm;


import edu.pucmm.exception.DuplicateEmployeeException;
import edu.pucmm.exception.EmployeeNotFoundException;
import edu.pucmm.exception.InvalidSalaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author me@fredpena.dev
 * @created 02/06/2024  - 00:47
 */

public class EmployeeManagerTest {

    private EmployeeManager employeeManager;
    private Position juniorDeveloper;
    private Position seniorDeveloper;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    public void setUp() {
        employeeManager = new EmployeeManager();
        juniorDeveloper = new Position("1", "Junior Developer", 30000, 50000);
        seniorDeveloper = new Position("2", "Senior Developer", 60000, 90000);
        employee1 = new Employee("1", "John Doe", juniorDeveloper, 40000);
        employee2 = new Employee("2", "Jane Smith", seniorDeveloper, 70000);
        employeeManager.addEmployee(employee1);
    }

    @Test
    public void testAddEmployee() {
        // TODO: Agregar employee2 al employeeManager y verificar que se agregó correctamente.
        employeeManager.addEmployee(employee2);
        // - Verificar que el número total de empleados ahora es 2.
        assertEquals(2, employeeManager.getEmployees().size());
        // - Verificar que employee2 está en la lista de empleados.
        assertTrue(employeeManager.getEmployees().contains(employee2));
    }

    @Test
    public void testRemoveEmployee() {
        // TODO: Eliminar employee1 del employeeManager y verificar que se eliminó correctamente.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Eliminar employee1 del employeeManager.
        employeeManager.removeEmployee(employee1);
        // - Verificar que el número total de empleados ahora es 1.
        assertEquals(1, employeeManager.getEmployees().size());
        // - Verificar que employee1 ya no está en la lista de empleados.
        assertFalse(employeeManager.getEmployees().contains(employee1));
    }

    @Test
    public void testCalculateTotalSalary() {
        // TODO: Agregar employee2 al employeeManager y verificar el cálculo del salario total.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Verificar que el salario total es la suma de los salarios de employee1 y employee2.
        double expectedTotalSalary = employee1.getSalary() + employee2.getSalary();
        assertEquals(expectedTotalSalary, employeeManager.calculateTotalSalary());
    }

    @Test
    public void testUpdateEmployeeSalaryValid() {
        // TODO: Actualizar el salario de employee1 a una cantidad válida y verificar la actualización.
        // - Actualizar el salario de employee1 a 45000.
        employee1.setSalary(45000);
        // - Verificar que el salario de employee1 ahora es 45000.
        assertEquals(45000, employee1.getSalary());
        // - Verificar que no se lanza ninguna excepción con ese salario
        assertDoesNotThrow(() -> employeeManager.updateEmployeeSalary(employee1, 45000));
    }

    @Test
    public void testUpdateEmployeeSalaryInvalid() {
        // TODO: Intentar actualizar el salario de employee1 a una cantidad inválida y verificar la excepción.
        // - Intentar actualizar el salario de employee1 a 60000 (que está fuera del rango para Junior Developer).
        // - Verificar que se lanza una InvalidSalaryException.
        assertThrows(InvalidSalaryException.class, () -> employeeManager.updateEmployeeSalary(employee1, 60000));
    }

    @Test
    public void testUpdateEmployeeSalaryEmployeeNotFound() {
        // TODO: Intentar actualizar el salario de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar el salario de employee2 a 70000.
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> employeeManager.updateEmployeeSalary(employee2, 70000));
    }

    @Test
    public void testUpdateEmployeePositionValid() {
        // TODO: Actualizar la posición de employee2 a una posición válida y verificar la actualización.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Actualizar la posición de employee2 a seniorDeveloper.
        employeeManager.updateEmployeePosition(employee2, seniorDeveloper);
        // - Verificar que la posición de employee2 ahora es seniorDeveloper.
        assertEquals(seniorDeveloper, employee2.getPosition());
    }

    @Test
    public void testUpdateEmployeePositionInvalidDueToSalary() {
        // TODO: Intentar actualizar la posición de employee1 a seniorDeveloper y verificar la excepción.
        // - Intentar actualizar la posición de employee1 a seniorDeveloper.
        // - Verificar que se lanza una InvalidSalaryException porque el salario de employee1 no está dentro del rango para Senior Developer.
        assertThrows(InvalidSalaryException.class, () -> employeeManager.updateEmployeePosition(employee1, seniorDeveloper));
    }

    @Test
    public void testUpdateEmployeePositionEmployeeNotFound() {
        // TODO: Intentar actualizar la posición de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar la posición de employee2 a juniorDeveloper.
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> employeeManager.updateEmployeePosition(employee2, juniorDeveloper));
    }

    @Test
    public void testIsSalaryValidForPosition() {
        // TODO: Verificar la lógica de validación de salario para diferentes posiciones.
        // - Verificar que un salario de 40000 es válido para juniorDeveloper.
        assertTrue(employeeManager.isSalaryValidForPosition(juniorDeveloper, 40000));
        // - Verificar que un salario de 60000 no es válido para juniorDeveloper.
        assertFalse(employeeManager.isSalaryValidForPosition(juniorDeveloper, 60000));
        // - Verificar que un salario de 70000 es válido para seniorDeveloper.
        assertTrue(employeeManager.isSalaryValidForPosition(seniorDeveloper, 70000));
        // - Verificar que un salario de 50000 no es válido para seniorDeveloper.
        assertFalse(employeeManager.isSalaryValidForPosition(seniorDeveloper, 50000));
    }

    @Test
    public void testAddEmployeeWithInvalidSalary() {
        // TODO: Intentar agregar empleados con salarios inválidos y verificar las excepciones.
        // - Crear un empleado con un salario de 60000 para juniorDeveloper.
        Employee employee3 = new Employee("3", "Thiago Martinez", juniorDeveloper, 60000);
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        assertThrows(InvalidSalaryException.class, () -> employeeManager.addEmployee(employee3));
        // - Crear otro empleado con un salario de 40000 para seniorDeveloper.
        Employee employee4 = new Employee("4", "Diogenes Martinez", seniorDeveloper, 40000);
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        assertThrows(InvalidSalaryException.class, () -> employeeManager.addEmployee(employee4));
    }

    @Test
    public void testRemoveExistentEmployee() {
        // TODO: Eliminar un empleado existente y verificar que no se lanza una excepción.
        // - Eliminar employee1 del employeeManager.
        employeeManager.removeEmployee(employee1);
        // - Verificar que no se lanza ninguna excepción.
        assertFalse(employeeManager.getEmployees().contains(employee1));
    }

    @Test
    public void testRemoveNonExistentEmployee() {
        // TODO: Intentar eliminar un empleado no existente y verificar la excepción.
        // - Intentar eliminar employee2 (no agregado al manager).
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> employeeManager.removeEmployee(employee2));
    }

    @Test
    public void testAddDuplicateEmployee() {
        // TODO: Intentar agregar un empleado duplicado y verificar la excepción.
        // - Intentar agregar employee1 nuevamente al employeeManager.
        // - Verificar que se lanza una DuplicateEmployeeException.
        assertThrows(DuplicateEmployeeException.class, () -> employeeManager.addEmployee(employee1));
    }

    // test para validar que el id del empleado sea el ingresado
    @Test
    public void testEmployeeId() {
        assertEquals("1", employee1.getId());
        assertEquals("2", employee2.getId());
    }

    // test para validar que el name del empleado sea el ingresado
    @Test
    public void testEmployeeName() {
        assertEquals("John Doe", employee1.getName());
        assertEquals("Jane Smith", employee2.getName());
    }

    // test para validar que setId del empleado
    @Test
    public void testSetEmployeeId() {
        employee1.setId("3");
        assertEquals("3", employee1.getId());
    }

    // test para validar que setName del empleado
    @Test
    public void testSetEmployeeName() {
        employee1.setName("John Smith");
        assertEquals("John Smith", employee1.getName());
    }

    // test para validar que setId de la posición
    @Test
    public void testSetPositionId() {
        juniorDeveloper.setId("3");
        assertEquals("3", juniorDeveloper.getId());
    }

    // test para validar que setName de la posición
    @Test
    public void testSetPositionName() {
        juniorDeveloper.setName("Junior Software Engineer");
        assertEquals("Junior Software Engineer", juniorDeveloper.getName());
    }

    // test para validar getEmployees
    @Test
    public void testGetEmployees() {
        assertNotNull(employeeManager.getEmployees());
        assertTrue(employeeManager.getEmployees().contains(employee1));
        assertFalse(employeeManager.getEmployees().isEmpty());
    }
}
