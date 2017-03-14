package com.overseer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import javax.validation.*;


/**
 * Validate entity and throw {@link ValidationException} if class has constraint error.
 */
public class ValidationUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ValidationUtil.class);
    private static  ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    private static  Validator validator = vf.getValidator();

    /**
     * Verification of compliance with the constraints.
     * @param object Entity with some annotation constraint.
     */
    public static void validate(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(object);

        LOG.debug(object.toString());
        LOG.debug("Number of errors: {}", constraintViolations.size());

        for (ConstraintViolation<Object> cv : constraintViolations) {
            stringBuilder.append(String.format("property: [%s], value: [%s], message: [%s] \n",
                    cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
        }
        if (constraintViolations.size() != 0) {
            throw new ValidationException(stringBuilder.toString());
        }
    }
}
