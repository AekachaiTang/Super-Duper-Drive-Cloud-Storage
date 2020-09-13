package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Notes note);

    @Select("SELECT * FROM NOTES WHERE userid = #{noteId}")
    List<Notes> getNotesByNoteId(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Notes> getNotesByUsername(Integer userId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle},notedescription = #{noteDescription} where noteid = #{noteId}")
    int update(Notes note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int remove(Integer noteId);

}
