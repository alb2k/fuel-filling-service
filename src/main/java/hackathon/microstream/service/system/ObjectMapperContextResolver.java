package hackathon.microstream.service.system;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * The default provider for a configured {@link ObjectMapper}
 *
 * Configuration:
 * <ul>
 *     <li>
 *         By default LocalDate is written as array, which is not <a href="https://en.wikipedia.org/wiki/ISO_8601">ISO-8601</a> conform<br/>
 *         https://stackoverflow.com/a/28803634
 *     </li>
 *     <li>
 *         By default ObjectMapper throws an exception, if unknown properties are supplied.<br/>
 *         To be a bit less restrictive, unknown properties are allowed (easier handling of objects with / without UUID)
 *     </li>
 * </ul>
 *
 *
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
    private final ObjectMapper MAPPER;

    public ObjectMapperContextResolver() {
        MAPPER = new ObjectMapper();
        // Make LocalDate & Co to Strings (and vice versa)
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return MAPPER;
    }
}
