/* Copyright (C) 2012 Justin Wilcox
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.nitori.Takatsuki.Util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class Log {
    private static final Logger log = Logger.getLogger("nl.nitori.Takatsuki");
    private static StreamHandler hand = null;

    public static void debug(String msg) {
        log.fine(msg);
        hand.flush();
    }

    public static void info(String msg) {
        log.info(msg);
        hand.flush();
    }

    public static void error(String msg) {
        log.severe(msg);
        hand.flush();
    }

    public static void warning(String msg) {
        log.warning(msg);
        hand.flush();
    }

    public static void exception(Throwable e) {
        StringWriter w = new StringWriter();
        PrintWriter pw = new PrintWriter(w);
        e.printStackTrace(pw);
        Log.error("Unhandled exception " + e.getMessage() + "\n" + w.toString());

    }

    public static void registerLogger() {
        registerLogger(Level.ALL);
    }

    public static void registerLogger(Level level) {
        Handler[] hands = log.getHandlers();
        for (Handler h : hands)
            log.removeHandler(h);
        log.setUseParentHandlers(false);

        hand = new StreamHandler(System.out, new LocalFormatter());
        log.addHandler(hand);

        log.setLevel(level);
        hand.setLevel(level);

        log.info("Initialized logging system");
        hand.flush();

        Thread.setDefaultUncaughtExceptionHandler(new SendExceptionsToLogger());
    }

    private static class SendExceptionsToLogger implements
            Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Log.exception(e);
        }
    }

    private static class LocalFormatter extends Formatter {
        @Override
        public String format(LogRecord rec) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(rec.getMillis());
            return c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":"
                    + c.get(Calendar.SECOND) + " " + rec.getLevel() + ": "
                    + "----------------------------------\n" + rec.getMessage()
                    + "\n";
        }
    }
}
