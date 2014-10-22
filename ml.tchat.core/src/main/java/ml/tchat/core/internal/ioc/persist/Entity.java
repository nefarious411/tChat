package ml.tchat.core.internal.ioc.persist;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
  String value();
}
