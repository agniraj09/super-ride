package com.booking.superride.validation;

import com.booking.superride.constants.AppConstants;
import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.exception.DuplicateDataException;
import com.booking.superride.repository.TaxiRepository;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TaxiDetailsValidator {

    private final TaxiRepository taxiRepository;

    TaxiDetailsValidator(TaxiRepository taxiRepository) {
        this.taxiRepository = taxiRepository;
    }

    public void validateTaxiDetails(List<AddTaxiRequest> addTaxiRequest) {
        var isDuplicateTaxi = taxiRepository.findByTaxiNumber(
                addTaxiRequest.stream().map(AddTaxiRequest::taxiNumber).toList());

        if (!CollectionUtils.isEmpty(isDuplicateTaxi)) {
            throw new DuplicateDataException(AppConstants.DUPLICATE_TAXI_ERROR + isDuplicateTaxi);
        }
    }
}
