package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
public class TimeEntryController {

    @Autowired
    private TimeEntryRepository repo;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.repo = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public @ResponseBody ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        repo.create(timeEntry);
        return ResponseEntity.created(URI.create("time-entries")).body(timeEntry);
    }
    @GetMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity<TimeEntry> read(@PathVariable long id) {
        if(repo.find(id) != null) {
            return ResponseEntity.ok().body(repo.find(id));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/time-entries")
    public @ResponseBody ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok().body(repo.list());
    }

    @PutMapping ("/time-entries/{id}")
    public @ResponseBody ResponseEntity update(@PathVariable long id, TimeEntry expected) {
        if(repo.update(id, expected) == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok().body(expected);
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        repo.delete(id);
        return ResponseEntity.noContent().build();
    }
}
