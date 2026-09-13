package com.burime.calculator;

public class AreaClassifier {
    private double r;

    public AreaClassifier(double r) {
        setR(r);
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        if (r <= 0.0) {
            throw new IllegalArgumentException("Радиус R должен быть строго больше нуля. Передано: " + r);
        }
        this.r = r;
    }

    public int testPoint(double x, double y) {
        double distanceSquared = x * x + y * y;
        double rSquared = r * r;

        // 1. Проверка выхода за пределы окружности (Область 3)
        if (distanceSquared > rSquared) {
            return 3;
        }

        // 2. Проверка попадания в закрашенную область (Область 1)
        // Ветка А: III четверть (x <= 0 и y <= 0)
        boolean inThirdQuadrant = (x <= 0.0 && y <= 0.0);

        // Ветка Б: I четверть выше или на параболе y >= (x - 1)^2
        double parabolaY = (x - 1.0) * (x - 1.0);
        boolean inFirstQuadrantAboveParabola = (x >= 0.0 && y >= parabolaY);

        if (inThirdQuadrant || inFirstQuadrantAboveParabola) {
            return 1;
        }

        // 3. Точка внутри окружности, но не в закрашенной зоне (Область 2)
        return 2;
    }
}