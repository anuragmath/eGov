package builders.propertyTax;

import entities.propertyTax.RevisionPetitionDetails;

public class RevisionPetitionDetailsBuilder {

    RevisionPetitionDetails revisionPetitionDetails = new RevisionPetitionDetails();

    public RevisionPetitionDetailsBuilder(){
    }

    public RevisionPetitionDetailsBuilder withRevisionPetitionDetail(String revisionPetitionDetail) {
        revisionPetitionDetails.setRevisionPetitionDetail(revisionPetitionDetail);
        return this;
    }
    public RevisionPetitionDetails build(){
        return revisionPetitionDetails;
    }
}
