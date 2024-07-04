package org.example.queingmodel;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private boolean valid;

    @FXML
    private Label arrivalRateFeedback;

    @FXML
    private TextField arrivalRateInput;

    @FXML
    private Label nFeedback;

    @FXML
    private TextField nInput;

    @FXML
    private TableColumn<Model, String> resultColumn;

    @FXML
    private Label serversFeedback;

    @FXML
    private TextField serversInput;

    @FXML
    private Label serviceRateFeedback;

    @FXML
    private TextField serviceRateInput;

    @FXML
    private TableView<Model> table;

    @FXML
    private TableColumn<Model, String> valueColumn;

    @FXML
    void clear(ActionEvent event) {
        // Clear input fields and feedback
        serversInput.clear();
        arrivalRateInput.clear();
        serviceRateInput.clear();
        nInput.clear();
        serversFeedback.setText("");
        arrivalRateFeedback.setText("");
        serviceRateFeedback.setText("");
        nFeedback.setText("");
        valid = false; // Reset validation state
    }

    @FXML
    void compute(ActionEvent event) {
        if (valid) {
            table.getItems().clear();

            int s = Integer.parseInt(serversInput.getText());
            double lambda = Double.parseDouble(arrivalRateInput.getText());
            double mu = Double.parseDouble(serviceRateInput.getText());
            int n = isEmpty(nInput.getText()) ? 0 : Integer.parseInt(nInput.getText());

            MultichannelQueuingModel qModel = new MultichannelQueuingModel(s, lambda, mu);

            Model rhoModel = new Model("Traffic Intensity (ρ):", String.valueOf(qModel.getRho()));
            Model p0Model = new Model("Probability of 0 customers in the system (Po):",
                    String.valueOf(qModel.getP0()));
            Model pnModel = new Model("Probability of n customers in the system (Pn):",
                    String.valueOf(qModel.getPn(n)));
            Model lqModel = new Model("Average number of customers in the queue (Lq):",
                    String.valueOf(qModel.getLq()));

            var wq = BigDecimal.valueOf(qModel.getWq()).toPlainString();
            Model wqModel = new Model("Average waiting time in the queue (Wq):",
                    String.valueOf(wq));

            Model wsModel = new Model("Average time spent in the system (Ws):",
                    String.valueOf(qModel.getWs()));
            Model lsModel = new Model("Average number of customers in the system (Ls):",
                    String.valueOf(qModel.getLs()));

            table.getItems().add(rhoModel);
            table.getItems().add(p0Model);
            if (n != 0)
                table.getItems().add(pnModel);
            table.getItems().add(lqModel);
            table.getItems().add(wqModel);
            table.getItems().add(wsModel);
            table.getItems().add(lsModel);
        }
        else {
            serversFeedback.setText("Enter a valid number");
            arrivalRateFeedback.setText("Enter a valid number");
            serviceRateFeedback.setText("Enter a valid number");
        }
    }

    @FXML
    void validateInput(KeyEvent event) {
        valid = true; // Assume valid until proven otherwise

        // Numbers of server validation
        if (isNumber(serversInput.getText())) {
            serversFeedback.setText("");
        } else {
            if (isEmpty(serversInput.getText())) {
                serversFeedback.setText("");
            } else {
                serversFeedback.setText("Enter a valid number");
            }
            valid = false;
        }

        // Arrival rate validation
        if (isNumber(arrivalRateInput.getText())) {
            arrivalRateFeedback.setText("");
        } else {
            if (isEmpty(arrivalRateInput.getText())) {
                arrivalRateFeedback.setText("");
            } else {
                arrivalRateFeedback.setText("Enter a valid number");
            }
            valid = false;
        }

        // Service rate validation
        if (isNumber(serviceRateInput.getText())) {
            serviceRateFeedback.setText("");
        } else {
            if (isEmpty(serviceRateInput.getText())) {
                serviceRateFeedback.setText("");
            } else {
                serviceRateFeedback.setText("Enter a valid number");
            }
            valid = false;
        }

        // Number of Customers Validation (n)
        if (isNumber(nInput.getText())) {
            nFeedback.setText("");
        } else {
            if (isEmpty(nInput.getText())) {
                nFeedback.setText("");
            } else {
                nFeedback.setText("Enter a valid number");
                valid = false;
            }
        }
    }

    private boolean isNumber(String input) {
        // Regular expression to match a number
        return input.matches("^-?\\d*\\.?\\d+$");
    }

    private boolean isEmpty(String input) {
        return input.isEmpty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("resultText"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("valueText"));
    }
}
