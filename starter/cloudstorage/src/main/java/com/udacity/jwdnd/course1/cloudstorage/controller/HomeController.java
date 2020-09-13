package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    private UsersService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private FileService fileService;

    @Autowired
    public HomeController(UsersService userService,NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.userService=userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping(value = {"/", "/home"})
    public String getHome(Authentication authentication,
                          @ModelAttribute("note") Notes note,
                          @ModelAttribute("credential") Credentials credential,
                          @ModelAttribute("file") Files file,
                          Model model){
        Users appUser = userService.getUser(authentication.getName());
        Integer userId = appUser.getUserid();

        model.addAttribute("notes",noteService.getNotesByUserId(userId));
        model.addAttribute("credentials",credentialService.getCredentialsByUserId(userId));
        model.addAttribute("files",fileService.getFilesByUserId(userId));

        return "home";
    }

}
