package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    @Autowired
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Notes note){
        return noteMapper.insert(note);
    }

    public int updateNote(Notes note) {return  noteMapper.update(note);}

    public  int removeNote(Notes note){ return  noteMapper.remove(note.getNoteId());}

    public List<Notes> getNotesByUserId(Integer userId){

        return noteMapper.getNotesByUsername(userId);
    }


}
