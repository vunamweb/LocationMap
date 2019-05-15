package com.vunam.mylibrary.utils;

/**
 * Created by Administrator on 6/1/2018.
 */
public class CharSequenceUtils {
    /**
     175     * Green implementation of regionMatches.
     176     *
     177     * @param cs the {@code CharSequence} to be processed
     178     * @param ignoreCase whether or not to be case insensitive
     179     * @param thisStart the index to start on the {@code cs} CharSequence
     180     * @param substring the {@code CharSequence} to be looked for
     181     * @param start the index to start on the {@code substring} CharSequence
     182     * @param length character length of the region
     183     * @return whether the region matched
     184     */
    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                 final CharSequence substring, final int start, final int length)    {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                    && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }
}
