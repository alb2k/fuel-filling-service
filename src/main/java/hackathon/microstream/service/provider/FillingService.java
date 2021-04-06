package hackathon.microstream.service.provider;

import hackathon.microstream.dal.FillingRepository;
import hackathon.microstream.dal.util.NotFoundException;
import hackathon.microstream.domain.entities.Filling;
import hackathon.microstream.storage.entities.DBFilling;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class FillingService {

    @Inject
    private FillingRepository fillingRepository;

    public boolean cleanAndUseDefaults() { return this.fillingRepository.cleanAndUseDefaults(); }

    public List<DBFilling> getAll() {
        return this.fillingRepository.getAll();
    }

    /**
     * Get a filling by id
     * @param id
     * @return
     * @throws NotFoundException
     */
    public DBFilling getById(UUID id) {
        return this.fillingRepository.getById(id);
    }

    /**
     * Adds a filling
     * @param filling
     * @return
     */
    public DBFilling add(Filling filling) {
        return this.fillingRepository.add(filling);
    }

    /**
     * Updates a filling
     * @param filling
     * @return
     * @throws NotFoundException
     */
    public DBFilling update(UUID id, Filling filling) {
        return this.fillingRepository.update(id, filling);
    }

    /**
     * Deletes a filling from the DB
     * @param id
     * @throws NotFoundException
     */
    public void delete(UUID id) {
        this.fillingRepository.delete(id);
    }

}
