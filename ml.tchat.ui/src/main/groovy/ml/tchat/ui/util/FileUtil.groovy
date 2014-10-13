package ml.tchat.ui.util

public final class FileUtil {

  public static File path(String... pathParts) {
    if (pathParts == null || pathParts.length == 0) {
      return null;
    }

    return new File(join(pathParts));
  }

  public static File path(File base, String... parts) {
    String partsJoin = join(parts);
    return new File(base.getPath() + File.separator + partsJoin);
  }

  private FileUtil() {
    throw new UnsupportedOperationException("util class");
  }

  private static String join(String[] parts) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < parts.length; i++) {
      sb.append(parts[i]);
      if (i != parts.length) {
        sb.append(File.separator);
      }
    }
    return sb.toString();
  }
}
