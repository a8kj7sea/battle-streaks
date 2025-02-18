package me.a8kj.battlestreaks.util;

import java.util.ArrayList;
import java.util.List;

public class AndBooleanBuilder {

    private List<Integer> numbers;

    public AndBooleanBuilder() {
        numbers = new ArrayList<>();
    }

    public AndBooleanBuilder append(int number) {
        numbers.add(number != 0 ? 1 : 0);
        return this;
    }

    public AndBooleanBuilder append(boolean condition) {
        numbers.add(condition ? 1 : 0);
        return this;
    }

    public boolean toBoolean() {
        for (int number : numbers) {
            if (number == 0) {
                return false;
            }
        }
        return true;
    }
}
