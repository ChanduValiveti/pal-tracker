package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {



    private CounterService counter;
    private GaugeService gauge;
    private TimeEntryRepository timeEntriesRepo;

    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            GaugeService gauge,
            CounterService counter
    )
    {
        this.timeEntriesRepo = timeEntriesRepo;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntriesRepo.create(timeEntryToCreate);

        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());

        return new ResponseEntity(timeEntry, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {

        TimeEntry timeEntry = timeEntriesRepo.find(id);

        if (timeEntry == null) {
            counter.increment("TimeEntry.read");

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(timeEntry, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        List<TimeEntry> timeEntryList = timeEntriesRepo.list();
        counter.increment("TimeEntry.listed");

        return new ResponseEntity(timeEntryList, HttpStatus.OK);

    }

    @PutMapping("{l}")
    public ResponseEntity update(@PathVariable long l, @RequestBody TimeEntry expected) {

        TimeEntry timeEntry = timeEntriesRepo.update(l,expected);

        if (timeEntry == null) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        counter.increment("TimeEntry.updated");
        return new ResponseEntity(timeEntry, HttpStatus.OK);

    }

    @DeleteMapping("{l}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long l) {

        timeEntriesRepo.delete(l);

        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
