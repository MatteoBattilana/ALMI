package calculator;

public class Calculator
{
    public enum Operation
    {
        SUM,
        SUB,
        DIV,
        MUL
    }

    public double sqrt(double v1) throws ArithmeticException
    {
        if(v1 >= 0)
        {
            return Math.sqrt(v1);
        }
        throw new ArithmeticException("My bad! Cannot work with imaginary values!");
    }

    public double execute(double v1, Operation operation, double v2)
    {
        switch(operation)
        {
            case MUL:
                return v1 * v2;
            case SUB:
                return v1 - v2;
            case DIV:
                return v1 / v2;
            case SUM:
            default:
                return v1 + v2;
        }
    }
}
