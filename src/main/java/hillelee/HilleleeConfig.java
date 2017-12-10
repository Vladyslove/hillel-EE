package hillelee;

import hillelee.doctor.Doctor;
import hillelee.doctor.JpaDoctorRepository;
import hillelee.pet.JpaPetRepository;
import hillelee.pet.Pet;
import hillelee.pet.PetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HilleleeConfig {

    @Bean
    PetService petService(JpaPetRepository petRepository){

        return new PetService(petRepository);
    }

    @Bean
    CommandLineRunner initDb(JpaPetRepository repository){
        return args -> {
            if (!repository.findAll().isEmpty()){
                return;
            }
            repository.save(new Pet( "Tom", "Cat", 3));
            repository.save(new Pet( "Jerry", "Mouse", 1));
        };
    }

    @Bean
    CommandLineRunner initDb(JpaDoctorRepository repository) {
        return args ->   {
            repository.save(new Doctor("John Doe", "Dentist"));
            repository.save(new Doctor("Jane Roe", "Therapist"));
            repository.save(new Doctor("Drake Ramore", "Surgeon"));
        };
    }
}
