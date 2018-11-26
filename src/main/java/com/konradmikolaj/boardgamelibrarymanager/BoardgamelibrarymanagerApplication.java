package com.konradmikolaj.boardgamelibrarymanager;

import com.konradmikolaj.boardgamelibrarymanager.demo.DemoContentCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardgamelibrarymanagerApplication {

    @Autowired
    public BoardgamelibrarymanagerApplication(DemoContentCreator demoContentCreator) {
        demoContentCreator.prepare();
    }

    public static void main(String[] args) {
        SpringApplication.run(BoardgamelibrarymanagerApplication.class, args);
    }
}
