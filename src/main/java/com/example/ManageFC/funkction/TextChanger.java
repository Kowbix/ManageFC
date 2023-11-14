package com.example.ManageFC.funkction;

import org.springframework.stereotype.Service;

@Service
public class TextChanger {

    public String toUpperCase(String text){

        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
