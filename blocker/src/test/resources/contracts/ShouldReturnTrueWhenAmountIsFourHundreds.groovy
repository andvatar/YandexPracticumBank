package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "When amount is 400 the service should approve it"
    request {
        method 'GET'
        url '/check/PUT/400'
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
                isAllowed: true,
                reason: "",
                errorMessage: ""
        )
    }
}