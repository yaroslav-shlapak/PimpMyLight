/*
 * Copyright 2013 two forty four a.m. LLC <http://www.twofortyfouram.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.example.yshlapak.lightremote.entities;

import android.content.Context;

/**
 * Class of constants used by this Locale plug-in.
 */
public final class Constants
{
    /**
     * Log tag for logcat messages.
     */
    public static final String LOG_TAG = "UDPSender"; //$NON-NLS-1$

    /**
     * Determines the "versionCode" in the {@code AndroidManifest}.
     *
     * @param context to read the versionCode.
     * @return versionCode of the app.
     */
    public static int getVersionCode(final Context context)
    {

        try
        {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        catch (final UnsupportedOperationException e)
        {
            /*
             * This exception is thrown by test contexts
             */

            return 1;
        }
        catch (final Exception e)
        {
            throw new RuntimeException(e);
        }
    }

	public final static String BUTTON_ID = "com.house.smart.remote.BUTTON.STRIMG";
    public final static String DEFAULT_IP = "192.168.0.101";
    public final static String DEFAULT_PORT = "12345";
    public static final String DEFAULT_BUTTON_NAME = "Press and hold to modify";
    public static final String DEFAULT_BUTTON_HEX_VALUE = "";
    public static final int DEFAULT_BUTTON_HEX_OPTION = 0;
    public static final String DEFAULT_PROTOCOL_TYPE = "UDP";
    public static final String PROTOCOL_TCP = "TCP";
    public static final String PROTOCOL_UDP = "UDP";

    /**
     * Private constructor prevents instantiation.
     *
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private Constants()
    {
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }
    
    public static final String WIFI_DISCONNECTED_MESSAGE = "WI-FI connection is not established";
    public static final String INVALID_IP_ERROR_MESSAGE = "Error: Invalid IP Address";
    public static final String INVALID_PORT_ERROR_MESSAGE = "Error: Invalid Port Number";
    public static final String SENDING_CONTENT_ERROR_MESSAGE = "Error: Text/Hex required to send";
}