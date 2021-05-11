# cake-manager-master

This project has been rebuilt from the ground up using Spring Boot framework.

A second artifact cake-auth has also been built for OAuth2 authentication and authorization of the cake user.  It is responsible for generating tokens specific to the client and user.

The following is the project structure for our Cake Manager:

1) Configuration: This contains the Security Config, Application Config and Initializer.  
	- The Initializer sets up and configures the dispatcher servlet.
	- The Security config provides the usual Spring security configuration and bootstraps the authorization and resource servers using in memory token store.
	- The Application config imports the other configuration files as well as configures the properties source.

2) Presentation / View - This has been implemented using JSP to view and add the cakes on the client.

3) Cake Controller - This has been implemented to receive, map and handle requests from the client.

4) Login Controller - This has been implemented to forward login requests to the OAuth authorization service. 

4) Service class - This has been implemented to store the list of cakes in memory as well as perform all the business logic for requests received from the controller.

5) Cake object class - This is a simple POJO that creates an object for each cake.

6) Cake Authentication Provider - This class processes the authentication request when the user logs in.

7) Cake Remote Token Services - This is a custom-built class that carries out services related to the tokens specific to the client and user.

8) Cake Manager Main - This class runs the Spring Boot application for the project.

9) Test classes - Unit test classes for the service layer and controller layer.

The READ ME file contains instructions for running the application under Enhanced Project Info.

====================================================================

Enhanced Project Info
=====================

To run a server locally execute the following commands:

1) In cake-auth, select from the menu bar

    Run -> Run 'CakeAuthMain'

2) In cake-manager-master, select from the menu bar

    Run -> Run 'CakeManagerMain'

3) Access the following URL:

    `http://localhost:8282/`

4) Once the URL is accessed, click the `Add New Cake`.  This will redirect to the Login page.

5) Input username (cakeuser) and password (password).  This will be redirected to the /cakes endpoint.

6) Input details for the new cake and click the Add Cake button.  This will add the new cake to the list and display on the table.

====================================================================
