package pg.app.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.app.agenda.model.Contacto;
import pg.app.agenda.repo.ContactoRepository;

import java.util.List;

@Controller
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping("")
    public String index(Model model){

        List<Contacto> contactos = contactoRepository.findAll();
        model.addAttribute("contactos", contactos);
        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("contacto", new Contacto());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String crear(
            @Validated Contacto contacto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra
    ){

        if(bindingResult.hasErrors()){
            model.addAttribute("contacto", contacto);
            return "nuevo";
        }
        contactoRepository.save(contacto);
        ra.addFlashAttribute("msgExito", "El contacto ha sido creado correctamente.");
        return "redirect:/";

    }
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model){
        Contacto contacto = contactoRepository.getById(id);
        model.addAttribute("contacto", contacto);
        return "nuevo";
    }
    @PostMapping("/{id}/editar")
    public String actualizar(
            @PathVariable Integer id,
            @Validated Contacto contacto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra
    ){
        Contacto contactoFromDB = contactoRepository.getById(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("contacto", contacto);
            return "nuevo";
        }

        contactoFromDB.setNombre(contacto.getNombre());
        contactoFromDB.setCelular(contacto.getCelular());
        contactoFromDB.setEmail(contacto.getEmail());
        contactoFromDB.setFechaManualRegistro(contacto.getFechaManualRegistro());

        contactoRepository.save(contactoFromDB);

        ra.addFlashAttribute("msgExito", "El contacto ha sido actualizado correctamente.");
        return "redirect:/";

    }
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes ra){
        Contacto contacto = contactoRepository.getById(id);

        contactoRepository.delete(contacto);

        ra.addFlashAttribute("msgExito", "El contacto ha sido eliminado.");

        return "redirect:/";
    }

}
