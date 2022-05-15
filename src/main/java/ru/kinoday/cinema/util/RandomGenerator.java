package ru.kinoday.cinema.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomGenerator {

    public String getRandomAlphabeticString() {
        return getRandomAlphabeticString(10);
    }

    public String getRandomAlphabeticString(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
