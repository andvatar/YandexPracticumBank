package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "When amount is 400 the service should approve it"
    request {
        method 'POST'
        url '/check'
        body([
                fromUser: "fromUser",
                toUser: "",
                fromAccount: "fromAcc",
                toAccount: "",
                action: "PUT",
                amount: 400
        ])
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