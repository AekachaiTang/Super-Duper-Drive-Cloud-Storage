package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<Files> getFilesByUserId(Integer userId) {
        return fileMapper.findFilesByUserId(userId);
    }

    public Files getFilesByFileName(String fileName) {
        return  fileMapper.findFilesByFileName(fileName);
    }


    public int removeFile(int fileId) {
        return fileMapper.deleteFile(fileId);
    }

    public int uploadFile(MultipartFile fileToUpload, int userId) throws IOException {
        Files file = new Files();

        file.setContentType(fileToUpload.getContentType());
        file.setFileData(fileToUpload.getBytes());
        file.setFileName(fileToUpload.getOriginalFilename());
        file.setFileSize(Long.toString(fileToUpload.getSize()));
        file.setUserId(userId);

        return fileMapper.insertFile(file);
    }

    public Files getFileById(Integer fileId) {
        return fileMapper.findFileById(fileId);
    }

}
