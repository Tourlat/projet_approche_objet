// package com.projetjava.Controller;

// import javafx.stage.Stage;

// import com.projetjava.Model.Model;
// import com.projetjava.View.View;
// import com.projetjava.View.ViewListener;

// public class Controller implements ViewListener {
// private final Model model;
// private final View view;
// private final BagOfCommands bagOfCommands;

// public Controller(Stage stage) {
// model = new Model();
// view = new View(stage);
// bagOfCommands = new BagOfCommands(model);
// for (Event event : Event.values()) {
// view.addButton(event.getText(), event.getId(),
// geteventHandlerByEvent(event));
// }
// model.setListener(view);
// }

// @Override
// public void addBuildingButtonClicked() {
// System.out.println("Le bouton 'Add building' a été cliqué.");
// bagOfCommands.add(new addBuildingCommand());
// }

// @Override
// public void addEmployeesToBuildingButtonClicked() {
// System.out.println("Le bouton 'Add Employees' a été cliqué.");
// bagOfCommands.add(new addEmployeesToBuildingCommand());
// }

// /**
// */
// private EventHandler<ActionEvent> geteventHandlerByEvent(Event event) {
// return switch (event) {
// case ADD_BUILDING ->
// _ -> addBuildingButtonClicked();
// case ADD_EMPLOYEES_TO_BUILDING ->
// _ -> addEmployeesToBuildingButtonClicked();
// };
// }

// }