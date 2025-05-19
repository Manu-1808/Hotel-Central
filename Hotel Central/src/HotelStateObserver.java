public interface HotelStateObserver {
    void onHotelStateChanged(int availableRooms, int activeReservations, int registeredClients);
}