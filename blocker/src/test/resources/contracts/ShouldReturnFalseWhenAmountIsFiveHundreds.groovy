package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "When amount is 400 the service should approve it"
    request {
        method 'POST'
        url '/check'
        body([
                fromUser: "fromUser",
                toUser: "toUser",
                fromAccount: "fromAcc",
                toAccount: "toAcc",
                action: "transfer",
                amount: 500
        ])
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
                isAllowed: false,
                reason: "Transactions divisible by 500 are very suspicious",
                errorMessage: ""
        )
    }
}