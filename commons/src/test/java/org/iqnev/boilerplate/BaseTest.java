package org.iqnev.boilerplate;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;

@Slf4j
public abstract class BaseTest {

  private static final StopWatch stopWatch = new StopWatch();

  protected static final String TEST_CLEANUP_SCRIPT = "/files/sql/cleanup_db.sql";

  protected static final String TEST_INSERT_RECORDS_SCRIPT =
      "/files/sql/create_city_and_state_records.sql";

  @Inject EntityManager entityManager;

  @Inject protected ObjectMapper objectMapper;

  protected void executeScript(final String scriptFile) {
    stopWatch.reset();
    stopWatch.start();

    QuarkusTransaction.requiringNew()
        .run(
            () -> {
              try {
                entityManager.createNativeQuery(loadResource(scriptFile)).executeUpdate();
              } catch (final IOException e) {
                throw new RuntimeException(e);
              }
            });

    log.info("Executing script time: {}", stopWatch.getTime(MILLISECONDS));
  }

  protected void setup() {
    executeScript(TEST_CLEANUP_SCRIPT);
  }

  private String loadResource(final String resourceName) throws IOException {

    final ClassLoader classLoader = getClass().getClassLoader();
    final InputStream inputStream = classLoader.getResourceAsStream(resourceName);

    if (inputStream == null) {
      throw new IOException("Resource not found: " + resourceName);
    }

    return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
  }
}
