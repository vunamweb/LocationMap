package com.vunam.mylibrary.utils;

/**
 * Created by Administrator on 6/1/2018.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class StringUtils {

    public static final String LF = "\n";
    public static final String CR = "\r";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;

    public static final String SPACE = " ";
    public static final String EMPTY = "";

    public static final String OPEN_BRACE = "{";
    public static final String CLOSE_BRACE = "}";

    /**
     *
     * @param cs
     * @return true if the CharSequence is empty or null or whitespace
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     *
     * @param
     * @return true if the CharSequence is not empty and not null and not whitespace
     */
    public static String getUrlimgFromRssDes24h(String description)
    {
        String cleanUrl=null;
        try {
            cleanUrl =description.substring(description.indexOf("src=") + 5, description.indexOf("jpg")+3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cleanUrl;
    }
    public static String getStringDesFromTagDes24h(String description)
    {
        String cleanUrl=null;
        try {
            cleanUrl =description.substring(description.indexOf("<br />") + 6,description.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cleanUrl;
    }
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return cs != null && cs.length() > 0;
    }

    public static String encodeBitmapToStringBase64(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static Bitmap decodeStringToBitmapBase64(String strDecode)
    {
        byte[] imageBytes;
        imageBytes = Base64.decode(strDecode, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }


    public static boolean endsWith(final CharSequence str, final CharSequence suffix) {
        return endsWith(str, suffix, false);
    }

    private static boolean endsWith(final CharSequence str, final CharSequence suffix, final boolean ignoreCase) {
        if (str == null || suffix == null) {
            return str == null && suffix == null;
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        final int strOffset = str.length() - suffix.length();
        return CharSequenceUtils.regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length());
    }

    /**
     4560     * <p>Replaces a String with another String inside a larger String,
     4561     * for the first {@code max} values of the search String.</p>
     4562     *
     4563     * <p>A {@code null} reference passed to this method is a no-op.</p>
     4564     *
     4565     * <pre>
     4566     * StringUtils.replace(null, *, *, *)         = null
     4567     * StringUtils.replace("", *, *, *)           = ""
     4568     * StringUtils.replace("any", null, *, *)     = "any"
     4569     * StringUtils.replace("any", *, null, *)     = "any"
     4570     * StringUtils.replace("any", "", *, *)       = "any"
     4571     * StringUtils.replace("any", *, *, 0)        = "any"
     4572     * StringUtils.replace("abaa", "a", null, -1) = "abaa"
     4573     * StringUtils.replace("abaa", "a", "", -1)   = "b"
     4574     * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
     4575     * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
     4576     * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
     4577     * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
     4578     * </pre>
     4579     *
     4580     * @param text  text to search and replace in, may be null
     4581     * @param searchString  the String to search for, may be null
     4582     * @param replacement  the String to replace it with, may be null
     4583     * @param max  maximum number of values to replace, or {@code -1} if no maximum
     4584     * @return the text with any replacements processed,
     4585     *  {@code null} if null String input
     4586     */
    public static String replace(final String text, final String searchString, final String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == INDEX_NOT_FOUND) {
            return text;
        }
        final int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase < 0 ? 0 : increase;
        increase *= max < 0 ? 16 : max > 64 ? 64 : max;
        final StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != INDEX_NOT_FOUND) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }


    /**
     4533     * <p>Replaces all occurrences of a String within another String.</p>
     4534     *
     4535     * <p>A {@code null} reference passed to this method is a no-op.</p>
     4536     *
     4537     * <pre>
     4538     * StringUtils.replace(null, *, *)        = null
     4539     * StringUtils.replace("", *, *)          = ""
     4540     * StringUtils.replace("any", null, *)    = "any"
     4541     * StringUtils.replace("any", *, null)    = "any"
     4542     * StringUtils.replace("any", "", *)      = "any"
     4543     * StringUtils.replace("aba", "a", null)  = "aba"
     4544     * StringUtils.replace("aba", "a", "")    = "b"
     4545     * StringUtils.replace("aba", "a", "z")   = "zbz"
     4546     * </pre>
     4547     *
     4548     * @see #replace(String text, String searchString, String replacement, int max)
     4549     * @param text  text to search and replace in, may be null
     4550     * @param searchString  the String to search for, may be null
     4551     * @param replacement  the String to replace it with, may be null
     4552     * @return the text with any replacements processed,
     4553     *  {@code null} if null String input
     4554     */
    public static String replace(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String trimToNull(final String str) {
        final String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(final String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    public static String upperFirstLetter(String str){
        String s = str.substring(0, 1).toUpperCase() + str.substring(1);
        return s;
    }

    public static String replaceCurlyBraces(String seed, HashMap<String, String> replacements) {
        Iterator it = replacements.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            seed = seed.replace(OPEN_BRACE + pair.getKey() + CLOSE_BRACE, (CharSequence) pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return seed;
    }
}
