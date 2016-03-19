package com.wudaozi.coder.uniform;

/**
 * Created by yunhun.fyy on 2016/3/18.
 */

import com.wudaozi.coder.ComponentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The commands glUniform{1|2|3|4}{f|i} are used to change the value of the uniform variable
 * specified by location using the values passed as arguments.
 * The number specified in the command should match the number of components in the data type of
 * the specified uniform variable (e.g., 1 for float, int, bool; 2 for vec2, ivec2, bvec2, etc.).
 * The suffix f indicates that floating-point values are being passed;
 * the suffix i indicates that integer values are being passed,
 * and this type should also match the data type of the specified uniform variable.
 * The i variants of this function should be used to provide values for uniform variables
 * defined as int, ivec2, ivec3, ivec4, or arrays of these.
 * The f variants should be used to provide values for uniform variables of
 * type float, vec2, vec3, vec4, or arrays of these.
 * Either the i or the f variants may be used to provide values for uniform variables
 * of type bool, bvec2, bvec3, bvec4, or arrays of these.
 * The uniform variable will be set to false if the input value is 0 or 0.0f, and it will be set to true otherwise.
 * <p/>
 * All active uniform variables defined in a program object are initialized to 0
 * when the program object is linked successfully.
 * They retain the values assigned to them by a call to glUniform until the next successful link operation occurs
 * on the program object, when they are once again initialized to 0.
 * <p/>
 * The commands glUniform{1|2|3|4}{f|i}v can be used to modify a single uniform variable or a uniform variable array.
 * These commands pass a count and a pointer to the values to be loaded into a uniform variable
 * or a uniform variable array.
 * A count of 1 should be used if modifying the value of a single uniform variable,
 * and a count of 1 or greater can be used to modify an entire array or part of an array.
 * When loading n elements starting at an arbitrary position m in a uniform variable array,
 * elements m + n - 1 in the array will be replaced with the new values.
 * If m + n - 1 is larger than the size of the uniform variable array,
 * values for all array elements beyond the end of the array will be ignored.
 * The number specified in the name of the command indicates the number of components for each element in value,
 * and it should match the number of components in the data type of the specified uniform variable
 * (e.g., 1 for float, int, bool; 2 for vec2, ivec2, bvec2, etc.).
 * The data type specified in the name of the command must match the data type for the specified uniform variable
 * as described previously for glUniform{1|2|3|4}{f|i}.
 * <p/>
 * For uniform variable arrays, each element of the array is considered to be of the type indicated
 * in the name of the command (e.g., glUniform3f or glUniform3fv can be used to
 * load a uniform variable array of type vec3).
 * The number of elements of the uniform variable array to be modified is specified by count.
 * <p/>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface UniformVectorClassCoder {

    //glUniform{1|2|3|4}{f|i} for single value,
    // glUniform{1|2|3|4}{f|i}v for single value or an array

    int componentSize();

    ComponentType componentType();

}
