package com.akbar.autosimakad.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface DosenService {
    void inputNilai() throws InterruptedException, JsonProcessingException;
    void login() throws InterruptedException;
    void logout() throws InterruptedException;
}
