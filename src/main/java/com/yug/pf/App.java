package com.yug.pf;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final int[][] map = new int[][]
    {
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0,   0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        {-1 ,  0 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 },
        {-1 ,  0 , -1 ,  0 , -1 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        {-1 ,  0 ,  0 ,  0 , -1 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        {-1 , -1 , -1 ,  0 , -1 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 , -1 ,  0 , -1 ,  0 , -1 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 , -1 ,  0 , -1 ,  0 , -1 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 , -1 ,  0 ,  0 ,  0 , -1 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 , -1 , -1 , -1 , -1 , -1 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 },
        { 0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  0 }
    };

    public static void main( String[] args )
    {
        final int mapWidth = map.length;
        final int mapHeight = map[0].length;
        final NavigationPoint[][] pointsMap = new NavigationPoint[mapWidth][mapHeight];

        for (int x = 0; x<mapWidth; x++)
        {
            for (int y=0; y<mapHeight; y++)
            {
                final NavigationPoint navigationPoint = new NavigationPoint(x, y);
                navigationPoint.setG(map[x][y]);
                navigationPoint.setWalkable(map[x][y]>-1);
                pointsMap[x][y] = navigationPoint;
            }
        }
        final NavigationMap navigationMap = new NavigationMap(pointsMap);
        final PathFinder pathFinder = new PathFinder();
        final long start = System.nanoTime();
        final List<NavigationPoint> path = pathFinder.calculatePath(0, 0, 0, 1, navigationMap);
        final long end = System.nanoTime();
        System.out.println("Run time: "+(end-start));
        if(path!=null)
        {
            int i = 1;
            for (NavigationPoint navigationPoint : path)
            {
                System.out.println("Point #"+i+" : "+ navigationPoint.getX()+", "+ navigationPoint.getY());
                i++;
            }
            for(int y = mapHeight-1; y>-1; y--)
            {
                System.out.println("");
                for(int x=0; x<mapWidth; x++)
                {
                    String val = isOnPath(x, y, path)?" * ": (map[x][y]<0?map[x][y]+" ": " "+map[x][y]+" ");
                    System.out.print(val);
                }
            }

        }
        else
        {
            System.out.println("Path not found");
        }
    }

    private static boolean isOnPath(int x, int y, List<NavigationPoint> path)
    {
        for(NavigationPoint navigationPoint : path)
        {
            if (navigationPoint.getX() == x && navigationPoint.getY()==y)
            {
                return true;
            }
        }
        return false;
    }
}
