package com.is.AutoShop.controllers;

import com.is.AutoShop.model.Car;
import com.is.AutoShop.model.Client;
import com.is.AutoShop.model.Employee;
import com.is.AutoShop.model.FuelType;
import com.is.AutoShop.services.CarServiceInterface;
import com.is.AutoShop.services.ClientServiceInterface;
import com.is.AutoShop.services.EmployeServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@Controller
public class AdminController {
    private ClientServiceInterface clientServiceInterface;
    private CarServiceInterface carServiceInterface;
    private EmployeServiceInterface employeeServiceInterface;
    private boolean toggleClient = false;
    private boolean toggleEmployee = false;
    private boolean toggleCar = false;
    private int variabila = 0;

    public AdminController(ClientServiceInterface clientServiceInterface, CarServiceInterface carServiceInterface, EmployeServiceInterface employeeServiceInterface) {
        this.clientServiceInterface = clientServiceInterface;
        this.carServiceInterface = carServiceInterface;
        this.employeeServiceInterface = employeeServiceInterface;
    }

    @RequestMapping(value = {"/adminHome","adminHome"}, method = RequestMethod.GET)
    public String handleAdminGet(){
        if(!clientServiceInterface.isAnyOneConnected()) {
            return "redirect:/login";
        }

        if(!clientServiceInterface.getLoggedClient().isAdmin()){
            return "redirect:/login";
        }

            return "adminHome";

    }

    @RequestMapping(value={"/adminEdit", "adminEdit"}, method = RequestMethod.GET)
    public String handleEditPageGet(){
        return "adminEdit";
    }

    @RequestMapping(value={"/adminHome/addClient"}, method = RequestMethod.POST)
    public String handleAddClient(@ModelAttribute(name = "client")Client client,@ModelAttribute(name = "administrator")String administrator){
        Client addedClient = new Client(client.getUserName(),client.getPassword(),client.getEmail(),client.getFirstName(),client.getLastName());
        Boolean isAdmin = false;
        if (administrator.equals("YES")) isAdmin=true;
        addedClient.setAdmin(isAdmin);
        clientServiceInterface.addNewClient(addedClient);
        return "redirect:/adminTables";
    }

    @RequestMapping(value = {"/adminTables","adminTables"},method = RequestMethod.GET)
    public String metoda(Model model){

        switch (variabila){
            case 0: {
                this.toggleCar = true;
                this.toggleClient = false;
                this.toggleEmployee = false;
                break;
            }
            case 1:{
                this.toggleCar = false;
                this.toggleClient = true;
                this.toggleEmployee = false;
                break;
            }
            case 2:{
                this.toggleCar = false;
                this.toggleClient = false;
                this.toggleEmployee = true;
                break;
            }
            default:{
                this.toggleCar = true;
                this.toggleClient = false;
                this.toggleEmployee = false;
                break;
            }
        }

        model.addAttribute("toggleClient",this.toggleClient);
        model.addAttribute("toggleCar",this.toggleCar);
        model.addAttribute("toggleEmployee",this.toggleEmployee);
        List<Client> clientList = clientServiceInterface.getAllClients();
        List<Car> carList = carServiceInterface.getAllCars();
        List<Employee> employeeList = employeeServiceInterface.getAllEmployees();
        System.out.println(employeeList);
        model.addAttribute("clients",clientList);
        model.addAttribute("cars",carList);
        model.addAttribute("employees",employeeList);
        return "adminTables";
    }



    @RequestMapping(value = {"/adminHome/adminTables"},method = RequestMethod.GET)
    public String handleReqClients(){
        return "redirect:/adminTables";
    }

    @RequestMapping(value = {"toggle","/toggle"}, method = RequestMethod.GET)
    public String handleToggle(){

        if (this.variabila < 2){
            this.variabila++;
        }
        else this.variabila = 0;


        return "redirect:/adminTables";
    }

    @RequestMapping(value = {"/adminHome/deleteCar"}, method = RequestMethod.POST)
    public String handleDeleteCar(@ModelAttribute(name = "carId")String carId){
            carServiceInterface.deleteCar(carId);
            return "redirect:/adminTables";
    }

    @RequestMapping(value = {"/adminHome/deleteClient"},method = RequestMethod.POST)
    public String handleDeleteClient(@ModelAttribute(name = "clientId")String clientId){
            clientServiceInterface.deleteClient(clientId);
            variabila = 1;
            return  "redirect:/adminTables";
    }

    @RequestMapping(value = {"/adminHome/deleteEmployee"}, method = RequestMethod.POST)
    public String handleDeleteEmployee(@ModelAttribute(name = "employeeId")String employeeId){
        employeeServiceInterface.deleteEmployee(employeeId);
        return "redirect:adminTables";
    }

    @RequestMapping(value = {"/adminHome/addEmployee"}, method = RequestMethod.POST)
    public String handleAddEmployee(@ModelAttribute(name = "employee")Employee employee,@ModelAttribute(name = "salary")String salary){
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String address = employee.getAddress();
        Integer actualSalary = Integer.parseInt(salary);

        employeeServiceInterface.addNewEmployee(new Employee(firstName,lastName,address,actualSalary));
        return "redirect:/adminTables";
    }


