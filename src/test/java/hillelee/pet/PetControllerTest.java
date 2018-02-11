 package hillelee.pet;

 import com.google.common.io.Resources;
 import hillelee.store.Medicine;
 import hillelee.store.MedicineRepository;
 import org.junit.After;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.http.MediaType;
 import org.springframework.test.context.junit4.SpringRunner;
 import org.springframework.test.web.servlet.MockMvc;

 import java.io.IOException;
 import java.nio.charset.Charset;
 import java.time.LocalDate;
 import java.util.List;
 import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // чтобы тест запускался вместе со Spring
@SpringBootTest // чтобы Spring его сконфигурировал
@AutoConfigureMockMvc // for creating Http-requests
public class PetControllerTest {
    @Autowired
    JpaPetRepository petRepository; // injection of Pets for future taking
    @Autowired
    MedicineRepository medicineRepository;
    @Autowired
    MockMvc  mockMvc;

    @After
    public void cleanUp() {
        petRepository.deleteAll();
        medicineRepository.deleteAll();
    }

    @Test
    public void getAllPets() throws Exception {
        petRepository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), null, null));

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Tom")));
    }

    @Test
    public void sortByAgeTest() throws Exception {
        petRepository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), null, null));
        petRepository.save(new Pet("Jerry", "Mouse", 1, LocalDate.now(), null, null));

        mockMvc.perform(get("/pets")
                .param("sort", "age"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].age", is(1)))
                .andExpect(jsonPath("$.content[1].age", is(3)));
    }

    @Test
    public void getPetByIdTest() throws Exception {  // for @GetMapping("/pets/{id}")
        Integer id= petRepository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), null, null)).
                                    getId();

        mockMvc.perform(get("/pets/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id))) // вызывается не на Джававском объекте, а на JSONе, который на вернул Controller
                .andExpect(jsonPath("$.name", is("Tom")))
                .andExpect(jsonPath("$.*", hasSize(7)));
        // $ - Грубо говоря ссылка на весь объект, который нам вернулся
        // $.id - Вызывается не на Java-вском объекте, а на JSON-е, кот. вернул наш Controller
        // $.* - проверяет количество полей
    }

    @Test
    public void petNotFound() throws Exception {
        mockMvc.perform(get("/pets/1"))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void createPetTest() throws Exception { // for @PostMapping("/pets")
//        String body = "{\"name\":\"Tom"\, \"specie\":\"Cat\"}"

        String resourceName = "cat.json";
        String body = readFile(resourceName);

        mockMvc.perform(post("/pets")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/pets/")));

        List<Pet> all = petRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all.get(0).getName().get(), is("Tom"));
    }

    @Test
    public void updatePetTest() throws Exception {

        Integer id = petRepository.save(new Pet("Jerry", "Mouse", 1, LocalDate.now(), null, null))
                .getId();

        String body = readFile("cat.json");

        mockMvc.perform(put("/pets/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());

        Pet pet = petRepository.findById(id).get();

        assertThat(pet.getAge(), is(3));
        assertThat(pet.getName().get(), is("Tom"));
        assertThat(pet.getSpecie(), is("Cat"));
//        assertThat(all.get(0).getName().get(), is("Tom"));

    }

    @Test
    public void deletePetTest() throws Exception { // for @DeleteMapping("/pets/{id}")
        Integer id= petRepository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), null, null)).
                getId();

        mockMvc.perform(delete("/pets/{id}", id))
                .andExpect(status().isNoContent());

        Optional<Pet> maybePet = petRepository.findById(id);
        assertFalse(maybePet.isPresent());

    }

    @Test
    public void prescribeMedicineTest() throws Exception { //@PostMapping("/pets/{id}/prescription")
        Integer id= petRepository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), null, null)).
                getId();

        medicineRepository.save(new Medicine("Brilliantum greenus",1));

        String body = readFile("prescription.json");

        mockMvc.perform(post("pets/{id}/prescriptions", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
                .andExpect(status().isOk());

        List<Prescription> prescriptions = petRepository.findById(id).get().getPrescriptions();

        assertThat(prescriptions, hasSize(1));

        Medicine greenus = medicineRepository.findByName("Bri lliantum greenus").get();

        assertThat(greenus.getQuantity(), is(0));
    }

    @Test
    public void notEnoughMedicine() throws Exception {
        Integer id= petRepository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), null, null)).
                getId();

        String body = readFile("prescription.json");

        mockMvc.perform(post("pets/{id}/prescriptions", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    private String readFile(String resourceName) throws IOException {
        return Resources.toString(Resources.getResource(resourceName), Charset.defaultCharset());
    }

}