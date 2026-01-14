package org.giant.dto;

import java.math.BigDecimal;

public record CreditRequest (String number, BigDecimal amount) {
}
