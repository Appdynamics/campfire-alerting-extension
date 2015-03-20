package com.appdynamics.extensions.campfire;


import com.appdynamics.extensions.campfire.exception.CampFireAlertException;
import com.google.common.base.Strings;
import com.madhackerdesigns.jinder.Campfire;
import com.madhackerdesigns.jinder.Room;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class CampfireMessagePoster {

    private static Logger logger = Logger.getLogger(CampfireMessagePoster.class);

    private Campfire campfire;

    public CampfireMessagePoster(CampfireConfig campfireConfig) {
        String userName = campfireConfig.getUserName();
        String password = campfireConfig.getPassword();
        String subDomain = campfireConfig.getSubDomain();

        if (Strings.isNullOrEmpty(userName)) {
            logger.error("userName can not be null");
            throw new IllegalArgumentException("userName can not be null");
        }

        if (Strings.isNullOrEmpty(password)) {
            logger.error("password can not be null");
            throw new IllegalArgumentException("password can not be null");
        }

        if (Strings.isNullOrEmpty(subDomain)) {
            logger.error("subDomain can not be null");
            throw new IllegalArgumentException("subDomain can not be null");
        }

        campfire = new Campfire(subDomain, userName, password);
    }

    public void postMessage(String roomName, String message) {

        try {
            List<Room> rooms = campfire.rooms();

            if (rooms == null || rooms.isEmpty()) {
                logger.error("No rooms found to post message");
                throw new CampFireAlertException("No rooms found to post message");
            }
            boolean success = false;
            for (Room room : rooms) {
                if (roomName.equals(room.name)) {
                    room.join();
                    room.speak(message);
                    room.leave();
                    success = true;
                }
            }

            if (!success) {
                logger.error("Room with name " + roomName + " not found");
                throw new CampFireAlertException("Room with name " + roomName + " not found");
            }

        } catch (IOException e) {
            logger.error("Error while posting message", e);
            throw new CampFireAlertException("Error while posting message", e);
        }
    }
}