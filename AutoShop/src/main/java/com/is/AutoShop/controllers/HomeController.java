package com.is.AutoShop.controllers;


import com.is.AutoShop.model.Car;
import com.is.AutoShop.model.Client;
import com.is.AutoShop.model.Employee;
import com.is.AutoShop.services.CarServiceInterface;
import com.is.AutoShop.services.ClientServiceInterface;
import com.is.AutoShop.services.EmployeServiceInterface;
import com.is.AutoShop.services.EmployeeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


@Controller
public class HomeController {
    private ClientServiceInterface clientServiceInterface;
    private CarServiceInterface carServiceInterface;
    private EmployeServiceInterface employeeServiceInterface;
    private boolean showDescription = false;
    private int orderNo = 0;

    public HomeController(ClientServiceInterface clientServiceInterface, CarServiceInterface carServiceInterface, EmployeServiceInterface employeeServiceInterface) {
        this.clientServiceInterface = clientServiceInterface;
        this.carServiceInterface = carServiceInterface;
        this.employeeServiceInterface = employeeServiceInterface;
    }

    @RequestMapping(value = {"/about","about"}, method = RequestMethod.GET)
    public String handleAbout(Model model){
        if (!clientServiceInterface.isAnyOneConnected()) return "redirect:/login";
        model.addAttribute("clientLogged",clientServiceInterface.getLoggedClient());
        return "about";
    }

    @RequestMapping(value = {"/logout","logout"}, method = RequestMethod.POST)
    public String handleLogout(){
        clientServiceInterface.logout();
        this.showDescription = false;
        return "redirect:/login";
    }

    @RequestMapping(value = {"contact","/contact"},method = RequestMethod.GET)
    public String handleContact(Model model){
        if (!clientServiceInterface.isAnyOneConnected()) return "redirect:/login";
        model.addAttribute("clientLogged",clientServiceInterface.getLoggedClient());
        return "contact";
    }

    @RequestMapping(value = {"services","/services"},method = RequestMethod.GET)
    public String handleServices(Model model){
        if (!clientServiceInterface.isAnyOneConnected()) return "redirect:/login";
        if (this.showDescription) {
            model.addAttribute("showD",true);
        }
        else{
            model.addAttribute("showD",false);
        }
        model.addAttribute("clientLogged",clientServiceInterface.getLoggedClient());
        List<Car> cars = carServiceInterface.getAllCars();
        model.addAttribute("cars",cars);
        return "services";
    }

    @RequestMapping(value = {"/home","home"},method = RequestMethod.GET)
    public String solveHomeReq(Model model){
        if (!clientServiceInterface.isAnyOneConnected()) return "redirect:/login";
        model.addAttribute("clientLogged",clientServiceInterface.getLoggedClient());
        return "home";
    }

    @RequestMapping(value = {"gett","/gett"},method = RequestMethod.GET)
    public String solveGet(Model model){
        this.showDescription = !this.showDescription;
        return "redirect:/services";
    }

    @RequestMapping(value = {"/services/{carId}/makeOrder"}, method = RequestMethod.POST)
    public String handleMakeOrderGet(@PathVariable(name = "carId")String carId){
        Car car = carServiceInterface.findCar(carId);
        Client client = clientServiceInterface.getLoggedClient();
        car.setOrdered(true);
        carServiceInterface.addNewCar(car);

        List<Employee> employeeList = employeeServiceInterface.getAllEmployees();
        List<Employee> AddedEmployeeList = new ArrayList<>();

        int randomNumber = ThreadLocalRandom.current().nextInt(1,3);
        System.out.println(randomNumber);
        int verified = 0;

        while (verified < randomNumber){
            int randomNumberIndex = ThreadLocalRandom.current().nextInt(0,employeeServiceInterface.getAllEmployees().size());
            if (!AddedEmployeeList.contains(employeeList.get(randomNumberIndex))){
                AddedEmployeeList.add(employeeList.get(randomNumberIndex));
                verified++;
            }
        }

        //AddedEmployeeList.forEach(employee -> System.out.println(employee.toString()));

        String path = System.getProperty("user.dir") + "\\src\\main\\java\\com\\is\\AutoShop\\orders";
        Desktop desktop = Desktop.getDesktop();
        try{
            File file = new File(path + "\\" + "Order" + orderNo + ".doc");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("                              Order Number :  " + orderNo);
            for(int i = 0 ; i < 3 ; i ++){
                bufferedWriter.newLine();
            }

            orderNo++;

            bufferedWriter.write("Car Informations" +"\n");
            bufferedWriter.newLine();
            bufferedWriter.write(" Car Id : " + car.getCarID() + "\n");
            bufferedWriter.write(" Car Make: " + car.getMake() +"\n");
            bufferedWriter.write(" Car Model : " + car.getModel() +"\n");
            bufferedWriter.write(" Car Fueltype : " + car.getFuelType() +"\n");
            bufferedWriter.write(" Car Fabyear : " + car.getFabYear() +"\n");
            bufferedWriter.write(" Car Price : " + car.getPrice() +"\n\n\n");

            bufferedWriter.write("Client Information" +"\n\n");

            bufferedWriter.write(" Client Id : " + client.getClientId()  +"\n");
            bufferedWriter.write(" Client Username : " + client.getUserName()  +"\n");
            bufferedWriter.write(" Client Email : " + client.getEmail()  +"\n");
            bufferedWriter.write(" Client First Name : " + client.getFirstName()  +"\n");
            bufferedWriter.write(" Client Last Name : " + client.getLastName()  +"\n\n\n");

            AddedEmployeeList.forEach(employee -> {
                try {
                    bufferedWriter.write("Employee" + "\n\n" + " Employee First Name : " + employee.getFirstName() + "\n" + " Employee Last Name : " + employee.getLastName() + "\n\n\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            for (int i = 0; i < 5; i++){
                bufferedWriter.newLine();
            }

            bufferedWriter.write("\t Termeni & Conditii \n" +
                    "Prin finalizarea Comenzii Cumparatorul consimte ca toate datele furnizate de acesta, necesare procesului de cumparare, sunt corecte, complete si adevarate la data plasarii Comenzii.\n" +
                    " Preturile Bunurilor si Serviciilor afisate in cadrul magazinului includ T.V.A. conform legislatiei in vigoare.\n" +
                    "Vanzatorul nu poate fi responsabil pentru daune de orice fel pe care Cumparatorul sau oricare terta parte o poate suferi ca rezultat al indeplinirii de catre Vanzator a oricarei din obligatiile sale conform Comenzii si pentru daune care rezulta din utilizarea Bunurilor si Serviciilor dupa livrare si in special pentru pierderea acestora. \n" +
                    "Puteti sa ne contactati la numarul de telefon 0765332331 in reteaua Cosmote dar si la adresa de email mirel@depozit.transport.curier.romania.com\n"  +
                    "Adresa Depozit : Strada Lanternei,Sectorul 6,Bucuresti\n");
            bufferedWriter.flush();
            bufferedWriter.close();
            desktop.open(file);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "redirect:/services";

    }



}
