package com.appdynamics.extensions.campfire;

import com.appdynamics.extensions.alerts.customevents.Event;
import com.appdynamics.extensions.alerts.customevents.EventBuilder;
import com.appdynamics.extensions.alerts.customevents.HealthRuleViolationEvent;
import com.appdynamics.extensions.alerts.customevents.OtherEvent;
import com.appdynamics.extensions.campfire.exception.CampFireAlertException;
import com.appdynamics.extensions.yml.YmlReader;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;

import java.io.File;


public class CampfireAlertExtension {

    private static Logger logger = Logger.getLogger(CampfireAlertExtension.class);

    private static final String CONFIG_FILENAME = "." + File.separator + "conf" + File.separator + "config.yaml";

    public static void main(String[] args) {
        String details = CampfireAlertExtension.class.getPackage().getImplementationTitle();
        String msg = "Using Extension Version [" + details + "]";
        logger.info(msg);

        if (args == null || args.length == 0) {
            logger.error("No arguments passed to the extension, exiting the program.");
            return;
        }
        processEvent(args);
    }

    /**
     * Processes the event provided by the controller to generate alert
     * Involves:
     * Parsing the event
     * Computing the alerting message
     * Sending the alert message to campfire room
     *
     * @param args
     */
    private static void processEvent(String[] args) {
        try {
            CampfireConfig campfireConfig = YmlReader.readFromFile(CONFIG_FILENAME, CampfireConfig.class);
            String alertMsg = null;
            try {
                Event event = parseEventParams(args);
                alertMsg = createAlertMessage(event);
                logger.debug("Computed alerting Message is = " + alertMsg);
            } catch (Exception exp) {
                logger.error("Failed to parse arguments, exiting the program", exp);
                return;
            }

            CampfireMessagePoster messagePoster = new CampfireMessagePoster(campfireConfig);

            String roomName = campfireConfig.getRoomName();
            if (!Strings.isNullOrEmpty(roomName)) {
                messagePoster.postMessage(roomName, alertMsg);
            } else {
                logger.error("Room name can not be null");
                throw new CampFireAlertException("Room name can not be null");
            }
        } catch (Exception exp) {
            logger.error("Exception in main()", exp);
        }
    }

    /**
     * Creates the alerting message string from the event object
     *
     * @param event
     */
    private static String createAlertMessage(Event event) {
        String status = "";

        if (event instanceof HealthRuleViolationEvent) {
            status = prepareHealthRuleViolationEventMessage(event);
        } else if (event instanceof OtherEvent) {
            status = prepareOtherEventMessage(event);
        }
        return status;
    }

    private static String prepareOtherEventMessage(Event event) {

        OtherEvent oEvent = (OtherEvent) event;

        StringBuilder sb = new StringBuilder();
        sb.append("Event = [").append("P:").append(oEvent.getPriority()).append(", ");
        sb.append("Severity:").append(oEvent.getSeverity()).append(", ");
        sb.append("App Name:").append(oEvent.getAppName()).append(", ");
        sb.append("Event Name:").append(oEvent.getEventNotificationName()).append(", ");
        sb.append("URL:  ").append(oEvent.getDeepLinkUrl()).append(oEvent.getEventNotificationId()).append("]");

        return sb.toString();
    }

    private static String prepareHealthRuleViolationEventMessage(Event event) {

        HealthRuleViolationEvent hrv = (HealthRuleViolationEvent) event;

        StringBuilder sb = new StringBuilder();
        sb.append("Health rule violation = [").append("P:").append(hrv.getPriority()).append(", ");
        sb.append("Severity:").append(hrv.getSeverity()).append(", ");
        sb.append("App Name:").append(hrv.getAppName()).append(", ");
        sb.append("Health rule name:").append(hrv.getHealthRuleName()).append(", ");
        sb.append("Affected Entity Type:").append(hrv.getAffectedEntityType()).append(", ");
        sb.append("Affected Entity Name:").append(hrv.getAffectedEntityName()).append(", ");
        sb.append("URL:  ").append(hrv.getDeepLinkUrl()).append(hrv.getIncidentID()).append("]");

        return sb.toString();
    }


    /**
     * Parses argument to create event object
     *
     * @param args
     */
    private static Event parseEventParams(String[] args) throws Exception {
        EventBuilder eventBuilder = new EventBuilder();
        Event event = eventBuilder.build(args);
        return event;
    }
}