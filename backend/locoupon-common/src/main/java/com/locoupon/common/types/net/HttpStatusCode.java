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

package com.locoupon.common.types.net;

@SuppressWarnings("MagicNumber")
public final class HttpStatusCode {
    public static final HttpStatusCode OK = new HttpStatusCode(200);
    public static final HttpStatusCode ACCEPTED = new HttpStatusCode(202);
    public static final HttpStatusCode NOT_FOUND = new HttpStatusCode(404);
    public static final HttpStatusCode METHOD_NOT_ALLOWED = new HttpStatusCode(405);
    public static final HttpStatusCode NOT_IMPLEMENTED = new HttpStatusCode(501);
    public static final HttpStatusCode SERVICE_UNAVAILABLE = new HttpStatusCode(503);
    public static final HttpStatusCode BAD_REQUEST = new HttpStatusCode(400);

    private final int value;

    private HttpStatusCode(int value) {
        this.value = value;
    }

    public static HttpStatusCode httpStatusCode(int value) {
        return new HttpStatusCode(value);
    }

    public boolean isSuccessful() {
        return inRange(200, 299);
    }

    public boolean isRedirection() {
        return inRange(300, 399);
    }

    public boolean isClientError() {
        return inRange(400, 499);
    }

    public boolean isServerError() {
        return inRange(500, 599);
    }

    public boolean isError() {
        return isClientError() || isServerError();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HttpStatusCode) {
            HttpStatusCode that = (HttpStatusCode) o;
            return value == that.value;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public int toInt() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    private boolean inRange(int min, int max) {
        return value >= min && value <= max;
    }
}
