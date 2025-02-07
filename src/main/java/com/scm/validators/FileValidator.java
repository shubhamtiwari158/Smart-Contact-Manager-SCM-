package com.scm.validators;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB

    // Add additional fields for type, height, and width if needed
    // private String allowedType;
    // private int minWidth;
    // private int minHeight;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        // Initialize any fields if necessary
        // this.allowedType = constraintAnnotation.allowedType();
        // this.minWidth = constraintAnnotation.minWidth();
        // this.minHeight = constraintAnnotation.minHeight();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if (file == null || file.isEmpty()) {
            // Optionally, you could also add a custom message here
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
            return false; // Consider returning false here if empty files are invalid
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2MB").addConstraintViolation();
            return false;
        }

        // Example for type validation (if needed)
        // String contentType = file.getContentType();
        // if (!allowedType.equals(contentType)) {
        //     context.disableDefaultConstraintViolation();
        //     context.buildConstraintViolationWithTemplate("Invalid file type").addConstraintViolation();
        //     return false;
        // }

        // Example for resolution validation (if needed)
        // try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        //     int width = bufferedImage.getWidth();
        //     int height = bufferedImage.getHeight();
        //     if (width < minWidth || height < minHeight) {
        //         context.disableDefaultConstraintViolation();
        //         context.buildConstraintViolationWithTemplate("Image resolution is too low").addConstraintViolation();
        //         return false;
        //     }
        // } catch (IOException e) {
        //     // Handle exception
        //     e.printStackTrace();
        //     return false;
        // }

        return true;
    }
}
