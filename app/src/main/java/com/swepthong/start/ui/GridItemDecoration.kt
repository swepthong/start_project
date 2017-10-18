package com.swepthong.start.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

/**
 * s
 * Created by dong.he on 2017/8/18.
 */

class GridItemDecoration(builder: Builder) : ItemDecoration() {

    private lateinit var mVerPaint: Paint
    private lateinit var mHorPaint: Paint
    private lateinit var mBuilder: Builder


    init {
        init(builder)
    }

    private fun init(builder: Builder) {
        this.mBuilder = builder
        mVerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mVerPaint.style = Paint.Style.FILL
        mVerPaint.color = builder.verColor
        mHorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mHorPaint.style = Paint.Style.FILL
        mHorPaint.color = builder.horColor
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (mBuilder.isExistHeadView && i == 0)
                continue
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin
            val right = (child.right + params.rightMargin
                    + mBuilder.dividerVerSize)
            val top = child.bottom + params.bottomMargin
            val bottom = top + mBuilder.dividerHorSize
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mHorPaint)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (mBuilder.isExistHeadView && i == 0)
                continue

            val child = parent.getChildAt(i)

            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = left + mBuilder.dividerVerSize

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mVerPaint)
        }
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        // 列数
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {

            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager
                    .spanCount
        }
        return spanCount
    }

    private fun isLastRaw(parent: RecyclerView, pos: Int, spanCount: Int,
                          childCount: Int): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            childCount = childCount - childCount % spanCount
            if (pos >= childCount)
                return true
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                    .orientation
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount

                if (pos >= childCount)
                    return true
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {

                if ((pos + 1) % spanCount == 0) {
                    return true
                }
            }
        }
        return false
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter.itemCount
        var itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        if (mBuilder.isExistHeadView)
            itemPosition -= 1
        if (itemPosition < 0)
            return

        val column = itemPosition % spanCount
        var bottom = 0

        val left = column * mBuilder.dividerVerSize / spanCount
        val right = mBuilder.dividerVerSize - (column + 1) * mBuilder.dividerVerSize / spanCount

        if (!(isLastRaw(parent, itemPosition, spanCount, childCount) && !mBuilder.isShowLastDivider))
            bottom = mBuilder.dividerHorSize

        outRect.set(left, 0, right, bottom)
        marginOffsets(outRect, spanCount, itemPosition)
    }

    private fun marginOffsets(outRect: Rect, spanCount: Int, itemPosition: Int) {
        if (mBuilder.marginRight == 0 && mBuilder.marginLeft == 0)
            return

        val itemShrink = (mBuilder.marginLeft + mBuilder.marginRight) / spanCount
        outRect.left += mBuilder.marginLeft - itemPosition % spanCount * itemShrink

        outRect.right += (itemPosition % spanCount + 1) * itemShrink - mBuilder.marginLeft
    }

    class Builder(private val c: Context) {
        internal var horColor: Int = 0
        internal var verColor: Int = 0
        internal var dividerHorSize: Int = 0
        internal var dividerVerSize: Int = 0
        internal var marginLeft: Int = 0
        internal var marginRight: Int = 0
        internal var isShowLastDivider = false
        internal var isExistHeadView = false

        /**
         * 设置divider的颜色
         *
         * @param color
         * @return
         */
        fun color(@ColorRes color: Int): Builder {
            this.horColor = c.resources.getColor(color)
            this.verColor = c.resources.getColor(color)
            return this
        }

        /**
         * 单独设置横向divider的颜色
         *
         * @param horColor
         * @return
         */
        fun horColor(@ColorRes horColor: Int): Builder {
            this.horColor = c.resources.getColor(horColor)
            return this
        }

        /**
         * 单独设置纵向divider的颜色
         *
         * @param verColor
         * @return
         */
        fun verColor(@ColorRes verColor: Int): Builder {
            this.verColor = c.resources.getColor(verColor)
            return this
        }

        /**
         * 设置divider的宽度
         *
         * @param size
         * @return
         */
        fun size(size: Int): Builder {
            this.dividerHorSize = size
            this.dividerVerSize = size
            return this
        }

        /**
         * 设置横向divider的宽度
         *
         * @param horSize
         * @return
         */
        fun horSize(horSize: Int): Builder {
            this.dividerHorSize = horSize
            return this
        }

        /**
         * 设置纵向divider的宽度
         *
         * @param verSize
         * @return
         */
        fun verSize(verSize: Int): Builder {
            this.dividerVerSize = verSize
            return this
        }

        /**
         * 设置剔除HeadView的RecyclerView左右两边的外间距
         *
         * @param marginLeft
         * @param marginRight
         * @return
         */
        fun margin(marginLeft: Int, marginRight: Int): Builder {
            this.marginLeft = marginLeft
            this.marginRight = marginRight
            return this
        }

        /**
         * 最后一行divider是否需要显示
         *
         * @param isShow
         * @return
         */
        fun showLastDivider(isShow: Boolean): Builder {
            this.isShowLastDivider = isShow
            return this
        }

        /**
         * 是否包含HeadView
         *
         * @param isExistHead
         * @return
         */
        fun isExistHead(isExistHead: Boolean): Builder {
            this.isExistHeadView = isExistHead
            return this
        }

        fun build(): GridItemDecoration {
            return GridItemDecoration(this)
        }

    }
}
