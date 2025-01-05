package dev.imb11.shields;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Util {
    public static <T> T removeFirstOrDefault(ArrayList<T> arrayList, T defaultValue) {
        try {
            return arrayList.removeFirst();
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
    }
}
