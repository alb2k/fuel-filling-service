package hackathon.microstream.storage;

import hackathon.microstream.domain.entities.Filling;
import hackathon.microstream.storage.entities.DBFilling;

import java.util.ArrayList;
import java.util.List;

public class DBContext {
    private List<DBFilling> fillings = new ArrayList<>();

    public List<DBFilling> getFillings() {
        return fillings;
    }
}
