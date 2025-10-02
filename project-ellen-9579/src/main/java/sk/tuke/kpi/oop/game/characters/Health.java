package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {

    private int initHealth;
    private int maxHealth;
    private List<FatigueEffect> fatigueEffects;
    private boolean called;

    public Health(int initHealth, int maxHealth) {
        this.initHealth = initHealth;
        this.maxHealth = maxHealth;
        this.fatigueEffects = new ArrayList<>();
        this.called = false;
    }

    public Health(int health) {
        this(health, health);
    }

    public void exhaust() {
        setInitHealth(0);
        if (!called) {
            fatigueEffects.forEach(FatigueEffect::apply);
            setCalledTrue();
        }
    }

    public int getValue() {
        return initHealth;
    }

    public void refill(int amount) {
        if (getSum(initHealth, amount) > maxHealth) {
            setInitHealth(maxHealth);
        } else {
            setInitHealth(getSum(initHealth, amount));
        }
    }

    public void restore() {
        initHealth = maxHealth;
    }

    public boolean isDead() {
        return getValue() <= 0;
    }

    public void drain(int amount) {
        if (getSub(initHealth, amount) <= 0) {
            exhaust();
        } else {
            setInitHealth(getSub(initHealth, amount));
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setInitHealth(int count) {
        initHealth = count;
    }


    public void onFatigued(FatigueEffect effect) {
        fatigueEffects.add(effect);
    }

    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }

    private void setCalledTrue() {
        called = true;
    }

    private int getSum(int a, int b) {
        return a + b;
    }

    private int getSub(int a, int b) {
        return a - b;
    }
}
