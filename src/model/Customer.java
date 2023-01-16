package model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        if(checkValid(email) == 1){
            this.email = email;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static int checkValid(String email){
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        int flag = 1;
        if(!matcher.matches()){
            flag = 0;
            throw new IllegalArgumentException("Invalid email, please input again");
        }
        return flag;
    }

    @Override
    public String toString() {
        return "Customer Information: " +
                "First Name: " + firstName +
                "; Last Name: " + lastName + '\'' +
                "; Email: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer otherCustomer = (Customer) o;

        boolean firstNameEquals = (this.firstName == null && otherCustomer.firstName == null)
                || (this.firstName != null && this.firstName.equals(otherCustomer.firstName));
        boolean lastNameEquals = (this.lastName == null && otherCustomer.lastName == null)
                || (this.lastName != null && this.lastName.equals(otherCustomer.lastName));
        boolean emailEquals = (this.email == null && otherCustomer.email == null)
                || (this.email != null && this.email.equals(otherCustomer.email));

        return firstNameEquals && lastNameEquals && emailEquals;
    }

    @Override
    public int hashCode() {
        int result = 17;
        if(firstName != null){
            result = 31 * result + firstName.hashCode();
        }
        if(lastName != null){
            result = 31 * result + lastName.hashCode();
        }
        if (email != null){
            result = 31 * result + email.hashCode();
        }

        return result;
    }
}
