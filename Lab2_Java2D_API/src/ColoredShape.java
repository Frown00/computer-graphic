import java.awt.*;

public class ColoredShape {

    private Color strokeColor;
    private Shape shape;

    public ColoredShape(Color strokeColor, Rectangle shape)
    {
        this.strokeColor = strokeColor;
        this.shape = shape;
    }

    public Color getStrokeColor()
    {
        return this.strokeColor;
    }

    public void setStrokeColor(Color strokeColor)
    {
        this.strokeColor = strokeColor;
    }

    public Shape getShape()
    {
        return shape;
    }
}
