package org.theleakycauldron.diagonalley.productservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.URI;

import org.theleakycauldron.diagonalley.commons.annotations.ValidURL;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class URLValidator implements ConstraintValidator<ValidURL, String> {

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        if(url == null) {
            return true;
        }
        try {
            new URI(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
