
package exceptions;

import rocketDomain.RateDomainModel;

public class RateException extends Exception {

	private RateDomainModel rateDomainModel;

	public RateException(RateDomainModel rdm) {
		this.rateDomainModel = rdm;
	}

	public RateDomainModel getRdm() {
		return rateDomainModel;
	}

}