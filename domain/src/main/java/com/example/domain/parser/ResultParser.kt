package com.example.domain.parser

import com.example.domain.usecases.Result

interface ResultParser {
    operator fun invoke(result: Result?): String?
}