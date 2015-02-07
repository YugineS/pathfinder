package com.yug.pf;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by yugine on 1.2.15.
 */
public class NavigationMapTest
{
    private NavigationMap<NavigationPoint> navigationMap;

    @Before
    public void setUp()
    {
        navigationMap = createNavigationMap();
    }

    @Test
    public void getMapWidth()
    {
        assertEquals(4, navigationMap.getMapWidth());
    }

    @Test
    public void getMapHeight()
    {
        assertEquals(4, navigationMap.getMapHeight());
    }

    @Test
    public void getPoint()
    {
        assertEquals(true, navigationMap.getPoint(0, 0).isWalkable());
        assertEquals(false, navigationMap.getPoint(1, 0).isWalkable());
    }

    @Test
    public void getMovementCost()
    {
        assertThat("Calculated cost should be equal to ORTHOGONAL_MOVEMENT_COST + current point G", navigationMap.getMovementCost(navigationMap.getPoint(0, 0), navigationMap.getPoint(0, 1)), is(NavigationMap.ORTHOGONAL_MOVEMENT_COST));
        assertThat("Calculated cost should be equal to ORTHOGONAL_MOVEMENT_COST + current point G", navigationMap.getMovementCost(navigationMap.getPoint(0, 2), navigationMap.getPoint(0, 3)), is(NavigationMap.ORTHOGONAL_MOVEMENT_COST+2));
        assertThat("Calculated cost should be equal to DIAGONAL_COST + current point G", navigationMap.getMovementCost(navigationMap.getPoint(2, 0), navigationMap.getPoint(3, 1)), is(NavigationMap.DIAGONAL_MOVEMENT_COST));
        assertThat("Calculated cost should be equal to DIAGONAL_COST + current point G", navigationMap.getMovementCost(navigationMap.getPoint(2, 2), navigationMap.getPoint(3, 3)), is(NavigationMap.DIAGONAL_MOVEMENT_COST+1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMovementCostNotNeighbor()
    {
        navigationMap.getMovementCost(new NavigationPoint(0, 0), new NavigationPoint(2, 0));
    }

    @Test
    public void getLeftOf()
    {
        assertThat("Left neighbor should be null", navigationMap.getLeftOf(0, 0), is(nullValue()));
        assertThat("Left neighbor X is wrong", navigationMap.getLeftOf(1, 2).getX(), is(0));
        assertThat("Left neighbor Y is wrong", navigationMap.getLeftOf(1, 2).getY(), is(2));
        final NavigationPoint expectedNavigationPoint = navigationMap.getPoint(0, 2);
        assertThat("Left point is not as expected", navigationMap.getLeftOf(new NavigationPoint(1, 2)), is(expectedNavigationPoint));
    }

    public static NavigationMap<NavigationPoint> createNavigationMap()
    {
        NavigationMap<NavigationPoint> result = null;
        final int[][] map = new int[][]
        {
            {0, 0, 2, 0},
            {-1, -1, 0, 0},
            {0, -1, 1, 0},
            {0, 0, 0, 0}
        };
        final int mapWidth = map.length;
        final int mapHeight = map[0].length;
        final NavigationPoint[][] navigationPoints = new NavigationPoint[mapWidth][mapHeight];
        for (int x = 0; x<mapWidth; x++)
        {
            for(int y = 0; y<mapHeight; y++)
            {
                final NavigationPoint navigationPoint = new NavigationPoint(x, y);
                navigationPoint.setG(map[x][y]);
                if (map[x][y] != -1)
                {
                    navigationPoint.setWalkable(true);
                }
                navigationPoints[x][y] = navigationPoint;
            }
        }
        return new NavigationMap<NavigationPoint>(navigationPoints);
    }
}
