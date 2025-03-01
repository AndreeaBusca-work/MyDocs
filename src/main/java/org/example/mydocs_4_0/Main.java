package org.example.mydocs_4_0;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.w3c.dom.Text;

import java.awt.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    private Stage primaryStage;
    private Scene loginScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Initialize Login Scene
        loginScene = createLoginScene();

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Portal");
        primaryStage.show();
    }

    private Scene createLoginScene() {
        // Title label
        Label titleLabel = new Label("MyDocs");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #f6fcff;");

        // UserType selection
        Label userTypeLabel = new Label("User Type:");
        userTypeLabel.setStyle(" -fx-font-size: 14px;");
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("admin", "citizen", "representant");
        userTypeComboBox.setPromptText("Select User Type");
        userTypeComboBox.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        // First Name input
        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setStyle(" -fx-font-size: 14px;");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");
        firstNameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        // Last Name input
        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setStyle("-fx-font-size: 14px;");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        lastNameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        // Password input
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 14px;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        loginButton.setDefaultButton(true);


        // Action when login button is clicked
        loginButton.setOnAction(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String password = passwordField.getText().trim();
            String userTypeInput = userTypeComboBox.getValue();

            // Validate input
            if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || userTypeInput == null || userTypeInput.isEmpty()) {
                showAlert("Error", "All fields must be completed!");
                return;
            }

            String regex = ".*\\d.*";  // This checks if the string contains at least one digit

            // Create a pattern and matcher
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher1 = pattern.matcher(firstName);
            Matcher matcher2 = pattern.matcher(lastName);

            if (matcher1.matches() || matcher2.matches()) {
                showAlert("Error", "Names should contain only letters!");
                return;
            }

            firstName = capitalizeFirstLetter(firstName);
            lastName = capitalizeFirstLetter(lastName);

            UserType userType = UserType.valueOf(userTypeInput.toLowerCase());

            UserManager userManager = new UserManager();

            try {
                userManager.login(firstName, lastName, password, userTypeInput);

                String fullName = firstName + " " + lastName;

                // Navigate to the respective menu scene
                switch (userType) {
                    case admin -> showAdminMenuScene();
                    case citizen -> showCitizenMenuScene(fullName);
                    case representant -> showRepresentantMenuScene();
                }
            } catch (InvalidLoginException ex) {
                showAlert("Error", "Invalid login credentials or user type!");
            }
        });

        // Group inputs for clean layout
        VBox userTypeBox = new VBox(5, userTypeLabel, userTypeComboBox);
        VBox firstNameBox = new VBox(5, firstNameLabel, firstNameField);
        VBox lastNameBox = new VBox(5, lastNameLabel, lastNameField);
        VBox passwordBox = new VBox(5, passwordLabel, passwordField);

        // Form layout
        VBox formLayout = new VBox(15, userTypeBox, firstNameBox, lastNameBox, passwordBox, loginButton);
        formLayout.setAlignment(Pos.CENTER_LEFT);
        formLayout.setPadding(new Insets(20));
        formLayout.setStyle("-fx-background-color: #f6fcff;; -fx-border-radius: 10px; -fx-padding: 20;");

        // Root layout
        VBox root = new VBox(20, titleLabel, formLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Attach BootstrapFX stylesheet
        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        return scene;
    }

    private void showAdminMenuScene() {
        // Title label for Admin Menu
        Label adminLabel = new Label("Admin Menu");
        adminLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill:  #21a5e7;");

        // Buttons for Admin Menu actions
        Button logoutButton = new Button("Logout");
        Button createuserButton = new Button("Create User");
        Button deleteuserButton = new Button("Delete User");
        Button exitButton = new Button("Exit");

        logoutButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        createuserButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        deleteuserButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        exitButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");


        // Layout for Admin Menu buttons
        VBox adminLayout = new VBox(15, adminLabel, createuserButton, deleteuserButton, exitButton, logoutButton);
        adminLayout.setSpacing(15);
        adminLayout.setPadding(new Insets(20));
        adminLayout.setAlignment(Pos.CENTER);

        // Background Color for Admin Menu Layout
        adminLayout.setStyle("-fx-background-color: #f6fcff; -fx-border-radius: 10px;");

        // Root layout with padding
        VBox root = new VBox(30, adminLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Scene creation
        Scene adminScene = new Scene(root, 400, 400);
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Admin Menu");

        // Button Actions
        logoutButton.setOnAction(e ->{ primaryStage.setTitle("Login Portal");primaryStage.setScene(loginScene);});
        createuserButton.setOnAction(e -> showCreateUserScene(primaryStage, adminScene));
        deleteuserButton.setOnAction(e -> showDeleteUserScene(primaryStage, adminScene));
        exitButton.setOnAction(e -> showExitConfirmationScene(primaryStage, adminScene));



    }

    private void showCreateUserScene(Stage primaryStage, Scene adminScene){
        Label createUserLabel = new Label("Create User");
        createUserLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #f6fcff;");

        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setStyle(" -fx-font-size: 14px;");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");
        firstNameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setStyle(" -fx-font-size: 14px;");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        lastNameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle(" -fx-font-size: 14px;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password name");
        passwordField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label userTypeLabel = new Label("User Type:");
        userTypeLabel.setStyle(" -fx-font-size: 14px;");
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("admin", "citizen", "representant");
        userTypeComboBox.setPromptText("Select User Type");
        userTypeComboBox.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label idLabel = new Label("User ID:");
        idLabel.setStyle(" -fx-font-size: 14px;");
        TextField idField = new TextField();
        idField.setPromptText("Enter your id name");
        idField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Button submitButton = new Button("Create");
        Button backButton = new Button("Back");

        submitButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        submitButton.setDefaultButton(true);

        backButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        backButton.setDefaultButton(true);



        submitButton.setOnAction(e -> {
            try {
                String firstName = capitalizeFirstLetter(firstNameField.getText().trim());
                String lastName = capitalizeFirstLetter(lastNameField.getText().trim());
                String password = passwordField.getText().trim();
                String userTypeInput = userTypeComboBox.getValue();
                int id = Integer.parseInt(idField.getText().trim());

                if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || userTypeInput == null || id <=0) {
                    showAlert("Error","All fields must be completed!");
                    return;
                }

                if(id <=0){
                    showAlert("Error", "ID must be a positive number!");
                    return;
                }

                String regex = ".*\\d.*";  // This checks if the string contains at least one digit

                // Create a pattern and matcher
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher1 = pattern.matcher(firstName);
                Matcher matcher2 = pattern.matcher(lastName);

                if (matcher1.matches() || matcher2.matches()) {
                    showAlert("Error", "Names should contain only letters!");
                    return;
                }

                UserType userType = UserType.valueOf(userTypeInput.toLowerCase());
                User user = new User(firstName, lastName, password, userType, id);

                if(UserManager.alreadyExists(firstName,lastName,userTypeInput)){
                    showAlert("Error", "User with the given credentials already exists!");
                    return;
                }

                UserManager.createUser(user);

                primaryStage.setTitle("Admin Menu");
                primaryStage.setScene(adminScene);
            } catch (Exception ex) {
                showAlert("Error", "Failed to create user: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> {primaryStage.setTitle("Admin Menu");primaryStage.setScene(adminScene);});

        // Group inputs for clean layout
        VBox userTypeBox = new VBox(5, userTypeLabel, userTypeComboBox);
        VBox firstNameBox = new VBox(5, firstNameLabel, firstNameField);
        VBox lastNameBox = new VBox(5, lastNameLabel, lastNameField);
        VBox passwordBox = new VBox(5, passwordLabel, passwordField);
        VBox idBox = new VBox(5,idLabel,idField);

        // Create user layout
        VBox createUserLayout = new VBox(15, userTypeBox, firstNameBox, lastNameBox, passwordBox, idBox,submitButton,backButton);
        createUserLayout.setAlignment(Pos.CENTER_LEFT);
        createUserLayout.setPadding(new Insets(20));
        createUserLayout.setStyle("-fx-background-color: #f6fcff;; -fx-border-radius: 10px; -fx-padding: 20;");

        // Root layout
        VBox root = new VBox(20, createUserLabel, createUserLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Attach BootstrapFX stylesheet
        Scene createUserScene = new Scene(root, 500, 500);
        createUserScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setTitle("Create User");
        primaryStage.setScene(createUserScene);
    }

    private void showDeleteUserScene(Stage primaryStage, Scene adminScene){
        Label deleteUserLabel = new Label("Delete User");
        deleteUserLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #f6fcff;");

        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setStyle(" -fx-font-size: 14px;");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");
        firstNameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setStyle(" -fx-font-size: 14px;");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        lastNameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");


        Label userTypeLabel = new Label("User Type:");
        userTypeLabel.setStyle(" -fx-font-size: 14px;");
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("admin", "citizen", "representant");
        userTypeComboBox.setPromptText("Select User Type");
        userTypeComboBox.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label idLabel = new Label("User ID:");
        idLabel.setStyle(" -fx-font-size: 14px;");
        TextField idField = new TextField();
        idField.setPromptText("Enter your id name");
        idField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Button submitButton = new Button("Delete");
        Button backButton = new Button("Back");

        submitButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        submitButton.setDefaultButton(true);

        backButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        backButton.setDefaultButton(true);


        submitButton.setOnAction(e -> {
            try {
                String firstName = capitalizeFirstLetter(firstNameField.getText().trim());
                String lastName = capitalizeFirstLetter(lastNameField.getText().trim());
                String userTypeInput = userTypeComboBox.getValue();
                int id = Integer.parseInt(idField.getText().trim());

                if (firstName.isEmpty() || lastName.isEmpty() || userTypeInput == null || id <=0) {
                    showAlert("Error","All fields must be completed!");
                    return;
                }

                if(id <=0){
                    showAlert("Error", "ID must be a positive number!");
                    return;
                }

                String regex = ".*\\d.*";  // This checks if the string contains at least one digit

                // Create a pattern and matcher
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher1 = pattern.matcher(firstName);
                Matcher matcher2 = pattern.matcher(lastName);

                if (matcher1.matches() || matcher2.matches()) {
                    showAlert("Error", "Names should contain only letters!");
                    return;
                }


                UserManager.deleteUser(firstName, lastName, userTypeInput,id);

                primaryStage.setTitle("Admin Menu");
                primaryStage.setScene(adminScene);
            } catch (Exception ex) {
                showAlert("Error", "Failed to delete user: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> {primaryStage.setTitle("Admin Menu");primaryStage.setScene(adminScene);});

        // Group inputs for clean layout
        VBox userTypeBox = new VBox(5, userTypeLabel, userTypeComboBox);
        VBox firstNameBox = new VBox(5, firstNameLabel, firstNameField);
        VBox lastNameBox = new VBox(5, lastNameLabel, lastNameField);
        VBox idBox = new VBox(5,idLabel,idField);

        // Delete user layout
        VBox deleteUserLayout = new VBox(15, userTypeBox, firstNameBox, lastNameBox, idBox,submitButton,backButton);
        deleteUserLayout.setAlignment(Pos.CENTER_LEFT);
        deleteUserLayout.setPadding(new Insets(20));
        deleteUserLayout.setStyle("-fx-background-color: #f6fcff;; -fx-border-radius: 10px; -fx-padding: 20;");

        // Root layout
        VBox root = new VBox(20, deleteUserLabel, deleteUserLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Attach BootstrapFX stylesheet
        Scene deleteUserScene = new Scene(root, 500, 500);
        deleteUserScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setTitle("Delete User");
        primaryStage.setScene(deleteUserScene);
        primaryStage.setScene(deleteUserScene);

    }


    private void showExitConfirmationScene(Stage primaryStage, Scene adminScene) {
        // Title Label with modern font and styling
        Label exitLabel = new Label("Are you sure you want to exit?");
        exitLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #f6fcff; -fx-padding: 10px;");

        // Yes Button
        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-background-color: #eaf230; -fx-text-fill: #21a5e7; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;");

        // No Button
        Button noButton = new Button("No");
        noButton.setStyle("-fx-background-color: #eaf230; -fx-text-fill: #21a5e7; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;");

        // Hover effects for buttons
        yesButton.setOnMouseEntered(e -> yesButton.setStyle("-fx-background-color: #f6fcff; -fx-text-fill: #21a5e7; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;"));
        yesButton.setOnMouseExited(e -> yesButton.setStyle("-fx-background-color: #eaf230; -fx-text-fill: #21a5e7; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;"));

        noButton.setOnMouseEntered(e -> noButton.setStyle("-fx-background-color: #f6fcff; -fx-text-fill: #21a5e7; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;"));
        noButton.setOnMouseExited(e -> noButton.setStyle("-fx-background-color: #eaf230; -fx-text-fill: #21a5e7; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;"));

        // Button actions
        yesButton.setOnAction(e -> primaryStage.close());
        noButton.setOnAction(e -> primaryStage.setScene(adminScene));

        // Layout with better spacing, alignment, and padding
        VBox exitLayout = new VBox(15, exitLabel, yesButton, noButton);
        exitLayout.setAlignment(Pos.CENTER);
        exitLayout.setPadding(new Insets(20));
        exitLayout.setStyle("-fx-background-color: #21a5e7; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, #21a5e7, 10, 0, 0, 4);");

        // Scene creation with a modern touch
        Scene exitScene = new Scene(exitLayout, 350, 300);
        primaryStage.setScene(exitScene);
    }


    private void showCitizenMenuScene(String fullName) {
        // Title label for Citizen Menu
        Label citizenLabel = new Label("Citizen Menu");
        citizenLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill:  #21a5e7;");

        // Buttons for Citizen Menu actions
        Button logoutButton = new Button("Logout");
        Button addDocumentButton = new Button("Add Document");
        Button deleteDocumentButton = new Button("Delete Document");
        Button viewDocumentButton = new Button("View Document");
        Button exitButton = new Button("Exit");

        logoutButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        addDocumentButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        deleteDocumentButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        viewDocumentButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        exitButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");


        // Layout for Citizen Menu buttons
        VBox citizenLayout = new VBox(15, citizenLabel, addDocumentButton, deleteDocumentButton, viewDocumentButton, exitButton, logoutButton);
        citizenLayout.setSpacing(15);
        citizenLayout.setPadding(new Insets(20));
        citizenLayout.setAlignment(Pos.CENTER);

        // Background Color for Citizen Menu Layout
        citizenLayout.setStyle("-fx-background-color: #f6fcff; -fx-border-radius: 10px;");

        // Root layout with padding
        VBox root = new VBox(30, citizenLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Scene creation
        Scene citizenScene = new Scene(root, 400, 400);
        primaryStage.setScene(citizenScene);
        primaryStage.setTitle("Citizen Menu");

        // Button Actions
        logoutButton.setOnAction(e ->{ primaryStage.setTitle("Login Portal");primaryStage.setScene(loginScene);});
        addDocumentButton.setOnAction(e-> showAddDocumentScene(primaryStage,citizenScene,fullName));

        deleteDocumentButton.setOnAction(e-> showDeleteDocumentScene(primaryStage,citizenScene,fullName));

        viewDocumentButton.setOnAction(e-> showViewDocumentScene(primaryStage,citizenScene,fullName));

        exitButton.setOnAction(e->showExitConfirmationScene(primaryStage,citizenScene));

    }

    private void showAddDocumentScene(Stage primaryStage, Scene citizenScene, String fullName){
        Label addDocumentLabel = new Label("Add Document");
        addDocumentLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #f6fcff;");

        FileChooser fileChooser = new FileChooser();

        Button selectDocumentButton = new Button("Select Document");
        selectDocumentButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        selectDocumentButton.setDefaultButton(true);

        Label filePathLabel = new Label("File path");
        filePathLabel.setStyle(" -fx-font-size: 14px;");
        String[] fullpath = new String[1];
        selectDocumentButton.setOnAction(e->{
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                filePathLabel.setText(file.getAbsolutePath());
                fullpath[0] = file.getAbsolutePath();

            } else {
                filePathLabel.setText("No file selected");
            }
        });
        Label nameLabel = new Label("Document Name:");
        nameLabel.setStyle(" -fx-font-size: 14px;");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter document name");
        nameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label documentTypeLabel = new Label("Document Type:");
        documentTypeLabel.setStyle(" -fx-font-size: 14px;");
        ComboBox<String> documentTypeComboBox = new ComboBox<>();
        documentTypeComboBox.getItems().addAll("doc", "pdf", "jpg", "jpeg");
        documentTypeComboBox.setPromptText("Select Document Type");
        documentTypeComboBox.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");



        Button addButton = new Button("Add");
        Button backButton = new Button("Back");
        addButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        addButton.setDefaultButton(true);
        backButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        backButton.setDefaultButton(true);

        // Group inputs for clean layout
        VBox nameBox = new VBox(5, nameLabel, nameField);
        VBox documentTypeBox = new VBox(5, documentTypeLabel, documentTypeComboBox);

        // Create  layout
        VBox addDocumentLayout = new VBox(15, addDocumentLabel, selectDocumentButton, filePathLabel, nameBox, documentTypeBox,addButton,backButton);
        addDocumentLayout.setAlignment(Pos.CENTER_LEFT);
        addDocumentLayout.setPadding(new Insets(20));
        addDocumentLayout.setStyle("-fx-background-color: #f6fcff;; -fx-border-radius: 10px; -fx-padding: 20;");

        // Root layout
        VBox root = new VBox(20, addDocumentLabel, addDocumentLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Attach BootstrapFX stylesheet
        Scene addDocumentScene = new Scene(root, 500, 500);
        addDocumentScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setTitle("Add Document");
        primaryStage.setScene(addDocumentScene);

        addButton.setOnAction(e -> {
            try {
                String name = capitalizeFirstLetter(nameField.getText().trim());
                String documentTypeInput = documentTypeComboBox.getValue();
                DocumentType documentType = DocumentType.valueOf(documentTypeInput.toLowerCase());
                File file = new File(fullpath[0]);
                String content = FileEncodeDecode.encodeFileToBase64(file);
                System.out.println(fullpath);

                if (name.isEmpty() || documentTypeInput == null) {
                    showAlert("Error", "Field is empty!");
                }

                String[] info = DocumentManager.searchDocument(name,fullName);

                if(!info[0].isEmpty()){
                    showAlert("Error","A document with this name already exists.");
                    return;
                }

                if(content != null){
                    Document doc = new Document(name,documentType,fullName,fullpath[0],content);
                    DocumentManager.addDocument(doc);
                }

                primaryStage.setTitle("Citizen Menu");
                primaryStage.setScene(citizenScene);
            } catch (Exception ex) {
                showAlert("Error", "Failed to add document: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> {primaryStage.setTitle("Citizen Menu");primaryStage.setScene(citizenScene);});

    }

    private void showViewDocumentScene(Stage primaryStage, Scene citizenScene, String fullName) {
        // Title Label
        Label viewDocumentLabel = new Label("View Document");
        viewDocumentLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #f6fcff;");

        // Input Fields
        Label nameLabel = new Label("Document Name:");
        nameLabel.setStyle(" -fx-font-size: 14px;");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter document name");
        nameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        // Buttons
        Button viewDocumentButton = new Button("View");
        viewDocumentButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        viewDocumentButton.setDefaultButton(true);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        backButton.setDefaultButton(false);

        backButton.setOnAction(e -> {
            primaryStage.setTitle("Citizen Menu");
            primaryStage.setScene(citizenScene);
        });

        // Button Actions
        viewDocumentButton.setOnAction(e -> {
            try {
                // Retrieve and format the document name
                String name = capitalizeFirstLetter(nameField.getText().trim());

                if (name.isEmpty()) {
                    showAlert("Error", "Field is empty!");
                    return;
                }

                // Search for the document using DocumentManager
                String[] info = DocumentManager.searchDocument(name, fullName);

                if (info == null || info.length < 2 || info[0].isEmpty()) {
                    showAlert("Error", "Document not found or is empty!");
                    return;
                }

                // Decode and open the file
                File file = new File(String.format("output.%s", info[1]));
                FileEncodeDecode.decodeFileFromBase64(info[0], file);

                if (!file.exists()) {
                    showAlert("Error", "File does not exist!");
                    return;
                }

                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);

                primaryStage.setTitle("Citizen Menu");
                primaryStage.setScene(citizenScene);

            } catch (Exception ex) {
                showAlert("Error", "Failed to view document: " + ex.getMessage());
            }
        });

        // Layout Setup
        VBox nameBox = new VBox(5, nameLabel, nameField);
        VBox viewDocumentLayout = new VBox(15, viewDocumentLabel, nameBox, viewDocumentButton, backButton);
        viewDocumentLayout.setAlignment(Pos.CENTER);
        viewDocumentLayout.setPadding(new Insets(20));
        viewDocumentLayout.setStyle("-fx-background-color: #f6fcff; -fx-border-radius: 10px; -fx-padding: 20;");

        VBox root = new VBox(20, viewDocumentLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Scene Setup
        Scene viewDocumentScene = new Scene(root, 500, 500);
        viewDocumentScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setTitle("View Document");
        primaryStage.setScene(viewDocumentScene);


    }

    private void showDeleteDocumentScene(Stage primaryStage,Scene citizenScene,String fullName){
        Label deleteDocumentLabel = new Label("Delete Document");
        deleteDocumentLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #f6fcff;");

        Label nameLabel = new Label("Document Name:");
        nameLabel.setStyle(" -fx-font-size: 14px;");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter document name");
        nameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Button deleteButton = new Button("Delete");
        Button backButton = new Button("Back");
        deleteButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        deleteButton.setDefaultButton(true);
        backButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        backButton.setDefaultButton(true);

        // Group inputs for clean layout
        VBox nameBox = new VBox(5, nameLabel, nameField);
        // Create  layout
        VBox deleteDocumentLayout = new VBox(15, deleteDocumentLabel, nameBox, deleteButton,backButton);
        deleteDocumentLayout.setAlignment(Pos.CENTER_LEFT);
        deleteDocumentLayout.setPadding(new Insets(20));
        deleteDocumentLayout.setStyle("-fx-background-color: #f6fcff;; -fx-border-radius: 10px; -fx-padding: 20;");

        // Root layout
        VBox root = new VBox(20, deleteDocumentLabel, deleteDocumentLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Attach BootstrapFX stylesheet
        Scene deleteDocumentScene = new Scene(root, 500, 500);
        deleteDocumentScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setTitle("Delete Document");
        primaryStage.setScene(deleteDocumentScene);



        deleteButton.setOnAction(e -> {
            try {
                String name = capitalizeFirstLetter(nameField.getText().trim());
                if (name.isEmpty()) {
                    showAlert("Error","Field is empty!");
                    return;
                }
                DocumentManager.deleteDocument(name,fullName);


                primaryStage.setTitle("Citizen Menu");
                primaryStage.setScene(citizenScene);
            } catch (Exception ex) {
                showAlert("Error", "Failed to add document: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> {primaryStage.setTitle("Citizen Menu");primaryStage.setScene(citizenScene);});



    }


    private void showRepresentantMenuScene() {
        // Title label for Citizen Menu
        Label representantLabel = new Label("Representant Menu");
        representantLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill:  #21a5e7;");

        // Buttons for Citizen Menu actions
        Button viewDocumentButton = new Button("View Document");
        Button exitButton = new Button("Exit");
        Button logoutButton = new Button("Logout");

        logoutButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        viewDocumentButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        exitButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");


        // Layout for Citizen Menu buttons
        VBox representantLayout = new VBox(15, representantLabel,viewDocumentButton, exitButton, logoutButton);
        representantLayout.setSpacing(15);
        representantLayout.setPadding(new Insets(20));
        representantLayout.setAlignment(Pos.CENTER);

        // Background Color for Citizen Menu Layout
        representantLayout.setStyle("-fx-background-color: #f6fcff; -fx-border-radius: 10px;");

        // Root layout with padding
        VBox root = new VBox(30, representantLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Scene creation
        Scene representantScene = new Scene(root, 400, 400);
        primaryStage.setScene(representantScene);
        primaryStage.setTitle("Representant Menu");

        // Button Actions
        logoutButton.setOnAction(e ->{ primaryStage.setTitle("Login Portal");primaryStage.setScene(loginScene);});

        viewDocumentButton.setOnAction(e->showViewDocumentRepresentantScene(primaryStage,representantScene));

        exitButton.setOnAction(e->showExitConfirmationScene(primaryStage,representantScene));
    }

    private  void showViewDocumentRepresentantScene(Stage primaryStage, Scene representantScene){
        Label viewDocumentLabel = new Label("View Document");
        viewDocumentLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #f6fcff;");

        Label ownerFirstNameLabel = new Label("Owner First Name:");
        ownerFirstNameLabel.setStyle(" -fx-font-size: 14px;");
        TextField ownerFirstNameField = new TextField();
        ownerFirstNameField.setPromptText("Enter first name of the owner");
        ownerFirstNameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label ownerLastNameLabel = new Label("Owner Last Name:");
        ownerLastNameLabel.setStyle(" -fx-font-size: 14px;");
        TextField ownerLastNameField = new TextField();
        ownerLastNameField.setPromptText("Enter last name of the owner");
        ownerLastNameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Label nameLabel = new Label("Document Name:");
        nameLabel.setStyle(" -fx-font-size: 14px;");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter first name of the owner");
        nameField.setStyle("-fx-border-color: #eaf230; -fx-border-radius: 5px;");

        Button viewDocumentButton = new Button("View");
        Button backButton = new Button("Back");
        viewDocumentButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        viewDocumentButton.setDefaultButton(true);
        backButton.setStyle("-fx-background-color:  #21a5e7; -fx-text-fill: #f6fcff; -fx-font-size: 16px; -fx-border-radius: 5px;");
        backButton.setDefaultButton(true);

        backButton.setOnAction(e -> {primaryStage.setTitle("Representant Menu");primaryStage.setScene(representantScene);});

        // Group inputs for clean layout
        VBox nameBox = new VBox(5, nameLabel, nameField);
        VBox firstNameBox = new VBox(5,ownerFirstNameLabel,ownerFirstNameField);
        VBox lastNameBox = new VBox(5,ownerLastNameLabel,ownerLastNameField);
        // Create  layout
        VBox viewDocumentLayout = new VBox(15, viewDocumentLabel, nameBox, firstNameBox, lastNameBox,viewDocumentButton,backButton);
        viewDocumentLayout.setAlignment(Pos.CENTER_LEFT);
        viewDocumentLayout.setPadding(new Insets(20));
        viewDocumentLayout.setStyle("-fx-background-color: #f6fcff;; -fx-border-radius: 10px; -fx-padding: 20;");

        // Root layout
        VBox root = new VBox(20, viewDocumentLabel, viewDocumentLayout);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #21a5e7;");

        // Attach BootstrapFX stylesheet
        Scene viewDocumentScene = new Scene(root, 500, 500);
        viewDocumentScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setTitle("View Document");
        primaryStage.setScene(viewDocumentScene);


        // Action for the View button
        viewDocumentButton.setOnAction(e -> {
            try {
                // Retrieve and format the document name
                String name = capitalizeFirstLetter(nameField.getText().trim());
                String firstName =ownerFirstNameField.getText().trim();
                String lastName = ownerLastNameField.getText().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || name.isEmpty()) {
                    showAlert("Error", "All fields must be completed!");
                    return;
                }

                String regex = ".*\\d.*";  // This checks if the string contains at least one digit

                // Create a pattern and matcher
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher1 = pattern.matcher(firstName);
                Matcher matcher2 = pattern.matcher(lastName);

                if (matcher1.matches() || matcher2.matches()) {
                    showAlert("Error", "Names should contain only letters!");
                    return;
                }

                firstName = capitalizeFirstLetter(firstName);
                lastName = capitalizeFirstLetter(lastName);
                String fullName = firstName + " " + lastName;

                // Search for the document using DocumentManager
                String[] info = DocumentManager.searchDocument(name, fullName);
                if(!info[0].isEmpty()){
                    // Check if the document was found and then decode it
                    File file = new File(String.format("output.%s", info[1]));
                    FileEncodeDecode.decodeFileFromBase64(info[0], file);
                    // Check if the file exists before proceeding
                    if (file.exists()) {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(file);
                        primaryStage.setTitle("Representant Menu");
                        primaryStage.setScene(representantScene);

                    } else {
                        showAlert("Error", "File does not exists!");
                        return;
                    }

                }else{
                    showAlert("Error","File is empty!");
                    return;
                }

            } catch (Exception ex) {
                // Show an error message if something goes wrong
                showAlert("Error", "Failed to view document: " + ex.getMessage());
            }
        });


    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    private void showAlert(String title, String message) {
        // Create the alert of type ERROR
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Set the title and content of the alert
        alert.setTitle(title);
        alert.setHeaderText(null);  // Remove the default header text
        alert.setContentText(message);

        // Get the DialogPane of the alert to apply custom styles
        DialogPane dialogPane = alert.getDialogPane();

        // Apply styles directly to the dialog pane (alert background, text, etc.)
        dialogPane.setStyle("-fx-background-color: #21a5e7; -fx-border-radius: 15px; -fx-padding: 20px;");

        // Style the content area (the message text)
        dialogPane.lookup(".content").setStyle("-fx-font-size: 16px; -fx-text-fill: #f6fcff;");

        // Apply style to the buttons (Yes/No or OK button)
        for (Button button : dialogPane.getButtonTypes().stream()
                .map(buttonType -> (Button) dialogPane.lookupButton(buttonType))
                .toList()) {
            button.setStyle("-fx-background-color: #eaf230; -fx-text-fill: #21a5e7; -fx-font-size: 14px; " +
                    "-fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;");

            // Add hover effect for buttons
            button.setOnMouseEntered(e -> button.setStyle(
                    "-fx-background-color: #f6fcff; -fx-text-fill: #21a5e7; -fx-font-size: 14px; " +
                            "-fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;"));

            button.setOnMouseExited(e -> button.setStyle(
                    "-fx-background-color: #eaf230; -fx-text-fill: #21a5e7; -fx-font-size: 14px; " +
                            "-fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 20px;"));
        }

        // Show the alert and wait for the user to respond
        alert.showAndWait();
    }
}


