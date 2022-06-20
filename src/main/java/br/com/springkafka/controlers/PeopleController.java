package br.com.springkafka.controlers;

import br.com.springkafka.People;
import br.com.springkafka.dto.PeopleDto;
import br.com.springkafka.producer.PeopleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleProducer peopleProducer;


    @Autowired
    public PeopleController(PeopleProducer peopleProducer) {
        this.peopleProducer = peopleProducer;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PeopleDto> sendMessage(@RequestBody PeopleDto peopleDto){
        var id = UUID.randomUUID().toString();
        var message = People.newBuilder()
                .setId(id)
                .setName(peopleDto.getName())
                .setCpf(peopleDto.getCpf())
                .setBooks(peopleDto.getBooks().stream().map(p -> (CharSequence) p).collect(Collectors.toList()))
                .build();
        peopleProducer.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(peopleDto);
    }

}
