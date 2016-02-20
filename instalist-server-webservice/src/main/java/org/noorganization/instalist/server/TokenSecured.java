package org.noorganization.instalist.server;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This interface (annotation) allows to secure resources easier.
 *
 * Resources can be secured by annotating methods with {@code @TokenSecured}. The corresponding
 * Provider is located in {@link AuthenticationFilter}.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TokenSecured {
}
