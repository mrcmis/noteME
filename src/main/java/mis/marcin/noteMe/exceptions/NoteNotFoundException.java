package mis.marcin.noteMe.exceptions;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Integer id) {
        super("Could not find note " + id);
    }
}
