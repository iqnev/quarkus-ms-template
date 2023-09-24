package org.iqnev.boilerplate.commons;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.Validate;

public final class UuidUtils {

  private UuidUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String uuidToBase32Random() {
    return uuidToBase32(UUID.randomUUID());
  }

  public static String uuidToBase32(UUID uuid) {
    Validate.notNull(uuid);
    Base32 base32 = new Base32();
    return base32.encodeAsString(uuidToByteArray(uuid)).replace("=", "").toUpperCase();
  }

  public static byte[] uuidToByteArray(UUID uuid) {
    Validate.notNull(uuid);
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }
}
