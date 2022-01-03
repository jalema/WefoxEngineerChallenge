package com.technicaltest.payments.domain.values;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
public class AccountId {
    @NonNull Integer id;
}
