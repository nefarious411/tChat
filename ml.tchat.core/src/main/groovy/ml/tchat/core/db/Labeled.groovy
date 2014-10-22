package ml.tchat.core.db

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Labeled {
  String value()
}