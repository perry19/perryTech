/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.perryTech.Controller;

/**
 *
 * @author compilerarmelovic
 */
import com.example.perryTech.Exceptions.BadResourceException;
import com.example.perryTech.Exceptions.ResourceAlreadyExistsException;
import com.example.perryTech.Exceptions.ResourceNotFoundException;
import com.example.perryTech.Services.ContactService;
import java.util.List;
import com.example.perryTech.Model.Contact;
//import Exceptions.ResourceNotFoundException;
//import Services.ContactService;
//import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
@Controller
@CrossOrigin(origins = "*")
//@RequestMapping("/contacts")
public class ContactController {
 
    private final Logger logger;
 
    private final int ROW_PER_PAGE = 5;
 
    @Autowired
    private ContactService contactService;
 
//    @Value("${msg.title}")
      private String title;

    public ContactController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
 
    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", title);
        return "index";
    }
 
    @GetMapping(value = "/contacts")
    public String getContacts(Model model,
    @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
    List<Contact> contacts = contactService.findAll(pageNumber, ROW_PER_PAGE);
 
    long count = contactService.count();
    boolean hasPrev = pageNumber > 1;
    boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
    model.addAttribute("contacts", contacts);
    model.addAttribute("hasPrev", hasPrev);
    model.addAttribute("prev", pageNumber - 1);
    model.addAttribute("hasNext", hasNext);
    model.addAttribute("next", pageNumber + 1);
    return "contact-list";
    }
 
    @GetMapping(value = "/contacts/{contactId}")
    public String getContactById(Model model, @PathVariable long contactId) {
    Contact contact = null;
    try {
        contact = contactService.findById(contactId);
    } catch (ResourceNotFoundException ex) {
        model.addAttribute("errorMessage", "Contact not found");
    }
    model.addAttribute("contact", contact);
    return "contact";
}
 
    @GetMapping(value = {"/contacts/add"})
    public String showAddContact(Model model) {
    
    Contact contact = new Contact();
    model.addAttribute("add", true);
    model.addAttribute("contact", contact);
 
    return "contact-edit";
    }
 
    @PostMapping(value = "/contacts/add")
    public String addContact(Model model,
        @ModelAttribute("contact") Contact contact) {        
    try {
        Contact newContact = contactService.save(contact);
        return "redirect:/contacts/" + String.valueOf(newContact.getId());
    } catch (BadResourceException | ResourceAlreadyExistsException ex) {
        // log exception first, 
        // then show error
        String errorMessage = ex.getMessage();
        logger.error(errorMessage);
        model.addAttribute("errorMessage", errorMessage);
 
        //model.addAttribute("contact", contact);
        model.addAttribute("add", true);
        return "contact-edit";
    }        
}


    @GetMapping(value = {"/contacts/{contactId}/edit"})
   public String showEditContact(Model model, @PathVariable long contactId) {
    Contact contact = null;
    try {
        contact = contactService.findById(contactId);
    } catch (ResourceNotFoundException ex) {
        model.addAttribute("errorMessage", "Contact not found");
    }
    model.addAttribute("add", false);
    model.addAttribute("contact", contact);
    return "contact-edit";
}


@PostMapping(value = {"/contacts/{contactId}/edit"})
 public String updateContact(Model model,
        @PathVariable long contactId,
        @ModelAttribute("contact") Contact contact) {        
    try {
        contact.setId(contactId);
        contactService.update(contact);
        return "redirect:/contacts/" + String.valueOf(contact.getId());
    } catch (BadResourceException | ResourceNotFoundException ex) {
        // log exception first, 
        // then show error
        String errorMessage = ex.getMessage();
        logger.error(errorMessage);
        model.addAttribute("errorMessage", errorMessage);
 
         model.addAttribute("add", false);
        return "contact-edit";
    }
}
 
    @GetMapping(value = {"/contacts/{contactId}/delete"})
public String showDeleteContactById(
        Model model, @PathVariable long contactId) {
    Contact contact = null;
    try {
        contact = contactService.findById(contactId);
    } catch (ResourceNotFoundException ex) {
        model.addAttribute("errorMessage", "Contact not found");
    }
    model.addAttribute("allowDelete", true);
    model.addAttribute("contact", contact);
    return "contact";
}
 
@PostMapping(value = {"/contacts/{contactId}/delete"})
public String deleteContactById(
        Model model, @PathVariable long contactId) {
    try {
        contactService.deleteById(contactId);
        return "redirect:/contacts";
    } catch (ResourceNotFoundException ex) {
        String errorMessage = ex.getMessage();
        logger.error(errorMessage);
        model.addAttribute("errorMessage", errorMessage);
        return "contact";
    }
}
}
