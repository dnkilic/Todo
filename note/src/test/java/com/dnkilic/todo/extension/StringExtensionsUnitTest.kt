package com.dnkilic.todo.extension

import com.dnkilic.todo.data.dateToLong
import com.dnkilic.todo.data.dateToString
import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsUnitTest {

    private val date2019 = "22.08.2019"
    private val date2019_ms = 1566406800000

    @Test
    fun dateToLong_test() {
        val actual = dateToLong(date2019)
        assertEquals(date2019_ms, actual)
    }

    @Test
    fun dateToString_test() {
        val actual = dateToString(date2019_ms)
        assertEquals(date2019, actual)
    }
}