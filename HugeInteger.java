package HugeInteger;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isDigit;
import java.util.Random;

public class HugeInteger {

    int[] data;
    int length = 0;
    char sign;//+ if positive, - if negative

    public HugeInteger(String val) throws IllegalArgumentException {
        int size = val.length();

        if (size == 0) {//empty string
            throw new IllegalArgumentException("Invalid character.");
        }

        if (val.charAt(0) == '-') {//negative object initialization
            sign = '-';
            size = size - 1;
            data = new int[size];
            if (size > 1) {
                for (int i = 0; i <= size - 1; i++) {//verify that each character is a valid digiit
                    if (isDigit(val.charAt(size - i))) {
                        data[i] = getNumericValue(val.charAt(size - i));
                    } else {
                        throw new IllegalArgumentException("Invalid character.");
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid character.");
            }
        } else {//positive object initialization
            sign = '+';
            data = new int[size];
            for (int i = 0; i <= size - 1; i++) {//verify that each character is a valid digiit
                if (isDigit(val.charAt(size - 1 - i))) {
                    data[i] = getNumericValue(val.charAt(size - 1 - i));
                } else {
                    throw new IllegalArgumentException("Invalid character.");
                }
            }
        }
        length = size;
    }

    public HugeInteger(int n) throws IllegalArgumentException {//random HugeInteger object constructor
        if (n > 0) {
            length = n;

            data = new int[n];
            Random random = new Random();
            sign = (random.nextInt(2) == 1 ? '+' : '-');//randomize sign

            for (int i = 0; i < n - 1; i++) {//initialize rest of array
                data[i] = random.nextInt(10);
            }
            data[n - 1] = random.nextInt(9) + 1;//range for first number will not include 0
        } else {
            throw new IllegalArgumentException("Invalid character.");
        }
    }

    public HugeInteger() {//null constructor
        length = 0;
        data = new int[0];
    }

    public HugeInteger add(HugeInteger h) {

        int[] big;
        int blength;
        int[] small;
        int slength;
        //determines which of the 2 objects is bigger to simplify operations
        if (length >= h.length) {
            big = data;
            blength = length;
            small = h.data;
            slength = h.length;
        } else {
            big = h.data;
            blength = h.length;
            small = data;
            slength = length;
        }

        HugeInteger end = new HugeInteger();

        if ((sign == '+' && h.sign == '+') || (sign == '-' && h.sign == '-')) {
            //regular add
            //end sign is +
            int[] sum = new int[blength];
            for (int i = 0; i < slength; i++) {
                int add = big[i] + small[i];
                //initialize add
                if (sum[i] + add >= 10) {//if the sum of the 2 numbers results in a carry
                    if ((i == sum.length - 1) && (sum[i] + 1 == 10) || (i == sum.length - 1) && (sum[i] + add >= 10)) {//resize if the index is the last element of the array
                        int sum2[] = new int[sum.length + 1];
                        int j;
                        for (j = 0; j < sum.length; j++) {
                            sum2[j] = sum[j];
                        }
                        sum = sum2;
                        sum[i] = (sum[i] + add) % 10;
                        sum[i + 1]++;
                    } else {
                        sum[i] = (sum[i] + add) % 10;
                        sum[i + 1]++;
                    }
                } else {//no carry from addition
                    sum[i] += add;
                }
            }

            for (int j = 0; j < (blength - slength); j++) {//after running through smaller object, continue loop through bigger object
                if (sum[slength + j] + big[slength + j] >= 10) {//if there is a carry
                    if (((blength - j) == sum.length - 1) && (sum[slength + j] + 1 == 10) || ((blength - j) == sum.length - 1) && (sum[slength + j] + big[slength + j] >= 10)) {//if nescessary, resize object
                        int sum3[] = new int[sum.length + 1];
                        int k;
                        for (k = 0; k < sum.length; k++) {
                            sum3[k] = sum[k];
                        }
                        sum = sum3;
                        sum[slength + j] = (sum[slength + j] + big[slength + j]) % 10;
                        sum[slength + j + 1]++;
                    } else {
                        sum[slength + j] = (sum[slength + j] + big[slength + j]) % 10;
                        sum[slength + j + 1]++;
                    }
                } else {
                    sum[slength + j] += big[slength + j];
                }
            }
            end.data = sum;
            end.sign = big == data ? sign : h.sign;
            end.length = sum.length;
        }

        if ((sign == '+' && h.sign == '-') || (sign == '-' && h.sign == '+')) {
            //subtraction between the 2 objects by adding
            //sign is of bigger number
            //calculate the complement of the negative number
            int[] complement = new int[blength];
            complement[0] = 10 - small[0];
            for (int i = 1; i < slength; i++) {
                complement[i] = 9 - small[i];
            }
            for (int j = slength; j < blength; j++) {
                complement[j] = 9;
            }

            small = complement;
            slength = complement.length;

            int[] subtract = new int[length];

            for (int i = 0; i < slength - 1; i++) {
                //initialize add
                int add = big[i] + small[i];

                if (subtract[i] + add >= 10) {//if the sum of the 2 numbers results in a carry
                    if ((i == subtract.length - 1) && (subtract[i] + 1 == 10) || (i == subtract.length - 1) && (subtract[i] + add >= 10)) {//if the index is the last element, resize 
                        int subtract2[] = new int[subtract.length + 1];
                        int j;
                        for (j = 0; j < subtract.length; j++) {
                            subtract2[j] = subtract[j];
                        }
                        subtract = subtract2;
                        subtract[i] = (subtract[i] + add) % 10;
                        subtract[i + 1]++;
                    } else {
                        subtract[i] = (subtract[i] + add) % 10;
                        subtract[i + 1]++;
                    }
                } else {
                    subtract[i] += add;
                }
            }
            subtract[slength - 1] += (big[slength - 1] + small[slength - 1]) % 10;

            end.data = subtract;
            end.sign = big == data ? sign : h.sign;
            end.length = subtract.length;

        }
        return end;
    }

    public HugeInteger subtract(HugeInteger h) {
        int[] big;
        int blength;
        int[] small;
        int slength;

        if (length >= h.length) {
            big = data;
            blength = length;
            small = h.data;
            slength = h.length;
        } else {
            big = h.data;
            blength = h.length;
            small = data;
            slength = length;
        }

        HugeInteger end = new HugeInteger();

        if ((sign == '+' && h.sign == '-') || (sign == '-' && h.sign == '+')) {
            //regular add
            int[] sum = new int[blength];
            for (int i = 0; i < slength; i++) {
                //initialize add
                int add = big[i] + small[i];

                if (sum[i] + add >= 10) {//if the sum of the 2 numbers results in a carry
                    if ((i == sum.length - 1) && (sum[i] + 1 == 10) || (i == sum.length - 1) && (sum[i] + add >= 10)) {//if the index is the last element, resize
                        int sum2[] = new int[sum.length + 1];
                        int j;
                        for (j = 0; j < sum.length; j++) {
                            sum2[j] = sum[j];
                        }
                        sum = sum2;
                        sum[i] = (sum[i] + add) % 10;
                        sum[i + 1]++;
                    } else {
                        sum[i] = (sum[i] + add) % 10;
                        sum[i + 1]++;
                    }
                } else {
                    sum[i] += add;
                }
            }

            for (int j = 0; j < (blength - slength); j++) {//after running through smaller object, continue loop through bigger object
                if (sum[slength + j] + big[slength + j] >= 10) {//if there is a carry
                    if (((blength - j) == sum.length - 1) && (sum[slength + j] + 1 == 10) || ((blength - j) == sum.length - 1) && (sum[slength + j] + big[slength + j] >= 10)) {
                        int sum3[] = new int[sum.length + 1];
                        int k;
                        for (k = 0; k < sum.length; k++) {
                            sum3[k] = sum[k];
                        }
                        sum = sum3;
                        sum[slength + j] = (sum[slength + j] + big[slength + j]) % 10;
                        sum[slength + j + 1]++;
                    } else {
                        sum[slength + j] = (sum[slength + j] + big[slength + j]) % 10;
                        sum[slength + j + 1]++;
                    }
                } else {
                    sum[slength + j] += big[slength + j];
                }
            }

            end.data = sum;
            end.sign = big == data ? sign : h.sign;
            end.length = sum.length;
        }

        if ((sign == '+' && h.sign == '+') || (sign == '-' && h.sign == '-')) {
            //subtraction between the 2 numbers by adding the complement of the negative number
            //sign is of bigger number
            int[] complement = new int[blength];
            complement[0] = 10 - small[0];
            for (int i = 1; i < slength; i++) {
                complement[i] = 9 - small[i];
            }
            for (int j = slength; j < blength; j++) {
                complement[j] = 9;
            }

            small = complement;
            slength = complement.length;

            int[] subtract = new int[length];

            for (int i = 0; i < slength; i++) {
                //initialize add
                int add = big[i] + small[i];

                if (subtract[i] + add >= 10) {//if the sum of the 2 numbers results in a carry
                    if ((i == subtract.length - 1) && (subtract[i] + 1 == 10) || (i == subtract.length - 1) && (subtract[i] + add >= 10)) {//if the index is the last element, resize 
                        int subtract2[] = new int[subtract.length + 1];
                        for (int j = 0; j < subtract.length; j++) {
                            subtract2[j] = subtract[j];
                        }
                        subtract = subtract2;
                        subtract[i] = (subtract[i] + add) % 10;
                        subtract[i + 1]++;
                    } else {
                        subtract[i] = (subtract[i] + add) % 10;
                        subtract[i + 1]++;
                    }
                } else {
                    subtract[i] += add;
                }
            }
            //different lengths for subtract
            subtract[slength - 1] += (big[slength - 1] + small[slength - 1]) % 10;

            //eliminate extra 1 in the array after adding the complement
            int sub2[] = new int[subtract.length - 1];
            for (int n = 0; n < sub2.length; n++) {
                sub2[n] = subtract[n];
            }

            subtract = sub2;

            end.data = subtract;
            end.sign = big == data ? sign : h.sign;
            end.length = subtract.length;

        }
        return end;
    }

    public HugeInteger multiply(HugeInteger h) {
        //determines bigger and smaller objects to simplify operations
        int[] big;
        int blength;
        int[] small;
        int slength;

        if (length >= h.length) {
            big = data;
            blength = length;
            small = h.data;
            slength = h.length;
        } else {
            big = h.data;
            blength = h.length;
            small = data;
            slength = length;
        }

        HugeInteger end = new HugeInteger();

        int[] product = new int[blength];

        for (int i = 0; i < slength; i++) {
            for (int j = 0; j < blength; j++) {
                //initialize add
                int add = big[j] * small[i];

                if ((j + 1 == product.length || j + i == product.length - 1)) {//resizes array if last element
                    int product2[] = new int[product.length + 1];
                    for (int n = 0; n < product.length; n++) {
                        product2[n] = product[n];
                    }
                    product = product2;
                }

                int counter1 = 0;
                int carry;//holds carry
                int temp;//temporary variable to hold

                if (product[j + i + counter1] + add >= 10) {
                    carry = (product[j + i] + add) / 10;
                    product[j + i] = (product[j + i] + add) % 10;
                    counter1 = 1;
                    while (product[j + i + counter1] + carry >= 10) {//to distribute the carry along the rest of the integers
                        if (j + i + counter1 == product.length - 1) {
                            int product2[] = new int[product.length + 1];
                            for (int n = 0; n < product.length; n++) {
                                product2[n] = product[n];
                            }
                            product = product2;
                        }
                        temp = (product[j + i + counter1] + carry) % 10;
                        carry = (product[j + i + counter1] + carry) / 10;
                        product[j + i + counter1] = temp;
                        counter1++;
                    }
                    product[j + i + counter1] += carry;
                } else {
                    product[j + i] += add;
                }
            }
        }

        if (0 == product[product.length - 1]) {//if first integer is 0 or an extra memory space is created afer operations
            int product2[] = new int[product.length - 1];
            for (int n = 1; n < product2.length; n++) {
                product2[n] = product[n];
            }
            product = product2;
        }

        end.data = product;
        end.sign = (sign == '+' && h.sign == '+') || (sign == '-' && h.sign == '-') == true ? '+' : '-';
        end.length = product.length;
        return end;
    }

    public int compareTo(HugeInteger h) {
        int compare = 0;//0 if equal
        //1 if this object is greater
        //-1 if h object is smaller

        if (length > h.length) {
            compare = sign == '+' ? 1 : -1;
        }
        if (h.length > length) {
            compare = h.sign == '+' ? -1 : 1;
        }
        if (length == h.length) {
            for (int i = 0; i < length; i++) {
                if (data[i] > h.data[i]) {
                    compare = 1;
                    break;
                }
                if (data[i] < h.data[i]) {
                    compare = -1;
                    break;
                }
            }
        }
        return compare;
    }

    @Override
    public String toString() {
        //returns data array
        String output = new String();
        // creates an empty string
        for (int i = length - 1; i >= 0; i--) {
            output = output + data[i];
        }
        output += "\n";
        return output;
    }
}
