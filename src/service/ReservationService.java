package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationService {
    private static ReservationService reservationService = null;

    private ReservationService(){}

    public static ReservationService getInstance(){
        if (null == reservationService){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    ArrayList<IRoom> rooms = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();

    public void addRoom(IRoom room){
        rooms.add(room);
    }

    public IRoom getARoom(String roomId){
        for (IRoom room: rooms){
            if (room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public List<IRoom> findRooms (Date checkInDate, Date checkOutDate){
        List<IRoom> availableRooms = new ArrayList<>();
        try {
            List<IRoom> reservationRooms = getReservedRooms(checkInDate, checkOutDate);
            for (IRoom room : rooms){
                if (!reservationRooms.contains(room)){
                    availableRooms.add(room);
                }
            }
        }catch (Exception e){
            if (availableRooms.isEmpty()) return null;
        }
        return availableRooms;
    }

    public List<Reservation> getCustomerReservations(String customerEmail){
        List<Reservation> getReservationsByEmail = new ArrayList<>();
        for (Reservation reservation : reservations){
            if (reservation.getCustomer().getEmail().equals(customerEmail)){
                getReservationsByEmail.add(reservation);
            }
        }
        return getReservationsByEmail;
    }

    public void printAllReservations(){
        for (Reservation reservation : reservations){
            System.out.println(reservation);
        }
    }

    public List<IRoom> getAllRooms(){
        return rooms;
    }

    public boolean ifRoomAvailable(IRoom room, List<IRoom> availableRooms){
        for (IRoom availableRoom : availableRooms){
            if (availableRoom.equals(room)){
                return true;
            }
        }
        return false;
    }

    public List<IRoom> getReservedRooms(Date checkInDate, Date checkOutDate){
        List<IRoom> reservedRooms = new ArrayList<>();
        try {
            for (Reservation reservation : reservations){
                if (reservation.getCheckInDate().getTime() <= checkInDate.getTime()
                        && reservation.getCheckOutDate().getTime() >= checkOutDate.getTime()){
                    reservedRooms.add(reservation.getRoom());
                }
            }
        }catch (Exception e){
            if (reservedRooms.isEmpty()) return null;
        }

        return reservedRooms;
    }


}
