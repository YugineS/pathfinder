package com.yug.pf;

/**
 * Represents the navigation map. It is a wrapper around two dimensional array of the Navigation Points.
 */
public class NavigationMap<T extends NavigationPoint>
{
    private final T[][] map;
    private final int mapWidth;
    private final int mapHeight;
    public final static float ORTHOGONAL_MOVEMENT_COST = 10;
    public final static float DIAGONAL_MOVEMENT_COST = 14;

    public NavigationMap(T[][] map)
    {
        assert (map != null);
        this.map = map;
        this.mapWidth = map.length;
        this.mapHeight = map[0].length;
    }

    public T getPoint(final int x, final int y)
    {
        return map[x][y];
    }

    public void setPoint(final T point, final int x, final int y)
    {
        map[x][y] = point;
    }

    /**
     * Returns the cost of the movement from the point to its neighbor.
     */
    public float getMovementCost(final NavigationPoint navigationPointFrom, final NavigationPoint navigationPointTo)
    {
        final NavigationPoint navigationPointF = getPoint(navigationPointFrom.getX(), navigationPointFrom.getY());
        final NavigationPoint navigationPointT = getPoint(navigationPointTo.getX(), navigationPointTo.getY());
        final int deltaX = Math.abs(navigationPointF.getX() - navigationPointT.getX());
        final int deltaY = Math.abs(navigationPointF.getY() - navigationPointT.getY());
        if (navigationPointF.equals(navigationPointT) || deltaX > 1 || deltaY > 1)
        {
            throw new IllegalArgumentException("The specified points are not neighbors!");
        }
        if(deltaX == 1 && deltaY == 1)
        {
            return DIAGONAL_MOVEMENT_COST + navigationPointFrom.getG();
        }
        else
        {
            return ORTHOGONAL_MOVEMENT_COST + navigationPointFrom.getG();
        }
    }

    /**
     * Returns the left neighbor of the specified point.
     */
    public T getLeftOf(final NavigationPoint navigationPoint)
    {
        return getLeftOf(navigationPoint.getX(), navigationPoint.getY());
    }

    /**
     * Returns the left neighbor of the point specified by x, y coordinates.
     */
    public T getLeftOf(final int x, final int y)
    {
        if (!isInBounds(x, y) || x - 1 < 0)
        {
            return null;
        }
        return map[x - 1][y];
    }

    /**
     * Returns the left neighbor of the specified point.
     */
    public T getRightOf(final NavigationPoint navigationPoint)
    {
        return getRightOf(navigationPoint.getX(), navigationPoint.getY());
    }

    /**
     * Returns the right neighbor of the point specified by x, y coordinates.
     */
    public T getRightOf(final int x, final int y)
    {
        if (!isInBounds(x, y) || x + 1 >= mapWidth)
        {
            return null;
        }
        return map[x + 1][y];
    }

    /**
     * Returns the top neighbor of the specified point.
     */
    public T getTopOf(final NavigationPoint navigationPoint)
    {
        return getTopOf(navigationPoint.getX(), navigationPoint.getY());
    }

    /**
     * Returns the top neighbor of the point specified by x, y coordinates.
     */
    public T getTopOf(final int x, final int y)
    {
        if (!isInBounds(x, y) || y + 1 >= mapHeight)
        {
            return null;
        }
        return map[x][y + 1];
    }

    /**
     * Returns the bottom neighbor of the specified point.
     */
    public T getBottomOf(final NavigationPoint navigationPoint)
    {
        return getBottomOf(navigationPoint.getX(), navigationPoint.getY());
    }

    /**
     * Returns the bottom neighbor of the point specified by x, y coordinates.
     */
    public T getBottomOf(final int x, final int y)
    {
        if (!isInBounds(x, y) || y - 1 < 0)
        {
            return null;
        }
        return map[x][y - 1];
    }

    /**
     * Returns the left top neighbor of the specified point.
     */
    public T getLeftTopOf(final NavigationPoint navigationPoint)
    {
        return getLeftTopOf(navigationPoint.getX(), navigationPoint.getY());
    }

    /**
     * Returns the left top neighbor of the point specified by x, y coordinates.
     */
    public T getLeftTopOf(final int x, final int y)
    {
        if (!isInBounds(x, y) || !isInBounds(x - 1, y + 1))
        {
            return null;
        }
        return map[x - 1][y + 1];
    }

    /**
     * Returns the left bottom neighbor of the specified point.
     */
    public T getLeftBottomOf(final NavigationPoint navigationPoint)
    {
       return getLeftBottomOf(navigationPoint.getX(), navigationPoint.getY());
    }

    /**
     * Returns the left bottom neighbor of the point specified by x, y coordinates.
     */
    public T getLeftBottomOf(final int x, final int y)
    {
        if (!isInBounds(x, y) || !isInBounds(x - 1, y - 1))
        {
            return null;
        }
        return map[x - 1][y - 1];
    }

    /**
     * Returns the right top neighbor of the specified point.
     */
    public T getRightTopOf(final NavigationPoint navigationPoint)
    {
        return getRightTopOf(navigationPoint.getX(), navigationPoint.getY());
    }

    /**
     * Returns the right top neighbor of the point specified by x, y coordinates.
     */
    public T getRightTopOf(final int x, final int y)
    {
        if (!isInBounds(x, y) || !isInBounds(x + 1, y + 1))
        {
            return null;
        }
        return map[x + 1][y + 1];
    }

    /**
     * Returns the right bottom neighbor of the specified point.
     */
    public T getRightBottomOf(final NavigationPoint navigationPoint)
    {
        return getRightBottomOf(navigationPoint.getX(), navigationPoint.getY());
    }

    /**
     * Returns the right bottom neighbor of the point specified by x, y coordinates.
     */
    public T getRightBottomOf(final int x, final int y)
    {
        if (!isInBounds(x, y) || !isInBounds(x + 1, y - 1))
        {
            return null;
        }
        return map[x + 1][y - 1];
    }

    public boolean isInBounds(final int x, final int y)
    {
        return x > -1 && x < mapWidth && y > -1 && y < mapHeight;
    }

    /**
     * Clears navigation data from the all points.
     */
    public void clearNavigationData()
    {
        for (int x = 0; x < mapWidth; x++)
        {
            for (int y = 0; y < mapHeight; y++)
            {
                T point = map[x][y];
                if (point != null)
                {
                    point.clearNavigationData();
                }
            }
        }
    }

    public int getMapWidth()
    {
        return mapWidth;
    }

    public int getMapHeight()
    {
        return mapHeight;
    }
}
