package com.ikea.warehouse.ports

import org.springframework.core.io.InputStreamSource

interface FileReader<T: InputStreamSource> {
    fun read(source: T): String
}
