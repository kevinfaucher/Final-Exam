package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();

	public static double getRate(int GivenCreditScore) throws RateException {

		ArrayList<RateDomainModel> rdm = _RateDAL.getAllRates();
		RateDomainModel rate_domain_model = null;
		double finRate = -1.0;

		for (RateDomainModel rates : rdm) {

			if (rates.getiMinCreditScore() == GivenCreditScore) {
				finRate = rates.getdInterestRate();
				rate_domain_model = rates;
			}
		}

		if (rate_domain_model == null) {
			throw new RateException(rate_domain_model);
		}

		else
			return finRate;

	}

	public static double getPayment(double r, double n, double p, double f, boolean t) {
		return -FinanceLib.pmt(r, n, p, f, t);
	}
}