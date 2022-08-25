package site.moheng.ling.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.util.math.MathHelper;

public final class GridHelper {
    private List<GridRow> rows = new ArrayList<>();
    private HashMap<GridRow, Integer> yMap = new HashMap<>();
    public HashMap<Object, GridCell> links = new HashMap<>();

    private int width = 0;
    private int height = 0;

    private int miniWidth = 0;
    private int miniHeight = 0;

    public GridHelper(int miniWidth, int miniHeight) {
        this.miniWidth = miniWidth;
        this.miniHeight = miniHeight;
    }

    public GridHelper() {
    }

    public GridRow createRow() {
        var row = new GridRow(this);
        rows.add(row);
        return row;
    }

    public int getMiniWidth() {
        return miniWidth;
    }

    public int getMiniHeight() {
        return miniHeight;
    }

    /**
     * 向下方创建一个几行几列的表格
     * 
     * @param row
     * @param column
     */
    public void createTable(int row, int column) {
        for (int i = 0; i < row; i++) {
            var rows = createRow();
            for (int j = 0; j < column; j++) {
                rows.createColumn(0);
            }
        }
    }

    public void clear() {
        rows.clear();
        yMap.clear();
        links.clear();
    }

    public void setContentSize(Object object, int width, int height) {
        var column = links.get(object);
        column.contentWidth = width;
        column.contentHeight = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void layout() {
        var count = 0;
        for (GridRow gridRow : rows) {
            gridRow.rowHeight = gridRow.getContentHeight();
            gridRow.rowY = count;
            count += gridRow.rowHeight;
        }
        height = Math.max(miniHeight, count);
        count = 0;
        for (GridRow row : rows) {
            count = Math.max(count, row.getContentWidth());
        }
        width = Math.max(miniWidth, count);
        for (GridRow row : rows) {
            row.layout();
        }
    }

    public GridCell getCell(Object object) {
        return links.get(object);
    }

    public GridCell getColumn(int row, int column) {
        return rows.get(row).cells.get(column);
    }

    public static class GridRow {
        private List<GridCell> cells = new ArrayList<>();

        private GridHelper grid;

        public int rowY;
        public int rowHeight;

        public GridRow(GridHelper grid) {
            this.grid = grid;
        }

        public GridCell createColumn(int flex) {
            var column = new GridCell(grid, this, flex);
            cells.add(column);
            return column;
        }

        public GridCell createColumn(int width, int height) {
            var cell = createColumn(0);
            cell.contentWidth = width;
            cell.contentHeight = height;

            return cell;
        }

        public int getContentWidth() {
            var count = 0;
            for (GridCell gridColumn : cells) {
                count += gridColumn.contentWidth;
            }

            return count;
        }

        /**
         * 获得当前列中最高的单元格给人高度
         * 
         * @return
         */
        public int getContentHeight() {
            var count = 0;
            for (GridCell gridColumn : cells) {
                if (gridColumn.contentHeight > count) {
                    count = gridColumn.contentHeight;
                }
            }

            return count;
        }

        public void layout() {
            /**
             * 当前列的所有内容宽度
             */
            var contentWidth = grid.width;
            var takeWidth = 0;
            var countFlex = 0;
            for (GridCell cell : cells) {
                if (cell.flex == 0) {
                    cell.cellWidth = cell.contentWidth;
                    takeWidth += cell.contentWidth;
                } else if (cell.flex > 0) {
                    countFlex += cell.flex;
                }
            }

            if (countFlex > 0) {
                var noTakeWidth = contentWidth - takeWidth;
                var flexWidth = noTakeWidth / countFlex;
                for (GridCell cell : cells) {
                    if (cell.flex > 0) {
                        cell.cellWidth = flexWidth * cell.flex;
                    }
                }
            }

            var count = 0;
            for (GridCell cell : cells) {
                cell.cellX = count;
                count += cell.cellWidth;
            }

            for (GridCell cell : cells) {
                cell.layout();
            }
        }
    }

    public static class GridCell {
        private GridHelper grid;
        private GridRow row;
        public GridHDir hDir = GridHDir.LEFT;
        public GridVDir vDir = GridVDir.TOP;
        // 内容宽高
        public int contentWidth = 0;
        public int contentHeight = 0;

        // 当前单元格占整个行空间的比例 为0为适配内容宽度
        public int flex = 0;

        public int contentX = 0;
        public int contentY = 0;

        public int cellWidth = 0;

        public int cellX;

        public GridCell(GridHelper grid, GridRow row, int flex) {
            this.grid = grid;
            this.row = row;
            this.flex = flex;
        }

        public void link(Object object) {
            grid.links.put(object, this);
        }

        public int getX() {
            return contentX;
        }

        public int getY() {
            return contentY;
        }

        public GridCell setContentSize(int width, int height) {
            contentWidth = width;
            contentHeight = height;
            return this;
        }

        public GridCell setDir(GridHDir hDir, GridVDir vDir) {
            this.hDir = hDir;
            this.vDir = vDir;

            return this;
        }

        public GridCell setFlex(int flex) {
            this.flex = flex;
            return this;
        }

        public MathRect getRect() {
            return new MathRect(contentX, contentY, contentWidth, contentHeight);
        }

        public void layout() {
            switch (hDir) {
                case LEFT:
                    contentX = cellX;
                    break;
                case CENTER:
                    contentX = MathHelper.clamp(cellX + (cellWidth - contentWidth) / 2, 0, cellX + cellWidth);
                    break;
                case RIGHT:
                    contentX = cellX + cellWidth - contentWidth;
                    break;
            }
            switch (vDir) {
                case TOP:
                    contentY = row.rowY;
                    break;
                case CENTER:
                    contentY = row.rowY + (row.rowHeight - contentHeight) / 2;
                    break;
                case BUTTOM:
                    contentY = row.rowY + row.rowHeight - contentHeight;
                    break;
            }
        }
    }

    public static enum GridHDir {
        LEFT, CENTER, RIGHT
    }

    public static enum GridVDir {
        TOP, CENTER, BUTTOM
    }
}
