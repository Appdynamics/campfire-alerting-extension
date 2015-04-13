campfire-alerting-extension
==========================

This alerting extension is only meant for on-premise controllers.

## Use Case

The Campfire alerting extension enables AppDynamics to post custom notifications as messages in a provided Campfire chat room. Chat room members can see a brief description of the health rule violation or event and get more detail on AppDynamics by following the URL provided in the alert message.

### Prerequisites

- You have a Campfire Account.
- You have a chat room created (and active) to send alert messages to.
- Campfire jinder library is not in maven repo. To get it using maven we have to install the library in the local maven repo. Jinder 0.0.1 is checked in to the lib folder. Use the below maven command to install the library to local maven repo. For latest source of Jinder please go to https://github.com/flintinatux/jinder

mvn install:install-file -Dfile={path to jinder jar} -DgroupId=com.madhackerdesigns -DartifactId=jinder -Dversion=0.0.1 -Dpackaging=jar

### Steps

1. Run "mvn clean install"
2. Download and unzip the file 'target/campfire-alerting-extension.zip'
3. Create a folder/directory as <controller-install-path>custom\actions\campfire-alert
4. Unzip the contents from zip (from step 2) in the above directory. You will see a prompt if you already have a custom.xml file in the /custom/actions/ directory. Don't let the unzip process overwrite it. Instead, merge the contents.
5. Specify the values the properties in campfire-alert/conf/config.yaml
6. In directory = <controller-install-dir>\custom\actions add custom.xml, add below action in xml file(modify if the file already exists, and merge the below action)

 ```
 <custom-actions>
 	<action>
		<type>campfire-alert</type>
       <!-- For windows *.bat -->
 		<executable>campfire-alert.bat</executable>
 		<!-- For Linux/Unix *.sh -->
 		<!-- executable>campfire-alert.sh</executable -->
 	</action>
 </custom-actions>
 ```

7. UnComment the appropriate executable tag based on windows or linux/unix machine.

8. Installing Custom Actions:

      To create a Custom Action, first refer to the the following topics (requires login):
      * [Creating custom action](http://docs.appdynamics.com/display/PRO13S/Custom+Actions)
      * [Build an Alerting Extension](http://docs.appdynamics.com/display/PRO13S/Build+an+Alerting+Extension)

Now you are ready to use this extension as a custom action. In the AppDynamics UI, go to Alert & Respond -> Actions. Click Create Action. Select Custom Action and click OK. In the drop-down menu you can find the action called 'campfire-alert'.

##Contributing

Find out more in the [AppSphere](http://community.appdynamics.com/t5/eXchange-Community-AppDynamics/Campfire-Alerting-Extension/idi-p/14882) community.

##Support

For any questions or feature request, please contact [AppDynamics Center of Excellence](mailto:help@appdynamics.com).
