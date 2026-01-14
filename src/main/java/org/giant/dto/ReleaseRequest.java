package org.giant.dto;

import java.math.BigDecimal;

public record ReleaseRequest(String number, BigDecimal amount)  {
}
