package builders.propertyTax;

import entities.propertyTax.HearingDetails;

public class HearingDetailsBuilder {

    HearingDetails hearingDetails = new HearingDetails();

    public HearingDetailsBuilder withHearingDate(String hearingDate) {
        hearingDetails.setHearingDate(hearingDate);
        return this;
    }

    public HearingDetailsBuilder withHearingTime(String hearingTime) {
        hearingDetails.setHearingTime(hearingTime);
        return this;
    }

    public HearingDetailsBuilder withvenue(String venue) {
        hearingDetails.setVenue(venue);
        return this;

    }

    public HearingDetails build() {
         return hearingDetails;
    }
}
