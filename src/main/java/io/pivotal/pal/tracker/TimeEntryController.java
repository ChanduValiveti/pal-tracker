package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private final TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.repository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = repository.create(timeEntryToCreate);
        return new ResponseEntity(timeEntry, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {

        TimeEntry timeEntry = repository.find(id);

        if (timeEntry == null) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(timeEntry, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        List<TimeEntry> timeEntryList = repository.list();

        return new ResponseEntity(timeEntryList, HttpStatus.OK);

    }

    @PutMapping("{l}")
    public ResponseEntity update(@PathVariable long l, @RequestBody TimeEntry expected) {

        TimeEntry timeEntry = repository.update(l,expected);

        if (timeEntry == null) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(timeEntry, HttpStatus.OK);

    }

    @DeleteMapping("{l}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long l) {

        repository.delete(l);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
