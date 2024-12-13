package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntryController {

    @Autowired
    private EntryRepository entryRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createEntry(@RequestBody Entry entry) {
        entryRepository.save(entry);
        return ResponseEntity.ok("успех");
    }

    @GetMapping
    public ResponseEntity<List<Entry>> getAllEntries() {
        List<Entry> entries = entryRepository.findAll();
        return ResponseEntity.ok(entries);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEntry(@PathVariable Long id, @RequestBody Entry entry) {
        if (entryRepository.existsById(id)) {
            entry.setId(id);
            entryRepository.save(entry);
            return ResponseEntity.ok("успех");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ошибка");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable Long id) {
        if (entryRepository.existsById(id)) {
            entryRepository.deleteById(id);
            return ResponseEntity.ok("успех");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ошибка");
    }
}
