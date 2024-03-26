package com.tp2.hospitalv2.web;

import com.tp2.hospitalv2.entites.Patient;
import com.tp2.hospitalv2.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientControlle {

    private PatientRepository patientRepository;
    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size" , defaultValue = "4") int size, @RequestParam(name = "keyword" , defaultValue = "") String keyword){

//        Page<Patient> pagePatient=patientRepository.findAll(PageRequest.of(page,size));
        Page<Patient> pagePatient=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients", pagePatient.getContent());
        model.addAttribute("pages",new int[pagePatient.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }
    @GetMapping("/delete")
    public  String delete (@RequestParam(name= "id" )Long id,@RequestParam(name = "keyword" ,defaultValue = "") String keyword, @RequestParam(name = "page",defaultValue = "0") int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }
    @GetMapping("/patients")
    @ResponseBody
    public  List<Patient> patientList(){
        return patientRepository.findAll();
    }


    @GetMapping("/formPatients")

    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return  "formPatients";
    }
@PostMapping(path = "/save")
public String  save(Model model, @Valid Patient patient, BindingResult bindingResult,@RequestParam(name = "page",defaultValue = "0") int page,@RequestParam(name = "keyword",defaultValue = "") String keyword){
if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
    return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping(path = "/editPatients")
    public String  editPatients(Model model, Long id,String keyword,int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if (patient==null) throw new RuntimeException("Patient introuvable");
model.addAttribute("patient",patient);
model.addAttribute("page",page);
model.addAttribute("keyword",keyword);

        return "editPatients";
    }
}
