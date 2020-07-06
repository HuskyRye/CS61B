public class Body {
    public double xxPos;        // Its current x position
    public double yyPos;        // Its current y position
    public double xxVel;        // Its current velocity in the x direction
    public double yyVel;        // Its current velocity in the y direction
    public double mass;         // Its mass
    public String imgFileName;  // Name of the image file depicts the body

    public Body(double xP, double yP, double xV, double yV,
                double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double dx = b.xxPos - xxPos;
        double dy = b.yyPos - yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Body b) {
        double G = 6.67E-11;
        double r = calcDistance(b);
        return G * mass * b.mass / (r * r);
    }

    public double calcForceExertedByX(Body b) {
        double F = calcForceExertedBy(b);
        double dx = b.xxPos - xxPos;
        double r = calcDistance(b);
        return F * dx / r;
    }

    public double calcForceExertedByY(Body b) {
        double F = calcForceExertedBy(b);
        double dy = b.yyPos - yyPos;
        double r = calcDistance(b);
        return F * dy / r;
    }

    public double calcNetForceExertedByX(Body[] allBodys) {
        double FNetX = 0;
        for (Body body : allBodys) {
            if (this.equals(body)) {
                continue;
            }
            FNetX += calcForceExertedByX(body);
        }
        return FNetX;
    }

    public double calcNetForceExertedByY(Body[] allBodys) {
        double FNetY = 0;
        for (Body body : allBodys) {
            if (this.equals(body)) {
                continue;
            }
            FNetY += calcForceExertedByY(body);
        }
        return FNetY;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;
        xxVel = xxVel + dt * aX;
        yyVel = yyVel + dt * aY;
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
