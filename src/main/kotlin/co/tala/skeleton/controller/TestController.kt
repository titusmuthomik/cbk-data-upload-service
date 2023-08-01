package co.tala.skeleton.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

/**
 * Testing that the Springboot Skeleton Service starts and returns data when called. Should be deleted for real projects.
 */
@RestController
@RequestMapping("/api/v1/test")
class TestController {

    @ApiOperation(value = "Test GET endpoint", response = Any::class)
    @ApiImplicitParam(
        name = "X-Request-ID",
        value = "Unique UUID value",
        paramType = "Header",
        required = true
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successful response"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 401, message = "Unauthorized"),
            ApiResponse(code = 500, message = "Unexpected internal error")
        ]
    )
    @Secured("ROLE_USER_EXTERNAL")
    @GetMapping
    fun testUserToken(@RequestHeader("X-Request-ID") requestId: String): ResponseEntity<Any> =
        ResponseEntity.ok(
            mapOf(
                "requestId" to requestId,
                "time" to Instant.now(),
                "message" to "This is the Springboot Skeleton Service"
            )
        )

    @ApiOperation(value = "Test POST endpoint", response = Any::class)
    @ApiImplicitParam(
        name = "X-Request-ID",
        value = "Unique UUID value",
        paramType = "Header",
        required = true
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successful response"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 401, message = "Unauthorized"),
            ApiResponse(code = 500, message = "Unexpected internal error")
        ]
    )
    @Secured("ROLE_SERVICE_INGESTION_WRITE")
    @PostMapping
    fun testServiceToken(@RequestHeader("X-Request-ID") requestId: String): ResponseEntity<Any> {
        return ResponseEntity.ok(requestId)
    }
}
