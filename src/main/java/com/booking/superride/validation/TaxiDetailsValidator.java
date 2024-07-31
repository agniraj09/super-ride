package com.booking.superride.validation;

import com.booking.superride.constants.AppConstants;
import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.entity.projections.TaxiNumberDetails;
import com.booking.superride.exception.DuplicateDataException;
import com.booking.superride.repository.TaxiRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
@Transactional(readOnly = true)
public class TaxiDetailsValidator {

    Logger log = LoggerFactory.getLogger(TaxiDetailsValidator.class);

    private final TaxiRepository taxiRepository;

    TaxiDetailsValidator(TaxiRepository taxiRepository) {
        this.taxiRepository = taxiRepository;
    }

    public void validateTaxiDetails(List<AddTaxiRequest> addTaxiRequest) {
        var duplicateTaxis = taxiRepository.findByTaxiNumberIn(
                addTaxiRequest.stream().map(AddTaxiRequest::taxiNumber).toList());

        if (!CollectionUtils.isEmpty(duplicateTaxis)) {
            throw new DuplicateDataException(AppConstants.DUPLICATE_TAXI_ERROR
                    + duplicateTaxis.stream()
                            .map(TaxiNumberDetails::getTaxiNumber)
                            .toList());
        }
    }
}
