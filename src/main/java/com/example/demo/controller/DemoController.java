package com.example.demo.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Constants;
import com.example.demo.DemoApplication;
import com.example.demo.model.RegistroPortagem;

@RestController
public class DemoController {


    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {

        String toReturn = String.format("Hello %s!", name);

        addToTopic(Constants.topicA, toReturn);
        return toReturn;
    }


    @PostMapping("/hello")
    public String portagem(@RequestBody RegistroPortagem registro) {

        Double resultado;
        resultado = (double) ((registro.getDataSaida().getTime() - registro.getDataEntrada().getTime()) / 10000);
        resultado = resultado * registro.getVeiculo().getEixos();

        addToTopic(Constants.topicA, resultado.toString());
        return resultado.toString();
    }


    /**
     * @param kafkaTopic
     * @param Message
     */
    private void addToTopic(String kafkaTopic, String message) {

        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaTopic, message);
        DemoApplication.producer.send(record);
    }


}
