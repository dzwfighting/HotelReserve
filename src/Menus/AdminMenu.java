package Menus;

import api.AdminResource;
import model.Room;
import model.RoomType;

import java.util.Scanner;

public class AdminMenu {
    public static AdminResource adminResource = AdminResource.getInstance();
    public static Scanner scanner;

    public static void startAdmin(){
        scanner = new Scanner(System.in);
        try {
            boolean exit = false;
            while (!exit){
                String option = showAdminMenu();
                switch (option){
                    case "1": System.out.println(adminResource.getAllCustomers());
                    case "2": System.out.println(adminResource.getAllRooms());
                    case "3": adminResource.displayAllReservations();
                    case "4": addRoom();
                    case "5": exit = true;
                    default: showAdminMenu();
                }
            }

            String[] arguments = new String[] {""};
            MainMenu.main(arguments);
        }catch (Exception e){
            e.getLocalizedMessage();
        }finally {
            scanner.close();
        }
    }

    private static void addRoom(){
        Room room = new Room();
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Create a room number ");
        room.roomNumber = scanner.nextLine().trim();
        System.out.println(" Create the room price ");
        room.price = scanner.nextDouble();
        System.out.println(" Create the room type: 1. single, 2. double");
        int type = scanner.nextInt();
        if (type == 1){
            room.roomType = RoomType.SINGLE;
        }else{
            room.roomType = RoomType.DOUBLE;
        }

        adminResource.addRoom(room);
    }

    private static String showAdminMenu(){
        System.out.println("_______________________________");
        System.out.println(" Admin Menu ");
        System.out.println(" 1. See all Customers ");
        System.out.println(" 2. See all Rooms ");
        System.out.println(" 3. See all Reservations ");
        System.out.println(" 4. Add a Room ");
        System.out.println(" 5. Back to Main Menu ");
        System.out.println("_______________________________");
        System.out.println(" Enter to Start ");

        return scanner.nextLine();
    }

    public static void setAdminResource(AdminResource adminResource){
        AdminMenu.adminResource = adminResource;
    }
}
