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
                addDefaultFillings();
            } else {
                context = (DBContext) this.getStorageManager().root();
            }

            this.initialized = true;
        } catch (Exception ex) {
            LOG.error("Failed to init", ex);
            throw ex;
        }
    }

    /**
     *
     * @return true when cleaned
     */
    public boolean addDefaultFillings() {
        if(!demoMode)
            return false;

        context.getFillings().clear();
        context.getFillings().add(
                new DBFilling(
                        LocalDate.of(2020, 10, 24),
                        "Diesel",
                        500,
                        1.00,
                        29.5)
        );
        context.getFillings().add(
                new DBFilling(
                        LocalDate.of(2021, 2, 20),
                        "Diesel",
                        475,
                        1.25,
                        35)
        );
        context.getFillings().add(
                new DBFilling(
                        LocalDate.of(2015, 10, 21),
                        "Plutonium",
                        100000,
                        1_700_000,
                        1_700_000)
        );
        return true;
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

    public boolean isReady() {
        return this.initialized && this.storageManager.isRunning();
    }

    public boolean isDemoMode() {
        return demoMode;
    }

    public StorageManagerDetails getStorageManagerDetails() {
        return this.storageManager != null ? new StorageManagerDetails(this.storageManager) : null;
    }

    public class StorageManagerDetails {
        private String name;
        private boolean active;
        private long initializationTime;
        private long initializationDuration;
        private long operationModeTime;

        public StorageManagerDetails(StorageManager storageManager) {
            this.name = storageManager.getClass().getCanonicalName();
            this.active = storageManager.isActive();
            this.initializationTime = storageManager.initializationTime();
            this.initializationDuration = storageManager.initializationDuration();
            this.operationModeTime = storageManager.operationModeTime();
        }

        public String getName() {
            return name;
        }

        public boolean isActive() {
            return active;
        }

        public long getInitializationTime() {
            return initializationTime;
        }

        public long getInitializationDuration() {
            return initializationDuration;
        }

        public long getOperationModeTime() {
            return operationModeTime;
        }
    }
}
