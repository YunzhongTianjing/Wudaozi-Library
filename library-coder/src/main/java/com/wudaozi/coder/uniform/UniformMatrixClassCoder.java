package com.wudaozi.coder.uniform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The commands glUniformMatrix{2|3|4}fv are used to modify a matrix or an array of matrices.
 * The numbers in the command name are interpreted as the dimensionality of the matrix.
 * The number 2 indicates a 2 × 2 matrix (i.e., 4 values), the number 3 indicates a 3 × 3 matrix (i.e., 9 values),
 * and the number 4 indicates a 4 × 4 matrix (i.e., 16 values).
 * Each matrix is assumed to be supplied in column major order.
 * The count argument indicates the number of matrices to be passed.
 * A count of 1 should be used if modifying the value of a single matrix,
 * and a count greater than 1 can be used to modify an array of matrices.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface UniformMatrixClassCoder {
    //glUniformMatrix{2|3|4}fv

    int dimensionality();

    String classNameForGeneratedClass() default "";
}
