package com.is.AutoShop.config;


import com.is.AutoShop.model.Car;
import com.is.AutoShop.model.Client;
import com.is.AutoShop.model.Employee;
import com.is.AutoShop.model.FuelType;
import com.is.AutoShop.repository.CarRepository;
import com.is.AutoShop.repository.ClientRepository;
import com.is.AutoShop.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = {ClientRepository.class,CarRepository.class, EmployeeRepository.class})
@Configuration
public class ConfigDb {
    @Bean
    CommandLineRunner commandLineRunner(ClientRepository clientRepository, CarRepository carRepository, EmployeeRepository employeeRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
               employeeRepository.deleteAll();

                clientRepository.deleteAll();
                Client client = new Client("admin","a","admin@gmail.com","Andrei","Crisan");
                client.setAdmin(true);
                clientRepository.save(client);

                clientRepository.save(new Client("user", "u","something@yahoo.com","Mitrut","Mirabela"));

                carRepository.deleteAll();
                carRepository.save(new Car("Dacia","Logan", FuelType.GAS,"2006",2700,"Motor 3.0 MPI, SuperCharged, 450 HP, Cutie Automata 10 trepte, dublu ambreiaj", "https://apollo-ireland.akamaized.net/v1/files/eyJmbiI6InRwNWdjemczaHZhcTItQVVUT1ZJVFJPIiwidyI6W3siZm4iOiJxN216NTNiaWZwemstQVVUT1ZJVFJPIiwicyI6IjE2IiwicCI6IjEwLC0xMCIsImEiOiIwIn1dfQ.D5pXbF0fy5tU7EVwI5McSEHidxHjSY329CI7tJywFi4/image;s=1080x720;cars_;/826762689_;slot=2;filename=eyJmbiI6InRwNWdjemczaHZhcTItQVVUT1ZJVFJPIiwidyI6W3siZm4iOiJxN216NTNiaWZwemstQVVUT1ZJVFJPIiwicyI6IjE2IiwicCI6IjEwLC0xMCIsImEiOiIwIn1dfQ.D5pXbF0fy5tU7EVwI5McSEHidxHjSY329CI7tJywFi4_rev001.jpg"));
                carRepository.save(new Car("Mercedes","A-Class", FuelType.DIESEL,"2000",3500,"Motor 1.7 TDI, SimpleCharghed, 90 HP, Cutie pilotata manual, simplu ambreiaj","https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/Mercedes_A_160_Elegance_%28W168%29_front_20090926.jpg/280px-Mercedes_A_160_Elegance_%28W168%29_front_20090926.jpg"));

                employeeRepository.deleteAll();
                employeeRepository.save(new Employee("Magdalena","Mihai","Strada",2000));
                employeeRepository.save(new Employee("Maria","Suciu","Nothing",4500));
                employeeRepository.save(new Employee("Tudor","Vladimirescu","Trandafirilor",5000));
                employeeRepository.save(new Employee("Geam","Gheata","Papucului",9000));

            }
        };
    }

}
