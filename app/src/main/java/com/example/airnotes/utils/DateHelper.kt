package com.example.airnotes.utils

import java.time.LocalDateTime

    fun isToday (dateToCheck: LocalDateTime) : Boolean {
        val now = LocalDateTime.now()
        return dateToCheck.year == now.year
                && dateToCheck.month == now.month
                && dateToCheck.dayOfMonth == now.dayOfMonth
    }

    fun isYesterday (dateToCheck: LocalDateTime) : Boolean {
        val now = LocalDateTime.now()
        return dateToCheck.year == now.year
                && dateToCheck.month == now.month
                && dateToCheck.dayOfMonth == now.dayOfMonth - 1
    }
