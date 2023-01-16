package Menus;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    public static AdminResource adminResource = AdminResource.getInstance();
    public static HotelResource hotelResource = HotelResource.getInstance();
    public static Scanner scanner;

    public static void main(String[] args){
        scanner = new Scanner(System.in);
        try {
            boolean exit = false;
            while (!exit){
                String selection = showMenu();

                switch (selection){
                    case "1": findAndReserve();
                    case "2": seeMyReservations();
                    case "3": createAccount();
                    case "4": {
                        AdminMenu.setAdminResource(adminResource);
                        AdminMenu.startAdmin();
                    }
                    case "5": exit = true;
                    default: showMenu();
                }
            }

            System.exit(0);

        }catch (Exception e){
            e.getLocalizedMessage();
        }finally {
            scanner.close();
        }

    }

    public static void findAndReserve() throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Enter CheckIn Date dd/mm/yyyy example (01/02/2021)");
        Date checkInDate = dateFormat.parse(scanner.nextLine());
        System.out.println("Enter CheckOut Date dd/mm/yyyy example (01/02/2021)");
        Date checkOutDate = dateFormat.parse(scanner.nextLine());
        List<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

        if (!availableRooms.isEmpty()){
            Customer customer;

            System.out.println("Would you like book a room? y/n ");
            char bookOption =scanner.next().trim().charAt(0);

            if (bookOption == 'y'){
                customer = getExitAccount();

                if (customer == null){
                    System.out.println("You have not any account, please register and try again");;
                    customer = createAccount();
                }

                boolean bookSuccess = false;
                while (!bookSuccess){
                    System.out.println("Available Rooms: ");
                    System.out.println(availableRooms);
                    System.out.println("Please Enter a Room number which you want to book");
                    String roomNumber = scanner.next();
                    IRoom selectRoom = hotelResource.getRoom(roomNumber);

                    if (!availableRooms.contains(selectRoom)){
                        System.out.println("Sorry, this room not available");
                    }else{
                        hotelResource.bookARoom(customer, selectRoom, checkInDate, checkOutDate);
                        System.out.println("Success Reserved! Enjoy a good vacation ~ ");
                        scanner.nextLine();
                        bookSuccess = true;
                    }
                }
            }else {
                showMenu();
            }


        }
    }

    private static Customer getExitAccount(){
        System.out.println(" Enter your email ");
        String email = scanner.next();

        return hotelResource.getCustomer(email);
    }

    private static Customer createAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Enter your FirstName: ");
        String firstName = scanner.next();
        System.out.println(" Enter your LastName: ");
        String lastName = scanner.next();
        System.out.println(" Enter your Email: ");
        String email = scanner.next();

        try {
            hotelResource.createACustomer(firstName, lastName, email);;
        }catch (IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage());
        }

        return new Customer(firstName,lastName, email);
    }

    public static void seeMyReservations(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email");
        String email = scanner.next();
        System.out.println(hotelResource.getCustomer(email));
    }

    public static String showMenu(){
        System.out.println("_________________________________________");;
        System.out.println(" Main Menu ");
        System.out.println(" 1. Find and Reserve Room ");
        System.out.println(" 2. See My Reservations ");
        System.out.println(" 3. Create An Account ");
        System.out.println(" 4. Admin Menu ");
        System.out.println(" 5. Exit ");
        System.out.println("_________________________________________");
        System.out.println(" Enter To Start ");

        return scanner.nextLine();

    }
}
