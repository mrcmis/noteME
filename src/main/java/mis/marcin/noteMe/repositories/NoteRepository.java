package mis.marcin.noteMe.repositories;

import mis.marcin.noteMe.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
