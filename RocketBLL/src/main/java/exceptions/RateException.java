
package exceptions;

import rocketDomain.RateDomainModel;

public class RateException extends Exception {

	private RateDomainModel rdm;

	public RateException(RateDomainModel rdm) {
		this.rdm = rdm;
	}

	public RateDomainModel getRdm() {
		return rdm;
	}

}