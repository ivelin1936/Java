package com.company.animals.models;

import com.company.animals.Animal;
import com.company.exceptions.InvalidIntelligenceCoefficientException;

public class Cat extends Animal {

    private int intelligenceCoefficient;

    public Cat(String name, int age, int intelligenceCoefficient) {
        super(name, age);
        this.setIntelligenceCoefficient(intelligenceCoefficient);
    }

    public int getIntelligenceCoefficient() {
        return intelligenceCoefficient;
    }

    private void setIntelligenceCoefficient(int intelligenceCoefficient) {
        if (intelligenceCoefficient < 0) {
            throw new InvalidIntelligenceCoefficientException();
        }
        this.intelligenceCoefficient = intelligenceCoefficient;
    }
}
