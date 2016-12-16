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
			final double cnvrt = (100 * 12);
			final double fac12 = 12;

			double rate = lq.getdRate() / cnvrt;
			double numPer = (double) lq.getiTerm() * fac12;
			double principleAmountt = lq.getdAmount();
			double futureValue = 0;
			boolean tr = false;

			double paymentDue = RateBLL.getPayment(rate, numPer, principleAmountt, futureValue, tr);
			lq.setdPayment(paymentDue);

			sendToAll(lq);
		}
	}
}