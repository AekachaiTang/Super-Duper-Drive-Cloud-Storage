package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class NoteController {

    private UsersService userService;
    private NoteService noteService;

    @Autowired
    public NoteController(UsersService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String postNote(Authentication authentication, @ModelAttribute("note") Notes note, RedirectAttributes redirectAttributes) {

        try {
            Users appUser = userService.getUser(authentication.getName());
            Integer userId = appUser.getUserid();
            note.setUserId(userId);

            if (note.getNoteId() == null)
                noteService.createNote(note);
            else
                noteService.updateNote(note);

            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorMessage", e.getCause().getMessage());
            return "redirect:/result?error";
        }
    }

    @PostMapping("/remove-note")
    public String removeNote(@ModelAttribute("note") Notes note, RedirectAttributes redirectAttributes) {
        try {
            Integer userId = note.getUserId();
            noteService.removeNote(note);
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorMessage", e.getCause().getMessage());
            return "redirect:/result?error";
        }

    }


}
