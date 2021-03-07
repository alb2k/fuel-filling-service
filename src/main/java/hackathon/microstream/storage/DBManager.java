package hackathon.microstream.storage;

import hackathon.microstream.storage.entities.DBFilling;
import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class DBManager {
    private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);

    private static final DBManager INSTANCE = new DBManager();

    public static DBManager getInstance() {
        return INSTANCE;
    }

    private DBContext context;

    private boolean demoMode = false;
    private boolean initialized = false;
    private StorageManager storageManager;

    private StorageManager getStorageManager() {
        if(!initialized)
            throw new IllegalArgumentException("Not configured");

        if(storageManager == null)
            throw new NullPointerException("Unexpected: storageManager is null");

        return storageManager;
    }

    public void init() {
        try {
            LOG.info("Loading configuration");
            Configuration configuration = Configuration.Load();
            if(configuration == null)
                throw new RuntimeException("Failed to load configuration");

            LOG.info("BaseDirectory is '{}'", configuration.getBaseDirectory());

            demoMode = ConfigProvider.getConfig().getValue("db.demoMode", Boolean.class) == true;

            LOG.info("Initializing storageManager");
            storageManager = configuration.createEmbeddedStorageFoundation().start();


            this.storageManager.setRoot(context);

            if (demoMode) {
                LOG.info("Demo mode; Using demo data");
                context = new DBContext();
                context.getFillings().add(
                        new DBFilling(
                                LocalDate.of(2020, 10, 24),
                                "Diesel",
                                29.5,
                                1.00,
                                500)
                );
                context.getFillings().add(
                        new DBFilling(
                                LocalDate.of(2021, 2, 20),
                                "Diesel",
                                35,
                                1.25,
                                475)
                );
                context.getFillings().add(
                        new DBFilling(
                                LocalDate.of(2015, 10, 21),
                                "Plutonium",
                                100000,
                                1_700_000,
                                1_700_000)
                );
            } else {
                context = (DBContext) this.getStorageManager().root();
            }

            this.initialized = true;
        } catch (Exception ex) {
            LOG.error("Failed to init", ex);
            throw ex;
        }
    }

    public void shutdown() {
        if(storageManager != null)
            storageManager.shutdown();
    }

    public void saveAll() {
        this.getStorageManager().storeRoot();
    }

    public void save(Object instance) {
        this.getStorageManager().store(instance);
    }

    public void save(Object... instances) {
        this.getStorageManager().storeAll(instances);
    }

    public void save(Iterable<Object> instances) {
        this.getStorageManager().storeAll(instances);
    }

    public DBContext getContext() {
        return context;
    }
}