    @RequestMapping(value = {"/adminHome/addCar"},method = RequestMethod.POST)
    public String handleAdminAddCar(@ModelAttribute(name = "car")Car car,@ModelAttribute(name = "fuelType")String fuelType, @ModelAttribute(name = "price")String price){
        String make = car.getMake();
        String model = car.getModel();
        String fabYear = car.getFabYear();
        String description = car.getDescription();
        String carImage = car.getCarImage();

        Integer actualPrice = Integer.parseInt(price);
        FuelType actualFuelType = FuelType.valueOf(fuelType);

        Car addedCar = new Car(make,model,actualFuelType,fabYear,actualPrice,description,carImage);
        carServiceInterface.addNewCar(addedCar);

        System.out.println("ADDED " + make + " " + model + " " + fuelType + " " + fabYear + " " + price + " " + description + " " + carImage);

        return "redirect:/adminHome";
    }

    @RequestMapping(value = {"/adminTables/{carId}/editCar"},method = RequestMethod.GET)
    public String handleCarEditReq(@PathVariable("carId")String carID, Model model){
        Car editedCar = carServiceInterface.findCar(carID);
        model.addAttribute("carId",editedCar.getCarID());
        model.addAttribute("make", editedCar.getMake());
        model.addAttribute("model", editedCar.getModel());
        model.addAttribute("fuelType", editedCar.getFuelType().toString());
        model.addAttribute("fabYear", editedCar.getFabYear());
        model.addAttribute("price", editedCar.getPrice().toString());
        model.addAttribute("description", editedCar.getDescription());
        model.addAttribute("carImage",editedCar.getCarImage());

        return "adminEdit";

    }


    @RequestMapping(value = {"/adminEdit/{carId}/editCar"},method = RequestMethod.POST)
    public String handleCarEditPost(@PathVariable("carId")String carId,@ModelAttribute("make")String make,@ModelAttribute("model")String model,@ModelAttribute("fabYear")String fabYear, @ModelAttribute("description")String description,@ModelAttribute("carImage")String carImage, @ModelAttribute(name = "fuelType")String fuelType, @ModelAttribute(name = "price")String price){

        //System.out.println("NOT WORKING + "+ carId + make + model + price + fabYear + description + fuelType);
        Integer actualPrice = Integer.parseInt(price);
        FuelType actualFuelType = FuelType.valueOf(fuelType);

        Car addedCar = new Car(make,model,actualFuelType,fabYear,actualPrice,description,carImage);
        carServiceInterface.deleteCar(carId);
        carServiceInterface.addNewCar(addedCar);
        return "redirect:/adminTables";

    }

    @RequestMapping(value = {"/adminTables/{employeeId}/editEmployee"}, method = RequestMethod.GET)
    public String handleEmployeeEditReq(@PathVariable("employeeId")String employeeId,Model model){
        Employee editedEmployee = employeeServiceInterface.findEmployee(employeeId);
        model.addAttribute("employeeId",editedEmployee.getId());
        model.addAttribute("firstName",editedEmployee.getFirstName());
        model.addAttribute("lastName",editedEmployee.getLastName());
        model.addAttribute("address",editedEmployee.getAddress());
        model.addAttribute("salary",editedEmployee.getSalary());
        return "adminEdit";
    }

    @RequestMapping(value = {"/adminEdit/{employeeId}/editEmployee"}, method = RequestMethod.POST)
    public String handleEmployeeEditPost(@PathVariable("employeeId")String employeeId,@ModelAttribute("employee")Employee employee,@ModelAttribute("salary")String salary){
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String address = employee.getAddress();
        Integer actualSalary = Integer.parseInt(salary);

        employeeServiceInterface.deleteEmployee(employeeId);
        employeeServiceInterface.addNewEmployee(new Employee(firstName,lastName,address,actualSalary));
        return "redirect:/adminTables";
    }

    @RequestMapping(value = {"/adminTables/{clientId}/editClient"}, method = RequestMethod.GET)
    public String handleClientEditReq(@PathVariable("clientId")String clientId, Model model){
        Client editedClient = clientServiceInterface.findClientById(clientId);
        model.addAttribute("clientId",clientId);
        model.addAttribute("clientUserName",editedClient.getUserName());
        model.addAttribute("clientPassword",editedClient.getPassword());
        model.addAttribute("clientEmail",editedClient.getEmail());
        model.addAttribute("clientFirstName",editedClient.getFirstName());
        model.addAttribute("clientLastName",editedClient.getLastName());
        if (editedClient.isAdmin()){
            model.addAttribute("clientAdministrator","YES");
        }
        else{
            model.addAttribute("clientAdministrator","NO");
        }
        return "adminEdit";
    }

    @RequestMapping(value = {"/adminEdit/{clientId}/editClient"}, method = RequestMethod.POST)
    public String handleClientEditPost(@PathVariable("clientId")String clientId,@ModelAttribute(name = "client")Client client,@ModelAttribute(name = "administrator")String administrator){
        Client addedClient = new Client(client.getUserName(),client.getPassword(),client.getEmail(),client.getFirstName(),client.getLastName());
        Boolean isAdmin = false;
        if (administrator.equals("YES")) isAdmin=true;
        addedClient.setAdmin(isAdmin);
        System.out.println(client.getUserName() + client.getPassword() +  client.getEmail() + client.getFirstName() + client.getLastName());
        clientServiceInterface.deleteClient(clientId);
        clientServiceInterface.addNewClient(addedClient);
        return "redirect:/adminTables";

    }











}
