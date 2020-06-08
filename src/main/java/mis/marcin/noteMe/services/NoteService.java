package mis.marcin.noteMe.services;

import mis.marcin.noteMe.models.Note;
import mis.marcin.noteMe.models.User;
import mis.marcin.noteMe.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Optional<Note> getNoteById(Integer id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Optional.of(noteRepository.findById(id).get())
                .filter(note -> note.getOwner().getUsername().equals(user.getUsername()));
    }

    public List<Note> getAll(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return noteRepository.findAll()
                .stream()
                .filter(note-> note.getOwner().getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
    }

    public Note save(Note note){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        note.setCreateOn(new Date());
        note.setOwner(user);

        return noteRepository.save(note);
    }

    public boolean delete(Integer id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Note> toDelete = Optional.of(noteRepository.findById(id).get());

        Note note = toDelete.get();
        User owner = note.getOwner();
        if(owner.getUsername().equals(user.getUsername())){
            noteRepository.delete(note);
            return true;
        }
        return false;
    }
}
