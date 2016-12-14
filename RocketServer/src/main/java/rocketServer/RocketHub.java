package rocketServer;

import java.io.IOException;

import exceptions.RateException;
import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;

public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();

	public RocketHub(int port) throws IOException {
		super(port);
	}

	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");

		if (message instanceof LoanRequest) {
			resetOutput();

			LoanRequest lq = (LoanRequest) message;

			try {
				int crScore = lq.getiCreditScore();
				lq.setdRate(RateBLL.getRate(crScore));
			} catch (RateException exception) {
				sendToAll(exception);
			}
			final double CONVERSION = (100 * 12);
			final double FACTOR12 = 12;

			double rate = lq.getdRate() / CONVERSION;
			double numPer = (double) lq.getiTerm() * FACTOR12;
			double principleAmt = lq.getdAmount();
			double futVal = 0;
			boolean tr = false;

			double paymentDue = RateBLL.getPayment(rate, numPer, principleAmt, futVal, tr);
			lq.setdPayment(paymentDue);

			sendToAll(lq);
		}
	}
}