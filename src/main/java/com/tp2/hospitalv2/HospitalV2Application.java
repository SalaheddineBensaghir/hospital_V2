package com.tp2.hospitalv2;

import com.tp2.hospitalv2.entites.Patient;
import com.tp2.hospitalv2.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class HospitalV2Application implements CommandLineRunner {
@Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(HospitalV2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Patient patient=new Patient();
//        patient.setId(null);
//        patient.setNom("Salaheddine");
//        patient.setDateNaissance(new Date());
//        patient.setMalade(false);
//        patient.setScore(23);



    }
//    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
       return  args -> {
           patientRepository.save(new Patient(null, "Salaheddine", new Date(), false, 123));
           patientRepository.save(new Patient(null, "Ahmad", new Date(), true, 520));
           patientRepository.save(new Patient(null, "Hamza", new Date(), false, 180));
       };
    }
}
