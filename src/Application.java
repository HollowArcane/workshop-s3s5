import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import toolkit.util.Arrays;

public class Application
{
    public static class Customer
    {
        private Integer id;
        private String name;
        private String email;
        private String telephone;
        private String address;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getTelephone() {
            return telephone;
        }
        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        @Override
        public String toString() {
            return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", telephone=" + telephone
                    + ", address=" + address + "]";
        }

        
    }

    public static interface ThrowingFunction<T, R, E extends Exception>
    {
        R doStuff(T arg) throws E;
    }

    public static void main(String[] args)
        throws SQLException
    {
        ArrayList<Customer> customers = doStuffWithConnection(result -> {
            ArrayList<Customer> cs = new ArrayList<>();
            while(result.next())
            {
                Customer c = new Customer();
                c.setId(result.getInt("name"));
                c.setEmail(result.getString("email"));
                c.setAddress(result.getString("address"));
                c.setName(result.getString("name"));
                c.setTelephone(result.getString("telephone"));

                cs.add(c);
            }
        
            return cs;
        });

        System.out.println(Arrays.join(customers, "\n"));
    }

    private static <T, E extends Exception> T doStuffWithConnection(ThrowingFunction<ResultSet, T, E> callback)
        throws SQLException, E
    {
        T res = null;
        try(
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/workshop", "postres", "43710");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet result = statement.executeQuery();
        )
        {
            res = callback.doStuff(result);
        }

        return res;
    }
}