package org.iqnev.boilerplate.commons;

import static java.util.Objects.requireNonNull;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.apache.commons.codec.binary.Base32;

public final class UuidUtils {

  private UuidUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String uuidToBase32Random() {
    return uuidToBase32(UUID.randomUUID());
  }

  public static String uuidToBase32(final UUID uuid) {
    requireNonNull(uuid);

    final Base32 base32 = new Base32();
    return base32.encodeAsString(uuidToByteArray(uuid)).replace("=", "").toUpperCase();
  }

  public static byte[] uuidToByteArray(final UUID uuid) {
    requireNonNull(uuid);

    final ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    
    return bb.array();
  }
}
