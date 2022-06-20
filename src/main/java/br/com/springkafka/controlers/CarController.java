package br.com.springkafka.controlers;

import br.com.springkafka.Car;
import br.com.springkafka.dto.CarDto;
import br.com.springkafka.producer.CarProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {

    private final CarProducer carProducer;

    @PostMapping
    private ResponseEntity<CarDto> sendMessage(@RequestBody CarDto carDto) {
        var id = UUID.randomUUID().toString();

        var message = Car.newBuilder()
                .setId(id)
                .setName(carDto.getName())
                .setModel(carDto.getModel())
                .setBrand(carDto.getBrand()).build();
        carProducer.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(carDto);
    }
}
