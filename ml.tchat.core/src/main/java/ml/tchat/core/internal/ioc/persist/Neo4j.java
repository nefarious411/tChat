package ml.tchat.core.internal.ioc.persist;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A binding annotation for internal Neo4j module properties.
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface Neo4j {
}
