package org.poseidondevs.jcommandapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandler {
    String command();

    String[] aliases() default {};

    String description() default "";

    String usage() default "";

    String permission() default "";

    boolean playersOnly() default false;

    int minArgs() default 0;

    int maxArgs() default -1;
}