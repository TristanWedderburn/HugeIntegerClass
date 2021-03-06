package HugeInteger;

public class Test {

    public static void main(String[] args) {

        //constructor test
        String test = "-12345";
        HugeInteger h1 = new HugeInteger(test);
        System.out.print("h1: ");
        System.out.print(h1.toString());
        System.out.println("sign: " + h1.sign);

        //random constructor test
        HugeInteger h2 = new HugeInteger(5);
        System.out.print("h2: ");
        System.out.print(h2.toString());
        System.out.println("sign: " + h2.sign + ", length: " + h2.length);

        HugeInteger h3 = new HugeInteger("-123");
        System.out.print("h3: ");
        System.out.print(h3.toString());
        System.out.println("sign: " + h3.sign + ", length: " + h3.length);

        //addition test
        HugeInteger h4 = h1.add(h3);
        System.out.print("h1 and h3 added: " + h4.toString());
        System.out.println("sign: " + h4.sign + ", length: " + h4.length);

        //subtract test
        HugeInteger h5 = new HugeInteger("123");
        HugeInteger h6 = h1.subtract(h5);
        System.out.print("h1 and h5 subtracted: " + h6.toString());
        System.out.println("sign: " + h6.sign + ", length: " + h6.length);

        //multiply test
        HugeInteger h7 = new HugeInteger("12345678");
        HugeInteger h8 = h1.multiply(h7);
        System.out.print("h1 and h7 multiplied: " + h8.toString());
        System.out.println("sign: " + h8.sign + ", length: " + h8.length);

        //compare test
        System.out.println("compare h1 to h7: " + h1.compareTo(h7));
    }
}
