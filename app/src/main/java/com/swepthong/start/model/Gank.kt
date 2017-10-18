package com.swepthong.start.model

/**
 * Created by xiangrui on 17-10-17.
 */
data class Gank(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val images: Array<String>,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
) {
    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    fun hasImg(): Boolean {
        images?.let {
            return !it.isEmpty()
        }
    }

    fun create() = createdAt.substring(0, 10)

}