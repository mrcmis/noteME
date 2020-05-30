package mis.marcin.noteMe.controllers;

import mis.marcin.noteMe.exceptions.NoteNotFoundException;
import mis.marcin.noteMe.models.Note;
import mis.marcin.noteMe.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping(value="/", produces = "application/json")
    public ResponseEntity<List<Note>> getNotes(){
        return ResponseEntity.ok(noteService.getAll());
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<Note> getSingleNote(@PathVariable Integer id){
        Optional<Note> note = noteService.getNoteById(id);
        if (note.isPresent())
            return ResponseEntity.ok(note.get());
        else
            throw new NoteNotFoundException(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> addNote(@RequestBody Note note){
        return ResponseEntity.ok(noteService.save(note));
    }

    @PutMapping("/{id}")
    public Note replaceNote(@RequestBody Note note, @PathVariable Integer id) {

        return noteService.getNoteById(id)
                .map(n -> {
                    n.setContent(note.getContent());
                    return noteService.save(n);
                })
                .orElseGet(() -> noteService.save(note));
    }
}
