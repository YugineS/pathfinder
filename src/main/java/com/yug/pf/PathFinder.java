package com.yug.pf;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yugine on 29.1.15.
 */
public class PathFinder
{
    private final Comparator pointComparator;

    public PathFinder()
    {
        pointComparator = new Comparator<NavigationPoint>()
        {
            @Override
            public int compare(final NavigationPoint p1, final NavigationPoint p2)
            {
                if (p1 == null || p2 == null)
                {
                    if (p1 == null && p2 == null)
                    {
                        return 0;
                    }
                    else if (p1 == null && p2 != null)
                    {
                        return -1;
                    }
                    else if (p1 != null && p2 == null)
                    {
                        return 1;
                    }
                }
                return (int) (p1.getF() - p2.getF());
            }
        };
    }

    public <T extends NavigationPoint> LinkedList<T> calculatePath(final int x1, final int y1, final int x2, final int y2, final NavigationMap<T> map)
    {
        final LinkedList<NavigationPoint> openList = new LinkedList<NavigationPoint>();
        map.clearNavigationData();
        final NavigationPoint start = map.getPoint(x1, y1);
        final NavigationPoint end = map.getPoint(x2, y2);
        if (start == null || end == null || !end.isWalkable())
        {
            return null;
        }
        openList.add(start);
        final List<NavigationPoint> neighbors = new ArrayList<NavigationPoint>(8);
        while (openList.size() > 0)
        {
            final NavigationPoint currentNavigationPoint = openList.getFirst();
            //the end point is found
            if (end.equals(currentNavigationPoint))
            {
                return backtracePath((T) currentNavigationPoint);
            }
            openList.remove(currentNavigationPoint);
            if (currentNavigationPoint.isProcessed())
            {
                continue;
            }
            currentNavigationPoint.setProcessed(true);
            neighbors.clear();
            final NavigationPoint leftNeighbor = map.getLeftOf(currentNavigationPoint);
            final NavigationPoint rightNeighbor = map.getRightOf(currentNavigationPoint);
            final NavigationPoint topNeighbor = map.getTopOf(currentNavigationPoint);
            final NavigationPoint bottomNeighbor = map.getBottomOf(currentNavigationPoint);
            final NavigationPoint leftTopNeighbor = map.getLeftTopOf(currentNavigationPoint);
            final NavigationPoint leftBottomNeighbor = map.getLeftBottomOf(currentNavigationPoint);
            final NavigationPoint rightTopNeighbor = map.getRightTopOf(currentNavigationPoint);
            final NavigationPoint rightBottomNeighbor = map.getRightBottomOf(currentNavigationPoint);
            CollectionUtils.addIgnoreNull(neighbors, leftNeighbor);
            CollectionUtils.addIgnoreNull(neighbors, rightNeighbor);
            CollectionUtils.addIgnoreNull(neighbors, topNeighbor);
            CollectionUtils.addIgnoreNull(neighbors, bottomNeighbor);
            //exclude diagonal wall crossing
            if (canCrossCorner(leftNeighbor) && canCrossCorner(topNeighbor))
            {
                CollectionUtils.addIgnoreNull(neighbors, leftTopNeighbor);
            }
            if (canCrossCorner(leftNeighbor) && canCrossCorner(bottomNeighbor))
            {
                CollectionUtils.addIgnoreNull(neighbors, leftBottomNeighbor);
            }
            if (canCrossCorner(rightNeighbor) && canCrossCorner(topNeighbor))
            {
                CollectionUtils.addIgnoreNull(neighbors, rightTopNeighbor);
            }
            if (canCrossCorner(rightNeighbor) && canCrossCorner(bottomNeighbor))
            {
                CollectionUtils.addIgnoreNull(neighbors, rightBottomNeighbor);
            }
            for (final NavigationPoint neighbor : neighbors)
            {
                if (neighbor.isWalkable() && !neighbor.isProcessed())
                {
                    final float movementCost = map.getMovementCost(currentNavigationPoint, neighbor);
                    //if the point has parent then it should be in the open list
                    if (neighbor.getParent() != null)
                    {
                        if (neighbor.getG() > movementCost)
                        {
                            neighbor.setG(movementCost);
                            neighbor.setParent(currentNavigationPoint);
                            Collections.sort(openList, pointComparator);
                        }
                    }
                    else
                    {
                        //not in open list
                        neighbor.setG(movementCost);
                        neighbor.setH(calcHeuristicDistance(neighbor, end));
                        neighbor.setParent(currentNavigationPoint);
                        openList.add(neighbor);
                        Collections.sort(openList, pointComparator);
                    }

                }
            }
        }
        //path not found
        return null;
    }

    /**
     * Calculates heuristic distance using Manhattan method.
     */
    private float calcHeuristicDistance(final NavigationPoint navigationPoint, final NavigationPoint endNavigationPoint)
    {
        return (Math.abs(navigationPoint.getX() - endNavigationPoint.getX()) + Math.abs(navigationPoint.getY() - endNavigationPoint.getY())) * 10;
    }

    private <T extends NavigationPoint> LinkedList<T> backtracePath(final T endPoint)
    {
        final LinkedList<T> result = new LinkedList<T>();
        result.push(endPoint);
        T point = endPoint;
        while (point.getParent() != null)
        {
            final T parent = (T) point.getParent();
            result.push(parent);
            point = parent;
        }
        if (result.size() > 0)
        {
            result.pop();
        }
        return result;
    }

    private boolean canCrossCorner(final NavigationPoint navigationPoint)
    {
        return navigationPoint != null && navigationPoint.isWalkable() && navigationPoint.isWalkableCorners();
    }

}
