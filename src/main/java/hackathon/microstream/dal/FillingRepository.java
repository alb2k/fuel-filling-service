package hackathon.microstream.dal;

import hackathon.microstream.dal.util.NotFoundException;
import hackathon.microstream.domain.entities.Filling;
import hackathon.microstream.storage.DBManager;
import hackathon.microstream.storage.entities.DBFilling;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FillingRepository {

    public boolean cleanAndUseDefaults() {
        return DBManager.getInstance().addDefaultFillings();
    }

    public List<DBFilling> getAll() {
        return Collections.unmodifiableList(getFillings());
    }

    /**
     *
     * @param id
     * @return
     * @throws NotFoundException
     */
    public Filling getById(UUID id) {
        if(id == null)
            throw new IllegalArgumentException("id can't be null");

        var optDbFilling =
                getFillings().stream()
                        .filter(f -> f.getId().equals(id))
                        .findFirst();

        if(optDbFilling.isEmpty())
            throw new NotFoundException(String.format("Unable to find filling with id=%s", id));

        return optDbFilling.get();
    }


    /**
     * Adds a filling
     * @param filling
     * @return
     */
    public Filling add(Filling filling) {
        if(filling == null)
            throw new IllegalArgumentException("filling can't be null");

        var dbFilling = new DBFilling();
        try {
            BeanUtils.copyProperties(dbFilling, filling);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to override", e);
        }

        var fillings = getFillings();
        fillings.add(dbFilling);
        DBManager.getInstance().save(fillings);

        return dbFilling;
    }

    /**
     * Updates a filling
     * @param filling
     * @return
     * @throws NotFoundException
     */
    public Filling update(UUID id, Filling filling) {
        if(id == null)
            throw new IllegalArgumentException("id can't be null");
        if(filling == null)
            throw new IllegalArgumentException("filling can't be null");

        Filling dbFilling = getById(id);

        // Update the properties in the bean
        try {
            BeanUtils.copyProperties(dbFilling, filling);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to override", e);
        }

        DBManager.getInstance().save(dbFilling);
        return dbFilling;
    }

    /**
     * Deletes a filling
     * @param id
     * @throws NotFoundException
     */
    public void delete(UUID id) {
        var fillings = getFillings();

        if(!fillings.remove(getById(id)))
            throw new NotFoundException(String.format("Unable to find filling with id=%s", id));

        DBManager.getInstance().save(fillings);
    }

    protected List<DBFilling> getFillings() {
        return DBManager.getInstance().getContext().getFillings();
    }
}
