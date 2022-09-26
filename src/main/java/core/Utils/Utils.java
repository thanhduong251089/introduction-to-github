package core.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Random;

public class Utils {
    private static Logger log = LogManager.getLogger(Utils.class);

    public static String buildErrorMessage(String actual, String expected, String prefix) {
        return String.format("%s - Expected value is '%s' but was '%s'", prefix, expected, actual);
    }

    public static String buildErrorMessage(Object actual, Object expected, String prefix) {
        return String.format("%s - Expected value is '%s' but was '%s'", prefix, expected, actual);
    }

    public static String buildErrorMessage(String actual, String expected) {
        return String.format("Expected value is '%s' but was '%s'", expected, actual);
    }

    public static String buildErrorMessageExists(String prefix) {
        return String.format("%s does not exist", prefix);
    }

    public static String buildErrorMessageNotExist(String prefix) {
        return String.format("%s exists", prefix);
    }

    public static void delay(int timeInSecond) {
        try {
            Thread.sleep(timeInSecond);
        } catch (InterruptedException ignored) {
        }
    }







}
