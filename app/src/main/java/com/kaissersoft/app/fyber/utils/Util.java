package com.kaissersoft.app.fyber.utils;

import android.content.Context;
import android.os.Build;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by eefret on 11/11/15.
 */
public class Util {

    public static final String BASE_URL = "http://api.fyber.com";
    public static final String HASHKEY_HEADER = "X-Sponsorpay-Response-Signature";

    public static String generateHashKey(TreeMap<String, String> params, String apiKey) {
        String sha1 = null;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.append(apiKey);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(sb.toString().getBytes("UTF-8"));
            sha1 = byteToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static String getAdvertisingIdClient(Context c) {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(c).getId();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getLimitAdTrackingEnabled(Context c) {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(c).isLimitAdTrackingEnabled();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getAndroidOSVersion() {
        return Build.VERSION.RELEASE;
    }


    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static class RequestParams {
        public static final String FORMAT = "format";
        public static final String APP_ID = "appid";
        public static final String UID = "uid";
        public static final String LOCALE = "locale";
        public static final String OS_VERSION = "os_version";
        public static final String TIMESTAMP = "timestamp";
        public static final String HASH_KEY = "hashkey";
        public static final String GOOGLE_AD_ID = "google_ad_id";
        public static final String GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED = "google_ad_id_limited_tracking_enabled";
        public static final String IP = "ip";
        public static final String PUB0 = "pub0";
        public static final String PAGE = "page";
        public static final String OFFER_TYPES = "offer_types";
        public static final String PS_TIME = "ps_time";
        public static final String DEVICE = "device";

        @Deprecated
        public static final String DEVICE_ID = "device_id";

        @Deprecated
        public static final String MAC_ADDRESS = "mac_address";

        @Deprecated
        public static final String MD5_MAC = "md5_mac";

        @Deprecated
        public static final String SHA1_MAC = "sha1_mac";

        @Deprecated
        public static final String ANDROID_ID = "android_id";
    }
}