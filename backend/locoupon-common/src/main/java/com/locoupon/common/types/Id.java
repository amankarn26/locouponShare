/*********************************************************************************************
*
* Copyright © 2020  Drut Technologies, Inc. All Rights Reserved. Proprietary and Confidential
*
* This confidential and proprietary software may be used only as expressly authorized by a
* licensing and specific agreement from DRUT.  In the event that DRUT authorizes any copying,
* distribution or publication of this software, then this notice must be reproduced in full
* on all such authorized copies.
*
**********************************************************************************************
*/

package com.locoupon.common.types;

import javax.annotation.Nonnull;

import static com.locoupon.common.util.Contracts.requiresNonNull;

import java.io.Serializable;

public final class Id implements Comparable<Id>, Serializable {
	private static final long serialVersionUID = -4051352713257896901L;

	private final String value;

	private Id(long value) {
		this.value = Long.toString(value);
	}

	private Id(String value) {
		requiresNonNull(value, "value");
		this.value = value;
	}

	public static Id id(long value) {
		return new Id(value);
	}

	public static Id id(String value) {
		return new Id(value);
	}

	/* used by JAX-RS */
	public static Id fromString(String value) {
		return new Id(value);
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Id id = (Id) o;
		return value.equals(id.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public int compareTo(@Nonnull Id anotherId) {
		return value.compareTo(anotherId.getValue());
	}
}
