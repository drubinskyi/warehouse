package com.ikea.warehouse.adapters.api

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("products")
internal class ProductEndpoint(private val facade: ProductFacade) {
    @PostMapping
    fun uploadProducts(@RequestParam("file") file: MultipartFile) = facade.saveAll(file)

    @GetMapping
    fun getProducts(): List<ProductsQuantityResponse> = facade.getAllWithQuantity()

    @PutMapping("{id}")
    fun sellProduct(@PathVariable id: String, @RequestParam(required = false, defaultValue = "1") quantity: Int) {
        facade.sellProduct(id, quantity)
    }
}
