package Plano.GenericsPlano;

public class CoordinateSystem {
    public enum Type {
        CARTESIAN_ABSOLUTE("Coordenadas Cartesianas Absolutas"),
        CARTESIAN_RELATIVE("Coordenadas Cartesianas Relativas"),
        POLAR_ABSOLUTE("Coordenadas Polares Absolutas"),
        POLAR_RELATIVE("Coordenadas Polares Relativas");

        private final String description;

        Type(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public static class PolarCoordinate {
        private double radius;
        private double angle;

        public PolarCoordinate(double radius, double angle) {
            this.radius = radius;
            this.angle = angle;
        }

        public Object getRadius() { return (Object) radius; }
        public Object getAngle() { return (Object) angle; }
    }

    public static PolarCoordinate cartesianToPolar(double x, double y) {
        double radius = Math.sqrt(x * x + y * y);
        double angle = Math.toDegrees(Math.atan2(y, x));
        if (angle < 0) {
            //angle += 360;
        }
        return new PolarCoordinate(radius, angle);
    }

    public static PolarCoordinate cartesianToPolarRelative(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return cartesianToPolar(dx, dy);
    }
}