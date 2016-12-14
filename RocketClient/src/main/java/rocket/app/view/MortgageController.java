
package rocket.app.view;

import java.text.NumberFormat;

import eNums.eAction;
import exceptions.RateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private TextField txtIncome;
	@FXML
	private TextField txtExpenses;
	@FXML
	private TextField txtCreditScore;
	@FXML
	private TextField txtHouseCost;
	@FXML
	private TextField txtDownPayment;

	@FXML
	private ComboBox<String> cmbTerm;

	@FXML
	private Button btnTerms;

	@FXML
	private Label lblIncome;
	@FXML
	private Label lblExpenses;
	@FXML
	private Label lblCreditScore;
	@FXML
	private Label lblHouseCost;
	@FXML
	private Label lblDownPayment;
	@FXML
	private Label lblCmbTerms;
	@FXML
	private Label lblFinanicalPayment;
	@FXML
	private Label lblFinancialRate;
	@FXML
	private Label lblFinancialPayment1;
	@FXML
	private Label lblFinancialRate1;
	@FXML
	private Label lblErrors;

	@FXML
	private void initialize() {
		cmbTerm.getItems().add("15 Years");
		cmbTerm.getItems().add("30 Years");
	}

	private static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

	@FXML
	public void btnCalculatePayment(ActionEvent event) {
		Object message = null;
		// RocketClient.RocketMainController

		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();

		lq.setdAmount(Double.parseDouble(txtHouseCost.getText()) - Double.parseDouble(txtDownPayment.getText()));
		lq.setiDownPayment(Integer.parseInt(txtDownPayment.getText()));
		lq.setiIncome(Double.parseDouble(txtIncome.getText()));
		lq.setiExpenses(Double.parseDouble(txtExpenses.getText()));
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));
		try {
			lq.setdRate(RateBLL.getRate(lq.getiCreditScore()));
		} catch (RateException e) {
			lq.setdRate(-1);
			lblErrors.setText("Invalid Credit-Score");
		}
		if (cmbTerm.getValue() == "30 Years") {
			lq.setiTerm(30);
		} else {
			lq.setiTerm(15);
		}

		a.setLoanRequest(lq);

		// send lq as a message to RocketHub
		mainApp.messageSend(lq);
	}

	public void HandleLoanRequestDetails(LoanRequest lRequest) {

		double fincRate = lRequest.getdRate();
		double pmts = lRequest.getdPayment();
		double PIT1 = lRequest.getiIncome() * .28;
		double PIT2 = ((lRequest.getiIncome() * .36) - lRequest.getiExpenses());
		double PIT;

		if (PIT1 > PIT2) {
			PIT = PIT2;
		} else {
			PIT = PIT1;
		}
		if (fincRate == -1) {
			lblFinancialPayment1.setText("N/A");
			lblFinancialRate1.setText("N/A");
		} else if (pmts > PIT) {
			lblErrors.setText("The Housing Cost is too high");
			lblFinancialRate1.setText(null);
			lblFinancialPayment1.setText(null);
		} else {
			lblFinancialRate1.setText("Financial Rate: " + fincRate + "%");
			lblFinancialPayment1.setText("Monthly Payment: " + currencyFormat.format(pmts));
			lblErrors.setText(null);
		}
	}

}