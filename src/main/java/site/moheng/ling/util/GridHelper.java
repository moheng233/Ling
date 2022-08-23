package site.moheng.ling.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class GridHelper {
    private List<GridRow> rows = new ArrayList<>();
    private HashMap<GridRow, Integer> yMap = new HashMap<>();
    public HashMap<Object, GridColumn> links = new HashMap<>();

    public GridRow createRow() {
        var row = new GridRow(this);
        rows.add(row);
        return row;
    }

    public void clear() {
        rows.clear();
        yMap.clear();
        links.clear();
    }

    public void setSize(Object object, int width, int height) {
        var column = links.get(object);
        column.width = width;
        column.height = height;
    }

    public int getWidth() {
        var count = 0;
        for (GridRow row : rows) {
            var w = row.getWidth();
            if (w > count) {
                count = w;
            }
        }

        return count;
    }

    public int getHeight() {
        var count = 0;
        for (GridRow row : rows) {
            count += row.getHeight();
        }

        return count;
    }

    public void layout() {
        yMap.clear();
        var count = 0;
        for (GridRow gridRow : rows) {
            gridRow.layout();
            yMap.put(gridRow, count);
            count += gridRow.getHeight();
        }
    }

    public GridColumn getColumn(Object object) {
        return links.get(object);
    }

    public static class GridRow {
        private List<GridColumn> columns = new ArrayList<>();
        private HashMap<GridColumn, Integer> xMap = new HashMap<>();

        private GridHelper grid;

        public GridRow(GridHelper grid) {
            this.grid = grid;
        }

        public GridColumn createColumn(int width, int height) {
            var column = new GridColumn(grid, this, width, height);
            columns.add(column);
            return column;
        }

        public int getWidth() {
            var count = 0;
            for (GridColumn gridColumn : columns) {
                count += gridColumn.width;
            }

            return count;
        }

        public int getHeight() {
            var count = 0;
            for (GridColumn gridColumn : columns) {
                if (gridColumn.height > count) {
                    count = gridColumn.height;
                }
            }

            return count;
        }

        public void layout() {
            xMap.clear();
            var count = 0;
            for (GridColumn gridColumn : columns) {
                gridColumn.layout();
                xMap.put(gridColumn, count);
                count += gridColumn.width;
            }
        }
    }

    public static class GridColumn {
        private GridHelper grid;
        private GridRow row;
        private GridHDir dir = GridHDir.LEFT;
        public int width;
        public int height;

        public GridColumn(GridHelper grid, GridRow row, int width, int height) {
            this.grid = grid;
            this.row = row;
            this.width = width;
            this.height = height;
        }

        public GridColumn setDir(GridHDir dir) {
            this.dir = dir;

            return this;
        }

        public void link(Object object) {
            grid.links.put(object, this);
        }

        public int getX() {
            return row.xMap.get(this);
        }

        public int getY() {
            return grid.yMap.get(row);
        }

        public void layout() {

        }
    }

    public static enum GridHDir {
        LEFT, CENTER, RIGHT
    }

    public static enum GridVDir {
        TOP, CENTER, BUTTOM
    }
}
