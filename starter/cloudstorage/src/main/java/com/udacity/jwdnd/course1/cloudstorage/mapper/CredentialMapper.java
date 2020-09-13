package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credentials findCredentialById(int credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> findByUserId(int userid);

    @Insert(
            "INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credentials credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    int deleteCredential(int credentialId);

    @Update(
            "UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId}")
    int updateCredential(Credentials credential);

}
