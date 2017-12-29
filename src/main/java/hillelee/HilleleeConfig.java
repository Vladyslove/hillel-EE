package hillelee;

import hillelee.doctor.Doctor;
import hillelee.doctor.JpaDoctorRepository;
import hillelee.pet.*;
import hillelee.store.Medicine;
import hillelee.store.MedicineRepository;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "clinic-info")
@Getter
public class HilleleeConfig {
    private List<String> specializations = new ArrayList<>();

    /*@Bean
    PetService petService(JpaPetRepository petRepository){
        return new PetService(petRepository);
    }*/

    @Bean
    CommandLineRunner initPets(JpaPetRepository repository){
        return args -> {
            if (!repository.findAll().isEmpty()){
                return;
            }
            List<Prescription> tomsPrescriptions = new ArrayList<>();
            tomsPrescriptions.add(new Prescription("paracetatamol", LocalDate.now(), 3));
            tomsPrescriptions.add(new Prescription("asperin", LocalDate.now(), 3));

            List<Prescription> jerrysPrescriptions = new ArrayList<>();

            jerrysPrescriptions.add(new Prescription("asperin", LocalDate.now(), 3));

            MedicalCard tomsCard = new MedicalCard(LocalDate.now(), "bla-bla");
            MedicalCard jerrysCard = new MedicalCard(LocalDate.now(), "foo-bar");
            repository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), tomsCard, tomsPrescriptions));
            repository.save(new Pet("Jerry", "Mouse", 1, LocalDate.now(), jerrysCard, jerrysPrescriptions));
        };
    }

    @Bean
    CommandLineRunner initDoctors(JpaDoctorRepository repository) {
        return args -> {

            List<String> spec1 = new ArrayList<>();
            spec1.add("dentist");
            spec1.add("therapist");

            List<String> spec2 = new ArrayList<>();
            spec2.add("therapist");
            spec2.add("surgeon");

            List<String> spec3 = new ArrayList<>();
            spec3.add("surgeon");
            spec3.add("intern");


            if (!repository.findAll().isEmpty()) return;
            repository.save(new Doctor("John Doe", spec1));
            repository.save(new Doctor("Jane Roe", spec2));
            repository.save(new Doctor("Drake Ramore", spec3));
        };
    }

    @Bean
    CommandLineRunner initMedicineStore(MedicineRepository medicineRepository) {
        return args -> {
            medicineRepository.save(new Medicine("Brilliant green", 1));
        };
    }
}
