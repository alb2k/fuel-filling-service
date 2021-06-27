# Microstream

[Microstream](https://microstream.one/) is a storage/persistence solution which uses Graphs and binary files for it's storage.

The project was mainly written based on the [Microstream Getting Started Guide](https://manual.docs.microstream.one/data-store/getting-started)

#### TL;DR / What was done to add it to this project?
* Added [the dependencies](https://manual.docs.microstream.one/data-store/getting-started#prerequisites) to the [pom.xml](../pom.xml)
* Added a [root instance / DBContext class](../src/main/java/hackathon/microstream/storage/DBContext.java), where all objects that should be persisted are attached to (more information available [here](https://manual.docs.microstream.one/data-store/getting-started#the-root-instance))
* Added a [DB Manager](../src/main/java/hackathon/microstream/storage/DBManager.java), which manages (cares about initializing, reading, saving, shutdown ...) and holds the DBContext
  * There also exist a demo mode → if enabled all data is overriden by the defaults on init<br>
    The demo mode is managed by [microprofile-config.properties](../src/main/resources/META-INF/microprofile-config.properties#L2)
* Added [microstream-storage.properties](../src/main/resources/microstream-storage.properties)
* Added a [DAL Layer](../src/main/java/hackathon/microstream/dal) which uses the DB Manager


The app by default writes the storage / binary files to the ``data`` folder.


### Notes
* Microstream doesn't have any built in ID management. I used [UUIDs](https://en.wikipedia.org/wiki/Universally_unique_identifier) for that.
