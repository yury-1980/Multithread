package ru.clevertec.util.impl;

import ru.clevertec.util.RandomUtil;

import java.util.Random;

public final class RandomUtilImpl implements RandomUtil {

    private static final Random RANDOM = new Random();
    private static final int ONE_HUNDRED = 100;
    private static final int FOUR_HUNDRED = 401;
    private static final int NINE_HUNDRED = 901;

    @Override
    public int getRandomFromOneHundredToFiveHundred() {
        return RANDOM.nextInt(FOUR_HUNDRED) + ONE_HUNDRED;
    }

    @Override
    public int getRandomFromOneHundredToThousand() {
        return RANDOM.nextInt(NINE_HUNDRED) + ONE_HUNDRED;
    }
}
