package com.ikea.warehouse.adapters.api.exception

import com.ikea.warehouse.adapters.ProductNotPersistedException
import com.ikea.warehouse.adapters.ResourceNotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import com.ikea.warehouse.adapters.BadRequestException

private val log = KotlinLogging.logger { }

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxSizeException(exception: MaxUploadSizeExceededException?): ResponseEntity<UploadResponseMessage> {
        val errorMessage = "Unable to upload, file is too large!"
        log.error(exception) { errorMessage }
        return ResponseEntity<UploadResponseMessage>(UploadResponseMessage(errorMessage), HttpStatus.EXPECTATION_FAILED)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(exception: BadRequestException): ResponseEntity<ErrorResponse> {
        log.error(exception) { exception.message }
        return ResponseEntity(ErrorResponse(exception.message), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(exception: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        log.error(exception) { exception.message }
        return ResponseEntity<ErrorResponse>(ErrorResponse(exception.message), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ProductNotPersistedException::class)
    fun handleIllegalArgumentException(exception: ProductNotPersistedException): ResponseEntity<ErrorResponse> {
        log.error(exception) { exception.message }
        return ResponseEntity<ErrorResponse>(ErrorResponse(exception.message), HttpStatus.BAD_REQUEST)
    }

}

data class UploadResponseMessage(val responseMessage: String)
data class ErrorResponse(val detail: String?)
