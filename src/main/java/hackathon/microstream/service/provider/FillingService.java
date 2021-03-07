package hackathon.microstream.service.provider;

import hackathon.microstream.dal.FillingRepository;
import hackathon.microstream.dal.util.CRUDException;
import hackathon.microstream.domain.entities.Filling;
import hackathon.microstream.storage.entities.DBFilling;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class FillingService {

    @Inject
    private FillingRepository fillingRepository;

    public List<DBFilling> getAll() {
        return this.fillingRepository.getAll();
    }

    /**
     * Get a filling by id
     * @param id
     * @return
     * @throws CRUDException
     */
    public Filling getById(UUID id) {
        return this.fillingRepository.getById(id);
    }

    /**
     * Adds a filling
     * @param filling
     * @return
     */
    public Filling add(Filling filling) {
        return this.fillingRepository.add(filling);
    }

    /**
     * Updates a filling
     * @param filling
     * @return
     * @throws CRUDException
     */
    public Filling update(UUID id, Filling filling) {
        return this.fillingRepository.update(id, filling);
    }

    /**
     * Deletes a filling from the DB
     * @param id
     * @throws CRUDException
     */
    public void delete(UUID id) {
        this.fillingRepository.delete(id);
    }

}
