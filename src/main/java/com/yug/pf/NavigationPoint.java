package com.yug.pf;

/**
 * Represents the point on the NavigationMap.
 */
public class NavigationPoint
{
    private int x;
    private int y;
    private boolean walkable = false;
    private boolean walkableCorners = true;
    /**
     * The point which the current point was reached from.
     */
    private NavigationPoint parent;
    /**
     * The point walk through penalty. Adds to the movement cost of the next point.
     */
    private float G = 0;
    private float walkingResistance = 0;
    /**
     * The heuristic distance prom this point to the end point of the path. Calculates using heuristic function.
     */
    private float H = 0;
    private boolean processed = false;

    public NavigationPoint()
    {
    }

    public NavigationPoint(final int x, final int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the weight of the point. Used to determine the point with minimal weight while path finding process.
     */
    public float getF()
    {
        return G + H;
    }

    public void clearNavigationData()
    {
        this.G = 0;
        this.H = 0;
        this.processed = false;
        this.parent = null;
    }

    public int getX()
    {
        return x;
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(final int y)
    {
        this.y = y;
    }

    public NavigationPoint getParent()
    {
        return parent;
    }

    public void setParent(final NavigationPoint parent)
    {
        this.parent = parent;
    }

    public float getG()
    {
        return G;
    }

    public void setG(final float g)
    {
        G = g;
    }

    public float getH()
    {
        return H;
    }

    public void setH(final float h)
    {
        H = h;
    }

    public boolean isProcessed()
    {
        return processed;
    }

    public void setProcessed(final boolean processed)
    {
        this.processed = processed;
    }

    public boolean isWalkable()
    {
        return walkable;
    }

    public void setWalkable(final boolean walkable)
    {
        this.walkable = walkable;
    }

    public boolean isWalkableCorners()
    {
        return walkableCorners;
    }

    public void setWalkableCorners(final boolean walkableCorners)
    {
        this.walkableCorners = walkableCorners;
    }

    public float getWalkingResistance()
    {
        return walkingResistance;
    }

    public void setWalkingResistance(final float walkingResistance)
    {
        this.walkingResistance = walkingResistance;
    }

    @Override
    public boolean equals(final Object obj)
    {
        return obj != null && (obj instanceof NavigationPoint) && ((NavigationPoint) obj).getX() == this.x && ((NavigationPoint) obj).getY() == this.y;
    }
}
