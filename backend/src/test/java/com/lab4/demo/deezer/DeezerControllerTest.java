package com.lab4.demo.deezer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeezerControllerTest {
    @Test
    void apiCall() {
        DeezerController deezerController = new DeezerController();
        deezerController.getApi();
    }

}