package com.ikea.warehouse.adapters.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("articles")
internal class ArticleEndpoint(private val facade: ArticleFacade) {
    @PostMapping
    fun uploadInventory(@RequestParam("file") file: MultipartFile) = facade.upsert(file)
}
