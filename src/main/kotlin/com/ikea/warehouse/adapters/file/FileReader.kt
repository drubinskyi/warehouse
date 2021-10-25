package com.ikea.warehouse.adapters.file

import com.ikea.warehouse.ports.FileReader
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
internal class MultipartFileReader: FileReader<MultipartFile> {
    override fun read(source: MultipartFile) = source.inputStream.buffered().reader().use { it.readText() }
}
