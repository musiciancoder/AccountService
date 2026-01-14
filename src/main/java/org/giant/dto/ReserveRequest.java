package org.giant.dto;

import java.math.BigDecimal;

public record ReserveRequest(String number, BigDecimal amount) { }