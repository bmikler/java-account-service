package account.security.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Constraint(validatedBy = PasswordSafetyValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Password {
    String message() default "Value is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
