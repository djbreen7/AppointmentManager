package test;

public class TryMe {

    public static void sayHello() throws Exception {
        System.out.println("Hello!");
        DatabaseConnection.makeConnection();
    }
}
